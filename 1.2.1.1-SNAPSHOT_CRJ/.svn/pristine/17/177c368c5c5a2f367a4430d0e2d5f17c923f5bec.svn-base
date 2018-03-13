/**
 * 
 */
package com.yc.rm.caas.appserver.bus.service.contract;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import pccom.common.util.DateHelper;
import pccom.common.util.FtpUtil;
import pccom.web.beans.SystemConfig;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.server.agreement.AgreementMgeService;

import com.alibaba.fastjson.JSONObject;
import com.raising.framework.utils.md5.MD5Utils;
import com.raising.framework.utils.rsa.RSAUtil;
import com.yc.rm.caas.appserver.model.user.User;
import com.yc.rm.caas.appserver.base.support.BaseService;
import com.yc.rm.caas.appserver.base.syscfg.dao.ISysCfgDao;
import com.yc.rm.caas.appserver.base.syscfg.serv.ISysCfgServ;
import com.yc.rm.caas.appserver.bus.controller.house.fo.HouseFo;
import com.yc.rm.caas.appserver.bus.dao.contract.IContractDao;
import com.yc.rm.caas.appserver.bus.dao.house.IHouseDao;
import com.yc.rm.caas.appserver.model.contract.ContractBean;
import com.yc.rm.caas.appserver.model.house.Area;
import com.yc.rm.caas.appserver.model.house.RankAgreement;
import com.yc.rm.caas.appserver.model.house.RankHouse;
import com.yc.rm.caas.appserver.model.syscfg.CABean;
import com.yc.rm.caas.appserver.util.AES_CBCUtils;
import com.yc.rm.caas.appserver.util.Apis;
import com.yc.rm.caas.appserver.util.ConvertNum;

/**
 * @author stephen
 * 
 */
@Service("_contractServImpl")
public class ContractServImpl extends BaseService implements IContractServ {
	@Resource
	private IContractDao _contractDao;
	@Resource
	private IHouseDao _houseDao;
	@Resource
	private ISysCfgDao _sysCfgDao;
	@Autowired
	private ISysCfgServ _sysCfgService;

	/*
	 * 获取合约信息
	 */
	@Override
	public ContractBean getAgreementInfo(String agreementId, String house_id) {
		String filePath = SystemConfig.getValue("FTP_HTTP")
				+ SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		ContractBean agreement = new ContractBean();
		try {
			agreement = _contractDao.getAgreementInfo(filePath, "|" + filePath,
					agreementId, house_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return agreement;
	}

	/*
	 * 出租合约明细
	 */
	@Override
	public List<RankAgreement> getRankAgreementInfo(JSONObject json) {
		String flag = super.get(json, "flag"); // 0是合约id，1是房间id
		String agreementId = "";
		if ("1".equals(flag)) {
			String id = super.get(json, "id");
			agreementId = _contractDao.getRankAgreementIdByRankHouseId(id);
			log.debug("agreementId:" + agreementId);
		} else {
			agreementId = super.get(json, "id");
		}
		String filepath = SystemConfig.getValue("FTP_HTTP")
				+ SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		List<RankAgreement> rankAgreement = _contractDao.getRankAgreementInfo(
				filepath, "|" + filepath, agreementId);
		if (rankAgreement == null) {
			return new ArrayList<RankAgreement>();
		}
		return rankAgreement;
	}

	/**
	 * 保存合约信息
	 */
	/*
	 * @see
	 * com.yc.rm.caas.appserver.bus.service.house.HouseMgrService#saveRankAgreement
	 * (com.yc.rm.caas.appserver.model.house.RankAgreement)
	 */
	@Override
	@Transactional
	public Object saveRankAgreement(RankAgreement rankAgreement,
			HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();
		// 2017-3-7解除限制本人网格的房子只能由本人签
		/*
		 * if (this.isSelfHouse(rankAgreement) == 0) { resultMap.put("result",
		 * "-2"); resultMap.put("msg", "非本人网格不能签约"); return resultMap; }
		 */
		Map<String, String> notPayMap = null;
		try {
			notPayMap = _contractDao.checkAgreementISNotPay(rankAgreement
					.getRankId());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (notPayMap != null && "1".equals(super.get(notPayMap, "cnt"))) {
			resultMap.put("result", "-2");
			resultMap.put("msg", "该房间可能已被其他管家签约,请重新到出租合约管理中查看后在操作！");
		} else {
			boolean existsAgreement = true;
			// 验证房间是存在有效合约
			String maxEndTime = null;
			try {
				maxEndTime = _contractDao
						.loadValidRankAgreementMaxEndTime(rankAgreement
								.getRankId());
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			if (maxEndTime == null || "".equals(maxEndTime)) {
				maxEndTime = rankAgreement.getBegin_time();
				existsAgreement = false;
			} else {
				String bgTime = rankAgreement.getBegin_time();
				// 开始时间必须大于结束时间
				if (bgTime == null || "".equals(bgTime)) {
					resultMap.put("result", "-2");
					resultMap.put("msg", "开始时间为空!");
					return resultMap;
				}
				if (Integer.parseInt(rankAgreement.getBegin_time().replaceAll(
						"-", ""))
						- Integer.parseInt(maxEndTime.replaceAll("-", "")) <= 0) {
					resultMap.put("result", "-2");
					resultMap.put("msg", "签约开始时间必须大于前一个合约的结束时间");
					return resultMap;
				}
			}
			Map<String, String> signMp = null;
			try {
				signMp = _contractDao.checkDays(maxEndTime);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (signMp != null && !signMp.isEmpty()) {
				resultMap.put("result", "-2");
				resultMap.put("msg", "签约不能提前" + super.get(signMp, "item_id")
						+ "天");
				return resultMap;
			}
			String houseId = rankAgreement.getHouse_id();
			String newPath = "";
			String houseSplit = "/" + houseId
					+ SystemConfig.getValue("FTP_RANK_AGREEMENT_PATH"); // 房源分隔
			// 判断房源状态
			_houseDao.updateRankHouse("id = " + rankAgreement.getRankId(),
					rankAgreement.getRankId());
			// 获取用户信息
			List<User> u = _sysCfgDao.getUserInfoByMobile(rankAgreement
					.getMobile());
			String userId = rankAgreement.getUser_id();
			User user = new User();
			if (u == null || u.isEmpty()) {
				// 用户不存在 添加用户
				user.setCertificateno(rankAgreement.getCertificateno());
				user.setUserName(rankAgreement.getUsername());
				if (StringUtils.isNotBlank(rankAgreement.getMobile()))
				{
					user.setUserPhone(rankAgreement.getMobile());
				}

				user.setCaAuthor("0");
				user.setEmail(rankAgreement.getEmail());
				_sysCfgDao.saveUserInfo(user);
				userId = String.valueOf(user.getUserId());
				rankAgreement.setUser_id(userId);
			} else {
				user = u.get(0);
				if (rankAgreement.getCertificateno() != null
						&& !"".equals(rankAgreement.getCertificateno())) {
					user.setCertificateno(rankAgreement.getCertificateno());
				}
				if (rankAgreement.getEmail() != null
						&& !"".equals(rankAgreement.getEmail())) {
					user.setEmail(rankAgreement.getEmail());
				}
				if (rankAgreement.getUsername() != null
						&& !"".equals(rankAgreement.getUsername())) {
					user.setUserName(rankAgreement.getUsername());
				}
				rankAgreement.setUser_id(String.valueOf(user.getUserId()));
				// if (rankAgreement.getMobile() != null &&
				// !"".equals(rankAgreement.getMobile()))
				// {
				// user.setMobile(rankAgreement.getMobile());
				// }
			}
			userId = rankAgreement.getUser_id();
			log.info("userId:" + userId);
			String caAuthor = user.getCaAuthor();
			log.info("caAuthor:" + caAuthor);
			// 需要同步ca认证
			String errorCode = "-1";
			if (RUN_TYPE) {
				log.debug("集成测试，跳过CA用户认证");
			} else {
				if ("0".equals(caAuthor)) {
					int i = 0;
					try {
						while (i < 2) {
							String resultCA = _sysCfgService.authentorUser(user,
									"A");
							// System.out.println("==同步用户==");
							log.info("==同步用户==");
							if (!"".equals(resultCA)) {
								errorCode = JSONObject.parseObject(resultCA)
										.getString("errorCode");
								if ("0".equals(errorCode)) {
									break;
								}
							}
							i++;
						}
					} catch (Exception e) {
						resultMap.put("result", "-1");
						resultMap.put("msg", "CA用户认证失败!");
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						return resultMap;
					}
					log.debug(JSONObject.toJSONString(user));
					if (!"0".equals(errorCode)) {
						resultMap.put("result", "-1");
						resultMap.put("msg", "CA用户认证失败!");
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						return resultMap;
					}
				}
			}
			errorCode = "-1";
			user.setCertificateno(rankAgreement.getCertificateno());
			user.setUserName(rankAgreement.getUsername());
			user.setCaAuthor("1");
			user.setEmail(rankAgreement.getEmail());
			_sysCfgDao.updateUserInfo(user);
			try {
				String id = rankAgreement.getId();
				int res = _houseDao.checkRankHouseStatus(
						rankAgreement.getRankId(), String.valueOf(COMPLETE));
				if (res == 0 && !existsAgreement) {
					// 返回结果 -2
					resultMap.put("result", "0");
					resultMap.put("msg", "房源状态已经改变,请重新操作!");
					return resultMap;
				}
				// 获取合约的区域和fatherid
				ContractBean agreement = _contractDao
						.getRankAgreementFatherId(rankAgreement.getHouse_id());
				String areaid = agreement.getAreaid();
				String fatherId = agreement.getId();
				String rankCode = rankAgreement.getRank_code();
				rankAgreement.setFather_id(fatherId);
				rankAgreement.setRank_code(rankCode);
				rankAgreement.setAreaid(areaid);
				log.debug(JSONObject.toJSONString(rankAgreement));
				_contractDao.insertRankAgreement(rankAgreement);
				id = rankAgreement.getId();
				String picPath = rankAgreement.getFile_path().replace(",", "|");

				// 上传图片
				FtpUtil ftp = null;
				try {
					ftp = new FtpUtil();
					if (!"".equals(picPath)) {
						log.debug("picPath:" + picPath);
						String[] pathArray = picPath.split("\\|");
						for (int j = 0; j < pathArray.length; j++) {
							String tmpPath = UUID.randomUUID().toString()
									.replaceAll("-", "")
									+ pathArray[j].substring(pathArray[j]
											.lastIndexOf("."));
							newPath += "," + houseSplit + id + "/" + tmpPath;
							// logger.debug(request.getRealPath("/")+pathArray[j]);
							boolean flag = ftp.uploadFile(
									request.getRealPath("/") + pathArray[j],
									tmpPath,
									SystemConfig.getValue("FTP_HOUSE_PATH")
											+ houseSplit + id + "/");
							if (!flag) {
								TransactionAspectSupport
										.currentTransactionStatus()
										.setRollbackOnly();
								resultMap.put("result", "-1");
								resultMap.put("msg", "图片上传失败!");
								return resultMap;
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus()
							.setRollbackOnly();
					resultMap.put("result", "-1");
					resultMap.put("msg", "图片上传失败!");
					return resultMap;
				} finally {
					if (ftp != null) {
						ftp.closeServer();
					}
				}
				if (!"".equals(newPath)) {
					newPath = newPath.substring(1).replace(",", "|");
				}
				// 更新id
				_contractDao.updateRankAgreemtCode(rankCode, newPath, id);
				// 获取该房源下所有要出租房源信息
				if (!existsAgreement) {
					List<RankHouse> rankHouseList = _houseDao
							.loadRankHouseList(rankAgreement.getHouse_id());
					String rankId = rankAgreement.getRankId();// 房源 ID
					for (RankHouse rankHouse : rankHouseList) {
						if (rankId.equals(rankHouse.getId())
								&& "已发布".equals(rankHouse.getRankstatus())) {
							if ("整租".equals(rankHouse.getRanktype())) {
								for (RankHouse rank_House : rankHouseList) {
									if (!rankId.equals(rank_House.getId())) {
										// 撤销 合租房源
										_houseDao.updateRankHouseStatus(
												String.valueOf(LOST),
												rankAgreement.getOperId(),
												rank_House.getId());
									} else {
										_houseDao.updateRankHouseStatus(
												String.valueOf(AGREE_ING),
												rankAgreement.getOperId(),
												rankHouse.getId());
									}
								}
							} else {
								for (RankHouse rank_House : rankHouseList) {
									if (!rankId.equals(rank_House.getId())
											&& "整租".equals(rank_House
													.getRanktype())) {
										// 撤销 整租房源
										_houseDao.updateRankHouseStatus(
												String.valueOf(LOST),
												rankAgreement.getOperId(),
												rank_House.getId());
									}
								}
								// 修改当前房源为签约中
								_houseDao.updateRankHouseStatus(
										String.valueOf(AGREE_ING),
										rankAgreement.getOperId(),
										rankHouse.getId());
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
				// if("15".equals(rankAgreement.getPay_type()))
				// {
				// params.put("numbermonery2", String.valueOf(new
				// BigDecimal(rankAgreement.getPrice()).multiply(new
				// BigDecimal("2"))));
				// params.put("stringmonery2",
				// ConvertNum.NumToChinese(String.valueOf(new
				// BigDecimal(rankAgreement.getPrice()).multiply(new
				// BigDecimal("2")))));
				// }
				// else
				// {
				// params.put("numbermonery2", rankAgreement.getPrice());
				// params.put("stringmonery2",
				// ConvertNum.NumToChinese(rankAgreement.getPrice()));
				// }
				params.put("numbermonery2", rankAgreement.getPrice());
				params.put("stringmonery2",
						ConvertNum.NumToChinese(rankAgreement.getPrice()));
				params.put("servermonery", rankAgreement.getServiceMonery());
				params.put("propertymonery", rankAgreement.getPropertyMonery());
				params.put("stringmonery",
						ConvertNum.NumToChinese(rankAgreement.getPrice()));
				params.put("bgyear",
						rankAgreement.getBegin_time().split("-")[0]);
				params.put("bgmonth",
						rankAgreement.getBegin_time().split("-")[1]);
				params.put("bgday", rankAgreement.getBegin_time().split("-")[2]);
				if ("0".equals(rankAgreement.getPayway())) {
					params.put("payway", "支付宝");
				} else if ("1".equals(rankAgreement.getPayway())) {
					params.put("payway", "微信");
				} else if ("2".equals(rankAgreement.getPayway())) {
					params.put("payway", "银行卡");
				}
				Map mp = _contractDao.calcEndTime(rankAgreement.getBegin_time(),
						rankAgreement.getRent_month(), "1");
				String endDay = super.get(mp, "endtime");
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
				params.put("electricity_meter",
						"总值:" + rankAgreement.getElectricity_meter());
				params.put("electricity_meter_h",
						" 峰值:" + rankAgreement.getElectricity_meter_h());
				params.put("electricity_meter_l",
						" 谷值:" + rankAgreement.getElectricity_meter_l());
				params.put("gas_meter", rankAgreement.getGas_meter());
				params.put("currenyear", currentday.split("-")[0]);
				params.put("currenmonth", currentday.split("-")[1]);
				params.put("day", currentday.split("-")[2]);
				params.put("pay_type", rankAgreement.getPay_type());
				params.put("deposit", rankAgreement.getDeposit());
				String old_matched = rankAgreement.getOld_matched();
				// System.out.println("old_matched:"+JSONArray.toJSONString(old_matched.split("##")));
				if (null != old_matched && !"".equals(old_matched)) {
					String[] innerCnt = new String[0];
					String[] outCnt = new String[0];
					if (old_matched.split("##").length == 1) {
						innerCnt = old_matched.split("##")[0].split("\\|");
					} else if (old_matched.split("##").length == 2) {
						innerCnt = old_matched.split("##")[0].split("\\|");
						outCnt = old_matched.split("##")[1].split("\\|");
					}
					for (int i = 0; i < innerCnt.length; i++) {
						params.put(
								innerCnt[i].split("=")[0],
								innerCnt[i].split("=").length > 1 ? innerCnt[i]
										.split("=")[1] : "");
					}
					for (int i = 0; i < outCnt.length; i++) {
						params.put(
								outCnt[i].split("=")[0],
								outCnt[i].split("=").length > 1 ? outCnt[i]
										.split("=")[1] : "");
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
				log.debug("params:" + params);
				log.debug(JSONObject.toJSONString(params));
				signDocs.add(signDoc);
				contentMap.put("sign_docs", signDocs);
				String contentmapString = com.alibaba.fastjson.JSONObject
						.toJSONString(contentMap);
				String encryptContent = AES_CBCUtils.encrypt(aesKey,
						contentmapString.getBytes("utf-8"));
				String encryptedKey = RSAUtil.encryptByPublicKey(aesKey,
						Apis.notaryPublicKey);
				String data = encryptContent + encryptedKey;
				String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(),
						Apis.clientPrivateKey);
				String bodyText = null;
				if (RUN_TYPE) {
					log.debug("跳过合约认证，返回成功");
					bodyText = "{\"errorCode\":\"0\"}";
				} else {
					bodyText = _sysCfgService.postMessage(new CABean(
							Apis.synRankAgreement, Apis.APIID, encryptContent,
							signed, encryptedKey));
				}
				log.debug("bodyText:" + bodyText);
				//
				if (!"".equals(bodyText)) {
					try {
						errorCode = JSONObject.parseObject(bodyText).getString(
								"errorCode");
					} catch (Exception e) {
						e.printStackTrace();
					}
					log.debug("errorCode:" + errorCode);
					if ("0".equals(errorCode)) {
						if (RUN_TYPE) {
							resultMap.put("result", "-1");
							resultMap.put("msg", "CA同步到测试桩，请进行付款");
							return resultMap;
						} else {
							return rankAgreementNotarization(
									rankAgreement.getId(), userId,
									rankAgreement.getOperId(), houseId, "0",
									true);
						}
					}
				}
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				resultMap.put("result", "-1");
				resultMap.put("msg", "CA同步合同信息失败!");
				return resultMap;
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus()
						.setRollbackOnly();
				e.printStackTrace();
				resultMap.put("result", "-1");
				resultMap.put("msg", "图片上传失败!");
				return resultMap;
			}
		}
		return resultMap;
	}

	/*
	 * 处理合约信息
	 */
	@Override
	@Transactional
	public Object dealFinance(HttpServletRequest request) {
		return null;
	}

	/*
	 * 收房列表
	 */
	@Override
	public List<ContractBean> agreementList(HouseFo condition) {
		List<ContractBean> list = _contractDao
				.agreementList(initPageInfo(condition));
		if (list == null) {
			return new ArrayList<>();
		}
		return list;
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

	/*
	 * 撤销合约
	 */
	@Override
	@Transactional
	public Object cancelHouseAgreement(JSONObject json) {
		// TODO Auto-generated method stub
		Map<String, String> resultMap = new HashMap<>();
		String id = super.get(json, "id"); // 合约id
		if ("".equals(id)) {
			resultMap.put("state", "-1");
			resultMap.put("msg", "非法访问");
			return resultMap;
		}
		_contractDao.updateAgreement("id = '" + id + "'", id, "1");
		// 验证合约状态
		if (_contractDao.checkAgreementStatus("2", id, "1") == 0) {
			resultMap.put("state", "-1");
			resultMap.put("msg", "合约状态已改变,请重新查看!");
			return resultMap;
		}
		_contractDao.updateAgreement("status = 4 ", id, "1");
		resultMap.put("state", "1");
		resultMap.put("msg", "操作成功");
		return resultMap;
	}

	/*
	 * 出租合约
	 */
	@Override
	public List<RankAgreement> rankAgreementList(HouseFo condition) {
		List<RankAgreement> list = _contractDao
				.rankAgreementList(initPageInfo(condition));
		if (list == null) {
			return new ArrayList<>();
		}
		// System.out.println(JSONArray.toJSONString(list));
		return list;
	}

	/*
	 * 审批房源合约
	 */
	@Override
	public Object spHouserAgeement(HttpServletRequest request,
			Financial financial, Onstruction onstruction,
			AgreementMgeService agreementMgeService, JSONObject json) {
		Map<String, String> resultMap = new HashMap<>();
		String id = super.get(json, "id"); // 合约id
		String isPass = super.get(json, "isPass"); // 无效 3 有效2
		String houseId = super.get(json, "houseId");
		String operId = super.get(json, "operId");
		if ("".equals(id) || "".equals(isPass) || "".equals(houseId)
				|| "".equals(operId)) {
			resultMap.put("state", "-1");
			resultMap.put("msg", "非法参数");
			return resultMap;
		}
		Map mp = agreementMgeService.spHouserAgeement(request, financial,
				onstruction, net.sf.json.JSONObject.fromObject(json));
		String state = super.get(mp, "state");
		if ("1".equals(state)) {
			resultMap.put("state", "1");
			resultMap.put("msg", "操作成功");
		} else if ("-2".equals(state)) {
			resultMap.put("state", "-1");
			resultMap.put("msg", "合约状态改变,请重新查看");
		} else if ("-10".equals(state)) {
			resultMap.put("state", "-1");
			resultMap.put("msg", "工程撤销失败!");
		} else if ("-12".equals(state)) {
			resultMap.put("state", "-1");
			resultMap.put("msg", "撤销财务接口失败!");
		}
		return resultMap;
	}

	/**
	 * 银行列表
	 * 
	 * @param json
	 * @return
	 * @date 2017年1月9日
	 */
	@Override
	public List<Map<String, String>> getBankList(JSONObject json) {
		String area_id = super.get(json, "area_id"); // 区域id
		String fatherId = super.get(json, "father_id"); // 银行大类
		String bankName = super.get(json, "bankName"); // 银行名称
		int pageNumber = Integer.valueOf(super.get(json, "pageNumber")); // 页码
		int pageSize = Integer.valueOf(super.get(json, "pageSize")); // 每页显示
		return _sysCfgDao.getBankList(area_id, fatherId, bankName, pageSize
				* pageNumber, pageSize);
	}

	/**
	 * @return
	 * @date 2017年1月10日
	 */
	@Override
	public Map<String, Object> getBankSearchConditionList() {
		JSONObject json = new JSONObject();
		json.put("father_id", "-1");
		json.put("pageSize", "1000");
		json.put("pageNumber", "0");
		List<Map<String, String>> list = this.getBankList(json);
		List<Area> areaList = _sysCfgDao.getAreaList("101", "3");
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
	public Map<String, Object> getAgreementDetailSelect() {
		// TODO Auto-generated method stub
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("managerList", _sysCfgDao.getManagerList("22"));
		resultMap.put("feeRentList", _sysCfgDao.getDictit("FEERENT.TYPE"));
		resultMap.put("rentList", _sysCfgDao.getDictit("GROUP.RENT.YEAR"));
		resultMap.put("payList", _sysCfgDao.getDictit("AGREEMENT.PAYTYPE"));
		resultMap.put("propertyList", _sysCfgDao.getDictit("PROPERTY.FILE"));
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
	public Map<String, String> saveHouseAgreement(ContractBean agreement,
			HttpServletRequest request) {
		log.debug("==============agreemnt:"
				+ JSONObject.toJSONString(agreement));
		Map<String, String> resultMap = new HashMap<>();
		// 验证房源状态是否被签
		if (_houseDao.checkHouseState(agreement.getHouse_id(), "2") != 1) {
			resultMap.put("msg", "房源状态改变");
			resultMap.put("result", "-2");
			return resultMap;
		}
		// String userId = agreement.getUser_id(); // 房东id
		// 获取用户信息
		List<User> u = _sysCfgDao.getUserInfoByMobile(agreement.getWtmobile());
		String wtUserId = agreement.getWtuserid();
		User user = new User();
		if (u == null || u.isEmpty()) {
			// 用户不存在 添加用户
			user.setCertificateno(agreement.getWtIDCard());
			user.setUserName(agreement.getWtname());
			user.setUserPhone(agreement.getWtmobile());
			user.setCaAuthor("0");
			user.setEmail(agreement.getWtemail());
			user.setAddress(agreement.getWtAddress());
			_sysCfgDao.saveUserInfo(user);
			log.debug(user.toString());
			wtUserId = String.valueOf(user.getUserId());
			agreement.setWtuserid(wtUserId);
		} else {
			user = u.get(0);
			if (agreement.getWtIDCard() != null
					&& !"".equals(agreement.getWtIDCard())) {
				user.setCertificateno(agreement.getWtIDCard());
			}
			if (agreement.getWtemail() != null
					&& !"".equals(agreement.getWtemail())) {
				user.setEmail(agreement.getWtemail());
			}
			if (agreement.getWtname() != null
					&& !"".equals(agreement.getWtname())) {
				user.setUserName(agreement.getWtname());
			}
			if (agreement.getWtmobile() != null
					&& !"".equals(agreement.getWtmobile())) {
				user.setUserPhone(agreement.getWtmobile());
			}
			if (agreement.getWtAddress() != null
					&& !"".equals(agreement.getWtAddress())) {
				user.setAddress(agreement.getWtAddress());
			}
			agreement.setWtuserid(String.valueOf(user.getUserId()));
		}
		wtUserId = agreement.getWtuserid();
		// logger.info("userId:" + userId);
		String caAuthor = user.getCaAuthor();
		log.info("caAuthor:" + caAuthor);
		// 需要同步ca认证
		String errorCode = "-1";
		if (RUN_TYPE) {
			log.debug("跳过CA认证用户");
		} else {
			if ("0".equals(caAuthor)) {
				int i = 0;
				try {
					while (i < 2) {
						String resultCA = _sysCfgService
								.authentorUser(user, "A");
						log.info("==同步用户==");
						// System.out.println("==同步用户==");
						if (!"".equals(resultCA)) {
							errorCode = JSONObject.parseObject(resultCA)
									.getString("errorCode");
							if ("0".equals(errorCode)) {
								break;
							}
						}
						i++;
					}
				} catch (Exception e) {
					resultMap.put("result", "-1");
					resultMap.put("msg", "CA用户认证失败!");
					TransactionAspectSupport.currentTransactionStatus()
							.setRollbackOnly();
					return resultMap;
				}
				log.debug(JSONObject.toJSONString(user));
				// System.out.println(JSONObject.toJSONString(user));
				if (!"0".equals(errorCode)) {
					resultMap.put("result", "-1");
					resultMap.put("msg", "CA用户认证失败!");
					TransactionAspectSupport.currentTransactionStatus()
							.setRollbackOnly();
					return resultMap;
				}
			}
		}
		errorCode = "-1";
		user.setCertificateno(agreement.getWtIDCard());
		user.setUserName(agreement.getWtname());
		user.setCaAuthor("1");
		user.setEmail(agreement.getWtemail());
		_sysCfgDao.updateUserInfo(user);
		log.debug(JSONObject.toJSONString(agreement));
		// 获取用户信息
		if (agreement.getDlmobile() != null
				&& !"".equals(agreement.getDlmobile())) {
			List<User> u2 = _sysCfgDao.getUserInfoByMobile(agreement
					.getDlmobile());
			String dlUserId = "";
			User user2 = new User();
			if (u2 == null || u2.isEmpty()) {
				// 用户不存在 添加用户
				user2.setCertificateno(agreement.getDlIDCard());
				user2.setUserName(agreement.getDlname());
				user2.setUserPhone(agreement.getDlmobile());
				user2.setCaAuthor("0");
				user2.setEmail(agreement.getDlemail());
				user2.setAddress(agreement.getDlAddress());
				_sysCfgDao.saveUserInfo(user);
				dlUserId = String.valueOf(user2.getUserId());
				agreement.setDluserid(dlUserId);
			} else {
				user = u.get(0);
				if (agreement.getDlIDCard() != null
						&& !"".equals(agreement.getDlIDCard())) {
					user.setCertificateno(agreement.getDlIDCard());
				}
				if (agreement.getDlemail() != null
						&& !"".equals(agreement.getDlemail())) {
					user.setEmail(agreement.getDlemail());
				}
				if (agreement.getDlname() != null
						&& !"".equals(agreement.getDlname())) {
					user.setUserName(agreement.getDlname());
				}
				if (agreement.getDlmobile() != null
						&& !"".equals(agreement.getDlmobile())) {
					user.setUserPhone(agreement.getDlmobile());
				}
				if (agreement.getDlAddress() != null
						&& !"".equals(agreement.getDlAddress())) {
					user.setAddress(agreement.getDlAddress());
				}
				agreement.setDluserid(String.valueOf(user.getUserId()));
			}
			dlUserId = agreement.getDluserid();
			user2.setCertificateno(agreement.getDlIDCard());
			user2.setUserName(agreement.getDlname());
			user2.setEmail(agreement.getDlemail());
			_sysCfgDao.updateUserInfo(user2);
		}
		BigInteger totalMonery = new BigInteger("0");
		String[] rentMonth = agreement.getCost_a().split("\\|");
		String rentMonths = "";
		for (int i = 0; i < rentMonth.length; i++) {
			String tmpRentMonth = rentMonth[i];
			rentMonths += tmpRentMonth + "|";
			totalMonery = totalMonery.add(new BigInteger(tmpRentMonth)
					.multiply(new BigInteger("12")));
		}
		agreement.setCost_a(rentMonths);
		agreement.setCost(String.valueOf(totalMonery));
		agreement.setType("1");
		_contractDao.saveHouseAgreement(agreement);
		log.debug(agreement.getId());
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
		String houseSplit = "/" + houseId
				+ SystemConfig.getValue("FTP_AGREEMENT_PATH"); // 房源分割
		FtpUtil ftp = null;
		try {
			ftp = new FtpUtil();
			if (!"".equals(path)) {
				String[] pathArray = path.split(",");
				for (int j = 0; j < pathArray.length; j++) {
					String tmpCard = "";
					if (j > 0) {
						tmpCard = String.valueOf(j);
					}
					String tmpPath = propertyCard
							+ tmpCard
							+ pathArray[j].substring(pathArray[j]
									.lastIndexOf("."));
					newPath += "," + houseSplit + id + "/" + tmpPath;
					boolean flag = ftp.uploadFile(request.getRealPath("/")
							+ pathArray[j], tmpPath,
							SystemConfig.getValue("FTP_HOUSE_PATH")
									+ houseSplit + id + "/");
					if (!flag) {
						log.debug("上传图片失败");
						resultMap.put("result", "-1");
						resultMap.put("msg", "房产证上传失败!");
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						return resultMap;
					}
				}
			}
			if (!"".equals(propertyPath)) {
				String[] pathArray = propertyPath.split(",");
				for (int j = 0; j < pathArray.length; j++) {
					String tmpCard = "";
					if (j > 0) {
						tmpCard = String.valueOf(j);
					}
					String tmpPath = idCard
							+ tmpCard
							+ pathArray[j].substring(pathArray[j]
									.lastIndexOf("."));
					newPropertyPath += "," + houseSplit + id + "/" + tmpPath;
					boolean flag = ftp.uploadFile(request.getRealPath("/")
							+ pathArray[j], tmpPath,
							SystemConfig.getValue("FTP_HOUSE_PATH")
									+ houseSplit + id + "/");
					if (!flag) {
						log.debug("上传图片失败");
						resultMap.put("result", "-1");
						resultMap.put("msg", "产权人上传失败!");
						// System.out.println(1/0);
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						return resultMap;
					}
				}
			}
			if (!"".equals(agentPath)) {
				String[] pathArray = agentPath.split(",");
				for (int j = 0; j < pathArray.length; j++) {
					String tmpCard = "";
					if (j > 0) {
						tmpCard = String.valueOf(j);
					}
					String tmpPath = agentIdCard
							+ tmpCard
							+ pathArray[j].substring(pathArray[j]
									.lastIndexOf("."));
					newAgentPath += "," + houseSplit + id + "/" + tmpPath;
					boolean flag = ftp.uploadFile(request.getRealPath("/")
							+ pathArray[j], tmpPath,
							SystemConfig.getValue("FTP_HOUSE_PATH")
									+ houseSplit + id + "/");
					if (!flag) {
						log.debug("上传图片失败");
						resultMap.put("result", "-1");
						resultMap.put("msg", "代理人上传失败!");
						TransactionAspectSupport.currentTransactionStatus()
								.setRollbackOnly();
						return resultMap;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			resultMap.put("result", "-1");
			resultMap.put("msg", "附件上传失败!");
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
		if (!"".equals(newPropertyPath)) {
			newPropertyPath = newPropertyPath.substring(1).replace(",", "|");
		}
		if (!"".equals(newAgentPath)) {
			newAgentPath = newAgentPath.substring(1).replace(",", "|");
		}
		// 更新code 图片
		String column = "code = CONCAT('" + agreement.getHouseCode()
				+ "','A',DATE_FORMAT(NOW(),'%y'),'T'," + agreement.getId()
				+ "), file_path = '" + newPath + "', propertyPath = '"
				+ newPropertyPath + "', agentPath = '" + newAgentPath + "' ";
		_contractDao.updateAgreement(column, agreement.getId(), "1");
		_houseDao.updateHouseInfo(" house_status = 3, update_time = now()",
				houseId);
		resultMap.put("result", "1");
		resultMap.put("msg", "操作成功!");
		try {
			// ca 对接
			String aesKey = RandomStringUtils.random(16, true, true);
			Map<String, String> params = new HashMap<String, String>(); // 合约要显示的参数
			params.put("username", agreement.getWtname());
			params.put("certificateno", agreement.getWtIDCard());
			params.put("address", agreement.getAddress());
			params.put("area", agreement.getArea());
			if (agreement.getSpec() != null && !"".equals(agreement.getSpec())) {
				String spec = agreement.getSpec();
				params.put("shi", spec.split("\\|")[0]);
				params.put("ting", spec.split("\\|")[1]);
				params.put("wei", spec.split("\\|")[2]);
			}
			if (agreement.getSpec2() != null
					&& !"".equals(agreement.getSpec2())) {
				String spec2 = agreement.getSpec2();
				params.put("chu", spec2.split("\\|")[0]);
				params.put("yang", spec2.split("\\|")[1]);
				params.put("dian", spec2.split("\\|")[2]);
			}
			String keys = agreement.getKeys_count();
			if (keys != null && !"".equals(keys)) {
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
			Map mp = _contractDao.calcEndTime(agreement.getBegin_time(),
					agreement.getRentMonth(), "12");
			String endDay = super.get(mp, "endtime");
			params.put("endyear", endDay.split("-")[0]);
			params.put("endmonth", endDay.split("-")[1]);
			params.put("endday", endDay.split("-")[2]);
			params.put("free_period", agreement.getFree_period());
			params.put("agreementtype", agreement.getFreeRankType());
			if ("2".equals(agreement.getFreeRankType())) {
				// 计算每年对应的免租期
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String type2Des = "";
				String leaseDay = agreement.getFree_period(); // 免租期
				BigDecimal avgLease = new BigDecimal(0);
				if (!"".equals(leaseDay)) {
					avgLease = new BigDecimal(leaseDay);
				}
				// if(!"".equals(leaseDay))
				// {
				// try
				// {
				// avgLease = new BigDecimal(leaseDay).divide(new
				// BigDecimal(agreement.getCost_a().split("\\|").length)).setScale(0,
				// BigDecimal.ROUND_HALF_UP);
				// }
				// catch (Exception e)
				// {
				// // TODO: handle exception
				// }
				// }
				String beginTime = agreement.getBegin_time();
				Map<String, String> lastDayMonth = _contractDao.calcEndTime(
						beginTime, agreement.getRentMonth(), "12");
				String endtime = super.get(lastDayMonth, "endtime");
				List<Map<String, String>> list = new ArrayList<>();
				for (int i = 0; i < agreement.getCost_a().split("\\|").length; i++) {
					Date date = sdf.parse(beginTime);
					GregorianCalendar gc = new GregorianCalendar();
					gc.setTime(date);
					gc.add(Calendar.YEAR, 1);
					String endTime = new SimpleDateFormat("yyyy-MM-dd")
							.format(gc.getTime());
					Map<String, String> datMp = new HashMap<>();
					datMp.put("bgTime", beginTime);
					if (i == agreement.getCost_a().split("\\|").length - 1) {
						datMp.put("endTime", endtime);
					} else {
						datMp.put("endTime", endTime);
					}
					list.add(datMp);
					beginTime = endTime;
				}
				log.debug("monthList:" + list);
				for (int i = 0; i < list.size(); i++) {
					Map<String, String> datMp = list.get(i);
					String bgTime = super.get(datMp, "bgTime");
					String endTime = super.get(datMp, "endTime");
					Calendar now = Calendar.getInstance();
					now.setTime(sdf.parse(bgTime));
					now.set(Calendar.DATE,
							now.get(Calendar.DATE) + avgLease.intValue());
					bgTime = sdf.format(now.getTime());
					type2Des += bgTime + "至" + endTime + "<br/>";
				}
				params.put("type2desc", type2Des);
				params.put("avg_free_period",
						String.valueOf(avgLease.intValue()));
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
			params.put("electricity_meter",
					"总值:" + agreement.getElectricity_meter());
			params.put("electricity_meter_h",
					" 峰值:" + agreement.getElectricity_meter_h());
			params.put("electricity_meter_l",
					" 谷值:" + agreement.getElectricity_meter_l());
			params.put("gas_meter", agreement.getGas_meter());
			// params.put("currenyear", currentday.split("-")[0]);
			// params.put("currenmonth", currentday.split("-")[1]);
			params.put("day", currentday.split("-")[2]);
			String new_old_matched = agreement.getNew_old_matched();
			String old_matched = "";
			String kt = "";
			if (new_old_matched != null && !"".equals(new_old_matched)) {
				log.debug(String.valueOf(new_old_matched.split("@@").length));
				if (new_old_matched.split("@@").length > 0) {
					log.debug(new_old_matched.split("@@")[0]);
					old_matched = new_old_matched.split("@@")[0];
				}
				if (new_old_matched.split("@@").length > 1) {
					kt = new_old_matched.split("@@")[1];
				}
			}
			if (kt != null && !"".equals(kt)) {
				String[] innerCnt = kt.split("\\|");
				for (int i = 0; i < innerCnt.length; i++) {
					params.put(
							"kt" + i,
							innerCnt[i].split("=").length > 1 ? innerCnt[i]
									.split("=")[1] : "");
				}
			}
			if (null != old_matched && !"".equals(old_matched)) {
				for (int i = 0; i < old_matched.split("##").length; i++) {
					String[] array = old_matched.split("##")[i].split("\\|");
					for (int j = 0; j < array.length; j++) {
						params.put(
								array[j].split("=")[0],
								array[j].split("=").length > 1 ? array[j]
										.split("=")[1] : "");
					}
				}
			}
			// logger.debug(params);
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
			// System.out.println("params:"+params);
			log.debug(params.toString());
			signDocs.add(signDoc);
			contentMap.put("sign_docs", signDocs);
			// System.out.println(JSONObject.toJSONString(contentMap));
			String contentmapString = com.alibaba.fastjson.JSONObject
					.toJSONString(contentMap);// JSONObject.toJSONString(contentMap).toString();
			// System.out.println(contentmapString);
			String encryptContent = AES_CBCUtils.encrypt(aesKey,
					contentmapString.getBytes("utf-8"));
			String encryptedKey = RSAUtil.encryptByPublicKey(aesKey,
					Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(),
					Apis.clientPrivateKey);
			String bodyText = null;
			if (RUN_TYPE) {
				log.debug("跳过合约认证，返回成功");
				bodyText = "{\"errorCode\":\"0\"}";
			} else {
				bodyText = _sysCfgService.postMessage(new CABean(
						Apis.synRankAgreement, Apis.APIID, encryptContent,
						signed, encryptedKey));
			}
			log.debug("bodyText:" + bodyText);
			// System.out.println("bodyText:" + bodyText);
			if (!"".equals(bodyText)) {
				try {
					errorCode = JSONObject.parseObject(bodyText).getString(
							"errorCode");
				} catch (Exception e) {
					e.printStackTrace();
				}
				log.debug("errorCode:" + errorCode);
				// System.out.println("errorCode:" + errorCode);
				if ("0".equals(errorCode)) {
					if (RUN_TYPE) {
						resultMap.put("result", "-1");
						resultMap.put("msg", "CA同步到测试桩，请进行付款");
						return resultMap;
					} else {
						return rankAgreementNotarization(agreement.getId(),
								agreement.getWtuserid(), agreement.getOperid(),
								houseId, "0", false);
					}
				}
			}
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			resultMap.put("result", "-1");
			resultMap.put("msg", "CA同步合同信息失败!");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	 *            flag true ： 出租 false : 收房
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> rankAgreementNotarization(String agreementId,
			String userId, String operId, String houseId, String isValidate,
			boolean flag) {
		Map<String, String> contentMap = new HashMap<>();
		Map<String, String> resultMap = new HashMap<>();
		try {
			if (!"0".equals(isValidate)) {
				String tag = null;
				if (flag) {
					tag = "2";
				} else {
					tag = "1";
				}
				if (_contractDao.checkAgreementStatus("11", agreementId, tag) != 1) {
					resultMap.put("msg", "合约状态改变,请重新查看!");
					resultMap.put("result", "-1");
					return resultMap;
				}
			}
			String aesKey = RandomStringUtils.random(16, true, true);
			contentMap.put("docid", agreementId);
			contentMap.put("userid", userId);
			if (flag) {
				/* 出租 */
				contentMap.put("callbackurl", Apis.callbackurl);
				contentMap.put("noticeurl", Apis.serverUrl
						+ "ca/dealRankAgreementInfo.do?agreementId="
						+ agreementId + "&operId=" + operId + "&houseId="
						+ houseId);
			} else {
				/* 收房 */
				contentMap.put("callbackurl", Apis.callAgreementBackurl);
				contentMap.put("noticeurl", Apis.serverUrl
						+ "ca/caCallBackAgreement.do?agreementId="
						+ agreementId + "&operId=" + operId + "&houseId="
						+ houseId);
			}
			String contentmapString = com.alibaba.fastjson.JSONObject
					.toJSONString(contentMap);
			String encryptContent = AES_CBCUtils.encrypt(aesKey,
					contentmapString.getBytes("utf-8"));
			String encryptedKey = RSAUtil.encryptByPublicKey(aesKey,
					Apis.notaryPublicKey);
			String data = encryptContent + encryptedKey;
			String signed = RSAUtil.sign(MD5Utils.sign(data).toLowerCase(),
					Apis.clientPrivateKey);
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功!");
			resultMap.put("url", Apis.showAgreement);
			resultMap.put("apiid", Apis.APIID);
			resultMap.put("content", encryptContent);
			resultMap.put("key", encryptedKey);
			resultMap.put("signed", signed);
			log.debug("resultMap:" + resultMap);
			// System.out.println("resultMap:" + resultMap);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (RUN_TYPE) {
			resultMap = new HashMap<>();
			resultMap.put("result", "1");
			resultMap.put("msg", "操作成功!");
		}
		return resultMap;
	}

}
