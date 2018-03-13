package com.yc.rm.caas.appserver.bus.service.house;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import pccom.common.util.FtpUtil;
import pccom.web.beans.SystemConfig;

import com.alibaba.fastjson.JSONObject;
import com.yc.rm.caas.appserver.model.user.User;
import com.yc.rm.caas.appserver.base.support.BaseService;
import com.yc.rm.caas.appserver.base.syscfg.dao.ISysCfgDao;
import com.yc.rm.caas.appserver.bus.controller.house.fo.HouseFo;
import com.yc.rm.caas.appserver.bus.dao.house.IHouseDao;
import com.yc.rm.caas.appserver.model.ResultCondition;
import com.yc.rm.caas.appserver.model.contract.AgreementSelect;
import com.yc.rm.caas.appserver.model.house.Area;
import com.yc.rm.caas.appserver.model.house.House;
import com.yc.rm.caas.appserver.model.house.HouseSelectInfo;
import com.yc.rm.caas.appserver.model.house.Project;
import com.yc.rm.caas.appserver.model.house.ProjectInfo;
import com.yc.rm.caas.appserver.model.house.RankAgreement;
import com.yc.rm.caas.appserver.model.house.RankAgreementSelect;
import com.yc.rm.caas.appserver.model.house.RankHouse;
import com.yc.rm.caas.appserver.model.syscfg.DictiItem;
//import com.yc.rm.caas.nbms.report.serv.IMktRepServ;

/**
 * 房源管理
 * 
 * @author suntf
 * @date 2016年11月28日
 */
@Service("_houseServImpl")
public class HouseServImpl extends BaseService implements IHouseServ {

	@Resource
	private IHouseDao _houseDao;
	@Resource
	private ISysCfgDao _sysCfgDao;
	
	@Override
	public ResultCondition resultCondition(String flag) {
		// TODO Auto-generated method stub
		ResultCondition resultCondition = new ResultCondition();
		List<Area> areaList = _sysCfgDao.getAreaList("101", "3");
		List<User> manageList = _sysCfgDao.getManagerList("22");
		resultCondition.setAreas(areaList);
		resultCondition.setUsers(manageList);
		resultCondition.setPublishList(new ArrayList<DictiItem>());
		resultCondition.setStateList("1".equals(flag) ? _sysCfgDao.getDictit("GROUP.AGREEMENT") : _sysCfgDao.getDictit("GROUP.RANK.AGREEMENT"));
		return resultCondition;
	}
	
	/*
	 * @see
	 * com.yc.rm.caas.appserver.bus.service.house.HouseMgrService#getResultCondition()
	 */
	@Override
	public ResultCondition getResultCondition() {
		ResultCondition resultCondition = new ResultCondition();
		try {
			// low b
			resultCondition.setAreas(_sysCfgDao.getAreaList("101", "3"));
			// low b
			resultCondition.setUsers(_sysCfgDao.getManagerList("22"));
			resultCondition.setPublishList(_sysCfgDao.getDictit("BASEHOUSE.PUBLISH"));
			resultCondition.setStateList(_sysCfgDao.getDictit("MOBILE.HOUSE.STATE"));
		} catch (Exception e) {
			log.error(e.toString());
		}
		return resultCondition;
	}

	
	/*
	 * @see com.yc.rm.caas.appserver.bus.service.house.HouseMgrService#getHouseList()
	 */
	@Override
	public List<House> getHouseList(HouseFo fo) {
		List<House> houserList = null;
		String status = fo.getStatus();
		
		if ("1".equals(status)) {
			fo.setStatus("0,1,2,3");
		} else if ("2".equals(status)) {
			fo.setStatus("4,5");
		} else {
			//
		}
		String filePath = SystemConfig.getValue("FTP_HTTP")
				+ SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		fo.setFilepath("|" + filePath);
		fo.setNewfilepath(filePath);
		try {
			houserList = _houseDao.getHouseList(initPageInfo(fo));
		} catch (Exception e) {
			log.error(e.toString());
		}
		// for (int i = 0; i < houserList.size(); i++) {
		// String houserStatus = _houseDao
		// .loadRankHouseStatusByHouseId(houserList.get(i).getId());
		// houserList.get(i).setRankHouseStatus(houserStatus);
		// }
		return houserList;
	}

	/**
	 * 获取商圈信息
	 * 
	 * @param father_id
	 * @param area_type
	 * @return
	 */
	public List<Area> getAreaList(String father_id, String area_type) {
		List<Area> sqList = null;
		try {
			sqList = _sysCfgDao.getAreaList(father_id, area_type);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return sqList;
	}

	/*
	 * 获取房源信息
	 */
	@Override
	public House getHouseInfo(String id) {
		House house = new House();
		String filePath = SystemConfig.getValue("FTP_HTTP")
				+ SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		try {
			house = _houseDao.getHouseInfo(filePath, "|" + filePath, id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return house;
	}

	/*
	 * 获取工程信息
	 */
	@Override
	
	
	
	
	public Project getProjectInfo(String agreementId) {
		Project p = new Project();
		ProjectInfo pm = _houseDao.getProjectMonery(agreementId);
		if (pm == null) {
			pm = new ProjectInfo();
			pm.setFurnituremoney("0");
			pm.setAppliancesmoney("0");
			pm.setOthermoney("0");
		}
		ProjectInfo pInfo = _houseDao.getProjectBaseInfo(pm.getFurnituremoney(),
				pm.getAppliancesmoney(), pm.getOthermoney(), agreementId);
		p.setProjectInfo(pInfo);
		p.setJjpjList(_houseDao.getProjectList(agreementId, " = 1 "));// 家具
		p.setJdpjList(_houseDao.getProjectList(agreementId, " = 2 "));// 家电
		p.setOtherpjList(_houseDao.getProjectList(agreementId, " not in (1,2)"));// 其他
		return p;
	}

	/*
	 * 房源合约下拉框
	 */
	@Override
	public AgreementSelect initAgreementSelect() {
		AgreementSelect select = null;
		try {
			List<User> managerList = _sysCfgDao.getManagerList("22");
			List<DictiItem> payTypeList = _sysCfgDao
					.getDictit("AGREEMENT.PAYTYPE");
			List<DictiItem> rentList = _sysCfgDao.getDictit("GROUP.RENT.YEAR");
			List<DictiItem> decorateList = _sysCfgDao.getDictit("DECORATE.TYPE");
			List<DictiItem> freeRentList = _sysCfgDao.getDictit("FEERENT.TYPE");
			List<DictiItem> propertyList = _sysCfgDao.getDictit("PROPERTY.FILE");
			select = new AgreementSelect(managerList, payTypeList, rentList,
					decorateList, freeRentList, propertyList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return select;
	}

	/*
	 * 加载租赁房源信息
	 */
	@Override
	public List<RankHouse> loadRankHouseList(String house_id) {
		List<RankHouse> list = null;
		try {
			list = _houseDao.loadRankHouseList(house_id);
			List<String> lvList = new ArrayList<>();
			List<DictiItem> jllist = getLvInfo("1");
			for (int i = 0; i < jllist.size(); i++) {
				DictiItem mp = jllist.get(i);
				String cnt = mp.getItem_name();
				lvList.add(cnt);
			}
			Collections.sort(lvList);
			String first = "1";
			String end = "1";
			if (lvList.size() > 0) {
				first = lvList.get(0);
				end = lvList.get(lvList.size() - 1);
			}
			for (int i = 0; i < list.size(); i++) {
				String price = list.get(i).getFee();
				list.get(i).setBaseMonery(price);
				if (list.get(i).getFee() != null
						&& !"".equals(list.get(i).getFee().trim())) {
					try {
						list.get(i)
								.setFee(String.valueOf(new BigDecimal(price)
										.multiply(new BigDecimal(first)))
										+ "~"
										+ String.valueOf(new BigDecimal(price)
												.multiply(new BigDecimal(end))));
					} catch (Exception e) {
						log.error(e.toString());
					}
				}
			}

		} catch (Exception e1) {
			log.error(e1.toString());
		}
		return list;
	}

	/**
	 * 获取月份和租赁类型
	 * 
	 * @param monery
	 * @return
	 */
	public List<DictiItem> getLvInfo(String cnt) {
		return _houseDao.getJlAndPaytype(cnt);
	}

	/*
	 * 租赁房源明细
	 */
	@Override
	public RankHouse loadRankHouseInfo(String rankId) {
		String filepath = SystemConfig.getValue("FTP_HTTP")
				+ SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		RankHouse rank = null;
		try {
			rank = _houseDao.loadRankHouseInfo(filepath, "|" + filepath, rankId);
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return rank;
	}

	/*
	 * 加载出租房源合约信息
	 */
	@Override
	public List<RankAgreement> loadRankAgreementList(String rankId) {
		List<RankAgreement> rankAgreement = null;
		try {
			rankAgreement = _houseDao.loadRankAgreementList(rankId);
		} catch (Exception e) {
			log.error(e.toString());
			e.printStackTrace();
		}
		return rankAgreement;
	}

	/**
	 * 加载合约下拉框
	 * 
	 * @return
	 */
	public RankAgreementSelect loadRankAgreementSelect(JSONObject json) {
		String flag = super.get(json, "flag");
		String monery = super.get(json, "monery");
		List<DictiItem> list = this.getLvInfo(monery);
		List<User> managerList = _sysCfgDao.getManagerList("22");
		if ("0".equals(flag)) {
			List<DictiItem> payTypeList = _sysCfgDao
					.getDictitAll("GROUP.PAYTYPE");
			List<DictiItem> rankDateList = _sysCfgDao
					.getDictitAll("GROUP.RANKDATE");
			return new RankAgreementSelect(payTypeList, rankDateList,
					managerList, list);
		} else {
			List<DictiItem> payTypeList = _sysCfgDao.getDictit("GROUP.PAYTYPE");
			List<DictiItem> rankDateList = _sysCfgDao
					.getDictit("GROUP.RANKDATE");
			return new RankAgreementSelect(payTypeList, rankDateList,
					managerList, list);
		}
	}


	/*
	 * 验证租赁房源状态是否为待签约
	 */
	@Override
	public Object checkRankHouseStatus(String id) {
		Map<String, String> resultMap = new HashMap<>();
		int res = _houseDao.checkRankHouseStatus(id, String.valueOf(COMPLETE));
		if (res == 1) {
			resultMap.put("msg", "");
			resultMap.put("state", "1");
		} else {
			resultMap.put("msg", "房源状态已改变,请重新查看!");
			resultMap.put("state", "0");
		}
		return resultMap;
	}


	/*
	 * 保存用户信息
	 */
	@Override
	@Transactional
	public Object saveHouse(House house, HttpServletRequest request) {
		log.debug("house:" + JSONObject.toJSONString(house));
		// System.out.println("house:"+JSONObject.toJSONString(house));
		String id = house.getId();
		String userId = house.getUserId(); // 用户id
		User u = new User();
		String newPath = "";
		String houseSplit = SystemConfig.getValue("FTP_HOUSE_PATH"); // 房源前缀
		String houseSoure = SystemConfig.getValue("FTP_HOUSE_SRC_PATH"); // 房源前缀
		u.setUserName(house.getUsername());
		u.setUserPhone(house.getMobile());
		u.setAddress("");
		u.setEmail("");
		u.setCertificateno("");
		if (userId == null || "".equals(userId)) {
			log.debug(JSONObject.toJSONString(u));
			_sysCfgDao.saveUserInfo(u);
			userId = String.valueOf(u.getUserId());
		} else {
			u.setUserId(Integer.valueOf(house.getUserId()));
			_sysCfgDao.updateUserInfo(u);
		}
		house.setUserId(userId);
		Map<String, String> resultMap = new HashMap<>();
		// 验证房源名称是否存在
		if (_houseDao.checkHouseNameIsExist(id, house.getHouseName()) > 0) {
			resultMap.put("result", "-1");
			resultMap.put("msg", "房源名称重复！");
			return resultMap;
		}
		String path = house.getAppendix();
		if (id != null && !"".equals(id)) {
			// 修改
		} else {
			// 保存用户信息
			_houseDao.insertHouse(house);
			id = house.getId();
			FtpUtil ftp = null;
			try {
				ftp = new FtpUtil();
				if (!"".equals(path)) {
					String[] pathArray = path.split("\\|");
					for (int j = 0; j < pathArray.length; j++) {
						String tmpPath = UUID.randomUUID().toString()
								.replaceAll("-", "")
								+ pathArray[j].substring(pathArray[j]
										.lastIndexOf("."));
						newPath += ",/" + id + houseSoure + tmpPath;
						// logger.debug(request.getRealPath("/")+pathArray[j]);
						boolean flag = ftp.uploadFile(request.getRealPath("/")
								+ pathArray[j], tmpPath, houseSplit + id
								+ houseSoure);
						if (!flag) {
							TransactionAspectSupport.currentTransactionStatus()
									.setRollbackOnly();
							resultMap.put("result", "-1");
							resultMap.put("msg", "图片上传失败！");
							return resultMap;
						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				resultMap.put("result", "-1");
				resultMap.put("msg", "图片上传失败！");
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				return resultMap;
			} finally {
				if (ftp != null) {
					ftp.closeServer();
				}
			}
			if (!"".equals(newPath)) {
				newPath = newPath.substring(1).replace(",", "|");
			}
			_houseDao.updateHouseCode(house.getRegionId(), house.getId(),
					newPath);
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功！");
		}
		return resultMap;
	}

	/*
	 * 加载小区信息
	 */
	@Override
	public List<com.yc.rm.caas.appserver.model.house.Area> loadGroupList(
			HouseFo condition) {
		// TODO Auto-generated method stub
		return _houseDao.loadGroupList(initPageInfo(condition));
	}

	/**
	 * 验证房源状态
	 * 
	 * @param json
	 * @return
	 */
	public Object checkHouseState(JSONObject json) {
		String id = super.get(json, "id"); // 编号id
		String status = super.get(json, "status"); // 房子状态
		Map<String, String> returnMap = new HashMap<>();
		returnMap.put("state", "1");
		returnMap.put("msg", "");
		if (_houseDao.checkHouseState(id, status) != 1) {
			returnMap.put("state", "-1");
			returnMap.put("msg", "房源状态已改变,请重新查看");
			return returnMap;
		}
		return returnMap;
	}

	/*
	 * 删除房源信息
	 */
	@Override
	@Transactional
	public Object delHouseInfo(JSONObject json) {
		// TODO Auto-generated method stub
		Map<String, String> returnMap = new HashMap<>();
		String id = super.get(json, "id"); // 编号id
		String operId = super.get(json, "operId"); // 操作人
		if ("".equals(id) || "".equals(operId)) {
			returnMap.put("state", "-1");
			returnMap.put("msg", "非法访问");
			return returnMap;
		}
		_houseDao.updateHouseInfo(" id = '" + id + "'", id);
		// 验证房源状态
		if (_houseDao.checkHouseState(id, "0") != 1) {
			returnMap.put("state", "-1");
			returnMap.put("msg", "房源状态已改变,请重新查看");
			return returnMap;
		}
		_houseDao.delHouseInfo(id, operId);
		returnMap.put("state", "1");
		returnMap.put("msg", "操作成功");
		return returnMap;
	}

	/*
	 * 交接房源
	 */
	@Override
	@Transactional
	public Object transferHouse(JSONObject json) {
		// TODO Auto-generated method stub
		Map<String, String> returnMap = new HashMap<>();
		String id = super.get(json, "id"); // 编号id
		String isPass = super.get(json, "isPass"); // 是否通过 9 通过 5 否
		if ("".equals(id) || "".equals(isPass)) {
			returnMap.put("state", "-1");
			returnMap.put("msg", "非法访问");
			return returnMap;
		}
		_houseDao.updateHouseInfo(" id = '" + id + "'", id);
		// 验证房源状态
		if (_houseDao.checkHouseState(id, "6") != 1) {
			returnMap.put("state", "-1");
			returnMap.put("msg", "房源状态已改变,请重新查看");
			return returnMap;
		}
		_houseDao.updateHouseInfo(" house_status = '" + isPass
				+ "', update_time = now() ", id);
		returnMap.put("state", "1");
		returnMap.put("msg", "操作成功");
		return returnMap;
	}

	/**
	 * 验证房源是否是自己网格内房源
	 */
	@Override
	public int isSelfHouse(RankAgreement rankAgreement) {
		return _houseDao.checkIsSelfGrid(rankAgreement.getHouse_id(),
				rankAgreement.getAgents());
	}

	/*
	 * 提交房源
	 */
	@Override
	@Transactional
	public Object submitHouse(JSONObject json) {
		// TODO Auto-generated method stub
		Map<String, String> returnMap = new HashMap<>();
		String id = super.get(json, "id"); // 编号id
		String operId = super.get(json, "operId"); // 操作人
		if ("".equals(id) || "".equals(operId)) {
			returnMap.put("state", "-1");
			returnMap.put("msg", "非法访问");
			return returnMap;
		}
		_houseDao.updateHouseInfo(" id = '" + id + "'", id);
		// 验证房源状态
		if (_houseDao.checkHouseState(id, "0") != 1) {
			returnMap.put("state", "-1");
			returnMap.put("msg", "房源状态已改变,请重新查看");
			return returnMap;
		}
		_houseDao.updateHouseInfo(" id = '" + id + "', `operid` = '" + operId
				+ "', house_status = 1 ", id);
		returnMap.put("state", "1");
		returnMap.put("msg", "操作成功");
		return returnMap;
	}

	/*
	 * 审批房源
	 */
	@Override
	public Object spHouse(JSONObject json) {
		// TODO Auto-generated method stub
		Map<String, String> returnMap = new HashMap<>();
		String id = super.get(json, "id"); // 编号id
		String operId = super.get(json, "operId"); // 操作人
		String isPass = super.get(json, "isPass"); // 是否通过 0 否 2
		if ("".equals(id) || "".equals(operId) || "".equals(isPass)) {
			returnMap.put("state", "-1");
			returnMap.put("msg", "非法访问");
			return returnMap;
		}
		_houseDao.updateHouseInfo(" id = '" + id + "'", id);
		log.debug("A");
		// 验证房源状态
		if (_houseDao.checkHouseState(id, "1") != 1) {
			returnMap.put("state", "-1");
			returnMap.put("msg", "房源状态已改变,请重新查看");
			return returnMap;
		}
		_houseDao.updateHouseInfo(" id = '" + id + "', `operid` = '" + operId
				+ "', house_status =  '" + isPass + "', update_time = now() ",
				id);
		log.debug("B");
		returnMap.put("state", "1");
		returnMap.put("msg", "操作成功"); 
		return returnMap;
	}

	/**
	 * 初始化页面信息
	 * 
	 * @param condition
	 * @return
	 */
	public HouseFo initPageInfo(HouseFo condition) {
		int pageNumber = condition.getStartPage();
		int pageSize = condition.getPageSize();
		condition.setStartPage(pageNumber * pageSize);
		return condition;
	}

	
	/**
	 * 获取首页数量
	 * 
	 * @param json
	 * @return
	 * @date 2017年2月6日
	 */
	/*
	@Override
	public Map<String, String> loadHousePageCnt(JSONObject json,
			IMktRepServ mktRepServ) {
		String g_id = super.get(json, "g_id");
		Map<String, String> resultMap = new HashMap<>();
		if (!"".equals(g_id) && !"-1".equals(g_id)) {
			resultMap.put("approveCnt",
					String.valueOf(_houseDao.loadApprovalHouseCnt(g_id))); // 待验收
			// BigInteger allAreadyHouseCnt = new
			// BigInteger(String.valueOf(_houseDao.allRankHouse(g_id))).add(new
			// BigInteger(String.valueOf(_houseDao.flatShareHouse(g_id))));
			BigInteger allHouseCnt = new BigInteger(String.valueOf(_houseDao
					.loadSignAllHouse(g_id)));
			BigInteger allAreadyHouseCnt = new BigInteger(
					String.valueOf(_houseDao.readySignRankHouse(g_id)));
			resultMap.put("allHouseCnt", String.valueOf(allHouseCnt));
			resultMap.put("rankCnt",
					String.valueOf(allHouseCnt.subtract(allAreadyHouseCnt))); // 待出租
			// resultMap.put("rankCnt",
			// String.valueOf(allHouseCnt.min(allAreadyHouseCnt))); // 待出租
			// BigInt allRankHouse = new BigInt(_houseDao.loadAllHouse(g_id));
			// BigInt flatHouse = new BigInt(_houseDao.flatShareHouse(g_id));
			resultMap.put("payCnt", String.valueOf(_houseDao.loadPayCnt(g_id))); // 待付款
			resultMap.put("expireCnt",
					String.valueOf(_houseDao.loadRankExpireCnt(g_id))); // 将要过期
			resultMap.put("allAreadyHouseCnt",
					String.valueOf(allAreadyHouseCnt)); // 已经出租的房源
			Map mp = mktRepServ.getMarketData(g_id, "", allAreadyHouseCnt,
					allHouseCnt);
			String cost_val = super.get(mp, "cost_val");
			String empty_rate = super.get(mp, "empty_rate");
			resultMap.put("cost_val", cost_val);
			resultMap.put("empty_rate", empty_rate);
			String rate = _houseDao.payMentRate(g_id);
			resultMap.put("payMentRate", (rate == null || "".equals(rate) ? "0"
					: rate));
			return resultMap;
		} else {
			resultMap.put("approveCnt", "0");
			resultMap.put("rankCnt", "0");
			resultMap.put("allHouseCnt", "0");
			resultMap.put("payCnt", "0");
			resultMap.put("expireCnt", "0");
			resultMap.put("allAreadyHouseCnt", "0");
			resultMap.put("cost_val", "0");
			resultMap.put("empty_rate", "0");
			resultMap.put("payMentRate", "0");
			return resultMap;
		}
	}
	*/
	/**
	 * 代缴费列表
	 * 
	 * @param json
	 * @return
	 * @date 2017年2月6日
	 */
	@Override
	public List<Map<String, String>> loadPayCntList(JSONObject json, HttpServletRequest request) {
		String userId = request.getHeader("userid");
		String keyWord = super.get(json, "keyWord");
		String startPage = super.get(json, "startPage");
		String pageSize = super.get(json, "pageSize");
		String areaid = super.get(json, "areaid");
		String trading = super.get(json, "trading");
		String regx = "^\\d+$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(startPage);
		if (!matcher.matches()) {
			startPage = "0";
		}
		matcher = pattern.matcher(pageSize);
		if (!matcher.matches()) {
			pageSize = "25";
		}
		List<Map<String, String>> resultList = _houseDao.loadPayCntList(userId,
				keyWord, String.valueOf(new BigInteger(startPage)
						.multiply(new BigInteger(pageSize))), pageSize, areaid,
				trading);
		if (resultList == null) {
			return new ArrayList<>();
		}
		return resultList;
	}

	/* (non-Javadoc)
	 * @see com.yc.rm.caas.appserver.bus.service.house.HouseMgrService#getHouseSelectInfo()
	 */
	@Override
	public HouseSelectInfo getHouseSelectInfo() {
		// TODO Auto-generated method stub
		return null;
	}
}
