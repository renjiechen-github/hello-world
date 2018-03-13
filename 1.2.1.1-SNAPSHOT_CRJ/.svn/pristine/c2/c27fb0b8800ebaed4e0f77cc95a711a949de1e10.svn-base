package pccom.web.interfaces;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pccom.common.util.StringHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.flow.base.TaskException;
import pccom.web.server.house.rankhouse.RankHouseService;
/**
 * 施工对外接口
 * @author 刘飞
 *
 */
@Service("rankHouse")
public class RankHouse extends Base {

	@Autowired
	public RankHouseService rankHouseService;
	
	@Autowired
	public Financial financial;
	
	/**
	 * 管家上门后调用接口
	 * @author 刘飞
	 * @param{
	 *  houseid// yc_house_tab id 收房房源id
	 *	oper     // 当前操作人
	 *	rankid  // 租赁yc_houserank_tab id房源id
	 *  agreeid // 租赁yc_agreement_tab id合约 id
	 *  servicetime // 租赁work_order  预约时间
	 *	}
	 * @return   -10新增失败   1  成功
	 * 更改合约状态为失效清算中、赋值退租日期、释放房源
	 * @throws ParseException 
	 */
	public int managerDone(Map params) throws ParseException {
		Object obj = null;
		String param = "";
		Object[] paramObj = null;
		// 根据退组类型（是否是新退租），更改合约的退组时间
		String type = str.get(params, "type");
		if (type.equals("N"))
		{
			// 新退租
			param = " status = ?";
			paramObj = new Object[]{"7", str.get(params, "agreeid")};
		} else {
			// 老退租
			param = " status = ? , checkouttime=?";
			paramObj = new Object[]{"7", str.get(params, "servicetime"),str.get(params, "agreeid")};
		}
		// 修改合约退租日期---进入失效待清算
		String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", param);
		int res = update(obj,sql,paramObj);
		if (res!=1) 
		{
			return -10;
		}
		else
		{
			// 判断房源是否存在两个合约 存在则不释放，不存在则释放房源
			res = queryForInt(obj,getSql("orderService.counthouse"),new Object[]{str.get(params, "rankid"),str.get(params, "agreeid")});
			if (res > 0)
			{
				return 1;
			} 
			else
			{
				// 增加判断，成功后，更改houserank中publish_time时间  孙诚明 2017-6-13
				int result = approveNo(params, null);
				if (result == 1) 
				{
					// 更新发布时间
					sql = "select a.rank_type,a.house_id from yc_houserank_tab a where a.id="+str.get(params, "rankid");
					Map map = db.queryForMap(sql);
					String rank_type = String.valueOf(map.get("rank_type"));
					if (!rank_type.equals("1"))
					{
						// 整租
						sql = "update yc_houserank_tab a set a.publish_time=now() where a.house_id="+String.valueOf(map.get("house_id"));
						result = db.update(sql);
						if (result > 1)
						{
							result = 1;
						}
					} else 
					{
						
						// 合租
						sql = "update yc_houserank_tab a set a.publish_time=now() where a.id="+str.get(params, "rankid");
						result = db.update(sql);
					}

				}
				return result;
			}
		}
	}
	
	
	/**
	 * 新增接口
	 * @author 刘飞
	 * @param{
	 *  agreeid // 租赁yc_agreement_tab id合约 id
	 *  servicetime // 租赁work_order  预约时间
	 *	}
	 * @return   -10新增失败   1  成功
	 * 更改合约状态为已生效、清空退租时间
	 * @throws ParseException 
	 */
	public int startOrder(Map params) {
		Object obj = null;
		// 修改合约退租日期---进入失效待清算
		String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", " checkouttime=? ");
		int res = update(obj,sql,new Object[]{str.get(params, "servicetime"),str.get(params, "agreeid")});
		return res;
	}
	
	/**
	 * 关闭接口
	 * @author 刘飞
	 * @param{
	 *  agreeid // 租赁yc_agreement_tab id合约 id
	 *  servicetime // 租赁work_order  预约时间
	 *	}
	 * @return   -10新增失败   1  成功
	 * 更改合约状态为已生效、清空退租时间
	 * @throws ParseException 
	 */
	public int closeOrder(Map params) {
		Object obj = null;
		// 修改合约退租日期---进入失效待清算
		String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", " checkouttime=? ");
		int res = update(obj,sql,new Object[]{null,str.get(params, "agreeid")});
		return res;
	}
	
	/**
	 * 释放房源接口
	 * @author 刘飞
	 * @param{
	 *  houseid// yc_house_tab id 收房房源id
	 *	oper     // 当前操作人
	 *	rankid  // 租赁yc_houserank_tab id房源id
	 *  agreeid // 租赁yc_agreement_tab id合约 id
	 *  servicetime // 租赁work_order  预约时间
	 *	}
	 * @return   -10新增失败   1  成功
	 * @throws ParseException 
	 */
	public int approveNo(Map params) throws ParseException {
		Object obj = null;
		
		// 修改合约退租日期---进入失效待清算
		String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", "checkouttime=?, status = ? ");
		int res=0;
		if ("1".equals(str.get(params, "breakContractWithoutLivingFlag"))) {
			res = update(obj,sql,new Object[]{str.get(params, "servicetime"), "3",str.get(params, "agreement_id")});
			if (res == 0) {
				return -10;
			}
			//判断当前合约是否为待公正
			int countStatus=queryForInt(obj, getSql("basehouse.rankHosue.checkRankAgreementStatus"), new Object[]{str.get(params, "agreement_id"),"11"} );
			if (countStatus<1)
			{
				//将财务信息置为失效
				res=financial.repealRentHouse(params);
				if (res != 1) {
					return -10;
				}
			}
			// 判断房源是否存在两个合约 存在则不释放，不存在则释放房源
			res = queryForInt(obj,getSql("orderService.counthouse"),new Object[]{str.get(params, "rankid"),str.get(params, "agreement_id")});
			if (res > 0)
			{
				return 1;
			} 
			return approveNo(params, null);
		}
		return 1;
	}
	
	/**
	 * 合约审批失败
	 * @author 刘飞
	 * @return 
	 * 		  -10新增失败
	 *         1  成功
	 * @throws ParseException 
	 */
	public int approveNo(Map params, Object obj) throws ParseException
	{
		String houseId = str.get(params, "houseid"); // 收房房源id
		//String publish = str.get(params, "publish"); // 收房房源id
		String oper = str.get(params, "oper");       // 获取操作人
		String rank_id = str.get(params, "rankid");  // 租房房源id
		String locksql = getSql("basehouse.rankHosue.lockRank");
		int res=1;
		res=this.update(obj,locksql, new Object[] {houseId,houseId});
		if (res!=1) 
		{
			return -10;
		}
		String pub=getSql("basehouse.houseInfo.getpublish");
		String publish=queryString(obj, pub, new Object[]{houseId});
		String sql = getSql("basehouse.getRankHosue.main")+ getSql("basehouse.getRankHosue.houseId");
		@SuppressWarnings("unchecked")//查询出所有房源
		List<Map<String,Object>> list= this.queryList(obj,sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_RANKHOUSE_HTTP_PATH")),new Object[]{houseId});
		String usSql = getSql("basehouse.rankHosue.updateStatus");
		for(Map<String, Object> mp : list)
         {
			if (rank_id.equals(StringHelper.get(mp, "id"))) //当前Id 房源
			{
				if ("0".equals(publish)||"1".equals(publish))//房源未发布
				{
					if ("整租".equals(StringHelper.get(mp, "rank_type")))//当前房源为整租
					{
						for(Map<String, Object> mp1 : list) //所有房源置为未编辑
						{
							res=this.update(obj, usSql,new Object[] {String.valueOf(rankHouseService.WAIT_APPROVE), oper, StringHelper.get(mp1, "id") });
							if (res!=1) 
							{
								return -10;
							}
					    }
					}
					else//当前房源为合租
					{
					  //修改当前房源为已发布
					  res=this.update(obj, usSql, new Object[] {String.valueOf(rankHouseService.WAIT_APPROVE), oper,rank_id});
					  if (res!=1)
					  {
						return -10;
					  }
					  boolean returni = true;
					  for (Map<String, Object> mp1 : list) 
					  {
						  //如果当前存在不是整租且为出租中状态时返回false
						  if (!rank_id.equals(StringHelper.get(mp1, "id"))&& !"整租".equals(StringHelper.get(mp1,"rank_type"))&&Integer.parseInt(StringHelper.get(mp1,"status"))>rankHouseService.COMPLETE)
						  {
							  returni = false;
						  }
					  }
				      if (returni) //如果当前不存在出租中房源将整租房源置为未发布
					  {
						  for (Map<String, Object> mp2 : list) 
						  { 
							if ("整租".equals(StringHelper.get(mp2, "rank_type")))
							{    // 修改当前房源为已发布
								res=this.update(obj, usSql, new Object[] {String.valueOf(rankHouseService.WAIT_APPROVE),oper, StringHelper.get(mp2, "id") });
								if (res!=1)
								  {
									return -10;
								  }
							}
						  }
					   }
				     }
				}else if ("2".equals(publish))
				{   //当前房源为已发布时
					//当前房源为整租时将所有房源置为已发布
					if ("整租".equals(StringHelper.get(mp, "rank_type")))
					{
						for(Map<String, Object> mp1 : list) 
						{
							res=this.update(obj, usSql,new Object[] {String.valueOf(rankHouseService.COMPLETE), oper, StringHelper.get(mp1, "id") });
							if (res!=1) 
							{
								return -10;
							}
					    }
					} 
					else//当前房源为合租时
					{
					    //修改当前房源为已发布
					    res=this.update(obj, usSql, new Object[] {String.valueOf(rankHouseService.COMPLETE), oper,rank_id});
					    if (res!=1)
					    {
						   return -10;
					    }
					    boolean returni = true;
					    for (Map<String, Object> mp1 : list) //如果当前存在不是整租且为出租中状态时返回false
					    {
						    if (!rank_id.equals(StringHelper.get(mp1, "id"))&& !"整租".equals(StringHelper.get(mp1,"rank_type"))&& Integer.parseInt(StringHelper.get(mp1,"status"))>rankHouseService.COMPLETE)
						    {
							    returni = false;
						    }
					    }
					    if (returni) 
					    {
						    for (Map<String, Object> mp2 : list) //如果当前不存在出租中房源将整租房源置为未发布
						    {
							    if ("整租".equals(StringHelper.get(mp2, "rank_type")))
							    {    // 修改当前房源为已发布
								    res=this.update(obj, usSql, new Object[] {String.valueOf(rankHouseService.COMPLETE),oper, StringHelper.get(mp2, "id") });
								    if (res!=1)
								    {
								       return -10;
								    }  
							    }
						    }
					    }
				    }
				}
			}
		}
		return res;
	}
	
	/**
	 * 合约审批成功
	 * @author 刘飞
	 * @return 
	 * @throws ParseException 
	 */
	public int approveOk(Map params, Object obj) throws ParseException
	{
		int res=1;
		String oper = str.get(params, "operid");       // 获取操作人
		String rank_id = str.get(params, "house_rank_id");  // 房源id
		String house_id = str.get(params, "house_id");  // 房源id
		String sql = getSql("basehouse.rankHosue.updateRankHouseStatus").replace("####", "id=?");
		res=this.update(obj, sql, new Object[]{rank_id,rank_id});
		if (res != 1) 
		{
			return -10;
		}
		String usSql = getSql("basehouse.rankHosue.updateStatus");
		logger.debug(str.getSql(usSql, new Object[]{String.valueOf(RankHouseService.RANT_OUT),oper, rank_id}));
		res =this.update(obj, usSql, new Object[]{String.valueOf(RankHouseService.RANT_OUT),oper, rank_id});
	  if (res!=1) 
	  {
		  return -10;
	  }
		return 1;
	}
	
	/**
	 * 房源签约中
	 * @author 刘飞
	 * @return 
	 * @throws ParseException 
	 */
	public int approveWaitOk(Map params, Object obj) throws ParseException
	{
		int res=1;
		String oper = str.get(params, "operid");       // 获取操作人
		String rank_id = str.get(params, "house_rank_id");  // 房源id
		String house_id = str.get(params, "house_id");  // 房源id
		String sql = getSql("basehouse.rankHosue.updateRankHouseStatus").replace("####", "id=?");
		res=this.update(obj, sql, new Object[]{rank_id,rank_id});
		if (res != 1) 
		{
			return -10;
		}
		String usSql = getSql("basehouse.rankHosue.updateStatus");
		res =this.update(obj, usSql, new Object[]{String.valueOf(RankHouseService.AGREE_ING),oper, rank_id});
		if (res!=1) 
		{
			return -10;
		}
		return 1;
	}
	
	/**
	 * 更新房源合约状态
	 * @param param
	 * @param obj
	 * @return
	 */
	public int updateRankAgreementStatus(Map param, Object obj)
	{
		String agreementId = StringHelper.get(param, "agreementId"); // 合约id
		if("".equals(agreementId))
		{
			return -3;
		}
		String sql = getSql("basehouse.rankHosue.checkRankAgreementStatus");
		int res = this.queryForInt(obj, sql, new Object[]{agreementId,12});
		if(res == 1)
		{
			sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", "status = 2");
			return this.update(obj, sql, new Object[]{agreementId});
		}
		return -2; // 改合约状态已经改变
	}
	
}
