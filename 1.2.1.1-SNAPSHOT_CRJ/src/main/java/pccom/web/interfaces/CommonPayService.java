/*
 * Copyright (c) 2014  . All Rights Reserved.
 */
package pccom.web.interfaces;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.ownercancelleaseorder.dto.OwnerCancelLeaseOrderDto;
import com.room1000.suborder.ownercancelleaseorder.service.IOwnerCancelLeaseOrderService;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;
import com.ycdc.core.plugin.sms.SmsSendContants;
import com.ycdc.core.plugin.sms.SmsSendMessage;

import pccom.common.util.Batch;
import pccom.common.util.SpringHelper;
import pccom.common.util.StringHelper;
import pccom.web.server.BaseService;
import pccom.web.server.house.rankhouse.RankHouseService;

@Service("CommonPayService")
public class CommonPayService extends BaseService
{

	@Autowired
	private ICancelLeaseOrderService cancelLeaseService;

	@Autowired
	private IOwnerCancelLeaseOrderService ownerCancelLeaseOrderService;

	@Autowired
	private IWorkOrderService workorderservice;

	@Autowired
	private Financial financial;

	/**
	 * 退租支付成功返回接口
	 * 
	 * @param request
	 * @param rankHouse
	 * @return
	 */
	public Object commonPay(final HttpServletRequest request, final RankHouse rankHouse)
	{
		// final JSONObject result =
		// YCBizMsgCrypt.decryptJsonObjectParams(request,Constants.TOKEN);//解密外部传值
		@SuppressWarnings("unchecked")
		Object obj = db.doInTransaction(new Batch()
		{
			@Override
			public Object execute() throws Exception
			{
				// relevance_type ==2 relevance_id=为财务明细id financial_receivable_tab 中的id
				// relevance_type ==1 relevance_id为服务订单编号 order_tab order_code订单编号
				String relevance_type = req.getAjaxValue(request, "relevance_type");
				String relevance_id = req.getAjaxValue(request, "relevance_id");
				switch (relevance_type)
				{
				case "1":
					// 释放房源
					return releaseHouse(this, request, rankHouse, relevance_id);
				case "2":
					return updateRankAgreementStatus(relevance_id, this);
				default:
					return 0;
				}
			}
		});
		if (obj == null)
		{
			return 0;
		}
		return (int) obj;
	}

	public int releaseHouse(Batch batch, final HttpServletRequest request, final RankHouse rankHouse, String relevance_id) throws Exception
	{
		WorkOrderDto workorderdto = workorderservice.getWorkOrderDtoByCode(relevance_id);
		if (null == workorderdto)
		{
			workorderdto = workorderservice.getWorkOrderDetailById(Long.valueOf(relevance_id));
		}

		Map<String, String> param = new HashMap<String, String>();
		switch (workorderdto.getType())
		{
		case WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER:
			String sql = "select count(1) from financial_bill_tab a where a.resource_id = ? and a.resource_type = 3 and a.state = 1";
			logger.info("查询账单是否支付完成 ==== " + StringHelper.getSql(sql, new Object[]{relevance_id}));
			int a = db.queryForInt(sql, new Object[]{relevance_id});
			logger.info("账单是否支付完成结果（大于0是未完成） ==== " + StringHelper.getSql(sql, new Object[]{relevance_id}));
			if (a > 0) 
			{
				// 存在未支付账单
				return 1;
			} else 
			{
				if (workorderdto.getSubOrderState().equals(SubOrderStateDef.PAY) || workorderdto.getSubOrderState().equals(SubOrderStateDef.REFUND))
				{
					CancelLeaseOrderDto dto = cancelLeaseService.getSubOrderByCode(workorderdto.getCode());
					int info = updateAgreementStatus(batch, dto.getRentalLeaseOrderId().toString());// 更改合约状态置为失效
					logger.info("info ===== " + info);
					if (info != 1)
					{
						return 1;
					}
					param.put("agreement_id", String.valueOf(dto.getRentalLeaseOrderId()));
					// 调用租赁房源审批失败接口
					info = financial.repealRentHouse(param, batch);
					if (info != 1)
					{
						batch.rollBack();
						return 0;
					}
					try
					{
						// 新退租单，支付完成调用
						cancelLeaseService.payNewWithTrans(workorderdto.getCode(), -2L, null);
					} catch (Exception e)
					{
						batch.rollBack();
						return 0;
					}
				}
			}
			break;
		case WorkOrderTypeDef.CANCEL_LEASE_ORDER:
			CancelLeaseOrderDto cancelLeaseOrderDto = cancelLeaseService.getSubOrderByCode(workorderdto.getCode());
			// 判断当前多条明细是否全部支付完成
			int res = checkFinancial(batch, cancelLeaseOrderDto.getRentalLeaseOrderId().toString());
			if (res > 0)
			{
				return 1;
			}
			// 更新收房合约合约状态--修改房源状态，包括出租房源状态
			res = updateAgreementStatus(batch, cancelLeaseOrderDto.getRentalLeaseOrderId().toString());// 更改合约状态
			if (res != 1)
			{
				return 1;
			}
			param.put("agreement_id", cancelLeaseOrderDto.getRentalLeaseOrderId().toString());
			// 调用租赁房源审批失败接口
			res = financial.repealRentHouse(param, batch);
			if (res != 1)
			{
				batch.rollBack();
				return 0;
			}
			try
			{
				// 老退租单，支付完成调用
				cancelLeaseService.payNewWithTrans(workorderdto.getCode(), -2L, null);
			} catch (Exception e)
			{
				batch.rollBack();
				return 0;
			}
			break;
		case WorkOrderTypeDef.REPAIR_ORDER:
			endOrder(workorderdto);
			break;
		case WorkOrderTypeDef.CLEANING_ORDER:
			endOrder(workorderdto);
			break;
		case WorkOrderTypeDef.OWNER_CANCEL_LEASE_ORDER:
			OwnerCancelLeaseOrderDto order = ownerCancelLeaseOrderService.getSubOrderByCode(workorderdto.getCode());
			// 判断当前多条明细是否全部支付完成
			res = checkFinancial(batch, order.getWorkOrderId().toString());
			if (res > 0)
			{
				return 1;
			}
			// 更新收房合约合约状态--修改房源状态，包括出租房源状态
			res = updateAgreementStatus(batch, order.getTakeHouseOrderId().toString());// 更改合约状态
			// 财务信息全部置为失效
			param.put("agreement_id", order.getTakeHouseOrderId().toString());
			int ress = financial.repealHouse(param, batch);
			if (ress != 1)
			{
				batch.rollBack();
				return 0;
			}
			try
			{
				endOrder(workorderdto);
			} catch (Exception e)
			{
				batch.rollBack();
				return 0;
			}
			break;
		default:
			break;
		}

		return 1;
	}

	/**
	 * //结束订单流程 @param{ WorkOrderDto};
	 * 
	 * @return 1表示完成
	 */
	private int endOrder(WorkOrderDto workorderdto)
	{
		IWorkOrderService workOrderService = (IWorkOrderService) SpringHelper.getBean("WorkOrderService");
		workOrderService.payWithTrans(workorderdto.getId(), -2L, null);
		return 1;
	}

	/**
	 * 更新合约状态置为失效
	 * 
	 * @param{Batch agreeId合约Id};
	 * @return 1表示完成 其它表示失败
	 */
	private int updateAgreementStatus(Batch batch, String agreeId) throws Exception
	{
		String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", " status = ? ");
		int res = batch.update(sql, new Object[]
		{ "3", agreeId });
		return res;
	}

	/**
	 * 查询当前财务中是否包含财务数据或者收入数据未完成支付
	 * 
	 * @param {Batch
	 *          ，agreeId（合约Id）}
	 * @return 0 表示未存在 1表示存在未销账财务数据
	 */
	private int checkFinancial(Batch batch, String work_id)
	{
		int res = 0;
		// 查询系统中的支出数据
		String sql = "SELECT count(1) FROM financial_payable_tab a WHERE a.`secondary`=? AND a.`secondary_type`=2 AND a.`isdelete`=1 AND a.`status`=0";
		res = batch.queryForInt(sql, new Object[]
		{ work_id });
		if (res > 0)
		{
			return 1;
		}
		// 查询系统中的收入数据
		sql = "SELECT count(1) FROM financial_receivable_tab a WHERE a.`secondary`=? AND a.`secondary_type`=2 AND a.`isdelete`=1 AND a.`status`=0";
		res = batch.queryForInt(sql, new Object[]
		{ work_id });
		return res;
	}

	/**
	 * 更新房源合约状态
	 * 
	 * @param param
	 * @param obj
	 * @return
	 */
	public int updateRankAgreementStatus(String relevance_id, Batch batch)
	{
		try
		{
			String sql = getSql("financial.receivable.queryCategory");
			logger.debug(str.getSql(sql, new Object[]
			{ relevance_id }));
			Map mp = batch.queryForMap(sql, new Object[]
			{ relevance_id });
			String agreementId = StringHelper.get(mp, "secondary");
			String category = StringHelper.get(mp, "category");
			if ("".equals(agreementId))
			{
				return -3;
			}
			sql = getSql("basehouse.rankHosue.checkRankAgreementStatus");
			logger.debug(str.getSql(sql, new Object[]
			{ agreementId, 12 }));
			int res = batch.queryForInt(sql, new Object[]
			{ agreementId, 12 });
			if (res == 1 && "5".equals(category))
			{
				sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", "status = 2");
				logger.debug(str.getSql(sql, new Object[]
				{ agreementId }));
				batch.update(sql, new Object[]
				{ agreementId });
				sql = getSql("basehouse.rankHosue.getHouseIdByAgreementId");
				Map resultMp = batch.queryForMap(sql, new Object[]
				{ agreementId });
				String operId = StringHelper.get(resultMp, "operid");
				String rank_id = StringHelper.get(resultMp, "house_id");
				String usSql = getSql("basehouse.rankHosue.updateStatus");
				logger.debug(str.getSql(usSql, new Object[]
				{ String.valueOf(RankHouseService.RANT_OUT), operId, rank_id }));
				batch.update(usSql, new Object[]
				{ String.valueOf(RankHouseService.RANT_OUT), operId, rank_id });
				// 全民经纪人修改推荐关系
				sql = getSql("commonPay.getRecommend");// SELECT * FROM yc_recommend_info a WHERE a.state=1 AND a.mobile='13376051372'
				Map<String, String> recommend = batch.queryForMap(sql, new Object[]
				{ str.get(resultMp, "user_mobile") });
				if (!recommend.isEmpty())
				{
					sql = getSql("commonPay.updateInfo");// "UPDATE yc_recommend_info SET state=2,agreement_id=? WHERE id=?";
					batch.update(sql, new Object[]
					{ agreementId, str.get(recommend, "id") });
					// 查询当前推荐人手机号
					sql = getSql("basehouse.houseInfo.getUserNameById");
					Map<String, String> userMap = db.queryForMap(sql, new Object[]
					{ str.get(recommend, "open_id") });

					sql = getSql("commonPay.updateRelation");
					batch.update(sql, new Object[]
					{ str.get(recommend, "id") });

					// 发送短信至推荐人
					smsSend(str.get(resultMp, "user_mobile"), str.get(userMap, "mobile"));
				}
				return 1;
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2; // 改合约状态已经改变
	}

	// sms信息发送
	private void smsSend(String user_mobile, String mobile)
	{
		Map<String, String> msgMap = new HashMap<String, String>();
		msgMap.put("user_name", user_mobile);
		SmsSendMessage.smsSendMessage(mobile, msgMap, SmsSendContants.SIGN_SUCCESS);
	}
}
