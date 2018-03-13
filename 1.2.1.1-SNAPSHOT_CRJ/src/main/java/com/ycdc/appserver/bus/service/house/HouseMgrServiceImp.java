package com.ycdc.appserver.bus.service.house;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.raising.framework.utils.md5.MD5Utils;
import com.raising.framework.utils.rsa.RSAUtil;
import com.ycdc.appserver.bus.dao.house.HouseMapper;
import com.ycdc.appserver.bus.dao.syscfg.SyscfgMapper;
import com.ycdc.appserver.bus.dao.user.IUserDao;
import com.ycdc.appserver.bus.service.syscfg.AES_CBCUtils;
import com.ycdc.appserver.bus.service.syscfg.Apis;
import com.ycdc.appserver.bus.service.syscfg.SysCfgService;
import com.ycdc.appserver.model.house.Agreement;
import com.ycdc.appserver.model.house.AgreementSelect;
import com.ycdc.appserver.model.house.House;
import com.ycdc.appserver.model.house.HouseCondition;
import com.ycdc.appserver.model.house.HouseSelectInfo;
import com.ycdc.appserver.model.house.Project;
import com.ycdc.appserver.model.house.ProjectInfo;
import com.ycdc.appserver.model.house.ProjectList;
import com.ycdc.appserver.model.house.RankAgreement;
import com.ycdc.appserver.model.house.RankAgreementSelect;
import com.ycdc.appserver.model.house.RankHouse;
import com.ycdc.appserver.model.house.ResultCondition;
import com.ycdc.appserver.model.syscfg.Area;
import com.ycdc.appserver.model.syscfg.CABean;
import com.ycdc.appserver.model.syscfg.DictiItem;
import com.ycdc.appserver.model.user.User;
import com.ycdc.appserver.util.ConvertNum;
import com.ycdc.appserver.util.PhoneRegular;
import com.ycdc.core.plugin.sms.SmsSendContants;
import com.ycdc.core.plugin.sms.SmsSendMessage;
import com.ycdc.nbms.report.serv.IMktRepServ;

import net.sf.json.JSONObject;
import net.sourceforge.pinyin4j.PinyinHelper;
import pccom.common.util.DateHelper;
import pccom.common.util.FtpUtil;
import pccom.common.util.StringHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.server.BaseService;
import pccom.web.server.agreement.AgreementMgeService;

/**
 * 房源管理
 * 
 * @author suntf
 * @date 2016年11月28日
 */
@Service("miHService")
public class HouseMgrServiceImp extends BaseService implements HouseMgrService 
{

	public static final int WAIT_SUBMIT = 0; // 待提交
	public static final int WAIT_APPROVE = 1; // 待审批
	public static final int DEING = 2; // 施工中
	public static final int COMPLETE = 3; // 已发布
	public static final int AGREE_ING = 4;// 签约中
	public static final int RANT_OUT = 5; // 已租
	public static final int LOST = 6; // 失效
	public static final int SOLDOUT = 7; // 已下架
	private static boolean RUN_TYPE = false; // 是否集成测试
	@Resource
	HouseMapper houseMapper;
	@Resource
	SyscfgMapper syscfgMapper;
	@Autowired
	SysCfgService sysCfgService;
	@Autowired
	Financial financial;
	@Autowired
	pccom.web.interfaces.RankHouse rankHouse;
	@Autowired
	PhoneRegular phoneRegular;
	@Resource
	IUserDao userMapper;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@PostConstruct
	private void checkTestType()
	{
		try
		{
			List<DictiItem> list = syscfgMapper.getDictit("RUN.TYPE.TEST");
			RUN_TYPE = "TEST".equals(list.get(0).getItem_value().toUpperCase());
			if (RUN_TYPE)
			{
				logger.info("集成测试开启");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/*
	 * @see com.ycdc.appserver.bus.service.house.HouseMgrService#getHouseList()
	 */
	@Override
	public List<House> getHouseList(HouseCondition condition)
	{
		String status = condition.getStatus();
		if (!"".equals(status))
		{
			if ("1".equals(status))
			{
				condition.setStatus("0,1,2,3");
			} else if ("2".equals(status))
			{
				condition.setStatus("4,5");
			}
		}
		String filePath = SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		condition.setFilepath("|" + filePath);
		condition.setNewfilepath(filePath);
		List<House> houserList = houseMapper.getHouseList(initPageInfo(condition));
		for (int i = 0; i < houserList.size(); i++)
		{
			House house = houserList.get(i);
			String houserStatus = houseMapper.loadRankHouseStatusByHouseId(house.getId());
			house.setRankHouseStatus(houserStatus);
			houserList.set(i, house);
		}
		return houserList;
	}

	/**
	 * 获取商圈信息
	 * 
	 * @param fater_id
	 * @param area_type
	 * @return
	 */
	public List<Area> getAreaList(String fater_id, String area_type)
	{
		return syscfgMapper.getAreaList(fater_id, area_type);
	}

	/*
	 * @see
	 * com.ycdc.appserver.bus.service.house.HouseMgrService#getResultCondition()
	 */
	@Override
	public ResultCondition getResultCondition()
	{
		List<Area> areaList = syscfgMapper.getAreaList("101", "3");
		List<User> manageList = syscfgMapper.getManagerList("22");
		List<DictiItem> publishList = syscfgMapper.getDictit("BASEHOUSE.PUBLISH");
		List<DictiItem> stateList = syscfgMapper.getDictit("MOBILE.HOUSE.STATE");
		ResultCondition resultCondition = new ResultCondition();
		resultCondition.setAreas(areaList);
		resultCondition.setUsers(manageList);
		resultCondition.setPublishList(publishList);
		resultCondition.setStateList(stateList);
		return resultCondition;
	}

	/*
	 * 获取房源下拉框信息
	 */
	@Override
	public HouseSelectInfo getHouseSelectInfo()
	{
		HouseSelectInfo h = new HouseSelectInfo();
		h.setDecorateList(syscfgMapper.getDictit("DECORATE.TYPE"));
		h.setRentList(syscfgMapper.getDictit("GROUP.RENT.YEAR"));
		h.setShiList(syscfgMapper.getDictit("GROUP.SPEC"));
		h.setTingList(syscfgMapper.getDictit("GROUP.TING"));
		h.setWeiList(syscfgMapper.getDictit("GROUP.WEI"));
		h.setPayTypeList(syscfgMapper.getDictit("AGREEMENT.PAYTYPE"));
		h.setChuList(syscfgMapper.getDictit("GROUP.KITCHEN"));
		h.setYangList(syscfgMapper.getDictit("GROUP.BALCONY"));
		h.setLeftList(syscfgMapper.getDictit("GROUP.LIFT"));
		h.setPurposeList(syscfgMapper.getDictit("GROUP.PURPOSE"));
		return h;
	}

	/*
	 * 获取房源信心
	 */
	@Override
	public House getHouseInfo(String id)
	{
		String filePath = SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		House house = houseMapper.getHouseInfo(filePath, "|" + filePath, id);
		if (house == null)
		{
			return new House();
		}
		return house;
	}

	/*
	 * 获取工程信息
	 */
	@Override
	public Project getProjectInfo(String agreementId)
	{
		ProjectInfo pm = houseMapper.getProjectMonery(agreementId);
		if (pm == null)
		{
			pm = new ProjectInfo();
			pm.setFurnituremoney("0");
			pm.setAppliancesmoney("0");
			pm.setOthermoney("0");
		}
		ProjectInfo pInfo = houseMapper.getProjectBaseInfo(pm.getFurnituremoney(), pm.getAppliancesmoney(),
				pm.getOthermoney(), agreementId);
		List<ProjectList> jjpjList = houseMapper.getProjectList(agreementId, " = 1 "); // 家具
		List<ProjectList> jdpjList = houseMapper.getProjectList(agreementId, " = 2 "); // 家电
		List<ProjectList> otherpjList = houseMapper.getProjectList(agreementId, " not in (1,2)"); // 其他
		Project p = new Project();
		p.setProjectInfo(pInfo);
		p.setJjpjList(jjpjList);
		p.setJdpjList(jdpjList);
		p.setOtherpjList(otherpjList);
		return p;
	}

	/*
	 * 获取合约信息
	 */
	@Override
	public Agreement getAgreementInfo(String agreementId, String house_id)
	{
		String filePath = SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		Agreement agreement = houseMapper.getAgreementInfo(filePath, "|" + filePath, agreementId, house_id);
		if (agreement == null)
		{
			return new Agreement();
		}
		return agreement;
	}

	/*
	 * 房源合约下拉框
	 */
	@Override
	public AgreementSelect initAgreementSelect()
	{
		List<User> managerList = syscfgMapper.getManagerList("22");
		List<DictiItem> payTypeList = syscfgMapper.getDictit("AGREEMENT.PAYTYPE");
		List<DictiItem> rentList = syscfgMapper.getDictit("GROUP.RENT.YEAR");
		List<DictiItem> decorateList = syscfgMapper.getDictit("DECORATE.TYPE");
		List<DictiItem> freeRentList = syscfgMapper.getDictit("FEERENT.TYPE");
		List<DictiItem> propertyList = syscfgMapper.getDictit("PROPERTY.FILE");
		return new AgreementSelect(managerList, payTypeList, rentList, decorateList, freeRentList, propertyList);
	}

	/*
	 * 加载租赁房源信息
	 */
	@Override
	public List<RankHouse> loadRankHouseList(String house_id)
	{
		List<RankHouse> list = houseMapper.loadRankHouseList(house_id);
		if (list == null)
		{
			return new ArrayList<>();
		}
		List<String> lvList = new ArrayList<>();
		List<DictiItem> jllist = getLvInfo("1");
		for (int i = 0; i < jllist.size(); i++)
		{
			DictiItem mp = jllist.get(i);
			String cnt = mp.getItem_name();
			lvList.add(cnt);
		}
		Collections.sort(lvList);
		String first = "1";
		String end = "1";
		if (lvList.size() > 0)
		{
			first = lvList.get(0);
			end = lvList.get(lvList.size() - 1);
		}
		List<RankHouse> newList = new ArrayList<>();
		for (RankHouse rankHouse : list)
		{
			String price = rankHouse.getFee();
			rankHouse.setBaseMonery(price);
			if (!"".equals(price))
			{
				try
				{
					rankHouse.setFee(String.valueOf(new BigDecimal(price).multiply(new BigDecimal(first))) + "~"
							+ String.valueOf(new BigDecimal(price).multiply(new BigDecimal(end))));
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			newList.add(rankHouse);
		}
		return newList;
	}

	/**
	 * 获取月份和租赁类型
	 * 
	 * @param monery
	 * @return
	 */
	public List<DictiItem> getLvInfo(String cnt)
	{
		return houseMapper.getJlAndPaytype(cnt);
	}

	/*
	 * 租赁房源明细
	 */
	@Override
	public RankHouse loadRankHouseInfo(String rankId)
	{
		String filepath = SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		RankHouse rank = houseMapper.loadRankHouseInfo(filepath, "|" + filepath, rankId);
		if (rank == null)
		{
			return new RankHouse();
		}
		return rank;
	}

	/*
	 * 加载出租房源合约信息
	 */
	@Override
	public List<RankAgreement> loadRankAgreementList(String rankId)
	{
		List<RankAgreement> rankAgreement = houseMapper.loadRankAgreementList(rankId);
		if (rankAgreement == null)
		{
			return new ArrayList<>();
		}
		return rankAgreement;
	}

	/**
	 * 加载合约下拉框
	 * 
	 * @return
	 */
	public RankAgreementSelect loadRankAgreementSelect(JSONObject json)
	{
		// 发布表主键id
		String id = StringHelper.get(json, "id");
		String flag = StringHelper.get(json, "flag");
		String monery = StringHelper.get(json, "monery");
		List<DictiItem> list = this.getLvInfo(monery);
		List<DictiItem> rentDepositList = this.getRentDepositList(list);
		List<User> managerList = syscfgMapper.getManagerList("22");
		List<DictiItem> payTypeList = new ArrayList<>();
		List<DictiItem> rankDateList = new ArrayList<>();
		List<Map<String, Object>> infos = new ArrayList<>();
		if ("0".equals(flag))
		{
			payTypeList = syscfgMapper.getDictitAll("GROUP.PAYTYPE");
			rankDateList = syscfgMapper.getDictitAll("GROUP.RANKDATE");
		} else
		{
			payTypeList = syscfgMapper.getDictit("GROUP.PAYTYPE");
			rankDateList = syscfgMapper.getDictit("GROUP.RANKDATE");
		}
		
		if (null == id || id.equals("") || id.equals("null"))
		{
			return new RankAgreementSelect(payTypeList, rankDateList, managerList, list, infos, rentDepositList);
		} else 
		{
			// 可出租的天数（业主合约到期时间与当前时间的天数差额）
			infos = syscfgMapper.getLeaseDay(id);
			if (null == infos || infos.isEmpty())
			{
				infos = new ArrayList<>();
				return new RankAgreementSelect(payTypeList, rankDateList, managerList, list, infos, rentDepositList);
			}
			infos = infos.subList(0, 1);
		}
		return new RankAgreementSelect(payTypeList, rankDateList, managerList, list, infos, rentDepositList);
	}

	/**
	 * 计算押金
	 * @param monery
	 * @return
	 */
	private List<DictiItem> getRentDepositList(List<DictiItem> list)
	{
		DictiItem item = null;
		List<DictiItem> resultList = new ArrayList<>();
		for (DictiItem info : list)
		{
			// 租金
			String item_name = info.getItem_name();
			String item_id = info.getItem_id();
			String item_value = info.getItem_value();
			// 计算押金
			item = houseMapper.getRentDeposit(item_name, item_id, item_value);
			if (null != item)
				resultList.add(item);
		}
		return resultList;
	}

	/*
	 * 出租合约明细
	 */
	@Override
	public List<RankAgreement> getRankAgreementInfo(JSONObject json)
	{
		String flag = StringHelper.get(json, "flag"); // 0是合约id，1是房间id
		String agreementId = "";
		if ("1".equals(flag))
		{
			String id = StringHelper.get(json, "id");
			agreementId = houseMapper.getRankAgreementIdByRankHouseId(id);
			logger.debug(agreementId);
		} else
		{
			agreementId = StringHelper.get(json, "id");
		}
		String filepath = SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		logger.debug("duanyongrui  filepath  --  "+filepath);
		// 获取待退租配置时长（孙诚明 2017-12-06）
		String time_configure = houseMapper.getTimeConfigure();
		if (null == time_configure)
		{
			time_configure = "30";
		}
		List<RankAgreement> rankAgreement = houseMapper.getRankAgreementInfo(filepath, "|" + filepath, agreementId, time_configure);
		if (rankAgreement == null)
		{
			return new ArrayList<RankAgreement>();
		}
		return rankAgreement;
	}

	/*
	 * 通过手机号码加载用户信息
	 */
	@Override
	public User getUserInfoByMobile(String mobile)
	{
		List<User> u = syscfgMapper.getUserInfoByMobile(mobile);
		if (u == null || u.isEmpty())
		{
			User user = new User();
			user.setStatus("0");
			return user;
		} else if (u.size() == 1)
		{
			User user = u.get(0);
			user.setStatus("1");
			return user;
		} else
		{
			User user = new User();
			user.setStatus("-1");
			user.setLoginMsg("手机号码重复,请联系技术部处理!");
			return user;
		}
	}

	/**
	 * 保存经纪人合约信息
	 */
	/*
	 * @see
	 * com.ycdc.appserver.bus.service.house.HouseMgrService#saveCRankAgreement
	 * (com.ycdc.appserver.model.house.RankAgreement)
	 */
	@Override
	@Transactional
	public Object saveCRankAgreement(RankAgreement rankAgreement, HttpServletRequest request)
	{
		int ifCTerminal = 1;
		rankAgreement.setIfCTerminal(ifCTerminal);
		logger.debug("-----ifC:-----" + ifCTerminal);
		logger.info("-----into saveCRankAgreement-----");
		logger.debug("rankAgreement:" + JSONObject.fromObject(rankAgreement));
		// 用户操作
		rankAgreement.setOperId("-2");
		rankAgreement.setAgents(houseMapper.selectAgentId(rankAgreement.getHouse_id()));
		User us = new User();
		us = userMapper.getUserId(rankAgreement.getBroker(), "");
		logger.debug("us:id " + us.getId() + "mobile " + us.getMobile() + "username " + us.getName());
		rankAgreement.setBroker(us.getId());
		logger.info("------------------------------------------------------------------");
		logger.debug("operid:" + rankAgreement.getOperId());
		logger.debug("agents:" + rankAgreement.getAgents());
		Map<String, String> resultMap = new HashMap<String, String>();
		// 判断该用户是否接受过其他推荐
		if (houseMapper.ifRecommend(rankAgreement.getMobile()) > 0)
		{
			resultMap.put("result", "-2");
			resultMap.put("msg", "该用户已被推荐并且认可推荐，请推荐其他用户！");
			return resultMap;
		}
		// 判断该推荐人今天推荐签约的数量
		if (houseMapper.countOfRankAgreement(us.getId()) > Integer.valueOf(SystemConfig.getValue("BROKER_RECOMMEND_TOTAL")))
		{
			resultMap.put("result", "-2");
			resultMap.put("msg", "今天推荐签约数量超限，请明天进行推荐！");
			return resultMap;
		}
		boolean existsAgreement = true;
		// 验证房间是存在有效合约，status=2
		String maxEndTime = houseMapper.loadValidRankAgreementMaxEndTime(rankAgreement.getRankId());
		if (maxEndTime == null || "".equals(maxEndTime))
		{
			maxEndTime = rankAgreement.getBegin_time();
			existsAgreement = false;
		}
		// 续签
		else
		{
			// 开始时间必须大于结束时间
			String bgTime = rankAgreement.getBegin_time();
			if (bgTime == null || "".equals(bgTime))
			{
				resultMap.put("result", "-2");
				resultMap.put("msg", "开始时间为空!");
				return resultMap;
			}
			if (Integer.parseInt(bgTime.replaceAll("-", "")) - Integer.parseInt(maxEndTime.replaceAll("-", "")) <= 0)
			{
				resultMap.put("result", "-2");
				resultMap.put("msg", "签约开始时间必须大于前一个合约的结束时间");
				return resultMap;
			}
		}
		Map<String, String> signMp = houseMapper.checkDays(rankAgreement.getBegin_time());
		if (signMp != null && !signMp.isEmpty())
		{
			resultMap.put("result", "-2");
			resultMap.put("msg", "签约不能提前" + StringHelper.get(signMp, "item_id") + "天");
			return resultMap;
		}
		String houseId = rankAgreement.getHouse_id();
		String newPath = "";
		String houseSplit = "/" + houseId + SystemConfig.getValue("FTP_RANK_AGREEMENT_PATH"); // 房源分隔
		// 判断房源状态
		houseMapper.updateRankHouse("id = " + rankAgreement.getRankId(), rankAgreement.getRankId());
		// 获取用户信息
		List<User> u = syscfgMapper.getUserInfoByMobile(rankAgreement.getMobile());
		String userId = rankAgreement.getUser_id();
		User user = new User();
		if (u == null || u.isEmpty())
		{
			// 用户不存在 添加用户
			user.setCertificateno(rankAgreement.getCertificateno());
			user.setName(rankAgreement.getUsername());
			user.setMobile(rankAgreement.getMobile());
			user.setCa_author("0");
			user.setEmail(rankAgreement.getEmail());
			user.setPwd(StringHelper.getEncrypt("123456"));
			syscfgMapper.saveUserInfo(user);
			// 自动注册，发送短信
			String phoneNumber = user.getMobile();
			Map<String, String> map = new HashMap<String, String>();
			map.put("username", phoneNumber);
			SmsSendMessage.smsSendMessage(phoneNumber, map, SmsSendContants.NO_RREGISTER);
			userId = user.getId();
			rankAgreement.setUser_id(userId);
		} else
		{
			user = u.get(0);
			// 用户身份证号与合约身份证号不一致
			if (rankAgreement.getCertificateno() != null && !"".equals(rankAgreement.getCertificateno())
					&& !rankAgreement.getCertificateno().equals(user.getCertificateno()))
			{
				user.setCertificateno(rankAgreement.getCertificateno());
			}
			if (rankAgreement.getEmail() != null && !"".equals(rankAgreement.getEmail()))
			{
				user.setEmail(rankAgreement.getEmail());
			}
			if (rankAgreement.getUsername() != null && !"".equals(rankAgreement.getUsername()))
			{
				user.setName(rankAgreement.getUsername());
			}
			rankAgreement.setUser_id(user.getId());
		}
		userId = rankAgreement.getUser_id();
		logger.debug("userId:" + userId);
		// 是否CA验证，0否
		String caAuthor = user.getCa_author();
		logger.debug("caAuthor:" + caAuthor);
		// 需要同步ca认证
		String errorCode = "-1";
		if (RUN_TYPE)
		{
			logger.info("集成测试，跳过CA用户认证");
		} else
		{
			if ("0".equals(caAuthor))
			{
				int i = 0;
				try
				{
					while (i < 2)
					{
						String resultCA = sysCfgService.authentorUser(user, "A");
						logger.info("==同步用户==");
						if (!"".equals(resultCA))
						{
							errorCode = JSONObject.fromObject(resultCA).getString("errorCode");
							if ("0".equals(errorCode))
							{
								break;
							}
						}
						i++;
					}
				} catch (Exception e)
				{
					resultMap.put("result", "-1");
					resultMap.put("msg", "CA用户认证失败!");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultMap;
				}
				logger.debug(JSONObject.fromObject(user).toString());
				if (!"0".equals(errorCode))
				{
					resultMap.put("result", "-1");
					resultMap.put("msg", "CA用户认证失败!");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultMap;
				}
			}
		}
		errorCode = "-1";
		user.setCertificateno(rankAgreement.getCertificateno());
		user.setName(rankAgreement.getUsername());
		user.setCa_author("1");
		user.setEmail(rankAgreement.getEmail());
		syscfgMapper.updateUserInfo(user);
		try
		{
			String id = rankAgreement.getId();
			// rank_status=3,已发布
			int res = houseMapper.checkRankHouseStatus(rankAgreement.getRankId(), String.valueOf(COMPLETE));
			if (res == 0 && !existsAgreement)
			{
				// 返回结果 -2
				resultMap.put("result", "0");
				resultMap.put("msg", "房源状态已经改变,请重新操作!");
				return resultMap;
			}
			// 获取合约的区域和fatherid
			Agreement agreement = houseMapper.getRankAgreementFatherId(rankAgreement.getHouse_id());
			String areaid = agreement.getAreaid();
			String fatherId = agreement.getId();
			String rankCode = rankAgreement.getRank_code();
			rankAgreement.setFather_id(fatherId);
			rankAgreement.setRank_code(rankCode);
			rankAgreement.setAreaid(areaid);
			logger.debug("-----insert-----" + JSONObject.fromObject(rankAgreement));
			// 插入经纪人记录
			String recomid = "";
			recomid = houseMapper.selectRecoInfo(us.getId(), rankAgreement.getRankId(), rankAgreement.getMobile());
			if (recomid != "" && recomid != null)
			{
				resultMap.put("result", "-1");
				resultMap.put("msg", "你已为该用户推荐过此房间，请换一个房间进行推荐!");
				return resultMap;
			} else
			{
				houseMapper.insertRecommend(rankAgreement.getUsername(), rankAgreement.getMobile(), areaid, us.getId(),
						rankAgreement.getRankId());
				recomid = houseMapper.selectRecoInfo(us.getId(), rankAgreement.getRankId(), rankAgreement.getMobile());
			}
			// 保存出租合约，状态为11待公证
			houseMapper.insertRankAgreement(rankAgreement);
			// 合约ID
			id = rankAgreement.getId();
			logger.debug("openid:" + us.getId() + "houseid:" + rankAgreement.getRankId() + "mobile:"
					+ rankAgreement.getMobile());
			logger.info("------------------------------------------------");
			logger.debug("recommend_id:" + recomid);
			logger.info("------------------------------------------------");
			houseMapper.insertRecoRelation(String.valueOf(recomid), id);
			String picPath = "";
			if (rankAgreement.getFile_path() != null && !"".equals(rankAgreement.getFile_path()))
			{
				picPath = rankAgreement.getFile_path().replace(",", "|");
			}

			if (!RUN_TYPE)
			{
				// 上传图片
				FtpUtil ftp = null;
				try
				{
					ftp = new FtpUtil();
					if (!"".equals(picPath))
					{
						String[] pathArray = picPath.split("\\|");
						for (int j = 0; j < pathArray.length; j++)
						{
							String tmpPath = UUID.randomUUID().toString().replaceAll("-", "")
									+ pathArray[j].substring(pathArray[j].lastIndexOf("."));
							newPath += "," + houseSplit + id + "/" + tmpPath;
							boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
									SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + id + "/");
							if (!flag)
							{
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								resultMap.put("result", "-1");
								resultMap.put("msg", "图片上传失败!");
								return resultMap;
							}
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					resultMap.put("result", "-1");
					resultMap.put("msg", "图片上传失败!");
					return resultMap;
				} finally
				{
					if (ftp != null)
					{
						ftp.closeServer();
					}
				}
			}
			if (!"".equals(newPath))
			{
				newPath = newPath.substring(1).replace(",", "|");
			}
			// 更新code码和图片路径
			houseMapper.updateRankAgreemtCode(rankCode, newPath, id);
			String aesKey = RandomStringUtils.random(16, true, true);
			Map<String, String> params = new HashMap<String, String>(); // 合约要显示的参数
			params.put("username", rankAgreement.getUsername());
			params.put("certificateno", rankAgreement.getCertificateno());
			params.put("address", rankAgreement.getAddress());
			params.put("roomcnt", rankAgreement.getTotalRoomCnt());
			params.put("rentroomcnt", rankAgreement.getRentRoomCnt());
			params.put("roomarea", rankAgreement.getRoomarea());
			params.put("numbermonery", rankAgreement.getPrice());
			params.put("numbermonery2", rankAgreement.getRent_deposit());
			params.put("stringmonery2", ConvertNum.NumToChinese(rankAgreement.getRent_deposit()));
			params.put("servermonery", rankAgreement.getServiceMonery());
			params.put("propertymonery", rankAgreement.getPropertyMonery());
			params.put("stringmonery", ConvertNum.NumToChinese(rankAgreement.getPrice()));
			params.put("bgyear", rankAgreement.getBegin_time().split("-")[0]);
			params.put("bgmonth", rankAgreement.getBegin_time().split("-")[1]);
			params.put("bgday", rankAgreement.getBegin_time().split("-")[2]);
			if ("0".equals(rankAgreement.getPayway()))
			{
				params.put("payway", "支付宝");
			} else if ("1".equals(rankAgreement.getPayway()))
			{
				params.put("payway", "微信");
			} else if ("2".equals(rankAgreement.getPayway()))
			{
				params.put("payway", "银行卡");
			}
			Map mp = houseMapper.calcEndTime(rankAgreement.getBegin_time(), rankAgreement.getRent_month(), "1");
			String endDay = StringHelper.get(mp, "endtime");
			params.put("endyear", endDay.split("-")[0]);
			params.put("endmonth", endDay.split("-")[1]);
			params.put("endday", endDay.split("-")[2]);
			params.put("month", rankAgreement.getRent_month());
			String currentday = DateHelper.getToday();
			params.put("currentday", currentday);
			params.put("watercard", rankAgreement.getWatercard());
			params.put("electriccard", rankAgreement.getElectriccard());
			params.put("gascard", rankAgreement.getGascard());
			params.put("water_meter", rankAgreement.getWater_meter());
			params.put("electricity_meter", "总值:" + rankAgreement.getElectricity_meter());
			params.put("electricity_meter_h", " 峰值:" + rankAgreement.getElectricity_meter_h());
			params.put("electricity_meter_l", " 谷值:" + rankAgreement.getElectricity_meter_l());
			params.put("gas_meter", rankAgreement.getGas_meter());
			params.put("currenyear", currentday.split("-")[0]);
			params.put("currenmonth", currentday.split("-")[1]);
			params.put("day", currentday.split("-")[2]);
			params.put("pay_type", rankAgreement.getPay_type());
			params.put("deposit", rankAgreement.getDeposit());
			String old_matched = rankAgreement.getOld_matched();
			if (null != old_matched && !"".equals(old_matched))
			{
				String[] innerCnt = new String[0];
				String[] outCnt = new String[0];
				if (old_matched.split("##").length == 1)
				{
					innerCnt = old_matched.split("##")[0].split("\\|");
				} else if (old_matched.split("##").length == 2)
				{
					innerCnt = old_matched.split("##")[0].split("\\|");
					outCnt = old_matched.split("##")[1].split("\\|");
				}
				for (int i = 0; i < innerCnt.length; i++)
				{
					params.put(innerCnt[i].split("=")[0], innerCnt[i].split("=").length > 1 ? innerCnt[i].split("=")[1] : "");
				}
				for (int i = 0; i < outCnt.length; i++)
				{
					params.put(outCnt[i].split("=")[0], outCnt[i].split("=").length > 1 ? outCnt[i].split("=")[1] : "");
				}
			}
			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("userid", userId);
			contentMap.put("docid", rankAgreement.getId());
			contentMap.put("sign_type", "1");
			contentMap.put("parent_docid", "0");
			List<Map<String, Object>> signDocs = new ArrayList<>();
			Map<String, Object> signDoc = new HashMap<>();
			signDoc.put("sign_doc_title", rankAgreement.getName());
			signDoc.put("template_id", Apis.TEMPLATE_ID);
			signDoc.put("element_template", params);
			logger.debug("params:" + params);
			logger.debug(JSONObject.fromObject(params).toString());
			signDocs.add(signDoc);
			contentMap.put("sign_docs", signDocs);
			String contentmapString = com.alibaba.fastjson.JSONObject.toJSONString(contentMap);// JSONObject.fromObject(contentMap).toString();
			String encryptContent = AES_CBCUtils.encrypt(aesKey, contentmapString.getBytes("utf-8"));
			String encryptedKey = RSAUtil.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(), Apis.clientPrivateKey);
			String bodyText = null;
			if (RUN_TYPE)
			{
				logger.info("跳过合约认证，返回成功");
				bodyText = "{\"errorCode\":\"0\"}";
			}
			// CA处理
			else
			{
				logger.info("-----进行CA-----");
				bodyText = sysCfgService.postMessage(new CABean(Apis.synRankAgreement, Apis.APIID, encryptContent, signed,
						encryptedKey));
			}
			logger.debug("bodyText:" + bodyText);
			// bodyText = "{\"errorCode\":\"0\"}";
			if (!"".equals(bodyText))
			{
				try
				{
					errorCode = JSONObject.fromObject(bodyText).getString("errorCode");
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				logger.debug("errorCode:" + errorCode);
				if ("0".equals(errorCode))
				{
					if (RUN_TYPE)
					{
						resultMap.put("result", "-1");
						resultMap.put("msg", "CA同步到测试桩，请进行付款");
						return resultMap;
					} else
					{
						return rankCAgreementNotarization(rankAgreement.getId(), userId, rankAgreement.getOperId(), houseId, "0");
					}
				}
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultMap.put("result", "-1");
			resultMap.put("msg", "CA同步合同信息失败!");
			return resultMap;
		} catch (Exception e)
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			resultMap.put("result", "-1");
			resultMap.put("msg", "图片上传失败!");
			return resultMap;
		}
	}

	/**
	 * 保存合约信息
	 */
	/*
	 * @see com.ycdc.appserver.bus.service.house.HouseMgrService#saveRankAgreement
	 * (com.ycdc.appserver.model.house.RankAgreement)
	 */
	@Override
	@Transactional
	public Object saveRankAgreement(RankAgreement rankAgreement, HttpServletRequest request)
	{
		logger.debug("rankAgreement:" + JSONObject.fromObject(rankAgreement));
		Map<String, String> resultMap = new HashMap<String, String>();
		
		// 校验手机号码
		String mobile = String.valueOf(rankAgreement.getMobile().trim());
		if (StringUtils.isBlank(mobile))
		{
      resultMap.put("result", "-2");
      resultMap.put("msg", "手机号码不能为空，请填写！");
      return resultMap;
		}

		boolean isLegal = phoneRegular.phoneNumIsLegal(mobile);
		if (!isLegal)
		{
      resultMap.put("result", "-2");
      resultMap.put("msg", "手机号码不合法，请重新填写！");
      return resultMap;
		}
		
    String serviceMoney = String.valueOf(rankAgreement.getServiceMonery()).trim();
    String propertyMoney = String.valueOf(rankAgreement.getPropertyMonery()).trim();
    Pattern pattern = Pattern.compile("^\\d+\\.\\d+$");
    if (serviceMoney.equals(""))
    {
      resultMap.put("result", "-2");
      resultMap.put("msg", "服务费不能为空，请填写！");
      return resultMap;
    }
    Matcher isServiceMoney = pattern.matcher(serviceMoney);
    if (isServiceMoney.matches())
    {
      serviceMoney = serviceMoney.substring(serviceMoney.indexOf(".") + 1, serviceMoney.length());
      if (serviceMoney.length() > 2)
      {
        resultMap.put("result", "-2");
        resultMap.put("msg", "服务费只能填写两位小数！");
        return resultMap;
      }
    }
    
    if (propertyMoney.equals(""))
    {
      resultMap.put("result", "-2");
      resultMap.put("msg", "物业费不能为空，请填写！");
      return resultMap;
    }
    
    Matcher isPropertyMoney = pattern.matcher(propertyMoney);
    if (isPropertyMoney.matches())
    {
      propertyMoney = propertyMoney.substring(propertyMoney.indexOf(".") + 1, propertyMoney.length());
      if (propertyMoney.length() > 2)
      {
        resultMap.put("result", "-2");
        resultMap.put("msg", "物业费只能填写两位小数！");
        return resultMap;
      }
    }   
		
		Map<String, String> notPayMap = houseMapper.checkAgreementISNotPay(rankAgreement.getRankId());
		if (notPayMap != null && "1".equals(StringHelper.get(notPayMap, "cnt")))
		{
			resultMap.put("result", "-2");
			resultMap.put("msg", "该房间可能已被其他管家签约,请重新到出租合约管理中查看后在操作！");
			return resultMap;
		}
		
		/*
		 *  校验押金的值
		 *  1、根据用户输入的租金和租期(rent_month)、付款方式(pay_type)计算押金
		 *  2、判断用户输入的押金不能小于计算之后的押金：如果不正确，增加一个返回码（result=-10：msg=正确的押金是：XX，输入不能小于xx）
		 */
		String rentDeposit = houseMapper.getRentDepositByPrice(rankAgreement.getPrice(), rankAgreement.getRent_month(), rankAgreement.getPay_type());
		if (null == rentDeposit || rentDeposit.equals("null") || rentDeposit.equals("")) 
		{
			resultMap.put("result", "-2");
			resultMap.put("msg", "无法查询到押金系数，请联系管理员！");
			return resultMap;
		}
		
		// 2 判断押金
		double rentDepositDoubleReq = Double.valueOf(rankAgreement.getRent_deposit());
		double rentDepositDouble = Double.valueOf(rentDeposit);
		if (rentDepositDoubleReq < rentDepositDouble)
		{
			resultMap.put("result", "-10");
			resultMap.put("msg", "押金不能小于【" + rentDepositDouble + "】，请重新输入。");
			return resultMap;
		}
		
		boolean existsAgreement = true;
		// 验证房间是存在有效合约
		String maxEndTime = houseMapper.loadValidRankAgreementMaxEndTime(rankAgreement.getRankId());
		if (maxEndTime == null || "".equals(maxEndTime))
		{
			maxEndTime = rankAgreement.getBegin_time();
			existsAgreement = false;
		} else
		{
			// 开始时间必须大于结束时间
			// 2017-07-05insertRankAgreement，将合约开始时间向后延迟1小时，用于解决当前到期当天可以签合约的问题
			String bgTime = rankAgreement.getBegin_time() + " 01:00:00";
			if (bgTime == null || "".equals(bgTime))
			{
				resultMap.put("result", "-2");
				resultMap.put("msg", "开始时间为空!");
				return resultMap;
			}
			DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try
			{
				Date beginT = fmt.parse(bgTime);
				Date maxEndT = fmt.parse(maxEndTime + " 00:00:00");

				logger.debug("beginT:" + beginT);
				logger.debug("maxEndT:" + maxEndT);

				if (!beginT.after(maxEndT))
				{
					resultMap.put("result", "-2");
					resultMap.put("msg", "签约开始时间必须大于前一个合约的结束时间");
					return resultMap;
				}
			} catch (ParseException e)
			{
				e.printStackTrace();
				logger.error("e:" + e);
			}
		}
		Map<String, String> signMp = houseMapper.checkDays(rankAgreement.getBegin_time());
		if (signMp != null && !signMp.isEmpty())
		{
			resultMap.put("result", "-2");
			resultMap.put("msg", "签约不能提前" + StringHelper.get(signMp, "item_id") + "天");
			return resultMap;
		}
		String houseId = rankAgreement.getHouse_id();
		String newPath = "";
		String houseSplit = "/" + houseId + SystemConfig.getValue("FTP_RANK_AGREEMENT_PATH"); // 房源分隔
		logger.debug("rankid=" + rankAgreement.getRankId() + "house_id=" + rankAgreement.getHouse_id());
		// 获取用户信息
		List<User> u = syscfgMapper.getUserInfoByMobile(rankAgreement.getMobile());
		String userId = rankAgreement.getUser_id();
		User user = new User();
		if (u == null || u.isEmpty())
		{
			// 用户不存在 添加用户
			user.setCertificateno(rankAgreement.getCertificateno());
			// 更新用户性别和出生年月
			user.setSex(rankAgreement.getSex());
			user.setBirthday(rankAgreement.getBirthday());
			user.setProvince(rankAgreement.getProvince());
			user.setName(rankAgreement.getUsername());
			user.setMobile(rankAgreement.getMobile());
			user.setCa_author("0");
			user.setEmail(rankAgreement.getEmail());
			syscfgMapper.saveUserInfo(user);
			userId = user.getId();
			rankAgreement.setUser_id(userId);
		} else
		{
			user = u.get(0);
			// 用户身份证号与合约身份证号不一致
			if (rankAgreement.getCertificateno() != null && !"".equals(rankAgreement.getCertificateno())
					&& !rankAgreement.getCertificateno().equals(user.getCertificateno()))
			{
				user.setCertificateno(rankAgreement.getCertificateno());
			}
			if (rankAgreement.getEmail() != null && !"".equals(rankAgreement.getEmail()))
			{
				user.setEmail(rankAgreement.getEmail());
			}
			if (rankAgreement.getUsername() != null && !"".equals(rankAgreement.getUsername()))
			{
				user.setName(rankAgreement.getUsername());
			}
			if (rankAgreement.getSex() != 0) {
				user.setSex(rankAgreement.getSex());
			}
			if (rankAgreement.getBirthday() != null && !"".equals(rankAgreement.getBirthday())) {
				user.setBirthday(rankAgreement.getBirthday());
			}
			if (rankAgreement.getProvince() != null && !"".equals(rankAgreement.getProvince())) {
				user.setProvince(rankAgreement.getProvince());
			}
			rankAgreement.setUser_id(user.getId());
		}
		userId = rankAgreement.getUser_id();
		logger.debug("userId:" + userId);
		String caAuthor = user.getCa_author();
		logger.debug("caAuthor:" + caAuthor);
		// 需要同步ca认证
		String errorCode = "-1";
		if (RUN_TYPE)
		{
			logger.info("集成测试，跳过CA用户认证");
		} else
		{
			if ("0".equals(caAuthor))
			{
				int i = 0;
				try
				{
					while (i < 2)
					{
						String resultCA = sysCfgService.authentorUser(user, "A");
						logger.info("==同步用户==");
						if (!"".equals(resultCA))
						{
							errorCode = JSONObject.fromObject(resultCA).getString("errorCode");
							if ("0".equals(errorCode))
							{
								break;
							}
						}
						i++;
					}
				} catch (Exception e)
				{
					resultMap.put("result", "-1");
					resultMap.put("msg", "CA用户认证失败!");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultMap;
				}
				logger.debug(JSONObject.fromObject(user).toString());
				if (!"0".equals(errorCode))
				{
					resultMap.put("result", "-1");
					resultMap.put("msg", "CA用户认证失败!");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultMap;
				}
			}
		}
		errorCode = "-1";
		user.setCertificateno(rankAgreement.getCertificateno());
		user.setName(rankAgreement.getUsername());
		user.setCa_author("1");
		user.setEmail(rankAgreement.getEmail());
		syscfgMapper.updateUserInfo(user);
		try
		{
			String id = rankAgreement.getId();
			int res = houseMapper.checkRankHouseStatus(rankAgreement.getRankId(), String.valueOf(COMPLETE));
			if (res == 0 && !existsAgreement)
			{
				// 返回结果 -2
				resultMap.put("result", "0");
				resultMap.put("msg", "房源状态已经改变,请重新操作!");
				return resultMap;
			}
			// 获取合约的区域和fatherid
			Agreement agreement = houseMapper.getRankAgreementFatherId(rankAgreement.getHouse_id());
			String areaid = agreement.getAreaid();
			String fatherId = agreement.getId();
			String rankCode = rankAgreement.getRank_code();
			rankAgreement.setFather_id(fatherId);
			rankAgreement.setRank_code(rankCode);
			rankAgreement.setAreaid(areaid);
			logger.debug(JSONObject.fromObject(rankAgreement).toString());
			houseMapper.insertRankAgreement(rankAgreement);
			id = rankAgreement.getId();
			String picPath = rankAgreement.getFile_path().replace(",", "|");
			if (!RUN_TYPE)
			{
				// 上传图片
				FtpUtil ftp = null;
				try
				{
					ftp = new FtpUtil();
					if (!"".equals(picPath))
					{
						String[] pathArray = picPath.split("\\|");
						for (int j = 0; j < pathArray.length; j++)
						{
							String tmpPath = UUID.randomUUID().toString().replaceAll("-", "")
									+ pathArray[j].substring(pathArray[j].lastIndexOf("."));
							newPath += "," + houseSplit + id + "/" + tmpPath;
							boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
									SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + id + "/");
							if (!flag)
							{
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								resultMap.put("result", "-1");
								resultMap.put("msg", "图片上传失败!");
								return resultMap;
							}
						}
					}
				} catch (Exception e)
				{
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					resultMap.put("result", "-1");
					resultMap.put("msg", "图片上传失败!");
					return resultMap;
				} finally
				{
					if (ftp != null)
					{
						ftp.closeServer();
					}
				}
			}
			if (!"".equals(newPath))
			{
				newPath = newPath.substring(1).replace(",", "|");
			}
			// 更新id
			houseMapper.updateRankAgreemtCode(rankCode, newPath, id);
			// 获取该房源下所有要出租房源信息
			if (!existsAgreement)
			{
				List<RankHouse> rankHouseList = houseMapper.loadRankHouseList(rankAgreement.getHouse_id());
				String rankId = rankAgreement.getRankId();
				for (RankHouse rankHouse : rankHouseList)
				{
					if (rankId.equals(rankHouse.getId()))
					{
						if ("已发布".equals(rankHouse.getRankstatus()))
						{
							if ("整租".equals(rankHouse.getRanktype()))
							{
								for (RankHouse rank_House : rankHouseList)
								{
									if (!rankId.equals(rank_House.getId()))
									{
										// 撤销 合租房源
										houseMapper.updateRankHouseStatus(String.valueOf(LOST), rankAgreement.getOperId(),
												rank_House.getId());
									} else
									{
										houseMapper.updateRankHouseStatus(String.valueOf(AGREE_ING), rankAgreement.getOperId(),
												rankHouse.getId());
									}
								}
							} else
							{
								for (RankHouse rank_House : rankHouseList)
								{
									if (!rankId.equals(rank_House.getId()) && "整租".equals(rank_House.getRanktype()))
									{
										// 撤销 整租房源
										houseMapper.updateRankHouseStatus(String.valueOf(LOST), rankAgreement.getOperId(),
												rank_House.getId());
									}
								}
								// 修改当前房源为签约中
								houseMapper.updateRankHouseStatus(String.valueOf(AGREE_ING), rankAgreement.getOperId(),
										rankHouse.getId());
							}
						}
					}
				}
			}
			String aesKey = RandomStringUtils.random(16, true, true);
			Map<String, String> params = new HashMap<String, String>(); // 合约要显示的参数
			params.put("username", rankAgreement.getUsername());
			params.put("certificateno", rankAgreement.getCertificateno());
			params.put("address", rankAgreement.getAddress());
			params.put("roomcnt", rankAgreement.getTotalRoomCnt());
			params.put("rentroomcnt", rankAgreement.getRentRoomCnt());
			params.put("roomarea", rankAgreement.getRoomarea());
			params.put("numbermonery", rankAgreement.getPrice());
			params.put("numbermonery2", rankAgreement.getRent_deposit());
			params.put("stringmonery2", ConvertNum.NumToChinese(rankAgreement.getRent_deposit()));
			params.put("servermonery", rankAgreement.getServiceMonery());
			params.put("propertymonery", rankAgreement.getPropertyMonery());
			params.put("stringmonery", ConvertNum.NumToChinese(rankAgreement.getPrice()));
			params.put("bgyear", rankAgreement.getBegin_time().split("-")[0]);
			params.put("bgmonth", rankAgreement.getBegin_time().split("-")[1]);
			params.put("bgday", rankAgreement.getBegin_time().split("-")[2]);
			if ("0".equals(rankAgreement.getPayway()))
			{
				params.put("payway", "支付宝");
			} else if ("1".equals(rankAgreement.getPayway()))
			{
				params.put("payway", "微信");
			} else if ("2".equals(rankAgreement.getPayway()))
			{
				params.put("payway", "银行卡");
			}
			Map mp = houseMapper.calcEndTime(rankAgreement.getBegin_time(), rankAgreement.getRent_month(), "1");
			String endDay = StringHelper.get(mp, "endtime");
			params.put("endyear", endDay.split("-")[0]);
			params.put("endmonth", endDay.split("-")[1]);
			params.put("endday", endDay.split("-")[2]);
			params.put("month", rankAgreement.getRent_month());
			String currentday = DateHelper.getToday();
			params.put("currentday", currentday);
			params.put("watercard", rankAgreement.getWatercard());
			params.put("electriccard", rankAgreement.getElectriccard());
			params.put("gascard", rankAgreement.getGascard());
			params.put("water_meter", rankAgreement.getWater_meter());
			params.put("electricity_meter", "总值:" + rankAgreement.getElectricity_meter());
			params.put("electricity_meter_h", " 峰值:" + rankAgreement.getElectricity_meter_h());
			params.put("electricity_meter_l", " 谷值:" + rankAgreement.getElectricity_meter_l());
			params.put("gas_meter", rankAgreement.getGas_meter());
			params.put("currenyear", currentday.split("-")[0]);
			params.put("currenmonth", currentday.split("-")[1]);
			params.put("day", currentday.split("-")[2]);
			params.put("pay_type", rankAgreement.getPay_type());
			// 传递给 CA 付款周期
			List<DictiItem> payTypeList = syscfgMapper.getDictitAll("GROUP.PAYTYPE");
			for (DictiItem item : payTypeList) {
				if (item.getItem_id().equals(rankAgreement.getPay_type())) {
					params.put("paytypename", item.getItem_name());
					params.put("paydescription", item.getRemark());
					break;
				}
			}
			params.put("deposit", rankAgreement.getDeposit());
			String old_matched = rankAgreement.getOld_matched();
			if (null != old_matched && !"".equals(old_matched))
			{
				String[] innerCnt = new String[0];
				String[] outCnt = new String[0];
				if (old_matched.split("##").length == 1)
				{
					innerCnt = old_matched.split("##")[0].split("\\|");
				} else if (old_matched.split("##").length == 2)
				{
					innerCnt = old_matched.split("##")[0].split("\\|");
					outCnt = old_matched.split("##")[1].split("\\|");
				}
				for (int i = 0; i < innerCnt.length; i++)
				{
					params.put(innerCnt[i].split("=")[0], innerCnt[i].split("=").length > 1 ? innerCnt[i].split("=")[1] : "");
				}
				for (int i = 0; i < outCnt.length; i++)
				{
					params.put(outCnt[i].split("=")[0], outCnt[i].split("=").length > 1 ? outCnt[i].split("=")[1] : "");
				}
			}
			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("userid", userId);
			contentMap.put("docid", rankAgreement.getId());
			contentMap.put("sign_type", "1");
			contentMap.put("parent_docid", "0");
			List<Map<String, Object>> signDocs = new ArrayList<>();
			Map<String, Object> signDoc = new HashMap<>();
			signDoc.put("sign_doc_title", rankAgreement.getName());
			signDoc.put("template_id", Apis.TEMPLATE_ID);
			signDoc.put("element_template", params);
			logger.debug("params:" + params);
			logger.debug(JSONObject.fromObject(params).toString());
			signDocs.add(signDoc);
			contentMap.put("sign_docs", signDocs);
			String contentmapString = com.alibaba.fastjson.JSONObject.toJSONString(contentMap);// JSONObject.fromObject(contentMap).toString();
			String encryptContent = AES_CBCUtils.encrypt(aesKey, contentmapString.getBytes("utf-8"));
			String encryptedKey = RSAUtil.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(), Apis.clientPrivateKey);
			String bodyText = null;
			if (RUN_TYPE)
			{
				logger.info("跳过合约认证，返回成功");
				bodyText = "{\"errorCode\":\"0\"}";
			} else
			{
				bodyText = sysCfgService.postMessage(new CABean(Apis.synRankAgreement, Apis.APIID, encryptContent, signed,
						encryptedKey));
			}
			logger.debug("bodyText:" + bodyText);
			if (!"".equals(bodyText))
			{
				try
				{
					errorCode = JSONObject.fromObject(bodyText).getString("errorCode");
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				logger.debug("errorCode:" + errorCode);
				if ("0".equals(errorCode))
				{
					if (RUN_TYPE)
					{
						resultMap.put("result", "-1");
						resultMap.put("msg", "CA同步到测试桩，请进行付款");
						return resultMap;
					} else
					{
						return rankAgreementNotarization(rankAgreement.getId(), userId, rankAgreement.getOperId(), houseId, "0");
					}
				}
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultMap.put("result", "-1");
			resultMap.put("msg", "CA同步合同信息失败!");
			return resultMap;
		} catch (Exception e)
		{
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			e.printStackTrace();
			resultMap.put("result", "-1");
			resultMap.put("msg", "图片上传失败!");
			return resultMap;
		}
	}

	/*
	 * 处理合约信息
	 */
	@Override
	@Transactional
	public Object dealFinance(HttpServletRequest request)
	{
		return null;
	}

	/*
	 * 验证租赁房源状态是否为待签约
	 */
	@Override
	public Object checkRankHouseStatus(String id)
	{
		Map<String, String> resultMap = new HashMap<>();
		int res = houseMapper.checkRankHouseStatus(id, String.valueOf(COMPLETE));
		if (res == 1)
		{
			resultMap.put("msg", "");
			resultMap.put("state", "1");
		} else
		{
			resultMap.put("msg", "房源状态已改变,请重新查看!");
			resultMap.put("state", "0");
		}
		return resultMap;
	}

	/**
	 * 出租合约ca认证
	 * 
	 * @param agreementId
	 * @param userId
	 * @param operId
	 * @param houseId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> rankCAgreementNotarization(String agreementId, String userId, String operId,
			String houseId, String isValidate)
	{
		Map<String, String> contentMap = new HashMap<>();
		Map<String, String> resultMap = new HashMap<>();
		try
		{
			if (!"0".equals(isValidate))
			{
				// 验证房源状态
				if (houseMapper.checkAgreementStatus("11", agreementId, "2") != 1)
				{
					resultMap.put("msg", "合约状态改变,请重新查看!");
					resultMap.put("result", "-1");
					return resultMap;
				}
			}
			String aesKey = RandomStringUtils.random(16, true, true);
			contentMap.put("docid", agreementId);
			contentMap.put("userid", userId);
			contentMap.put("callbackurl", Apis.callbackurl);
			contentMap.put("noticeurl", Apis.serverUrl + "ca/dealCRankAgreementInfo.do?agreementId=" + agreementId
					+ "&operId=" + operId + "&houseId=" + houseId);
			String contentmapString = com.alibaba.fastjson.JSONObject.toJSONString(contentMap);
			String encryptContent = AES_CBCUtils.encrypt(aesKey, contentmapString.getBytes("utf-8"));
			String encryptedKey = RSAUtil.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(), Apis.clientPrivateKey);
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功!");
			resultMap.put("url", Apis.showAgreement);
			resultMap.put("apiid", Apis.APIID);
			resultMap.put("content", encryptContent);
			resultMap.put("key", encryptedKey);
			resultMap.put("signed", signed);
			logger.debug("resultMap:" + resultMap);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (RUN_TYPE)
		{
			resultMap = new HashMap<>();
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功!");
		}
		return resultMap;
	}

	/**
	 * 出租合约ca认证
	 * 
	 * @param agreementId
	 * @param userId
	 * @param operId
	 * @param houseId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> rankAgreementNotarization(String agreementId, String userId, String operId,
			String houseId, String isValidate)
	{
		Map<String, String> contentMap = new HashMap<>();
		Map<String, String> resultMap = new HashMap<>();
		try
		{
			if (!"0".equals(isValidate))
			{
				// 验证房源状态
				if (houseMapper.checkAgreementStatus("11", agreementId, "2") != 1)
				{
					resultMap.put("msg", "合约状态改变,请重新查看!");
					resultMap.put("result", "-1");
					return resultMap;
				}
			}
			String aesKey = RandomStringUtils.random(16, true, true);
			contentMap.put("docid", agreementId);
			contentMap.put("userid", userId);
			contentMap.put("callbackurl", Apis.callbackurl);
			contentMap.put("noticeurl", Apis.serverUrl + "ca/dealRankAgreementInfo.do?agreementId=" + agreementId
					+ "&operId=" + operId + "&houseId=" + houseId);
			String contentmapString = com.alibaba.fastjson.JSONObject.toJSONString(contentMap);
			String encryptContent = AES_CBCUtils.encrypt(aesKey, contentmapString.getBytes("utf-8"));
			String encryptedKey = RSAUtil.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(), Apis.clientPrivateKey);
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功!");
			resultMap.put("url", Apis.showAgreement);
			resultMap.put("apiid", Apis.APIID);
			resultMap.put("content", encryptContent);
			resultMap.put("key", encryptedKey);
			resultMap.put("signed", signed);
			logger.debug("resultMap:" + resultMap);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (RUN_TYPE)
		{
			resultMap = new HashMap<>();
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功!");
		}
		return resultMap;
	}

	/**
	 * 出租合约ca认证
	 * 
	 * @param agreementId
	 * @param userId
	 * @param operId
	 * @param houseId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> agreementNotarization(String agreementId, String userId, String operId, String houseId,
			String isValidate)
	{
		Map<String, String> contentMap = new HashMap<>();
		Map<String, String> resultMap = new HashMap<>();
		try
		{
			if (!"0".equals(isValidate))
			{
				// 验证房源状态
				if (houseMapper.checkAgreementStatus("11", agreementId, "1") != 1)
				{
					resultMap.put("msg", "合约状态改变,请重新查看!");
					resultMap.put("result", "-1");
					return resultMap;
				}
			}
			String aesKey = RandomStringUtils.random(16, true, true);
			contentMap.put("docid", agreementId);
			contentMap.put("userid", userId);
			contentMap.put("callbackurl", Apis.callAgreementBackurl);
			contentMap.put("noticeurl", Apis.serverUrl + "ca/caCallBackAgreement.do?agreementId=" + agreementId + "&operId="
					+ operId + "&houseId=" + houseId);
			String contentmapString = com.alibaba.fastjson.JSONObject.toJSONString(contentMap);
			String encryptContent = AES_CBCUtils.encrypt(aesKey, contentmapString.getBytes("utf-8"));
			String encryptedKey = RSAUtil.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(), Apis.clientPrivateKey);
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功!");
			resultMap.put("url", Apis.showAgreement);
			resultMap.put("apiid", Apis.APIID);
			resultMap.put("content", encryptContent);
			resultMap.put("key", encryptedKey);
			resultMap.put("signed", signed);
			logger.debug("resultMap:" + resultMap);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		if (RUN_TYPE)
		{
			resultMap = new HashMap<>();
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功!");
		}
		return resultMap;
	}

	/*
	 * 保存用户信息
	 */
	@Override
	@Transactional
	public Object saveHouse(House house, HttpServletRequest request)
	{
		logger.debug("house:" + JSONObject.fromObject(house));
		String id = house.getId();
		String userId = house.getUserId(); // 用户id
		User u = new User();
		String newPath = "";
		String houseSplit = SystemConfig.getValue("FTP_HOUSE_PATH"); // 房源前缀
		// /img/
		String houseSoure = SystemConfig.getValue("FTP_HOUSE_SRC_PATH"); // 房源前缀
		// /src/
		u.setName(house.getUsername());
		u.setMobile(house.getMobile());
		u.setAddress("");
		u.setEmail("");
		u.setCertificateno("");
		if (userId == null || "".equals(userId))
		{
			logger.debug(JSONObject.fromObject(u).toString());
			syscfgMapper.saveUserInfo(u);
			userId = u.getId();
		} else
		{
			u.setId(house.getId());
			syscfgMapper.updateUserInfo(u);
		}
		house.setUserId(userId);
		Map<String, String> resultMap = new HashMap<>();
		// 验证房源名称是否存在
		if (houseMapper.checkHouseNameIsExist(id, house.getHouseName()) > 0)
		{
			resultMap.put("result", "-1");
			resultMap.put("msg", "房源名称重复！");
			return resultMap;
		}
		String path = house.getAppendix();
		if (id != null && !"".equals(id))
		{
			// 修改
		} else
		{
			// 保存用户信息
			houseMapper.insertHouse(house);
			id = house.getId();
			FtpUtil ftp = null;
			try
			{
				ftp = new FtpUtil();
				if (!"".equals(path))
				{
					String[] pathArray = path.split("\\|");
					for (int j = 0; j < pathArray.length; j++)
					{
						String tmpPath = UUID.randomUUID().toString().replaceAll("-", "")
								+ pathArray[j].substring(pathArray[j].lastIndexOf("."));
						newPath += ",/" + id + houseSoure + tmpPath;
						// logger.debug(request.getRealPath("/")+pathArray[j]);
						boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath, houseSplit + id
								+ houseSoure);
						if (!flag)
						{
							TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
							resultMap.put("result", "-1");
							resultMap.put("msg", "图片上传失败！");
							return resultMap;
						}
					}
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				resultMap.put("result", "-1");
				resultMap.put("msg", "图片上传失败！");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return resultMap;
			} finally
			{
				if (ftp != null)
				{
					ftp.closeServer();
				}
			}
			if (!"".equals(newPath))
			{
				newPath = newPath.substring(1).replace(",", "|");
			}
			houseMapper.updateHouseCode(house.getRegionId(), house.getId(), newPath);
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功！");
		}
		return resultMap;
	}

	/*
	 * 加载小区信息
	 */
	@Override
	public List<com.ycdc.appserver.model.house.Area> loadGroupList(HouseCondition condition)
	{
		return houseMapper.loadGroupList(initPageInfo(condition));
	}

	/**
	 * 验证房源状态
	 * 
	 * @param json
	 * @return
	 */
	public Object checkHouseState(JSONObject json)
	{
		String id = StringHelper.get(json, "id"); // 编号id
		String status = StringHelper.get(json, "status"); // 房子状态
		Map<String, String> returnMap = new HashMap<>();
		returnMap.put("state", "1");
		returnMap.put("msg", "");
		if (houseMapper.checkHouseState(id, status) != 1)
		{
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
	public Object delHouseInfo(JSONObject json)
	{
		// TODO Auto-generated method stub
		Map<String, String> returnMap = new HashMap<>();
		String id = StringHelper.get(json, "id"); // 编号id
		String operId = StringHelper.get(json, "operId"); // 操作人
		if ("".equals(id) || "".equals(operId))
		{
			returnMap.put("state", "-1");
			returnMap.put("msg", "非法访问");
			return returnMap;
		}
		houseMapper.updateHouseInfo(" id = '" + id + "'", id);
		// 验证房源状态
		if (houseMapper.checkHouseState(id, "0") != 1)
		{
			returnMap.put("state", "-1");
			returnMap.put("msg", "房源状态已改变,请重新查看");
			return returnMap;
		}
		houseMapper.delHouseInfo(id, operId);
		returnMap.put("state", "1");
		returnMap.put("msg", "操作成功");
		return returnMap;
	}

	/*
	 * 交接房源
	 */
	@Override
	@Transactional
	public Object transferHouse(JSONObject json)
	{
		// TODO Auto-generated method stub
		Map<String, String> returnMap = new HashMap<>();
		String id = StringHelper.get(json, "id"); // 编号id
		String isPass = StringHelper.get(json, "isPass"); // 是否通过 9 通过 5 否
		if ("".equals(id) || "".equals(isPass))
		{
			returnMap.put("state", "-1");
			returnMap.put("msg", "非法访问");
			return returnMap;
		}
		houseMapper.updateHouseInfo(" id = '" + id + "'", id);
		// 验证房源状态
		if (houseMapper.checkHouseState(id, "6") != 1)
		{
			returnMap.put("state", "-1");
			returnMap.put("msg", "房源状态已改变,请重新查看");
			return returnMap;
		}
		houseMapper.updateHouseInfo(" house_status = '" + isPass + "', update_time = now() ", id);
		returnMap.put("state", "1");
		returnMap.put("msg", "操作成功");
		return returnMap;
	}

	/**
	 * 验证房源是否是自己网格内房源
	 */
	@Override
	public int isSelfHouse(RankAgreement rankAgreement)
	{
		return houseMapper.checkIsSelfGrid(rankAgreement.getHouse_id(), rankAgreement.getAgents());
	}

	/*
	 * 提交房源
	 */
	@Override
	@Transactional
	public Object submitHouse(JSONObject json)
	{
		Map<String, String> returnMap = new HashMap<>();
		String id = StringHelper.get(json, "id"); // 编号id
		String operId = StringHelper.get(json, "operId"); // 操作人
		if ("".equals(id) || "".equals(operId))
		{
			returnMap.put("state", "-1");
			returnMap.put("msg", "非法访问");
			return returnMap;
		}
		houseMapper.updateHouseInfo(" id = '" + id + "'", id);
		// 验证房源状态
		if (houseMapper.checkHouseState(id, "0") != 1)
		{
			returnMap.put("state", "-1");
			returnMap.put("msg", "房源状态已改变,请重新查看");
			return returnMap;
		}
		houseMapper.updateHouseInfo(" id = '" + id + "', `operid` = '" + operId + "', house_status = 1 ", id);
		returnMap.put("state", "1");
		returnMap.put("msg", "操作成功");
		return returnMap;
	}

	/*
	 * 审批房源
	 */
	@Override
	public Object spHouse(JSONObject json)
	{
		Map<String, String> returnMap = new HashMap<>();
		String id = StringHelper.get(json, "id"); // 编号id
		String operId = StringHelper.get(json, "operId"); // 操作人
		String isPass = StringHelper.get(json, "isPass"); // 是否通过 0 否 2
		if ("".equals(id) || "".equals(operId) || "".equals(isPass))
		{
			returnMap.put("state", "-1");
			returnMap.put("msg", "非法访问");
			return returnMap;
		}
		houseMapper.updateHouseInfo(" id = '" + id + "'", id);
		// 验证房源状态
		if (houseMapper.checkHouseState(id, "1") != 1)
		{
			returnMap.put("state", "-1");
			returnMap.put("msg", "房源状态已改变,请重新查看");
			return returnMap;
		}
		houseMapper.updateHouseInfo(" id = '" + id + "', `operid` = '" + operId + "', house_status =  '" + isPass
				+ "', update_time = now() ", id);
		returnMap.put("state", "1");
		returnMap.put("msg", "操作成功");
		return returnMap;
	}

	/*
	 * 合约名称
	 */
	@Override
	public ResultCondition agreementCondition(String flag)
	{
		ResultCondition resultCondition = new ResultCondition();
		List<Area> areaList = syscfgMapper.getAreaList("101", "3");
		List<User> manageList = syscfgMapper.getManagerList("22");
		List<Map<String, Object>> categoryTypes = houseMapper.getCategoryTypeList();
		// 对管家姓名按照拼音排序
		Comparator<User> comparator = new Comparator<User>()
		{

			@Override
			public int compare(User o1, User o2)
			{
				char c1 = ((String) o1.getName()).charAt(0);  
		        char c2 = ((String) o2.getName()).charAt(0);  
		        return concatPinyinStringArray(  
		                PinyinHelper.toHanyuPinyinStringArray(c1)).compareTo(  
		                concatPinyinStringArray(PinyinHelper  
		                        .toHanyuPinyinStringArray(c2)));
			}
			private String concatPinyinStringArray(String[] pinyinArray) {  
		        StringBuffer pinyinSbf = new StringBuffer();  
		        if ((pinyinArray != null) && (pinyinArray.length > 0)) {  
		            for (int i = 0; i < pinyinArray.length; i++) {  
		                pinyinSbf.append(pinyinArray[i]);  
		            }  
		        }  
		        return pinyinSbf.toString();  
		    }
		};
		Collections.sort(manageList, comparator);
		resultCondition.setAreas(areaList);
		resultCondition.setUsers(manageList);
		resultCondition.setPublishList(new ArrayList<DictiItem>());
		if (null == categoryTypes || categoryTypes.isEmpty())
		{
			categoryTypes = new ArrayList<>();
		}
		resultCondition.setCategoryTypes(categoryTypes);		
		resultCondition.setStateList("1".equals(flag) ? syscfgMapper.getDictit("GROUP.AGREEMENT") : syscfgMapper
				.getDictit("GROUP.RANK.AGREEMENT"));
		return resultCondition;
	}

	/*
	 * 收房列表
	 */
	@Override
	public List<Agreement> agreementList(HouseCondition condition)
	{
		List<Agreement> list = houseMapper.agreementList(initPageInfo(condition));
		if (list == null)
		{
			return new ArrayList<>();
		}
		return list;
	}

	/*
	 * 撤销合约
	 */
	@Override
	@Transactional
	public Object cancelHouseAgreement(JSONObject json)
	{
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<>();
		String id = StringHelper.get(json, "id"); // 合约id
		if ("".equals(id))
		{
			resultMap.put("state", "-1");
			resultMap.put("msg", "非法访问");
			return resultMap;
		}
		houseMapper.updateAgreement("id = '" + id + "'", id, "1");
		// 验证合约状态
		if (houseMapper.checkAgreementStatus("2", id, "1") == 0)
		{
			resultMap.put("state", "-1");
			resultMap.put("msg", "合约状态已改变,请重新查看!");
			return resultMap;
		}
		houseMapper.updateAgreement("status = 4 ", id, "1");
		resultMap.put("state", "1");
		resultMap.put("msg", "操作成功");
		return resultMap;
	}

	/*
	 * 出租合约
	 */
	@Override
	public List<RankAgreement> rankAgreementList(HouseCondition condition, HttpServletRequest request)
	{
		// 获取待退租配置时长（孙诚明 2017-12-04）
		String time_configure = houseMapper.getTimeConfigure();
		if (null == time_configure)
		{
			time_configure = "15";
		}
		condition.setTime_configure(time_configure);
		
		// 获取员工id
		List<RankAgreement> list = houseMapper.rankAgreementList(initPageInfo(condition));
		
		if (list == null)
		{
			return new ArrayList<>();
		}
		// logger.debug(JSONArray.fromObject(list));
		return list;
	}

	/*
	 * 审批房源合约
	 */
	@Override
	public Object spHouserAgeement(HttpServletRequest request, Financial financial, Onstruction onstruction,
			AgreementMgeService agreementMgeService, JSONObject json)
	{
		Map<String, String> resultMap = new HashMap<>();
		String id = StringHelper.get(json, "id"); // 合约id
		String isPass = StringHelper.get(json, "isPass"); // 无效 3 有效2
		String houseId = StringHelper.get(json, "houseId");
		String operId = StringHelper.get(json, "operId");
		if ("".equals(id) || "".equals(isPass) || "".equals(houseId) || "".equals(operId))
		{
			resultMap.put("state", "-1");
			resultMap.put("msg", "非法参数");
			return resultMap;
		}
		Map mp = agreementMgeService.spHouserAgeement(request, financial, onstruction, json);
		String state = StringHelper.get(mp, "state");
		if ("1".equals(state))
		{
			resultMap.put("state", "1");
			resultMap.put("msg", "操作成功");
		} else if ("-2".equals(state))
		{
			resultMap.put("state", "-1");
			resultMap.put("msg", "合约状态改变,请重新查看");
		} else if ("-10".equals(state))
		{
			resultMap.put("state", "-1");
			resultMap.put("msg", "工程撤销失败!");
		} else if ("-12".equals(state))
		{
			resultMap.put("state", "-1");
			resultMap.put("msg", "撤销财务接口失败!");
		}
		return resultMap;
	}

	/**
	 * 初始化页面信息
	 * 
	 * @param condition
	 * @return
	 */
	public HouseCondition initPageInfo(HouseCondition condition)
	{
		String pageNumber = condition.getStartPage();
		String pageSize = condition.getPageSize();
		String regx = "^\\d+$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(pageNumber);
		if (!matcher.matches())
		{
			pageNumber = "0";
		}
		matcher = pattern.matcher(pageSize);
		if (!matcher.matches())
		{
			pageSize = "25";
		}
		BigInteger startPage = new BigInteger(pageNumber).multiply(new BigInteger(pageSize));
		condition.setPageSize(pageSize);
		condition.setStartPage(String.valueOf(startPage));
		return condition;
	}

	/**
	 * 银行列表
	 * 
	 * @param json
	 * @return
	 * @date 2017年1月9日
	 */
	@Override
	public List<Map<String, String>> getBankList(JSONObject json)
	{
		String area_id = StringHelper.get(json, "area_id"); // 区域id
		String fatherId = StringHelper.get(json, "father_id"); // 银行大类
		String bankName = StringHelper.get(json, "bankName"); // 银行名称
		String pageNumber = StringHelper.get(json, "pageNumber"); // 页码
		String pageSize = StringHelper.get(json, "pageSize"); // 每页显示
		String regx = "^\\d+$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(pageNumber);
		if (!matcher.matches())
		{
			pageNumber = "0";
		}
		matcher = pattern.matcher(pageSize);
		if (!matcher.matches())
		{
			pageSize = "25";
		}
		BigInteger startPage = new BigInteger(pageNumber).multiply(new BigInteger(pageSize));
		return syscfgMapper.getBankList(area_id, fatherId, bankName, startPage, Integer.valueOf(pageSize));
	}

	/**
	 * @return
	 * @date 2017年1月10日
	 */
	@Override
	public Map<String, Object> getBankSearchConditionList()
	{
		JSONObject json = new JSONObject();
		json.put("father_id", "-1");
		json.put("pageSize", "1000");
		json.put("pageNumber", "0");
		List<Map<String, String>> list = this.getBankList(json);
		List<Area> areaList = syscfgMapper.getAreaList("101", "3");
		Map<String, Object> mp = new HashMap<String, Object>();
		mp.put("bigBank", list);
		mp.put("arearList", areaList);
		return mp;
	}

	/**
	 * @return
	 * @date 2017年1月10日
	 */
	@Override
	public Map<String, Object> getAgreementDetailSelect()
	{
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("managerList", syscfgMapper.getManagerList("22"));
		resultMap.put("feeRentList", syscfgMapper.getDictit("FEERENT.TYPE"));
		resultMap.put("rentList", syscfgMapper.getDictit("GROUP.RENT.YEAR"));
		resultMap.put("payList", syscfgMapper.getDictit("AGREEMENT.PAYTYPE"));
		resultMap.put("propertyList", syscfgMapper.getDictit("PROPERTY.FILE"));
		return resultMap;
	}

	/**
	 * 签约房源后台
	 * 
	 * @param agreement
	 * @param request
	 * @return
	 * @date 2017年1月6日
	 */
	@Override
	@Transactional
	public Map<String, String> saveHouseAgreement(Agreement agreement, HttpServletRequest request)
	{
		logger.debug("==============agreemnt:" + JSONObject.fromObject(agreement));
		Map<String, String> resultMap = new HashMap<>();
		// 验证房源状态是否被签
		if (houseMapper.checkHouseState(agreement.getHouse_id(), "2") != 1)
		{
			resultMap.put("msg", "房源状态改变");
			resultMap.put("result", "-2");
			return resultMap;
		}
		// String userId = agreement.getUser_id(); // 房东id
		// 获取用户信息
		List<User> u = syscfgMapper.getUserInfoByMobile(agreement.getWtmobile());
		String wtUserId = agreement.getWtuserid();
		User user = new User();
		if (u == null || u.isEmpty())
		{
			// 用户不存在 添加用户
			user.setCertificateno(agreement.getWtIDCard());
			user.setName(agreement.getWtname());
			user.setMobile(agreement.getWtmobile());
			user.setSex(agreement.getWtSex());
			user.setBirthday(agreement.getWtBirthday());
			user.setProvince(agreement.getWtProvince());
			user.setCa_author("0");
			user.setEmail(agreement.getWtemail());
			user.setAddress(agreement.getWtAddress());
			syscfgMapper.saveUserInfo(user);
			logger.debug(user.toString());
			wtUserId = user.getId();
			agreement.setWtuserid(wtUserId);
		} else
		{
			user = u.get(0);
			if (agreement.getWtIDCard() != null && !"".equals(agreement.getWtIDCard()))
			{
				user.setCertificateno(agreement.getWtIDCard());
			}
			// 性别
			if (agreement.getWtSex() > 0)
			{
				user.setSex(agreement.getWtSex());
			}
			// 出生日期
			if (agreement.getWtBirthday() != null && !"".equals(agreement.getWtBirthday()))
			{
				user.setBirthday(agreement.getWtBirthday());
			}
			// 籍贯所在省份
			if (agreement.getWtProvince() != null && !"".equals(agreement.getWtProvince()))
			{
				user.setProvince(agreement.getWtProvince());
			}
			if (agreement.getWtemail() != null && !"".equals(agreement.getWtemail()))
			{
				user.setEmail(agreement.getWtemail());
			}
			if (agreement.getWtname() != null && !"".equals(agreement.getWtname()))
			{
				user.setName(agreement.getWtname());
			}
			if (agreement.getWtmobile() != null && !"".equals(agreement.getWtmobile()))
			{
				user.setMobile(agreement.getWtmobile());
			}
			if (agreement.getWtAddress() != null && !"".equals(agreement.getWtAddress()))
			{
				user.setAddress(agreement.getWtAddress());
			}
			agreement.setWtuserid(user.getId());
		}
		wtUserId = agreement.getWtuserid();
		// logger.debug("userId:" + userId);
		String caAuthor = user.getCa_author();
		logger.debug("caAuthor:" + caAuthor);
		// 需要同步ca认证
		String errorCode = "-1";
		if (RUN_TYPE)
		{
			logger.info("跳过CA认证用户信息");
		} else
		{
			if ("0".equals(caAuthor))
			{
				int i = 0;
				try
				{
					while (i < 2)
					{
						String resultCA = sysCfgService.authentorUser(user, "A");
						logger.info("==同步用户==");
						if (!"".equals(resultCA))
						{
							errorCode = JSONObject.fromObject(resultCA).getString("errorCode");
							if ("0".equals(errorCode))
							{
								break;
							}
						}
						i++;
					}
				} catch (Exception e)
				{
					resultMap.put("result", "-1");
					resultMap.put("msg", "CA用户认证失败!");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultMap;
				}
				logger.debug(JSONObject.fromObject(user).toString());
				if (!"0".equals(errorCode))
				{
					resultMap.put("result", "-1");
					resultMap.put("msg", "CA用户认证失败!");
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return resultMap;
				}
			}
		}
		errorCode = "-1";
		user.setCertificateno(agreement.getWtIDCard());
		user.setSex(agreement.getWtSex());
		user.setBirthday(agreement.getWtBirthday());
		user.setProvince(agreement.getWtProvince());
		user.setName(agreement.getWtname());
		user.setCa_author("1");
		user.setEmail(agreement.getWtemail());
		syscfgMapper.updateUserInfo(user);
		logger.debug(JSONObject.fromObject(agreement).toString());
		// 获取用户信息
		if (agreement.getDlmobile() != null && !"".equals(agreement.getDlmobile()))
		{
			List<User> u2 = syscfgMapper.getUserInfoByMobile(agreement.getDlmobile());
			String dlUserId = "";
			User user2 = new User();
			if (u2 == null || u2.isEmpty())
			{
				// 用户不存在 添加用户
				user2.setCertificateno(agreement.getDlIDCard());
				user.setSex(agreement.getDlSex());
				user.setBirthday(agreement.getDlBirthday());
				user.setProvince(agreement.getDlProvince());
				user2.setName(agreement.getDlname());
				user2.setMobile(agreement.getDlmobile());
				user2.setCa_author("0");
				user2.setEmail(agreement.getDlemail());
				user2.setAddress(agreement.getDlAddress());
				syscfgMapper.saveUserInfo(user);
				dlUserId = user2.getId();
				agreement.setDluserid(dlUserId);
			} else
			{
				user = u.get(0);
				if (agreement.getDlIDCard() != null && !"".equals(agreement.getDlIDCard()))
				{
					user.setCertificateno(agreement.getDlIDCard());
				}
				// 性别
				if (agreement.getDlSex() > 0)
				{
					user.setSex(agreement.getDlSex());
				}
				// 出生日期
				if (agreement.getDlBirthday() != null && !"".equals(agreement.getDlBirthday()))
				{
					user.setBirthday(agreement.getDlBirthday());
				}
				// 籍贯所在省份
				if (agreement.getDlProvince() != null && !"".equals(agreement.getDlProvince()))
				{
					user.setProvince(agreement.getDlProvince());
				}
				if (agreement.getDlemail() != null && !"".equals(agreement.getDlemail()))
				{
					user.setEmail(agreement.getDlemail());
				}
				if (agreement.getDlname() != null && !"".equals(agreement.getDlname()))
				{
					user.setName(agreement.getDlname());
				}
				if (agreement.getDlmobile() != null && !"".equals(agreement.getDlmobile()))
				{
					user.setMobile(agreement.getDlmobile());
				}
				if (agreement.getDlAddress() != null && !"".equals(agreement.getDlAddress()))
				{
					user.setAddress(agreement.getDlAddress());
				}
				agreement.setDluserid(user.getId());
			}
			dlUserId = agreement.getDluserid();
			user2.setCertificateno(agreement.getDlIDCard());
			user2.setName(agreement.getDlname());
			user2.setEmail(agreement.getDlemail());
			syscfgMapper.updateUserInfo(user2);
		}
		BigInteger totalMonery = new BigInteger("0");
		String[] rentMonth = agreement.getCost_a().split("\\|");
		String rentMonths = "";
		for (int i = 0; i < rentMonth.length; i++)
		{
			String tmpRentMonth = rentMonth[i];
			rentMonths += tmpRentMonth + "|";
			totalMonery = totalMonery.add(new BigInteger(tmpRentMonth).multiply(new BigInteger("12")));
		}
		agreement.setCost_a(rentMonths);
		agreement.setCost(String.valueOf(totalMonery));
		agreement.setType("1");
		houseMapper.saveHouseAgreement(agreement);
		logger.debug(agreement.getId());
		String id = agreement.getId();
		// 上次图片
		String newPath = "";
		String newPropertyPath = "";
		String newAgentPath = "";
		String idCard = "idCard";
		String agentIdCard = "agentIdCard";
		String propertyCard = "propertyCard";
		String houseId = agreement.getHouse_id();
		String path = agreement.getPic_path().replace("|", ",");
		String propertyPath = agreement.getPropertyPath().replace("|", ",");
		String agentPath = agreement.getAgentPath().replace("|", ",");
		String houseSplit = "/" + houseId + SystemConfig.getValue("FTP_AGREEMENT_PATH"); // 房源分割
		FtpUtil ftp = null;
		try
		{
			ftp = new FtpUtil();
			if (!"".equals(path))
			{
				String[] pathArray = path.split(",");
				for (int j = 0; j < pathArray.length; j++)
				{
					String tmpCard = "";
					if (j > 0)
					{
						tmpCard = String.valueOf(j);
					}
					String tmpPath = propertyCard + tmpCard + pathArray[j].substring(pathArray[j].lastIndexOf("."));
					newPath += "," + houseSplit + id + "/" + tmpPath;
					boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
							SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + id + "/");
					if (!flag)
					{
						logger.info("上传图片失败");
						resultMap.put("result", "-1");
						resultMap.put("msg", "房产证上传失败!");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return resultMap;
					}
				}
			}
			if (!"".equals(propertyPath))
			{
				String[] pathArray = propertyPath.split(",");
				for (int j = 0; j < pathArray.length; j++)
				{
					String tmpCard = "";
					if (j > 0)
					{
						tmpCard = String.valueOf(j);
					}
					String tmpPath = idCard + tmpCard + pathArray[j].substring(pathArray[j].lastIndexOf("."));
					newPropertyPath += "," + houseSplit + id + "/" + tmpPath;
					boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
							SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + id + "/");
					if (!flag)
					{
						logger.info("上传图片失败");
						resultMap.put("result", "-1");
						resultMap.put("msg", "产权人上传失败!");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return resultMap;
					}
				}
			}
			if (!"".equals(agentPath))
			{
				String[] pathArray = agentPath.split(",");
				for (int j = 0; j < pathArray.length; j++)
				{
					String tmpCard = "";
					if (j > 0)
					{
						tmpCard = String.valueOf(j);
					}
					String tmpPath = agentIdCard + tmpCard + pathArray[j].substring(pathArray[j].lastIndexOf("."));
					newAgentPath += "," + houseSplit + id + "/" + tmpPath;
					boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
							SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + id + "/");
					if (!flag)
					{
						logger.info("上传图片失败");
						resultMap.put("result", "-1");
						resultMap.put("msg", "代理人上传失败!");
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return resultMap;
					}
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			resultMap.put("result", "-1");
			resultMap.put("msg", "附件上传失败!");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return resultMap;
		} finally
		{
			if (ftp != null)
			{
				ftp.closeServer();
			}
		}
		if (!"".equals(newPath))
		{
			newPath = newPath.substring(1).replace(",", "|");
		}
		if (!"".equals(newPropertyPath))
		{
			newPropertyPath = newPropertyPath.substring(1).replace(",", "|");
		}
		if (!"".equals(newAgentPath))
		{
			newAgentPath = newAgentPath.substring(1).replace(",", "|");
		}
		// 更新code 图片
		String column = "code = CONCAT('" + agreement.getHouseCode() + "','A',DATE_FORMAT(NOW(),'%y'),'T',"
				+ agreement.getId() + "), file_path = '" + newPath + "', propertyPath = '" + newPropertyPath
				+ "', agentPath = '" + newAgentPath + "' ";
		houseMapper.updateAgreement(column, agreement.getId(), "1");
		houseMapper.updateHouseInfo(" house_status = 3, update_time = now()", houseId);
		resultMap.put("result", "1");
		resultMap.put("msg", "操作成功!");
		try
		{
			// ca 对接
			String aesKey = RandomStringUtils.random(16, true, true);
			Map<String, String> params = new HashMap<String, String>(); // 合约要显示的参数
			params.put("username", agreement.getWtname());
			params.put("certificateno", agreement.getWtIDCard());
			params.put("address", agreement.getAddress());
			params.put("area", agreement.getArea());
			if (agreement.getSpec() != null && !"".equals(agreement.getSpec()))
			{
				String spec = agreement.getSpec();
				params.put("shi", spec.split("\\|")[0]);
				params.put("ting", spec.split("\\|")[1]);
				params.put("wei", spec.split("\\|")[2]);
			}
			if (agreement.getSpec2() != null && !"".equals(agreement.getSpec2()))
			{
				String spec2 = agreement.getSpec2();
				params.put("chu", spec2.split("\\|")[0]);
				params.put("yang", spec2.split("\\|")[1]);
				params.put("dian", spec2.split("\\|")[2]);
			}
			String keys = agreement.getKeys_count();
			if (keys != null && !"".equals(keys))
			{
				params.put("key1", keys.split("\\|")[0]);
				params.put("key2", keys.split("\\|")[1]);
				params.put("key3", keys.split("\\|")[2]);
			}
			params.put("type", agreement.getPropertyType());
			params.put("propertynum", agreement.getPropertyCode());
			params.put("propertyman", agreement.getPropertyPerson());
			params.put("propertymans", agreement.getCo_owner());
			params.put("diya", agreement.getMortgage());
			params.put("decorate", agreement.getDecorate());
			params.put("shiyong", agreement.getPurpose());
			params.put("bgyear", agreement.getBegin_time().split("-")[0]);
			params.put("bgmonth", agreement.getBegin_time().split("-")[1]);
			params.put("bgday", agreement.getBegin_time().split("-")[2]);
			Map mp = houseMapper.calcEndTime(agreement.getBegin_time(), agreement.getRentMonth(), "12");
			String endDay = StringHelper.get(mp, "endtime");
			params.put("endyear", endDay.split("-")[0]);
			params.put("endmonth", endDay.split("-")[1]);
			params.put("endday", endDay.split("-")[2]);
			params.put("free_period", agreement.getFree_period());
			params.put("agreementtype", agreement.getFreeRankType());
			if ("2".equals(agreement.getFreeRankType()))
			{
				// 计算每年对应的免租期
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String type2Des = "";
				String leaseDay = agreement.getFree_period(); // 免租期
				BigDecimal avgLease = new BigDecimal(0);
				if (!"".equals(leaseDay))
				{
					avgLease = new BigDecimal(leaseDay);
				}
				String beginTime = agreement.getBegin_time();
				Map<String, String> lastDayMonth = houseMapper.calcEndTime(beginTime, agreement.getRentMonth(), "12");
				String endtime = StringHelper.get(lastDayMonth, "endtime");
				List<Map<String, String>> list = new ArrayList<>();
				for (int i = 0; i < agreement.getCost_a().split("\\|").length; i++)
				{
					Date date = sdf.parse(beginTime);
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(date);
					gc.add(Calendar.YEAR, 1);
					String endTime = new SimpleDateFormat("yyyy-MM-dd").format(gc.getTime());
					Map<String, String> datMp = new HashMap<>();
					datMp.put("bgTime", beginTime);
					if (i == agreement.getCost_a().split("\\|").length - 1)
					{
						datMp.put("endTime", endtime);
					} else
					{
						datMp.put("endTime", endTime);
					}
					list.add(datMp);
					beginTime = endTime;
				}
				logger.debug("monthList:" + list);
				for (int i = 0; i < list.size(); i++)
				{
					Map<String, String> datMp = list.get(i);
					String bgTime = StringHelper.get(datMp, "bgTime");
					String endTime = StringHelper.get(datMp, "endTime");
					Calendar now = Calendar.getInstance();
					now.setTime(sdf.parse(bgTime));
					now.set(Calendar.DATE, now.get(Calendar.DATE) + avgLease.intValue());
					bgTime = sdf.format(now.getTime());
					type2Des += bgTime + "至" + endTime + "<br/>";
				}
				params.put("type2desc", type2Des);
				params.put("avg_free_period", String.valueOf(avgLease.intValue()));
			}
			params.put("monery", agreement.getCost_a().split("\\|")[0]);
			params.put("pay_type", agreement.getPay_type());
			params.put("wtname", agreement.getWtname());
			params.put("wtnumber", agreement.getWtIDCard());
			params.put("wtaddress", agreement.getWtAddress());
			params.put("wtphone", agreement.getWtmobile());
			params.put("wtemail", agreement.getWtemail());
			params.put("dlname", agreement.getDlname());
			params.put("dlnumber", agreement.getDlIDCard());
			params.put("dladdress", agreement.getDlAddress());
			params.put("dlphone", agreement.getDlmobile());
			params.put("dlemail", agreement.getDlemail());
			params.put("bankowner", agreement.getCardowen());
			params.put("bank", agreement.getCardbank());
			params.put("bankcard", agreement.getBankcard());
			String currentday = DateHelper.getToday();
			params.put("currentday", currentday);
			params.put("watercard", agreement.getWatercard());
			params.put("electriccard", agreement.getElectriccard());
			params.put("gascard", agreement.getGascard());
			params.put("water_meter", agreement.getWater_meter());
			params.put("electricity_meter", "总值:" + agreement.getElectricity_meter());
			params.put("electricity_meter_h", " 峰值:" + agreement.getElectricity_meter_h());
			params.put("electricity_meter_l", " 谷值:" + agreement.getElectricity_meter_l());
			params.put("gas_meter", agreement.getGas_meter());
			// params.put("currenyear", currentday.split("-")[0]);
			// params.put("currenmonth", currentday.split("-")[1]);
			params.put("day", currentday.split("-")[2]);
			String new_old_matched = agreement.getNew_old_matched();
			String old_matched = "";
			String kt = "";
			if (new_old_matched != null && !"".equals(new_old_matched))
			{
				logger.debug(new_old_matched.split("@@").length + "");
				if (new_old_matched.split("@@").length > 0)
				{
					logger.debug(new_old_matched.split("@@")[0]);
					old_matched = new_old_matched.split("@@")[0];
				}
				if (new_old_matched.split("@@").length > 1)
				{
					kt = new_old_matched.split("@@")[1];
				}
			}
			if (kt != null && !"".equals(kt))
			{
				String[] innerCnt = kt.split("\\|");
				for (int i = 0; i < innerCnt.length; i++)
				{
					params.put("kt" + i, innerCnt[i].split("=").length > 1 ? innerCnt[i].split("=")[1] : "");
				}
			}
			if (null != old_matched && !"".equals(old_matched))
			{
				for (int i = 0; i < old_matched.split("##").length; i++)
				{
					String[] array = old_matched.split("##")[i].split("\\|");
					for (int j = 0; j < array.length; j++)
					{
						params.put(array[j].split("=")[0], array[j].split("=").length > 1 ? array[j].split("=")[1] : "");
					}
				}
			}
			Map<String, Object> contentMap = new HashMap<String, Object>();
			contentMap.put("userid", agreement.getWtuserid());
			contentMap.put("docid", agreement.getId());
			contentMap.put("sign_type", "1");
			contentMap.put("parent_docid", "0");
			List<Map<String, Object>> signDocs = new ArrayList<>();
			Map<String, Object> signDoc = new HashMap<>();
			signDoc.put("sign_doc_title", agreement.getName());
			signDoc.put("template_id", Apis.AGREEMNT_TEMPLATE_ID);
			signDoc.put("element_template", params);
			logger.debug(params.toString());
			signDocs.add(signDoc);
			contentMap.put("sign_docs", signDocs);
			// logger.debug(JSONObject.fromObject(contentMap));
			String contentmapString = com.alibaba.fastjson.JSONObject.toJSONString(contentMap);// JSONObject.fromObject(contentMap).toString();
			// logger.debug(contentmapString);
			String encryptContent = AES_CBCUtils.encrypt(aesKey, contentmapString.getBytes("utf-8"));
			String encryptedKey = RSAUtil.encryptByPublicKey(aesKey, Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(), Apis.clientPrivateKey);
			String bodyText = null;
			if (RUN_TYPE)
			{
				logger.info("跳过合约认证，返回成功");
				bodyText = "{\"errorCode\":\"0\"}";
			} else
			{
				bodyText = sysCfgService.postMessage(new CABean(Apis.synRankAgreement, Apis.APIID, encryptContent, signed,
						encryptedKey));
			}
			logger.debug("bodyText:" + bodyText);
			if (!"".equals(bodyText))
			{
				try
				{
					errorCode = JSONObject.fromObject(bodyText).getString("errorCode");
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				logger.debug("errorCode:" + errorCode);
				if ("0".equals(errorCode))
				{
					if (RUN_TYPE)
					{
						resultMap.put("result", "-1");
						resultMap.put("msg", "CA同步到测试桩，请进行付款");
						return resultMap;
					} else
					{
						return agreementNotarization(agreement.getId(), agreement.getWtuserid(), agreement.getOperid(), houseId,
								"0");
					}
				}
			}
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			resultMap.put("result", "-1");
			resultMap.put("msg", "CA同步合同信息失败!");
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 获取首页数量
	 * 
	 * @param json
	 * @return
	 * @date 2017年2月6日
	 */
	@Override
	public Map<String, Object> loadHousePageCnt(JSONObject json, IMktRepServ mktRepServ, HttpServletRequest request)
	{
		// 获取员工id
		String userId = StringHelper.get(json, "userId");
		String teamIds = StringHelper.get(json, "teamIds");
		// 获取团队的成员id
		Map<String, Object> resultMap = new HashMap<>();
		// 获取员工角色
		String userRoles = StringHelper.get(json, "userRoles");
		
		String[] roles = userRoles.split(",");
		String roleIds = "";
		for (int i = 0; i < roles.length; i++)
		{
			roleIds += ","+roles[i];
		}
		if (roleIds.length() > 0) {
			roleIds = roleIds.substring(1);
		}
		Map<String, Object> rolesAndId = new HashMap<>();
		rolesAndId.put("userId", userId);
		rolesAndId.put("roleIds", roleIds);
		rolesAndId.put("teamIds", teamIds);
		// 待处理预约看房数量
		String inspectHouseCnt = "";
		inspectHouseCnt = String.valueOf(houseMapper.loadInspectHouse(userId,teamIds));
		resultMap.put("inspectHouseCnt", inspectHouseCnt);
		logger.debug("待处理预约看房数量：" + inspectHouseCnt);
		try
		{
			// 待处理售后订单数量
			String afterSalesCnt = String.valueOf(houseMapper.loadAfterSales(rolesAndId));
			resultMap.put("afterSalesCnt", afterSalesCnt);
			logger.debug("待处理售后订单数量：" + afterSalesCnt);
		} catch (Exception e)
		{
			resultMap.put("afterSalesCnt", "0");
			logger.info("待处理售后订单数量错误!");
			logger.error(e.toString());
		}
		if ((userId != null && !"".equals(userId)) || (teamIds != null && !"".equals(teamIds)))
		{
			resultMap.put("approveCnt", String.valueOf(houseMapper.loadApprovalHouseCnt(userId,teamIds))); // 待验收
			BigInteger allHouseCnt = new BigInteger(String.valueOf(houseMapper.loadSignAllHouse(userId,teamIds)));
			BigInteger allAreadyHouseCnt = new BigInteger(String.valueOf(houseMapper.readySignRankHouse(userId,teamIds)));
			resultMap.put("allHouseCnt", String.valueOf(allHouseCnt));
			resultMap.put("rankCnt", String.valueOf(allHouseCnt.subtract(allAreadyHouseCnt))); // 待出租
			// 待付款
			List<Map<String, String>> payCntList = houseMapper.loadPayCnt(userId,teamIds);
			String secondaryStr = "";
			int totalSec = payCntList.size();
			String[] str = {"11","12","13"};
			for (Map<String, String> payCntMap : payCntList)
			{
				String category_id = String.valueOf(payCntMap.get("category_id"));
				if (Arrays.asList(str).contains(category_id))
				{
					secondaryStr += String.valueOf(payCntMap.get("secondary")) + ",";
				}
			}
			if (!secondaryStr.equals(""))
			{
				secondaryStr = secondaryStr.substring(0, secondaryStr.length() - 1);
				// 获取预缴费信息
				int total = houseMapper.loadPayCntTotal(secondaryStr);
				totalSec = totalSec - total;
			}
			resultMap.put("payCnt", totalSec);
			// 获取待退租配置时长（孙诚明 2017-12-06）
			String time_configure = houseMapper.getTimeConfigure();
			if (null == time_configure)
			{
				time_configure = "30";
			}
			resultMap.put("expireCnt", String.valueOf(houseMapper.loadRankExpireCnt(userId,teamIds,time_configure))); // 将要过期
			resultMap.put("allAreadyHouseCnt", String.valueOf(allAreadyHouseCnt)); // 已经出租的房源
			Map mp = mktRepServ.getMarketData(userId, teamIds, "", allAreadyHouseCnt, allHouseCnt);
			String cost_val = StringHelper.get(mp, "cost_val");
			String empty_rate = StringHelper.get(mp, "empty_rate");
			resultMap.put("cost_val", cost_val);
			resultMap.put("empty_rate", empty_rate);
			String rate = houseMapper.payMentRate(userId,teamIds);
			resultMap.put("payMentRate", (rate == null || "".equals(rate) ? "0" : rate));
		} else
		{
			resultMap.put("approveCnt", "0");
			resultMap.put("rankCnt", "0");
			resultMap.put("allHouseCnt", "0");
			resultMap.put("payCnt", "0");
			resultMap.put("expireCnt", "0");
			resultMap.put("allAreadyHouseCnt", "0");
			resultMap.put("cost_val", "0");
			resultMap.put("empty_rate", "0");
			resultMap.put("payMentRate", "0");
		}
		logger.debug("resultMap:" + resultMap.toString());
		return resultMap;
	}

	/**
	 * 代缴费列表
	 * 
	 * @param json
	 * @return
	 * @date 2017年2月6日
	 */
	@Override
	public List<Map<String, String>> loadPayCntList(JSONObject json,HttpServletRequest request)
	{
		// 获取员工id
		String userId = StringHelper.get(json, "userId");
		String keyWord = StringHelper.get(json, "keyWord");
		String startPage = StringHelper.get(json, "startPage");
		String pageSize = StringHelper.get(json, "pageSize");
		String areaid = StringHelper.get(json, "areaid");
		String trading = StringHelper.get(json, "trading");
		String teamIds = StringHelper.get(json, "teamIds");
		String typeId = StringHelper.get(json, "typeId");
    String timeSort = StringHelper.get(json, "timeSort");
		String regx = "^\\d+$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(startPage);
		if (!matcher.matches())
		{
			startPage = "0";
		}
		matcher = pattern.matcher(pageSize);
		if (!matcher.matches())
		{
			pageSize = "30";
		}
		pageSize = "30";
		List<Map<String, String>> resultList = houseMapper.loadPayCntList(userId, keyWord,
				String.valueOf(new BigInteger(startPage).multiply(new BigInteger(pageSize))), pageSize, areaid, trading,teamIds,typeId,timeSort);
		if (resultList == null)
		{
			return new ArrayList<>();
		} else 
		{
			// 遍历，判断当前数据是否是水电煤类型，如果是，则判断是否有预缴费信息，如果没有预缴费该条记录不显示
			Iterator<Map<String, String>> it = resultList.iterator();
			String[] str = {"11","12","13"};
			while (it.hasNext())
			{
				Map<String, String> map = it.next();
				String category_id = String.valueOf(map.get("category_id"));
				if (Arrays.asList(str).contains(category_id))
				{
					// 如果是水电煤类型，判断是否有预缴费信息
					int total = houseMapper.getPrepayment(String.valueOf(map.get("secondary")));
					if (total > 0) 
					{
						// 有预缴费信息，不显示水电煤信息
						it.remove();
					}
				}
			}
		}
		return resultList;
	}
}
