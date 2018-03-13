package com.room1000.workorder.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.room1000.agreement.dao.AgreementDtoMapper;
import com.room1000.agreement.dao.HouseRankDtoMapper;
import com.room1000.agreement.dto.AgreementDto;
import com.room1000.agreement.dto.HouseRankDto;
import com.room1000.attr.service.IAttrService;
import com.room1000.core.activiti.IProcessTask;
import com.room1000.core.activiti.impl.ProcessTaskImpl;
import com.room1000.core.activiti.model.ActivitiStepDto;
import com.room1000.core.define.BooleanFlagDef;
import com.room1000.core.exception.BaseAppException;
import com.room1000.core.utils.DateUtil;
import com.room1000.recommend.dao.RecommendInfoDtoMapper;
import com.room1000.recommend.dao.RecommendRelationDtoMapper;
import com.room1000.recommend.dto.RecommendInfoDto;
import com.room1000.recommend.dto.RecommendRelationDto;
import com.room1000.suborder.cancelleaseorder.dto.CancelLeaseOrderDto;
import com.room1000.suborder.cancelleaseorder.service.ICancelLeaseOrderService;
import com.room1000.suborder.cleaningorder.dto.CleaningOrderValueDto;
import com.room1000.suborder.complaintorder.dto.ComplaintOrderValueDto;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.houselookingorder.dto.HouseLookingOrderValueDto;
import com.room1000.suborder.livingproblemorder.dto.LivingProblemOrderValueDto;
import com.room1000.suborder.otherorder.dto.OtherOrderValueDto;
import com.room1000.suborder.ownerrepairorder.dto.OwnerRepairOrderValueDto;
import com.room1000.suborder.repairorder.dto.RepairOrderValueDto;
import com.room1000.suborder.routinecleaningorder.dto.RoutineCleaningOrderValueDto;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.dao.OrderCommentaryDtoMapper;
import com.room1000.workorder.dao.OrderCommentaryTypeDtoMapper;
import com.room1000.workorder.dao.SubOrderStateDtoMapper;
import com.room1000.workorder.dao.WorkOrderDtoMapper;
import com.room1000.workorder.dao.WorkOrderHisDtoMapper;
import com.room1000.workorder.dao.WorkOrderTypeDtoMapper;
import com.room1000.workorder.define.OrderStepStateDef;
import com.room1000.workorder.define.WorkOrderStateDef;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.OrderCommentaryDto;
import com.room1000.workorder.dto.OrderCommentaryTypeDto;
import com.room1000.workorder.dto.OrderStepDto;
import com.room1000.workorder.dto.SubOrderStateDto;
import com.room1000.workorder.dto.SubOrderValueDto;
import com.room1000.workorder.dto.TypeCountDto;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.dto.WorkOrderHisDto;
import com.room1000.workorder.dto.WorkOrderTypeDto;
import com.room1000.workorder.dto.request.AssignOrderRequest;
import com.room1000.workorder.dto.request.QryOrderCommentaryPagerListRequest;
import com.room1000.workorder.dto.request.QryWorkOrderPagerListRequest;
import com.room1000.workorder.dto.request.TeamWorkOrderPagerListRequest;
import com.room1000.workorder.dto.request.UpdateSubOrderRequest;
import com.room1000.workorder.dto.request.WorkOrderCommentaryRequest;
import com.room1000.workorder.factory.SubOrderFactory;
import com.room1000.workorder.service.ISubOrderPayService;
import com.room1000.workorder.service.ISubOrderService;
import com.room1000.workorder.service.IWorkOrderService;
import com.yc.rm.caas.appserver.bus.service.team.ITeamServ;
import com.ycdc.appserver.model.house.RankAgreement;
import com.ycdc.core.base.BaseService;
import com.ycdc.nbms.datapermission.service.DataPermissionServ;
import com.ycdc.util.page.PageResultDealInterface;

import net.sf.json.JSONObject;
import pccom.common.util.SpringHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.beans.User;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Financial.Result;
import pccom.web.server.house.rankhouse.RankHouseService;

/**
 * Description:
 * 
 * Created on 2017年2月6日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service("WorkOrderService")
public class WorkOrderServiceImpl extends BaseService implements IWorkOrderService
{
	
	@Autowired
	private DataPermissionServ serv;
	
	/**
	 * workOrderDtoMapper
	 */
	@Autowired
	private WorkOrderDtoMapper workOrderDtoMapper;

	/**
	 * workOrderHisDtoMapper
	 */
	@Autowired
	private WorkOrderHisDtoMapper workOrderHisDtoMapper;

	/**
	 * workOrderTypeDtoMapper
	 */
	@Autowired
	private WorkOrderTypeDtoMapper workOrderTypeDtoMapper;

	/**
	 * orderCommentaryDtoMapper
	 */
	@Autowired
	private OrderCommentaryDtoMapper orderCommentaryDtoMapper;

	/**
	 * subOrderStateDtoMapper
	 */
	@Autowired
	private SubOrderStateDtoMapper subOrderStateDtoMapper;

	/**
	 * agreementDtoMapper
	 */
	@Autowired
	private AgreementDtoMapper agreementDtoMapper;

	/**
	 * houseRankDtoMapper
	 */
	@Autowired
	private HouseRankDtoMapper houseRankDtoMapper;

	/**
	 * orderCommentaryTypeDtoMapper
	 */
	@Autowired
	private OrderCommentaryTypeDtoMapper orderCommentaryTypeDtoMapper;

	/**
	 * cancelLeaseService
	 */
	@Autowired
	private ICancelLeaseOrderService cancelLeaseService;

	/**
	 * attrService
	 */
	@Autowired
	private IAttrService attrService;

	@Autowired
	private RecommendInfoDtoMapper recommendInfoDtoMapper;

	@Autowired
	private RecommendRelationDtoMapper recommendRelationDtoMapper;

	@Autowired
	private ITeamServ _teamServImpl;
	
	/**
	 * 缓存订单类型
	 */
	private static Map<String, WorkOrderTypeDto> orderTypeMap = new HashMap<>();

	@Override
	public WorkOrderDto createWorkOrderWithTrans(WorkOrderDto workOrderDto)
	{
		workOrderDto.setCreatedDate(DateUtil.getDBDateTime());
		workOrderDto.setState(WorkOrderStateDef.DOING);
		workOrderDto.setStateDate(DateUtil.getDBDateTime());
		if (StringUtils.isEmpty(workOrderDto.getName()))
		{
			workOrderDto.setName(this.generateOrderName(workOrderDto));
		}
		workOrderDtoMapper.insert(workOrderDto);
		return workOrderDto;
	}

	@Override
	public WorkOrderDto getWorkOrderDtoByCode(String code)
	{
		return workOrderDtoMapper.selectByCode(code);
	}

	@Override
	public WorkOrderDto updateWorkOrderWithTrans(WorkOrderDto workOrderDto)
	{
		IProcessTask task = new ProcessTaskImpl();
		task.putInstanceVariable(workOrderDto.getId(), "workOrder", workOrderDto);
		this.updateWorkOrderAddHis(workOrderDto);
		workOrderDtoMapper.updateByPrimaryKey(workOrderDto);
		return workOrderDto;
	}

	/**
	 * Description: 修改工单并增加历史
	 * 
	 * @author jinyanan
	 * @param workOrderDto
	 *          workOrderDto
	 * @return WorkOrderDto
	 */
	private WorkOrderDto updateWorkOrderAddHis(WorkOrderDto workOrderDto)
	{
		WorkOrderDto oldWorkOrderDto = workOrderDtoMapper.selectByPrimaryKey(workOrderDto.getId());
		workOrderDtoMapper.updateByPrimaryKeySelective(workOrderDto);
		WorkOrderHisDto hisDto = this.createWorkOrderHis(oldWorkOrderDto);
		workOrderHisDtoMapper.insert(hisDto);
		return workOrderDto;
	}

	/**
	 * Description: 创建工单历史
	 * 
	 * @author jinyanan
	 * @param oldWorkOrderDto
	 *          oldWorkOrderDto
	 * @return WorkOrderHisDto
	 */
	private WorkOrderHisDto createWorkOrderHis(WorkOrderDto oldWorkOrderDto)
	{
		Long maxPriority = workOrderHisDtoMapper.selectMaxPriority(oldWorkOrderDto.getId());
		if (null == maxPriority)
		{
			maxPriority = 0L;
		}
		WorkOrderHisDto workOrderHisDto = new WorkOrderHisDto();
		SubOrderUtil.createdHisDto(oldWorkOrderDto, workOrderHisDto, maxPriority + 1);
		return workOrderHisDto;
	}

	@Override
	public WorkOrderDto getWorkOrderDtoById(Long id)
	{
		return workOrderDtoMapper.selectByPrimaryKey(id);
	}

	@Override
	public WorkOrderDto getWorkOrderDetailById(Long workOrderId)
	{
		WorkOrderDto workOrderDto = workOrderDtoMapper.selectDetailById(workOrderId);
		List<OrderCommentaryDto> list = orderCommentaryDtoMapper.selectByWorkOrderId(workOrderId);
		for (OrderCommentaryDto dto : list)
		{
			dto.setImageUrl(SubOrderUtil.getImagePath(dto.getImageUrl()));
		}
				
		workOrderDto.setOrderCommentaryList(list);
		return workOrderDto;
	}

	@Override
	public void workOrderCommentaryWithTrans(String code, List<OrderCommentaryDto> orderCommentaryList, String imagrUrl, String realPath, String flag)
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDtoByCode(code);
		this.workOrderCommentaryWithTrans(workOrderDto.getId(), orderCommentaryList, imagrUrl, realPath, flag);
	}

	@Override
	public void workOrderCommentaryWithTrans(Long workOrderId, List<OrderCommentaryDto> commentaryList, String imagrUrl, String realPath, String flag)
	{
		this.workOrderCommentaryWithTrans(workOrderId, commentaryList, SystemAccountDef.CUSTOMER, imagrUrl, realPath, flag);
	}

	@Override
	public void workOrderCommentaryWithTrans(Long workOrderId, List<OrderCommentaryDto> commentaryList,
			Long updatePersonId, String imagrUrl, String realPath, String flag)
	{
		this.checkOrderCommentary(workOrderId, commentaryList);
		
		String newImagrUrl = "";
		if (StringUtils.isNotBlank(imagrUrl) && StringUtils.isNotBlank(realPath)) {
			newImagrUrl = SubOrderUtil.uploadImage(imagrUrl, workOrderId, realPath);
		}
		
		for (OrderCommentaryDto orderCommentaryDto : commentaryList)
		{
			orderCommentaryDto.setImageUrl(newImagrUrl);
			orderCommentaryDto.setWorkOrderId(workOrderId);

			if (null == orderCommentaryDto.getCommentDate())
			{
				orderCommentaryDto.setCommentDate(DateUtil.getDBDateTime());
			}
			orderCommentaryDtoMapper.insert(orderCommentaryDto);
		}

		WorkOrderDto workOrderDto = this.getWorkOrderDtoById(workOrderId);
		
		if (workOrderDto.getType().equals("A") && flag.equals("Y"))
		{
			logger.info("退租，合约置成失效... 合约id=" + workOrderDto.getRentalLeaseOrderId());
			// 退租，合约失效
			AgreementDto record = new AgreementDto();
			record.setStatus("3");
			record.setId(workOrderDto.getRentalLeaseOrderId());
			agreementDtoMapper.updateByPrimaryKeySelective(record);
		}
		String type = workOrderDto.getType();
		if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
			type = "A";
		}
		ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
		Object subOrder = subOrderService.getOrderDetailById(workOrderDto.getRefId(), true);
		subOrderService.updateSubOrderWithTrans(subOrder, updatePersonId, "flag");
		this.setFields4CompleteOrder(workOrderDto.getType(), subOrder);
		subOrderService.updateSubOrderWithTrans(subOrder, null, "");
		this.closeWorkOrder(workOrderDto, null);

	}

	/**
	 * 
	 * Description: 设置参数
	 * 
	 * @author jinyanan
	 *
	 * @param orderType
	 *          orderType
	 * @param subOrderDto
	 *          subOrderDto
	 */
	private void setFields4CompleteOrder(String orderType, Object subOrderDto)
	{
		this.invokeMethod(orderType, subOrderDto, "setState", SubOrderStateDef.CLOSED);
		this.invokeMethod(orderType, subOrderDto, "setStateDate", DateUtil.getDBDateTime());
		this.invokeMethod(orderType, subOrderDto, "setAssignedDealerId", null, true);
		this.invokeMethod(orderType, subOrderDto, "setAssignedDealerRoleId", null, true);
	}

	/**
	 * Description: 校验评价
	 * 
	 * @author jinyanan
	 * @param workOrderId
	 *          workOrderId
	 * @param commentaryList
	 *          commentaryList
	 */
	private void checkOrderCommentary(Long workOrderId, List<OrderCommentaryDto> commentaryList)
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDtoById(workOrderId);
		if (null == workOrderDto)
		{
			throw new BaseAppException("C_ORDER_00002", "工单不存在");
		}
		for (OrderCommentaryDto orderCommentaryDto : commentaryList)
		{
			OrderCommentaryDto dto = orderCommentaryDtoMapper.selectByPrimaryKey(workOrderId, orderCommentaryDto.getType());
			if (dto != null)
			{
				throw new BaseAppException("C_ORDER_00001", "当前工单下已有" + orderCommentaryDto.getType() + "类型评价");
			}
		}

	}
	
	@Override
	public Object updateWorkOrderButlerById(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> result = new HashMap<>();
		long workOrderId = Long.valueOf(req.getAjaxValue(request, "workOrderId"));
		long subAssignedDealerId = Long.valueOf(req.getAjaxValue(request, "butlerId"));
		long subOrderId = Long.valueOf(req.getAjaxValue(request, "subOrderId"));
		
		WorkOrderDto dto = new WorkOrderDto();
		dto.setId(workOrderId);
		dto.setSubAssignedDealerId(subAssignedDealerId);
		dto.setSubActualDealerId(subAssignedDealerId);
		int info = workOrderDtoMapper.updateByPrimaryKeySelective(dto);
		
		if (info > 0) 
		{
			String type = req.getAjaxValue(request, "type");
			if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
			{
				// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
				type = "A";
			}
			ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
			info = subOrderService.updateSubOrderAssignedDealerId(subOrderId, subAssignedDealerId);
			if (info > 0) 
			{
				info = 1;
			}
		}
		result.put("state", info);
		return result;
	}

	@Override 
	public void getTeamWorkOrderList(HttpServletRequest request, HttpServletResponse response)
	{
		// TODO 团队任务列表（PC端）
		Map<String, Object> map = new HashMap<String, Object>();
		User currentStaff = this.getUser(request);
		String user_id = String.valueOf(currentStaff.getId());
		if (user_id.equals("") || user_id.equals("null")) 
		{
			try
			{
				Map<String,Object> returnMap = new HashMap<String, Object>();
				returnMap.put("state", "-99999");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(JSONObject.fromObject(returnMap));
			} catch (Exception e)
			{
				logger.error("错误信息：" + e);
			}
		} else 
		{
			map.put("userId", user_id);
			String roleId = "";
			String roleIds1 = currentStaff.getRoles();
			String[] roleArry = roleIds1.split(",");
			for (String string : roleArry)
			{
				if (string.trim().equals("")) {
					continue;
				}
				roleId += string + ",";
			}
			roleId = roleId.substring(0, roleId.length() - 1);
			map.put("roleId", roleId);
			// 根据登录用户id，获取所在团队所有用户id
			List<com.yc.rm.caas.appserver.model.user.User> users = _teamServImpl.getMemberListByUserId(Integer.valueOf(user_id));
			List<String> ids = new ArrayList<>();
			for (com.yc.rm.caas.appserver.model.user.User user : users)
			{
				ids.add(String.valueOf(user.getUserId()));
			}
			// 去重
			Set<String> idsSet = new HashSet<>(ids);
			// 所有用户id
			String idStr = "";

			for (String id : idsSet)
			{
				if (id.trim().equals("") || id.equals("null")) 
					continue;
				
				idStr += id + ",";
			}
			if (StringUtils.isNotBlank(idStr))
			{
				idStr = idStr.substring(0, idStr.length() - 1);
			}
			map.put("currentDealerId", idStr);
			
			// 所有历史订单id
			String orderIdStr = "";
			List<WorkOrderHisDto> workOrderHisList = workOrderHisDtoMapper.selectByCondList(idStr);
			for (WorkOrderHisDto workOrderHisDto : workOrderHisList)
			{
				if (!String.valueOf(workOrderHisDto.getId()).trim().equals("") || !String.valueOf(workOrderHisDto.getId()).trim().equals("null"))
				{
					orderIdStr += String.valueOf(workOrderHisDto.getId()) + ",";
				}
			}
			if (StringUtils.isNotBlank(orderIdStr))
			{
				orderIdStr = orderIdStr.substring(0, orderIdStr.length() - 1);
			}
			map.put("ids", orderIdStr);
	
			// 根据用户id列表，获取所有的角色id
			String roleIds = workOrderDtoMapper.getRoleIds(idStr);
			
			map.put("currentDealerRoleIds", roleIds);
	
			// 根据成员id，获取所有上级团队的负责人
			List<String> leaders = new ArrayList<>();
			List<Map<String, Object>> infos = workOrderDtoMapper.getTeamLeaderInfo(currentStaff.getId());
			for (Map<String, Object> mapInfos : infos)
			{
				leaders.add(String.valueOf(mapInfos.get("leader_id")));
				String parent_teamid = String.valueOf(mapInfos.get("parent_teamid"));
				if (parent_teamid.equals("0"))
				{
					// 已经根团队
					continue;
				} else
				{
					getLeaders(parent_teamid, leaders);
				}
			}
			Set<String> leaderSet = new HashSet<>(leaders);
			String leaderStr = "";
			for (String leader : leaderSet)
			{
				leaderStr += leader + ",";
			}
			if (StringUtils.isNotEmpty(leaderStr))
			{
				leaderStr = leaderStr.substring(0, leaderStr.length() - 1);
			}
			map.put("leaderStr", leaderStr);
			
			// 判断当前用户是不是平台管理员
			int info = workOrderDtoMapper.getManagerInfo(user_id);
			if (info > 0) 
			{
				map.put("isManager", "Y");
			} else 
			{
				map.put("isManager", "N");
			}
			
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "type")))
			{
				if (req.getAjaxValue(request, "type").equals("A"))
				{
					map.put("type", "'A','N'");
				} else 
				{
					map.put("type", "'" + req.getAjaxValue(request, "type") + "'");
				}
			}

			// 模糊查询
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "keyword")))
			{
				map.put("keyword", req.getAjaxValue(request, "keyword"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "state")))
			{
				map.put("state", req.getAjaxValue(request, "state"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "createdDateStart")))
			{
				map.put("createdDateStart", req.getAjaxValue(request, "createdDateStart"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "createdDateEnd")))
			{
				map.put("createdDateEnd", req.getAjaxValue(request, "createdDateEnd"));
			}
			getPageMapList(request, response, workOrderDtoMapper, "selectTeamTask", map, new PageResultDealInterface()
			{
				@Override
				public List<Map<String, Object>> deal(List<Map<String, Object>> list)
				{
					if (null != list && !list.isEmpty())
					{
						String roleIds = String.valueOf(list.get(0).get("roleIds"));
						List<Map<String, Object>> dataList = serv.getPermissionsInfoByRoleIds(roleIds, 1);
						
						for (Map<String, Object> map : dataList) 
						{
							String subTypeId = String.valueOf(map.get("subTypeId"));
							for (Map<String, Object> maps : list)
							{
								String type = String.valueOf(maps.get("type"));
								if (type.equals("N")) 
								{
									// 兼容老订单
									type = "A";
								}
								if (subTypeId.equals(type))
								{
									maps.putAll(map);
								}
							}
						}
					}
					
					// 查询超长时间界限节点
					String timeCode = workOrderDtoMapper.queryTimeCode();
					if (StringUtils.isBlank(timeCode) || timeCode.equals("null"))
					{
						timeCode = "48";
					}
					long to = 0;
					long from = 0;
					for (Map<String, Object> workOrder : list)
					{
						workOrder.put("timeCode", timeCode);
						try
						{
							String type = String.valueOf(workOrder.get("type"));
							if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
							{
								// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
								type = "A";
							}
							ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
							List<Object> subOrderList = subOrderService.getHis(Long.valueOf(String.valueOf(workOrder.get("ref_id"))));

							// 计算总时长
							String createdDate = String.valueOf(workOrder.get("created_date"));
							if (StringUtils.isNotBlank(createdDate) && !createdDate.equals("null"))
							{
								// 格式化订单创建时间
								SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								from = formatter.parse(createdDate).getTime();
								// 规则（判断任务过程是否有评价，有则用评价上一步结束时间-创建时间，若没有则判断订单是否已关闭，已关闭则用关闭时间-创建时间，若未关闭则当前时间-创建时间）
								List<String> stateList = new ArrayList<String>();
								int evaluationIndex = 0;
								for (int i = 0; i < subOrderList.size(); i++)
								{
									Object obj = subOrderList.get(i);
									Class<? extends Object> hisObj = obj.getClass();
									String state = String.valueOf(hisObj.getMethod("getState").invoke(obj));
									if (state.equalsIgnoreCase("H"))
									{
										evaluationIndex = i;
									}
									stateList.add(state);
								}

								String sub_order_state = String.valueOf(workOrder.get("sub_order_state"));
								if (sub_order_state.equals("I") || sub_order_state.equals("H"))
								{
									Object evaluationObj = subOrderList.get(subOrderList.size() - 1);
									Date evaluationUpdateDate = (Date) evaluationObj.getClass().getMethod("getUpdateDate")
											.invoke(evaluationObj);
									to = evaluationUpdateDate.getTime();
								}

								if (stateList.contains("H"))
								{
									// 如果当前订单存在评价，根据评价下标获取上一步的执行时间
									Object evaluationObj = subOrderList.get(evaluationIndex - 1);
									Date evaluationUpdateDate = (Date) evaluationObj.getClass().getMethod("getUpdateDate")
											.invoke(evaluationObj);
									to = evaluationUpdateDate.getTime();
								} else
								{
									if (stateList.contains("I"))
									{
										// 订单关闭
										Object lastObj = subOrderList.get(subOrderList.size() - 1);
										Date lastUpdateDate = (Date) lastObj.getClass().getMethod("getUpdateDate").invoke(lastObj);
										to = lastUpdateDate.getTime();
									} else
									{
										to = new Date().getTime();
									}
								}
								String totalTime = String.valueOf(((to - from) / (1000 * 60 * 60)));
								if (totalTime.contains("-"))
								{
									totalTime = "0";
								}
								workOrder.put("totalTime", totalTime);

								// 计算当前步骤时长
								Object o = subOrderList.get(subOrderList.size() - 1);
								Class<? extends Object> hisClass = o.getClass();
								Date updateDate = (Date) hisClass.getMethod("getUpdateDate").invoke(o);
								to = updateDate.getTime();

								if (String.valueOf(workOrder.get("state")).equalsIgnoreCase("C"))
								{
									// 表示订单已经处理完成，无需计算当前步骤停留时间
									workOrder.put("currentStepTime", "");
								} else
								{
									// 系统当前时间
									from = new Date().getTime();
									// 计算当前步骤停留时间
									String currentStepHours = String.valueOf(((from - to) / (1000 * 60 * 60)));
									if (currentStepHours.contains("-"))
									{
										currentStepHours = "0";
									}
									workOrder.put("currentStepTime", currentStepHours);
								}
							} else
							{
								workOrder.put("currentStepTime", "");
								workOrder.put("totalTime", "");
							}

						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
					return list;
				}
			});
		}
	}

	private List<String> getLeaders(String id, List<String> result)
	{
		// 根据父团队id，获取上级团队
		List<Map<String, Object>> teamPars = workOrderDtoMapper.getTeamParInfo(id);
		for (Map<String, Object> map : teamPars)
		{ 
			result.add(String.valueOf(map.get("leader_id")));
			String parent_teamid = String.valueOf(map.get("parent_teamid"));
			if (parent_teamid.equals("0"))
			{
				continue;
			} else
			{
				getLeaders(parent_teamid, result);
			}
		}
		return result;
	}

	@Override
	public List<WorkOrderDto> getTeamWorkOrderList(TeamWorkOrderPagerListRequest req)
	{ 
		// TODO 团队任务列表（手机端）
		List<com.yc.rm.caas.appserver.model.user.User> users = new ArrayList<>();
		// 判断是否存在登录用户id
		String userId = String.valueOf(req.getCurrentDealerId());
		String teamIds = String.valueOf(req.getTeamIds());
		if (!userId.equals("") && !userId.equals("null")) 
		{
			// 根据登录用户id，获取所在团队所有用户id
			users = _teamServImpl.getMemberListByUserId(Integer.valueOf(userId));
		} else 
		{
			if (!teamIds.equals("") && !teamIds.equals("null"))
			{
				// 根据团队id，查询所有成员
				users = workOrderDtoMapper.getUserIdsByTeamIds(teamIds);
			}
		}
		
		String idStr = "";
		if (users.size() > 0)
		{
			// 遍历所有成员
			List<String> ids = new ArrayList<>();
			for (com.yc.rm.caas.appserver.model.user.User user : users)
			{
				ids.add(String.valueOf(user.getUserId()));
			}
			// 去重所有成员
			Set<String> idsSet = new HashSet<>(ids);
			Set<Long> idSet = new HashSet<>();
			for (String id : idsSet)
			{
				idStr += id + ",";
			}
			if (StringUtils.isNotEmpty(idStr))
			{
				idStr = idStr.substring(0, idStr.length() - 1);
			}
			req.setCurrentDealerIds(idStr);
			// 根据用户id，获取角色id
			String roleIds = workOrderDtoMapper.getRoleIds(idStr);
			req.setCurrentDealerRoleIds(roleIds);
			
			// 1:我发起，2:待处理，3:已处理
			String todoType = String.valueOf(req.getTodoType());
			if (todoType.equals("3"))
			{
				// 根据所有成员id，获取已经处理过的订单id
				List<WorkOrderHisDto> workOrderHisList = workOrderHisDtoMapper.selectByCondList(idStr);
				for (WorkOrderHisDto workOrderHisDto : workOrderHisList)
				{
					idSet.add(workOrderHisDto.getId());
				}
				String idsStr = "";
				for (Long id : idSet)
				{
					idsStr += id + ",";
				}
				if (StringUtils.isNotEmpty(idsStr))
				{
					idsStr = idsStr.substring(0, idsStr.length() - 1);
				}
				req.setIds(idsStr);
			} else 
			{
				req.setIds("");
			}

		} else 
		{
			req.setCurrentDealerIds("");
			req.setCurrentDealerRoleIds("");
			req.setIds("");
		}
		
		if (idStr.equals(""))
		{
			return new ArrayList<WorkOrderDto>();
		}

		// 获取所有上级团队的负责人
		List<String> leaders = new ArrayList<>(); 
		if (!userId.equals("") && !userId.equals("null")) 
		{
			List<Map<String, Object>> infos = workOrderDtoMapper.getTeamLeaderInfo(String.valueOf(req.getCurrentDealerId()));
			for (Map<String, Object> mapInfos : infos)
			{
				leaders.add(String.valueOf(mapInfos.get("leader_id")));
				String parent_teamid = String.valueOf(mapInfos.get("parent_teamid"));
				if (parent_teamid.equals("0"))
				{
					// 已经根团队
					continue;
				} else
				{
					getLeaders(parent_teamid, leaders);
				}
			}
		} else 
		{
			if (!teamIds.equals("") && !teamIds.equals("null"))
			{
				List<Map<String, Object>> leaderList = workOrderDtoMapper.getLeaderIdsByTeamIds(teamIds);
				for (Map<String, Object> map : leaderList) 
				{
					String id = String.valueOf(map.get("leader_id"));
					leaders.add(id);
				}
			}
		}		
		
		Set<String> leaderSet = new HashSet<>(leaders);
		String leaderStr = "";
		for (String leader : leaderSet)
		{
			leaderStr += leader + ",";
		}
		if (StringUtils.isNotEmpty(leaderStr))
		{
			leaderStr = leaderStr.substring(0, leaderStr.length() - 1);
		}

		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		List<String> typeList = req.getTypeList();
		if (null != typeList && typeList.contains("A"))
		{
			// 老退租订单兼容
			typeList.add("N");
			req.setTypeList(typeList);
		}
		List<WorkOrderDto> resultList = workOrderDtoMapper.selectTeamTaskByCon(req);
		// 查询超长时间界限节点
		String timeCode = workOrderDtoMapper.queryTimeCode();
		if (StringUtils.isBlank(timeCode) || timeCode.equals("null"))
		{
			timeCode = "48";
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = formatter.format(new Date());
		for (WorkOrderDto dto : resultList)
		{
			// 查询当前订单的历史订单
			String type = String.valueOf(dto.getType());
			if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
			{
				// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
				type = "A";
			}
			ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
			List<Object> subOrderList = subOrderService.getHis(Long.valueOf(String.valueOf(dto.getRefId())));
			Object evaluationObj = subOrderList.get(subOrderList.size() - 1);
			try
			{
				Date evaluationUpdateDate = (Date) evaluationObj.getClass().getMethod("getUpdateDate").invoke(evaluationObj);
				String stateDateStr = formatter.format(evaluationUpdateDate);
				dto.setStateDateStr(stateDateStr);
			} catch (Exception e)
			{
				logger.error("手机端团队任务错误日志：" + e);
			}
			dto.setTimeCode(timeCode);
			dto.setNow_date_str(nowTime);
			dto.setLeaderStr(leaderStr);
		}
		return resultList;
	}

	@Override
	public List<WorkOrderDto> getWorkOrderList(QryWorkOrderPagerListRequest req)
	{
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		// 查询超长时间界限节点
		String timeCode = workOrderDtoMapper.queryTimeCode();
		if (StringUtils.isBlank(timeCode) || timeCode.equals("null"))
		{
			timeCode = "48"; 
		}
		String type = req.getType();
		if (StringUtils.isNotBlank(type))
		{
			if (StringUtils.isNotBlank(type) && type.equals("A"))
			{
				type = "'A','N'";
			} else 
			{
				type = "'"+type+"'";
			}
		}
		req.setType(type);
		List<String> typeList = req.getExcludeTypeList();
		if (null != typeList && typeList.contains("A"))
		{
			typeList.add("N");
			req.setExcludeStateList(typeList);
		}
		List<WorkOrderDto> resultList = workOrderDtoMapper.selectByReqCond(req);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = formatter.format(new Date());
		for (WorkOrderDto dto : resultList)
		{
			dto.setNow_date_str(nowTime);
			dto.setTimeCode(timeCode);
		}
		return resultList;
	}

	@Override
	public List<OrderCommentaryDto> getOrderCommentaryDto(QryOrderCommentaryPagerListRequest req)
	{
		PageHelper.startPage(req.getPageNum(), req.getPageSize());
		return orderCommentaryDtoMapper.selectByReqCond(req);
	}

	@Override
	public void updateOrderCommentaryWithTrans(WorkOrderCommentaryRequest req)
	{
		for (OrderCommentaryDto orderCommentary : req.getOrderCommentaryList())
		{
			if (null == orderCommentary.getWorkOrderId())
			{
				orderCommentary.setWorkOrderId(req.getWorkOrderId());
			}
			orderCommentaryDtoMapper.updateByPrimaryKey(orderCommentary);
		}
	}

	@Override
	public void deleteOrderCommentaryWithTrans(OrderCommentaryDto orderCommentary)
	{
		orderCommentaryDtoMapper.deleteByCond(orderCommentary);
	}

	@Override
	public void payWithTrans(Long workOrderId, Long staffId, Long paidMoney)
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDtoById(workOrderId);
		ISubOrderPayService subOrderPayService = SubOrderFactory.getSubOrderPayService(workOrderDto.getType());
		subOrderPayService.payWithTrans(workOrderDto.getCode(), staffId, paidMoney);
	}

	@Override
	public void updateSubOrderWithTrans(UpdateSubOrderRequest req, String realPath, Long staffId)
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDtoById(req.getWorkOrderId());
		workOrderDto.setName(req.getOrderName());
		workOrderDto.setSubActualDealerId(staffId);
		workOrderDto.setSubComments(req.getComments());
		workOrderDto.setAppointmentDate(req.getAppointmentDate());
		this.updateWorkOrderWithTrans(workOrderDto);

		String type = workOrderDto.getType();
		if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
			type = "A";
		}
		ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
		Object subOrderDto = subOrderService.getOrderDetailById(workOrderDto.getRefId(), true);
		this.setFields4UpdateOrder(workOrderDto.getType(), subOrderDto, req, staffId, realPath);
		subOrderService.updateSubOrderWithTrans(subOrderDto, staffId, "");

	}

	/**
	 * Description: 设置参数
	 * 
	 * @author jinyanan
	 * @param orderType
	 *          orderType
	 * @param subOrderDto
	 *          subOrderDto
	 * @param req
	 *          req
	 * @param staffId
	 *          staffId
	 * @param realPath
	 *          realPath
	 */
	private void setFields4UpdateOrder(String orderType, Object subOrderDto, UpdateSubOrderRequest req, Long staffId,
			String realPath)
	{
		this.invokeMethod(orderType, subOrderDto, "setCancelLeaseDate", req.getAppointmentDate());
		this.invokeMethod(orderType, subOrderDto, "setAppointmentDate", req.getAppointmentDate());
		this.invokeMethod(orderType, subOrderDto, "setComplaintDate", req.getAppointmentDate());
		this.invokeMethod(orderType, subOrderDto, "setComments", req.getComments());
		this.invokeMethod(orderType, subOrderDto, "setActualDealerIdField", staffId);
		this.invokeMethod(orderType, subOrderDto, "setUpdatePersonId", staffId);
		this.invokeMethod(orderType, subOrderDto, "setUpdateDate", DateUtil.getDBDateTime());
		this.invokeMethod(orderType, subOrderDto, "setChangeFlag", BooleanFlagDef.BOOLEAN_TRUE);
		this.invokeMethod(orderType, subOrderDto, "setImageUrl",
				SubOrderUtil.uploadImage(req.getImageUrl(), req.getWorkOrderId(), realPath));
	}

	/**
	 * Description:
	 * 
	 * @author jinyanan
	 * @param orderType
	 *          orderType
	 * @param subOrderDto
	 *          subOrderDto
	 * @param methodName
	 *          methodName
	 * @param args
	 *          要设置的参数
	 * @return Object
	 */
	private Object invokeMethod(String orderType, Object subOrderDto, String methodName, Object args)
	{
		return this.invokeMethod(orderType, subOrderDto, methodName, args, false);
	}

	/**
	 * Description:
	 * 
	 * @author jinyanan
	 * @param orderType
	 *          orderType
	 * @param subOrderDto
	 *          subOrderDto
	 * @param methodName
	 *          methodName
	 * @param args
	 *          args
	 * @param isSetNull
	 *          set操作是否设置为null
	 * @return Object
	 */
	private Object invokeMethod(String orderType, Object subOrderDto, String methodName, Object args, boolean isSetNull)
	{
		List<Method> methodList = Arrays.asList(subOrderDto.getClass().getMethods());

		Method currentMethod = null;

		for (Method method : methodList)
		{
			if (methodName.equals(method.getName()))
			{
				currentMethod = method;
				break;
			}
		}

		Object value = null;

		if (currentMethod != null)
		{
			try
			{
				if (args == null)
				{
					if (isSetNull)
					{
						value = currentMethod.invoke(subOrderDto, new Object[]
						{ null });
					} else
					{
						value = currentMethod.invoke(subOrderDto);
					}
				} else
				{
					value = currentMethod.invoke(subOrderDto, args);
				}
				return value;
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				logger.debug("No method named " + currentMethod.getName());
			}
		}
		return null;
	}

	@Override
	public List<WorkOrderTypeDto> getWorkOrderType(String roleIds)
	{
		List<WorkOrderTypeDto> typeList = workOrderTypeDtoMapper.selectAll();
		for (WorkOrderTypeDto workOrderTypeDto : typeList)
		{
			orderTypeMap.put(workOrderTypeDto.getType(), workOrderTypeDto);
		}
		
		if (StringUtils.isNotBlank(roleIds)) 
		{
			List<Map<String, Object>> list = serv.getPermissionsInfoByRoleIds(roleIds, 1);
			for (Map<String, Object> map : list) {
				String subTypeId = String.valueOf(map.get("subTypeId"));
				String addPermission = String.valueOf(map.get("addPermission"));
				switch (subTypeId)
				{
				case "N": // 退租
					if (!addPermission.equals("1")) {
						delOrderInfo(typeList, subTypeId);
					}
					break;
					
				case "B": // 保洁
					if (!addPermission.equals("1")) {
						delOrderInfo(typeList, subTypeId);
					}
					break;
				case "C": // 投诉
					if (!addPermission.equals("1")) {
						delOrderInfo(typeList, subTypeId);
					}
					break;
				case "D": // 看房
					if (!addPermission.equals("1")) {
						delOrderInfo(typeList, subTypeId);
					}
					break;
				case "E": // 入住问题
					if (!addPermission.equals("1")) {
						delOrderInfo(typeList, subTypeId);
					}
					break;
				case "F": // 其他
					if (!addPermission.equals("1")) {
						delOrderInfo(typeList, subTypeId);
					}
					break;
				case "G": // 业主维修
					if (!addPermission.equals("1")) {
						delOrderInfo(typeList, subTypeId);
					}
					break;
				case "H": // 维修
					if (!addPermission.equals("1")) {
						delOrderInfo(typeList, subTypeId);
					}
					break;
				case "I": // 例行保洁
					if (!addPermission.equals("1")) {
						delOrderInfo(typeList, subTypeId);
					}
					break;
				}
			}
		}
		Iterator<WorkOrderTypeDto> it = typeList.iterator();
		while (it.hasNext())
		{
			WorkOrderTypeDto dto = it.next();
			if (dto.getType().equals("N"))
			{
				it.remove();
			}
		}
		return typeList;
	}

	private void delOrderInfo(List<WorkOrderTypeDto> typeList, String type)
	{
		Iterator<WorkOrderTypeDto> it = typeList.iterator();
		while(it.hasNext())
		{
			WorkOrderTypeDto mapLists = it.next();
	    if(String.valueOf(mapLists.getType()).equals(type)) 
	    {
	    	it.remove();
	    }
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor =
	{ RuntimeException.class, Exception.class })
	@Override
	public void endProcessWithTrans(Long workOrderId, Long staffId) throws Exception
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDtoById(workOrderId);

		String type = workOrderDto.getType();
		if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
			type = "A";
		}
		ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
		Object subOrderDto = subOrderService.getOrderDetailById(workOrderDto.getRefId(), true);

		this.setFields4EndProcess(workOrderDto.getType(), subOrderDto, staffId);
		subOrderService.updateSubOrderWithTrans(subOrderDto, staffId, true, "");
		Financial financial = (Financial) SpringHelper.getBean("financial");
		if (WorkOrderTypeDef.CANCEL_LEASE_ORDER.equals(workOrderDto.getType()))
		{
			cancelLeaseService.resetAgreementState((CancelLeaseOrderDto) subOrderDto);
		} else if (WorkOrderTypeDef.CLEANING_ORDER.equals(workOrderDto.getType()))
		{
			this.cancelPay(workOrderDto.getId());
		} else if (WorkOrderTypeDef.HOUSE_LOOKING_ORDER.equals(workOrderDto.getType()))
		{
			this.updateRecommendInfo(workOrderDto.getCode());
		} else if (WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER.equals(workOrderDto.getType()) 
				&& (workOrderDto.getSubOrderState().equals(SubOrderStateDef.DO_IN_ORDER) 
						|| workOrderDto.getSubOrderState().equals(SubOrderStateDef.NOT_COST_LIQUIDATION)))
		{
			// 新退租并且当前步骤是管家上门，关闭订单时需要校验合约
			int cancelInfo = cancelLeaseService.updateAgreementState((CancelLeaseOrderDto) subOrderDto);
			if (cancelInfo == 1)
			{
				// 出租合约置为正常，对应房间置为已签约
				cancelLeaseService.resetAgreementNewState((CancelLeaseOrderDto) subOrderDto);
				if (workOrderDto.getSubOrderState().equals(SubOrderStateDef.NOT_COST_LIQUIDATION))
				{
					try
					{
						logger.info("启用账单接口入参 合约id=" + String.valueOf(workOrderDto.getRentalLeaseOrderId()));
						Result result = financial.closeOrderUnFreezeBill(String.valueOf(workOrderDto.getRentalLeaseOrderId()));
						logger.info("启用账单接口返回值 result（1：成功） == " + result.getState());
						if (result.getState() != 1)
						{
							throw new BaseAppException("账单启用失败");
						}
					} catch (Exception e)
					{
						throw new BaseAppException("账单启用失败");
					}
				}
			} else if (cancelInfo == -1)
			{
				throw new BaseAppException("无法关闭订单，因为没有查询到对应房间信息");
			} else if (cancelInfo == -2)
			{
				throw new BaseAppException("无法关闭订单，房间类型未知");
			} else if (cancelInfo == -3)
			{
				throw new BaseAppException("无法关闭订单，房间已经签约");
			} else if (cancelInfo == -4)
			{
				throw new BaseAppException("无法关闭订单，存在已生效或者生效待支付的出租合约");
			}
		} else if (WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER.equals(workOrderDto.getType()) 
				&& workOrderDto.getSubOrderState().equals(SubOrderStateDef.RELEASE_HOUSE_RANK))
		{
			// 新退租订单房源释放环节，判断对应房间是否已经签约
			int cancelInfo = cancelLeaseService.updateAgreementState((CancelLeaseOrderDto) subOrderDto);
			if (cancelInfo == 1)
			{
				cancelLeaseService.resetAgreementNewState((CancelLeaseOrderDto) subOrderDto);
			}
		} else if (WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER.equals(workOrderDto.getType()) 
				&& workOrderDto.getSubOrderState().equals(SubOrderStateDef.REASSIGNING))
		{
			// 新退租订单房重新指派或接单，判断对应房间是否已经签约
			int cancelInfo = cancelLeaseService.updateAgreementState((CancelLeaseOrderDto) subOrderDto);
			if (cancelInfo == 1)
			{
				cancelLeaseService.resetAgreementNewState((CancelLeaseOrderDto) subOrderDto);
			}
		}
		// 可以关闭订单	
		workOrderDto.setSubOrderState(SubOrderStateDef.CLOSED);
		closeWorkOrder(workOrderDto, staffId);
		IProcessTask task = new ProcessTaskImpl();
		task.endProcess(workOrderId);
	}

	/**
	 * 
	 * 
	 * @param code
	 */
	private void updateRecommendInfo(String code)
	{
		RecommendRelationDto relationDto = recommendRelationDtoMapper.selectByPrimaryKey(1L, code);

		if (null != relationDto)
		{
			RecommendInfoDto dto = new RecommendInfoDto();
			dto.setId(relationDto.getRecommendId());
			// 根据推荐主键ID，查询关联关系表中总数
			List<RecommendRelationDto> list = recommendRelationDtoMapper
					.selectTotalByRecommendId(relationDto.getRecommendId());
			if (list.size() == 1)
			{
				Long state = list.get(0).getState();
				if (state == 1L)
				{
					dto.setState(3L);
					dto.setStateTime(DateUtil.getDBDateTime());
					recommendInfoDtoMapper.updateByPrimaryKeySelective(dto);
				}
			}
			relationDto.setState(2L);
			recommendRelationDtoMapper.updateByPrimaryKey(relationDto);
		}

	}

	/**
	 * Description: 设置属性
	 * 
	 * @author jinyanan
	 * @param orderType
	 *          orderType
	 * @param subOrderDto
	 *          subOrderDto
	 * @param staffId
	 *          staffId
	 */
	private void setFields4EndProcess(String orderType, Object subOrderDto, Long staffId)
	{
		this.invokeMethod(orderType, subOrderDto, "clearAssignedDealer", null);
		this.invokeMethod(orderType, subOrderDto, "setActualDealerId", staffId);
		this.invokeMethod(orderType, subOrderDto, "setState", SubOrderStateDef.CLOSED);
		this.invokeMethod(orderType, subOrderDto, "setStateDate", DateUtil.getDBDateTime());
	}

	/**
	 * Description: 关闭订单
	 * 
	 * @author jinyanan
	 * @param staffId
	 *          staffId
	 * @param workOrderDto
	 *          workOrderDto
	 */
	private void closeWorkOrder(WorkOrderDto workOrderDto, Long staffId)
	{
		// 关闭workOrder
		workOrderDto.clearSubField();
		workOrderDto.setSubActualDealerId(staffId);
		workOrderDto.setSubOrderState(SubOrderStateDef.CLOSED);
		workOrderDto.setState(WorkOrderStateDef.DONE);
		workOrderDto.setStateDate(DateUtil.getDBDateTime());
		this.updateWorkOrderWithTrans(workOrderDto);
	}

	/**
	 * Description: 撤销支付
	 * 
	 * @author jinyanan
	 * @param workOrderId
	 *          workOrderId
	 */
	private void cancelPay(Long workOrderId)
	{
		Financial financial = (Financial) SpringHelper.getBean("financial");
		Map<String, String> params = new HashMap<>();
		params.put("order_id", workOrderId.toString());
		int resp = financial.repealOrder(params);
		if (resp != 1)
		{
			throw new BaseAppException("撤销支付失败");
		}
	}

	@Override
	public void assignOrderWithTrans(AssignOrderRequest req, Long staffId)
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDtoById(req.getWorkOrderId());

		String subOrderAssignCommentsAttrPath = getSubOrderAssignCommentsAttrPath(workOrderDto.getType());
		String type = workOrderDto.getType();
		if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
			type = "A";
		}
		ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
		List<SubOrderValueDto> subOrderValueList = new ArrayList<>();
		SubOrderValueDto subOrderValueDto = getSubOrderValueDto(workOrderDto.getType());
		subOrderValueList.add(subOrderValueDto);
		subOrderValueDto.setAttrId(attrService.getAttrByCode("COMMENTS").getId());
		subOrderValueDto.setAttrPath(subOrderAssignCommentsAttrPath);
		subOrderValueDto.setSubOrderId(workOrderDto.getRefId());
		subOrderValueDto.setTextInput(req.getComments());
		subOrderService.assignOrderWithTrans(workOrderDto.getCode(), req.getStaffId(), subOrderValueList, staffId);

	}

	/**
	 * Description: 实例化子订单value实体
	 * 
	 * @author jinyanan
	 * @param type
	 *          type
	 * @return SubOrderValueDto
	 */
	private SubOrderValueDto getSubOrderValueDto(String type)
	{
		switch (type)
		{
		case WorkOrderTypeDef.HOUSE_LOOKING_ORDER:
			return new HouseLookingOrderValueDto();

		case WorkOrderTypeDef.COMPLAINT_ORDER:
			return new ComplaintOrderValueDto();

		case WorkOrderTypeDef.CLEANING_ORDER:
			return new CleaningOrderValueDto();

		case WorkOrderTypeDef.LIVING_PROBLEM_ORDER:
			return new LivingProblemOrderValueDto();

		case WorkOrderTypeDef.OTHER_ORDER:
			return new OtherOrderValueDto();

		case WorkOrderTypeDef.OWNER_REPAIR_ORDER:
			return new OwnerRepairOrderValueDto();

		case WorkOrderTypeDef.REPAIR_ORDER:
			return new RepairOrderValueDto();

		case WorkOrderTypeDef.ROUTINE_CLEANING_ORDER:
			return new RoutineCleaningOrderValueDto();
		default:
			return null;
		}
	}

	/**
	 * Description: 返回指派订单备注对应的attrPath
	 * 
	 * @author jinyanan
	 * @param type
	 *          type
	 * @return String
	 */
	private String getSubOrderAssignCommentsAttrPath(String type)
	{
		switch (type)
		{
		case WorkOrderTypeDef.HOUSE_LOOKING_ORDER:
			return "HOUSE_LOOKING_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS";

		case WorkOrderTypeDef.COMPLAINT_ORDER:
			return "COMPLAINT_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS";

		case WorkOrderTypeDef.CLEANING_ORDER:
			return "CLEANING_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS";

		case WorkOrderTypeDef.LIVING_PROBLEM_ORDER:
			return "LIVING_PROBLEM_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS";

		case WorkOrderTypeDef.OTHER_ORDER:
			return "OTHER_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS";

		case WorkOrderTypeDef.OWNER_REPAIR_ORDER:
			return "OWNER_REPAIR_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS";

		case WorkOrderTypeDef.REPAIR_ORDER:
			return "REPAIR_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS";

		case WorkOrderTypeDef.ROUTINE_CLEANING_ORDER:
			return "ROUTINE_CLEANING_ORDER_PROCESS.CUSTOMER_SERV_ASSIGN.COMMENTS";
		default:
			return null;
		}
	}

	@Override
	public List<SubOrderStateDto> getSubOrderState()
	{
		return subOrderStateDtoMapper.selectAll();
	}

	@Override
	public void getWorkOrderList(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		User currentStaff = this.getUser(request);
		if (null == currentStaff || currentStaff.getId() == null || "".equals(currentStaff.getId()))
		{
			map.put("state", -99999);
			try
			{
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(JSONObject.fromObject(map));
			} catch (Exception e)
			{
				logger.info("错误日志：" + e);
			}
		} else 
		{
			String roleIds1 = currentStaff.getRoles();
			String roleId = "";
			String[] roleArry = roleIds1.split(",");
			for (String string : roleArry)
			{
				if (string.trim().equals("")) {
					continue;
				}
				roleId += string + ",";
			}
			roleId = roleId.substring(0, roleId.length() - 1);
			map.put("currentDealerId", currentStaff.getId());
			map.put("currentDealerRoleIds", roleId);
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "type")))
			{
				if (req.getAjaxValue(request, "type").equals("A"))
				{
					map.put("type", "'A','N'");
				} else 
				{
					map.put("type", "'"+req.getAjaxValue(request, "type")+"'");
				}
			}
			
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "totalScore")))
			{
				map.put("totalScore", req.getAjaxValue(request, "totalScore"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "createdStaffId")))
			{
				map.put("createdStaffId", req.getAjaxValue(request, "createdStaffId"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "createdUserId")))
			{
				map.put("createdUserId", req.getAjaxValue(request, "createdUserId"));
			}
			// 模糊查询
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "keyword")))
			{
				map.put("keyword", req.getAjaxValue(request, "keyword"));
			}
			// 模糊查询
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "name")))
			{
				map.put("name", req.getAjaxValue(request, "name"));
			}
			// 模糊查询
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "userName")))
			{
				map.put("userName", req.getAjaxValue(request, "userName"));
			}
			// 模糊查询
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "userPhone")))
			{
				map.put("userPhone", req.getAjaxValue(request, "userPhone"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "state")))
			{
				map.put("state", req.getAjaxValue(request, "state"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "stateList")))
			{
				map.put("stateList", req.getAjaxValue(request, "stateList"));
			}
			// 查询当前操作人能看到的数据
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "currentDealerFlag")))
			{
				map.put("currentDealerFlag", req.getAjaxValue(request, "currentDealerFlag"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "subOrderState")))
			{
				map.put("subOrderState", req.getAjaxValue(request, "subOrderState"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "code")))
			{
				map.put("code", req.getAjaxValue(request, "code"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "currentDealerDealtOrder")))
			{
				map.put("currentDealerDealtOrder", req.getAjaxValue(request, "currentDealerDealtOrder"));
				map.put("ids", this.getWorkOrderHisIds(Long.valueOf(currentStaff.getId())));
				map.put("currentDealerId", currentStaff.getId());
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "currentDealerStartedOrder")))
			{
				map.put("currentDealerStartedOrder", req.getAjaxValue(request, "currentDealerStartedOrder"));
				map.put("createdStaffId", currentStaff.getId());
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "createdDateStart")))
			{
				map.put("createdDateStart", req.getAjaxValue(request, "createdDateStart"));
			}
			if (StringUtils.isNotEmpty(req.getAjaxValue(request, "createdDateEnd")))
			{
				map.put("createdDateEnd", req.getAjaxValue(request, "createdDateEnd"));
			}
			String isDc = req.getAjaxValue(request, "isDc");

			if (StringUtils.isNotEmpty(isDc))
			{
				getPageMapList(request, response, workOrderDtoMapper, "selectByCond", map, new PageResultDealInterface()
				{
					@Override
					public List<Map<String, Object>> deal(List<Map<String, Object>> list)
					{
						if (null != list  && !list.isEmpty())
						{
							String roleIds = String.valueOf(list.get(0).get("roleIds"));
							List<Map<String, Object>> dataList = serv.getPermissionsInfoByRoleIds(roleIds, 1);
							
							for (Map<String, Object> map : dataList) 
							{
								String subTypeId = String.valueOf(map.get("subTypeId"));
								for (Map<String, Object> maps : list)
								{
									String type = String.valueOf(maps.get("type"));
									if (type.equals("N"))
									{
										// 兼容老订单
										type = "A";
									}
									if (subTypeId.equals(type))
									{
										maps.putAll(map);
									}
								}
							}
						}
						
						// 查询超长时间界限节点
						String timeCode = workOrderDtoMapper.queryTimeCode();
						if (StringUtils.isBlank(timeCode) || timeCode.equals("null"))
						{
							timeCode = "48";
						}
						long to = 0;
						long from = 0;
						for (Map<String, Object> workOrder : list)
						{
							workOrder.put("timeCode", timeCode);
							try
							{
								String type = String.valueOf(workOrder.get("type"));
								if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
								{
									// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
									type = "A";
								}
								ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
								List<Object> subOrderList = subOrderService.getHis(Long.valueOf(String.valueOf(workOrder.get("ref_id"))));

								// 计算总时长
								String createdDate = String.valueOf(workOrder.get("created_date"));
								if (StringUtils.isNotBlank(createdDate) && !createdDate.equals("null"))
								{
									// 格式化订单创建时间
									SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									from = formatter.parse(createdDate).getTime();
									// 规则（判断任务过程是否有评价，有则用评价上一步结束时间-创建时间，若没有则判断订单是否已关闭，已关闭则用关闭时间-创建时间，若未关闭则当前时间-创建时间）
									List<String> stateList = new ArrayList<String>();
									int evaluationIndex = 0;
									for (int i = 0; i < subOrderList.size(); i++)
									{
										Object obj = subOrderList.get(i);
										Class<? extends Object> hisObj = obj.getClass();
										String state = String.valueOf(hisObj.getMethod("getState").invoke(obj));
										if (state.equalsIgnoreCase("H"))
										{
											evaluationIndex = i;
										}
										stateList.add(state);
									}

									String sub_order_state = String.valueOf(workOrder.get("sub_order_state"));
									if (sub_order_state.equals("I") || sub_order_state.equals("H"))
									{
										Object evaluationObj = subOrderList.get(subOrderList.size() - 1);
										Date evaluationUpdateDate = (Date) evaluationObj.getClass().getMethod("getUpdateDate")
												.invoke(evaluationObj);
										to = evaluationUpdateDate.getTime();
									}

									if (stateList.contains("H"))
									{
										// 如果当前订单存在评价，根据评价下标获取上一步的执行时间
										Object evaluationObj = subOrderList.get(evaluationIndex - 1);
										Date evaluationUpdateDate = (Date) evaluationObj.getClass().getMethod("getUpdateDate")
												.invoke(evaluationObj);
										to = evaluationUpdateDate.getTime();
									} else
									{
										if (stateList.contains("I"))
										{
											// 订单关闭
											Object lastObj = subOrderList.get(subOrderList.size() - 1);
											Date lastUpdateDate = (Date) lastObj.getClass().getMethod("getUpdateDate").invoke(lastObj);
											to = lastUpdateDate.getTime();
										} else
										{
											to = new Date().getTime();
										}
									}
									String totalTime = String.valueOf(((to - from) / (1000 * 60 * 60)));
									if (totalTime.contains("-"))
									{
										totalTime = "0";
									}
									workOrder.put("totalTime", totalTime);

									// 计算当前步骤时长
									Object o = subOrderList.get(subOrderList.size() - 1);
									Class<? extends Object> hisClass = o.getClass();
									Date updateDate = (Date) hisClass.getMethod("getUpdateDate").invoke(o);
									to = updateDate.getTime();

									if (String.valueOf(workOrder.get("state")).equalsIgnoreCase("C"))
									{
										// 表示订单已经处理完成，无需计算当前步骤停留时间
										workOrder.put("currentStepTime", "");
									} else
									{
										// 系统当前时间
										from = new Date().getTime();
										// 计算当前步骤停留时间
										String currentStepHours = String.valueOf(((from - to) / (1000 * 60 * 60)));
										if (currentStepHours.contains("-"))
										{
											currentStepHours = "0";
										}
										workOrder.put("currentStepTime", currentStepHours);
									}
								} else
								{
									workOrder.put("currentStepTime", "");
									workOrder.put("totalTime", "");
								}

							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
						return list;
					}
				});
			} else
			{
				getPageMapList(request, response, workOrderDtoMapper, "selectByCond", map, new PageResultDealInterface()
				{

					@Override
					public List<Map<String, Object>> deal(List<Map<String, Object>> list)
					{
						if (null != list && !list.isEmpty())
						{
							String roleIds = String.valueOf(list.get(0).get("roleIds"));
							List<Map<String, Object>> dataList = serv.getPermissionsInfoByRoleIds(roleIds, 1);
							
							for (Map<String, Object> map : dataList) 
							{
								String subTypeId = String.valueOf(map.get("subTypeId"));
								for (Map<String, Object> maps : list)
								{
									String type = String.valueOf(maps.get("type"));
									if (type.equals("N"))
									{
										// 兼容老订单
										type = "A";
									}
									if (subTypeId.equals(type))
									{
										maps.putAll(map);
									}
								}
							}
						}

						
						// 查询超长时间界限节点
						String timeCode = workOrderDtoMapper.queryTimeCode();
						if (StringUtils.isBlank(timeCode) || timeCode.equals("null"))
						{
							timeCode = "48";
						}					
						long to = 0;
						long from = 0;
						for (Map<String, Object> workOrder : list)
						{
							workOrder.put("timeCode", timeCode);
							int workOrderId = (int) workOrder.get("id");
							List<OrderCommentaryDto> orderCommentaryList = orderCommentaryDtoMapper
									.selectByWorkOrderId(Long.valueOf(workOrderId));
							Iterator<OrderCommentaryDto> iterator = orderCommentaryList.iterator();
							String string = "";
							while (iterator.hasNext())
							{
								OrderCommentaryDto orderCommentaryDto = (OrderCommentaryDto) iterator.next();
								string += "\n" + orderCommentaryDto.getTypeName() + ":" + orderCommentaryDto.getScore();
							}
							if (string.length() > 1)
							{
								string = string.substring(1);
							}
							workOrder.put("orderCommentaryList", string);

							try
							{
								String type = String.valueOf(workOrder.get("type"));
								if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
								{
									// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
									type = "A";
								}
								ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
								List<Object> subOrderList = subOrderService.getHis(Long.valueOf(String.valueOf(workOrder.get("ref_id"))));

								// 计算总时长
								String createdDate = String.valueOf(workOrder.get("created_date"));
								if (StringUtils.isNotBlank(createdDate) && !createdDate.equals("null"))
								{
									// 格式化订单创建时间
									SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									from = formatter.parse(createdDate).getTime();
									// 规则（判断任务过程是否有评价，有则用评价上一步结束时间-创建时间，若没有则判断订单是否已关闭，已关闭则用关闭时间-创建时间，若未关闭则当前时间-创建时间）
									List<String> stateList = new ArrayList<String>();
									int evaluationIndex = 0;
									for (int i = 0; i < subOrderList.size(); i++)
									{
										Object obj = subOrderList.get(i);
										Class<? extends Object> hisObj = obj.getClass();
										String state = String.valueOf(hisObj.getMethod("getState").invoke(obj));
										if (state.equalsIgnoreCase("H"))
										{
											evaluationIndex = i;
										}
										stateList.add(state);
									}
									if (stateList.contains("H"))
									{
										// 如果当前订单存在评价，根据评价下标获取上一步的执行时间
										Object evaluationObj = subOrderList.get(evaluationIndex - 1);
										Date evaluationUpdateDate = (Date) evaluationObj.getClass().getMethod("getUpdateDate")
												.invoke(evaluationObj);
										to = evaluationUpdateDate.getTime();
									} else
									{
										if (stateList.contains("I"))
										{
											// 订单关闭
											Object lastObj = subOrderList.get(subOrderList.size() - 1);
											Date lastUpdateDate = (Date) lastObj.getClass().getMethod("getUpdateDate").invoke(lastObj);
											to = lastUpdateDate.getTime();
										} else
										{
											to = new Date().getTime();
										}
									}
									String totalTime = String.valueOf(((to - from) / (1000 * 60 * 60)));
									if (totalTime.contains("-"))
									{
										totalTime = "0";
									}
									workOrder.put("totalTime", totalTime);

									// 计算当前步骤时长
									Object o = subOrderList.get(subOrderList.size() - 1);
									Class<? extends Object> hisClass = o.getClass();
									Date updateDate = (Date) hisClass.getMethod("getUpdateDate").invoke(o);
									to = updateDate.getTime();

									if (String.valueOf(workOrder.get("state")).equalsIgnoreCase("C"))
									{
										// 表示订单已经处理完成，无需计算当前步骤停留时间
										workOrder.put("currentStepTime", "");
									} else
									{
										// 系统当前时间
										from = new Date().getTime();
										// 计算当前步骤停留时间
										String currentStepHours = String.valueOf(((from - to) / (1000 * 60 * 60)));
										if (currentStepHours.contains("-"))
										{
											currentStepHours = "0";
										}
										workOrder.put("currentStepTime", currentStepHours);
									}
								} else
								{
									workOrder.put("currentStepTime", "");
									workOrder.put("totalTime", "");
								}
							} catch (Exception e)
							{
								e.printStackTrace();
							}
						}
						return list;
					}
				});
			}		
		}
	}

	/**
	 * Description:
	 * 
	 * @author jinyanan
	 * @param subActualDealerId
	 *          subActualDealerId
	 * @return String
	 */
	private String getWorkOrderHisIds(Long subActualDealerId)
	{
		WorkOrderHisDto param = new WorkOrderHisDto();
		param.setSubActualDealerId(subActualDealerId);
		List<WorkOrderHisDto> workOrderHisList = workOrderHisDtoMapper.selectByCond(param);
		Set<Long> idSet = new HashSet<>();
		String ids = "";
		for (WorkOrderHisDto workOrderHisDto : workOrderHisList)
		{
			idSet.add(workOrderHisDto.getId());
		}
		for (Long id : idSet)
		{
			ids += id + ",";
		}
		if (StringUtils.isNotEmpty(ids))
		{
			return ids.substring(0, ids.length() - 1);
		}
		return null;
	}

	@Override
	public List<OrderStepDto> getOrderStep(Long workOrderId) throws Exception
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDetailById(workOrderId);
		List<OrderStepDto> orderStepList = new ArrayList<>();
		OrderStepDto orderStep = null;
		String currentSubOrderState = null;
		String type = workOrderDto.getType();
		if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
			type = "A";
		}
		ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
		List<Object> orderHisDtoList = subOrderService.getHis(workOrderDto.getRefId());
		for (Object orderHisDto : orderHisDtoList)
		{
			this.setField4OrderStep(workOrderDto, orderStepList, orderHisDto, OrderStepStateDef.FINISHED, false);
		}

		if (CollectionUtils.isNotEmpty(orderStepList)
				&& SubOrderStateDef.CLOSED.equals(orderStepList.get(orderStepList.size() - 1).getSubOrderState()))
		{
			OrderStepDto lastOrderStep = orderStepList.get(orderStepList.size() - 1);
			lastOrderStep.setStepState(OrderStepStateDef.PROCESSING);
			currentSubOrderState = SubOrderStateDef.CLOSED;
		} else
		{
			Object subOrderDto = subOrderService.getOrderDetailById(workOrderDto.getRefId(), true);
			this.setField4OrderStep(workOrderDto, orderStepList, subOrderDto, OrderStepStateDef.PROCESSING, true);
			currentSubOrderState = this.invokeMethod(workOrderDto.getType(), subOrderDto, "getState", null).toString();
		}

		// 针对PROCESSING步骤为订单关闭的，需要设置处理人为当前处理人
		OrderStepDto processOrderStep = orderStepList.get(orderStepList.size() - 1);
		if (OrderStepStateDef.PROCESSING.equals(processOrderStep.getStepState())
				&& SubOrderStateDef.CLOSED.equals(processOrderStep.getSubOrderState()))
		{
			processOrderStep.setDealerId(workOrderDto.getSubActualDealerId());
			processOrderStep.setDealerName(workOrderDto.getSubActualDealerName());
		}

		if (!SubOrderStateDef.CLOSED.equals(currentSubOrderState))
		{
			IProcessTask task = new ProcessTaskImpl();
			List<ActivitiStepDto> activitiStepList = task.getAllTaskActiviti(workOrderId);
			if (activitiStepList != null && activitiStepList.size() > 0)
			{
				ActivitiStepDto currentActivitiStep = this.getActivitiStepByState(activitiStepList, currentSubOrderState);
				for (ActivitiStepDto activitiStepDto : activitiStepList)
				{
					if (activitiStepDto.getOrderNbr() > currentActivitiStep.getOrderNbr())
					{
						orderStep = new OrderStepDto();
						orderStep.setStepState(OrderStepStateDef.UNFINISHED);
						orderStep.setSubOrderState(activitiStepDto.getOrderState());
						orderStep.setSubOrderStateName(this.getSubOrderState(activitiStepDto.getOrderState()).getStateName());
						orderStepList.add(orderStep);
					}
				}
			}
		}

		// 增加订单完成步骤
		orderStep = new OrderStepDto();
		if (!SubOrderStateDef.CLOSED.equals(currentSubOrderState))
		{
			orderStep.setStepState(OrderStepStateDef.UNFINISHED);
			orderStep.setSubOrderState(SubOrderStateDef.CLOSED);
			orderStep.setSubOrderStateName(this.getSubOrderState(SubOrderStateDef.CLOSED).getStateName());
			orderStepList.add(orderStep);
		}

		for (OrderStepDto orderStepDto : orderStepList)
		{
			// 热线电话
			orderStepDto.setDealerPhone(SystemConfig.getValue("ROOM1000_MOBILE"));
			if (SubOrderStateDef.COMMENT.equals(orderStepDto.getSubOrderState()))
			{
				orderStepDto.setDealerId(null);
				orderStepDto.setDealerName(workOrderDto.getUserName());
				orderStepDto.setDealerPhone(workOrderDto.getUserPhone());
			}
		}

		return orderStepList;

	}

	/**
	 * Description:
	 * 
	 * @author jinyanan
	 * @param workOrderDto
	 *          workOrderDto
	 * @param orderStepList
	 *          orderStepList
	 * @param orderDto
	 *          orderDto
	 * @param stepState
	 *          stepState
	 * @param isProcessOrder
	 *          isProcessOrder
	 */
	private void setField4OrderStep(WorkOrderDto workOrderDto, List<OrderStepDto> orderStepList, Object orderDto,
			String stepState, boolean isProcessOrder)
	{
		OrderStepDto orderStep;
		orderStep = new OrderStepDto();
		Object updatePersonId = this.invokeMethod(workOrderDto.getType(), orderDto, "getUpdatePersonId", null);
		if (updatePersonId != null && !isProcessOrder)
		{
			orderStep.setDealerId((Long) updatePersonId);
		}
		Object updatePersonName = this.invokeMethod(workOrderDto.getType(), orderDto, "getUpdatePersonName", null);
		if (updatePersonName != null && orderStep.getDealerId() != null)
		{
			orderStep.setDealerName(updatePersonName.toString());
		}
		// Object updatePersonPhone = this.invokeMethod(workOrderDto.getType(),
		// orderDto, "getUpdatePersonPhone", null);
		// if (updatePersonPhone != null) {
		// orderStep.setDealerPhone(updatePersonPhone.toString());
		// }
		Object assignedDealerId = this.invokeMethod(workOrderDto.getType(), orderDto, "getAssignedDealerId", null);
		if (assignedDealerId != null && isProcessOrder)
		{
			orderStep.setDealerId((Long) assignedDealerId);
		}
		Object assignedDealerName = this.invokeMethod(workOrderDto.getType(), orderDto, "getAssignedDealerName", null);
		if (assignedDealerName != null && isProcessOrder)
		{
			orderStep.setDealerName(assignedDealerName.toString());
		}
		Object assignedDealerRoleId = this.invokeMethod(workOrderDto.getType(), orderDto, "getAssignedDealerRoleId", null);
		if (assignedDealerRoleId != null && isProcessOrder 
				&& (assignedDealerId == null || String.valueOf(assignedDealerId).equals(String.valueOf(SystemAccountDef.CUSTOMER))))
		{
			orderStep.setDealerRoleId((Long) assignedDealerRoleId);
		}
		Object assignedDealerRoleName = this.invokeMethod(workOrderDto.getType(), orderDto, "getAssignedDealerRoleName",
				null);
		if (assignedDealerRoleName != null && orderStep.getDealerRoleId() != null 
				&& (assignedDealerId == null || String.valueOf(assignedDealerId).equals(String.valueOf(SystemAccountDef.CUSTOMER))))
		{
			String subOrderState = workOrderDto.getSubOrderState();
			if (subOrderState.equals(SubOrderStateDef.REFUND) || subOrderState.equals(SubOrderStateDef.RENTAL_ACCOUNTING) 
					|| subOrderState.equals(SubOrderStateDef.FINANCE_AUDITING) || subOrderState.equals(SubOrderStateDef.MARKETING_EXECUTIVE_AUDITING))
			{
				orderStep.setDealerName(null);
				orderStep.setDealerRoleName(assignedDealerRoleName.toString());				
			}
		}
		orderStep.setStepState(stepState);
		Object state = this.invokeMethod(workOrderDto.getType(), orderDto, "getState", null);
		if (state != null)
		{
			orderStep.setSubOrderState(state.toString());
		}
		Object stateName = this.invokeMethod(workOrderDto.getType(), orderDto, "getStateName", null);
		if (stateName != null)
		{
			orderStep.setSubOrderStateName(stateName.toString());
		}
		Object updateDate = this.invokeMethod(workOrderDto.getType(), orderDto, "getUpdateDate", null);
		if (updateDate != null)
		{
			orderStep.setUpdateDate((Date) updateDate);
		}
		orderStepList.add(orderStep);
	}

	/**
	 * Description:
	 * 
	 * @author jinyanan
	 * @param state
	 *          state
	 * @return SubOrderStateDto
	 */
	private SubOrderStateDto getSubOrderState(String state)
	{
		return subOrderStateDtoMapper.selectByPrimaryKey(state);
	}

	/**
	 * Description:
	 * 
	 * @author jinyanan
	 * @param activitiStepList
	 *          activitiStepList
	 * @param currentSubOrderState
	 *          currentSubOrderState
	 * @return ActivitiStepDto
	 */
	private ActivitiStepDto getActivitiStepByState(List<ActivitiStepDto> activitiStepList, String currentSubOrderState)
	{
		if (SubOrderStateDef.REASSIGNING_IN_STAFF_REVIEW.equals(currentSubOrderState)
				|| SubOrderStateDef.REASSIGNING.equals(currentSubOrderState))
		{
			currentSubOrderState = SubOrderStateDef.TAKE_ORDER;
		} else if (SubOrderStateDef.NOT_PASS_IN_FINANCE_AUDITING.equals(currentSubOrderState)
				|| SubOrderStateDef.NOT_PASS_IN_MARKETING_AUDITING.equals(currentSubOrderState))
		{
			currentSubOrderState = SubOrderStateDef.RENTAL_ACCOUNTING;
		} else if (SubOrderStateDef.NOT_PASS_IN_RENTAL_ACCOUNTING.equals(currentSubOrderState))
		{
			currentSubOrderState = SubOrderStateDef.DO_IN_ORDER;
		} else if (SubOrderStateDef.REFUND.equals(currentSubOrderState))
		{
			currentSubOrderState = SubOrderStateDef.PAY;
		} else if (SubOrderStateDef.WAIT_2_TACE.equals(currentSubOrderState))
		{
			currentSubOrderState = SubOrderStateDef.STAFF_REVIEW;
		}
		for (ActivitiStepDto activitiStepDto : activitiStepList)
		{
			if (currentSubOrderState.equals(activitiStepDto.getOrderState()))
			{
				return activitiStepDto;
			}
		}
		return null;
	}

	@Override
	public <T> List<T> getHis(Long workOrderId)
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDetailById(workOrderId);

		String type = workOrderDto.getType();
		if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
			type = "A";
		}
		ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
		List<T> list = subOrderService.getHis(workOrderDto.getRefId());
		for (int i = 0; i < list.size(); i++)
		{
			Class<? extends Object> hisClass = list.get(i).getClass();
			try
			{
				long to = 0;
				long from = 0;
				if (null != hisClass.getMethod("getUpdateDate").invoke(list.get(i)))
				{
					Date updateDate = (Date) hisClass.getMethod("getUpdateDate").invoke(list.get(i));
					to = updateDate.getTime();
				}
				if (i == 0)
				{
					// 第一条数据，时间差用创建时间计算
					if (null != hisClass.getMethod("getCreatedDate").invoke(list.get(i)))
					{
						Date createDate = (Date) hisClass.getMethod("getCreatedDate").invoke(list.get(i));
						from = createDate.getTime();
					}
				} else
				{
					if (null != hisClass.getMethod("getUpdateDate").invoke(list.get(i - 1)))
					{
						Date updateDate = (Date) hisClass.getMethod("getUpdateDate").invoke(list.get(i - 1));
						from = updateDate.getTime();
					}
				}
				String hours = String.valueOf(((to - from) / (1000 * 60 * 60)));
				if (hours.contains("-"))
				{
					hours = "0";
				}
				hisClass.getMethod("setCurrentStepTime", String.class).invoke(list.get(i), hours);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return list;

	}

	@Override
	public void getWorkOrderList4CustomerService(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String, Object> map = new HashMap<String, Object>();
		
		User currentStaff = this.getUser(request);
		String roleId = "";
		String roleIds1 = currentStaff.getRoles();
		String[] roleArry = roleIds1.split(",");
		for (String string : roleArry)
		{
			if (string.trim().equals("")) {
				continue;
			}
			roleId += string + ",";
		}
		roleId = roleId.substring(0, roleId.length() - 1);
		map.put("roleId", roleId);		
		if (StringUtils.isNotEmpty(req.getAjaxValue(request, "type")))
		{
			if (req.getAjaxValue(request, "type").equals("A"))
			{
				map.put("type", "'A','N'");
			} else 
			{
				map.put("type", "'" + req.getAjaxValue(request, "type") + "'");
			}
		}
		// 模糊查询
		if (StringUtils.isNotEmpty(req.getAjaxValue(request, "keyword")))
		{
			map.put("keyword", req.getAjaxValue(request, "keyword"));
		}
		// 模糊查询
		if (StringUtils.isNotEmpty(req.getAjaxValue(request, "name")))
		{
			map.put("name", req.getAjaxValue(request, "name"));
		}
		// 模糊查询
		if (StringUtils.isNotEmpty(req.getAjaxValue(request, "code")))
		{
			map.put("code", req.getAjaxValue(request, "code"));
		}
		// 模糊查询
		if (StringUtils.isNotEmpty(req.getAjaxValue(request, "userName")))
		{
			map.put("userName", req.getAjaxValue(request, "userName"));
		}
		// 模糊查询
		if (StringUtils.isNotEmpty(req.getAjaxValue(request, "userPhone")))
		{
			map.put("userPhone", req.getAjaxValue(request, "userPhone"));
		}
		if (StringUtils.isNotEmpty(req.getAjaxValue(request, "subOrderState")))
		{
			map.put("subOrderState", req.getAjaxValue(request, "subOrderState"));
		}
		getPageMapList(request, response, workOrderDtoMapper, "select4CustomerService", map, new PageResultDealInterface() {

			@Override
			public List<Map<String, Object>> deal(List<Map<String, Object>> list)
			{
				if (null != list && !list.isEmpty())
				{
					String roleIds = String.valueOf(list.get(0).get("roleIds"));
					List<Map<String, Object>> dataList = serv.getPermissionsInfoByRoleIds(roleIds, 1);
					
					for (Map<String, Object> map : dataList) 
					{
						String subTypeId = String.valueOf(map.get("subTypeId"));
						for (Map<String, Object> maps : list)
						{
							String type = String.valueOf(maps.get("type"));
							if (type.equals("N"))
							{
								// 兼容老订单
								type = "A";
							}
							if (subTypeId.equals(type))
							{
								maps.putAll(map);
							}
						}
					}
				}				
				return list;
			}
		});

	}

	@Override
	public List<TypeCountDto> getTypeCount()
	{
		return workOrderDtoMapper.selectTypeCount();
	}

	@Override
	public List<OrderStepDto> getOrderStep(String code) throws Exception
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDtoByCode(code);
		return this.getOrderStep(workOrderDto.getId());
	}

	@Override
	public String generateOrderName(WorkOrderDto workOrderDto)
	{
		if (StringUtils.isEmpty(workOrderDto.getType()))
		{
			return null;
		}
		String orderName = StringUtils.EMPTY;
		if (orderTypeMap.size() == 0)
		{
			this.getWorkOrderType("");
		}
		orderName += orderTypeMap.get(workOrderDto.getType()).getTypeShortName() + "_";

		if (workOrderDto.getTakeHouseOrderId() != null)
		{
			orderName += agreementDtoMapper.selectByPrimaryKey(workOrderDto.getTakeHouseOrderId()).getName();
		} else if (workOrderDto.getRentalLeaseOrderId() != null)
		{
			orderName += agreementDtoMapper.selectByPrimaryKey(workOrderDto.getRentalLeaseOrderId()).getName();
		} else if (workOrderDto.getHouseId() != null)
		{
			orderName += houseRankDtoMapper.selectByPrimaryKey(workOrderDto.getHouseId()).getTitle();
		} else if (WorkOrderTypeDef.AGENT_APPLY_ORDER.equals(workOrderDto.getType()))
		{
			orderName += workOrderDto.getUserName();
		}

		return orderName;
	}

	@Override
	public List<OrderCommentaryTypeDto> getOrderCommentaryType(String orderType)
	{
		return orderCommentaryTypeDtoMapper.selectByOrderType(orderType);
	}

	@Override
	public void deleteWorkOrder(WorkOrderDto workOrderDto)
	{
		if (StringUtils.isNotEmpty(workOrderDto.getCode()))
		{
			workOrderDto = this.getWorkOrderDtoByCode(workOrderDto.getCode());
		} else
		{
			workOrderDto = this.getWorkOrderDtoById(workOrderDto.getId());
		}
		if (workOrderDto == null)
		{
			throw new BaseAppException("订单不存在");
		}
		if (!WorkOrderStateDef.DONE.equals(workOrderDto.getState()))
		{
			throw new BaseAppException("订单未完成");
		}
		workOrderDto.setState(WorkOrderStateDef.DELETED);
		workOrderDto.setStateDate(DateUtil.getDBDateTime());
		this.updateWorkOrderWithTrans(workOrderDto);
	}

	@Override
	public void doAuditBrokerOrderWithTrans(Long workOrderId, String comments, boolean passed, Long staffId)
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDtoById(workOrderId);
		RankHouseService rankHouseService = (RankHouseService) SpringHelper.getBean("RankHouseService");
		if (passed)
		{
			// 查询合约
			AgreementDto agreementDto = agreementDtoMapper.selectByPrimaryKey(workOrderDto.getRentalLeaseOrderId());
			// 查询房源
			HouseRankDto houseRankDto = houseRankDtoMapper.selectByPrimaryKey(agreementDto.getHouseId());
			// 审批通过接口调用
			RankAgreement param = new RankAgreement();
			param.setId(workOrderDto.getRentalLeaseOrderId().toString());
			// house_rank.house_id
			param.setHouse_id(houseRankDto.getHouseId().toString());
			// yc_agreement_tab.house_id
			param.setRankId(agreementDto.getHouseId().toString());
			param.setOperId(staffId.toString());
			param.setMobile(agreementDto.getUserMobile());
			rankHouseService.approveCApplication(param, workOrderDto.getCode());
		} else
		{
			// 审批未通过接口调用
			RankAgreement param = new RankAgreement();
			param.setId(workOrderDto.getRentalLeaseOrderId().toString());
			param.setOperId(staffId.toString());
			rankHouseService.rejectCApplication(param);
		}
		workOrderDto.setSubComments(comments);
		closeWorkOrder(workOrderDto, staffId);
	}

	@Override
	public void closeBrokerOrder(String code, Long staffId)
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDtoByCode(code);
		if (workOrderDto == null)
		{
			throw new BaseAppException("订单不存在");
		}
		if (!WorkOrderTypeDef.BROKER_ORDER.equals(workOrderDto.getType()))
		{
			throw new BaseAppException("该订单不是经纪人推荐订单");
		}
		this.closeWorkOrder(workOrderDto, staffId);
	}

	@Override
	public Object getSubOrderDetailById(Long workOrderId, Boolean singleDetail)
	{
		WorkOrderDto workOrderDto = this.getWorkOrderDtoById(workOrderId);
		String type = workOrderDto.getType();
		if (type.equals(WorkOrderTypeDef.NEW_CANCEL_LEASE_ORDER))
		{
			// 兼容老退租流程。判断如果是新退租，就修改成老退租，以便能正常获取到退租的 CancelLeaseOrderServiceImpl 类
			type = "A";
		}
		ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(type);
		return subOrderService.getOrderDetailById(workOrderDto.getRefId(), singleDetail);
	}

	@Override
	public Object getWorkOrderTypeByPermission(HttpServletRequest request, HttpServletResponse response)
	{
		User user = getUser(request);
		String roleIds = user.getRoles();
		List<Map<String, Object>> list = serv.getPermissionsInfoByRoleIds(roleIds, 1);
		List<Map<String, Object>> lists = workOrderTypeDtoMapper.selectWorkOrderType();
		for (Map<String, Object> map : list) 
		{
			String subTypeId = String.valueOf(map.get("subTypeId"));
			String addPermission = String.valueOf(map.get("addPermission"));
			switch (subTypeId)
			{
			case "A": // 退租
				if (!addPermission.equals("1")) {
					delOrder(lists, "7");
				}
				break;
			case "B": // 保洁
				if (!addPermission.equals("1")) {
					delOrder(lists, "2");
				}
				break;
			case "C": // 投诉
				if (!addPermission.equals("1")) {
					delOrder(lists, "3");
				}
				break;
			case "D": // 看房
				if (!addPermission.equals("1")) {
					delOrder(lists, "0");
				}
				break;
			case "E": // 入住问题
				if (!addPermission.equals("1")) {
					delOrder(lists, "6");
				}
				break;
			case "F": // 其他
				if (!addPermission.equals("1")) {
					delOrder(lists, "4");
				}
				break;
			case "G": // 业主维修
				if (!addPermission.equals("1")) {
					delOrder(lists, "5");
				}
				break;
			case "H": // 维修
				if (!addPermission.equals("1")) {
					delOrder(lists, "1");
				}
				break;
			case "I": // 例行保洁
				if (!addPermission.equals("1")) {
					delOrder(lists, "9");
				}
				break;
			}
		}
		return lists;
	}
	
	private void delOrder(List<Map<String, Object>> lists, String type)
	{
		Iterator<Map<String, Object>> it = lists.iterator();
		while(it.hasNext()){
	    Map<String, Object> mapLists = it.next();
	    if(String.valueOf(mapLists.get("item_id")).equals(type)) 
	    {
	    	it.remove();
	    }
		}
	}


	
}
