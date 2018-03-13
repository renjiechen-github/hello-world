/*
 * Copyright (c) 2014 . All Rights Reserved.
 */

package pccom.web.server.house.rankhouse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pccom.common.SQLconfig;
import pccom.common.util.Batch;
import pccom.common.util.FtpUtil;
import pccom.common.util.StringHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.OrderInterfaces;
import pccom.web.interfaces.RankHouse;
import pccom.web.server.BaseService;
import pccom.web.server.agreement.AgreementMgeService;

import com.raising.framework.utils.md5.MD5Utils;
import com.raising.framework.utils.rsa.RSAUtil;
import com.room1000.core.exception.BaseAppException;
import com.room1000.core.utils.DateUtil;
import com.room1000.suborder.define.SubOrderStateDef;
import com.room1000.suborder.define.SystemAccountDef;
import com.room1000.suborder.utils.SubOrderUtil;
import com.room1000.workorder.define.WorkOrderTypeDef;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.service.IWorkOrderService;
import com.ycdc.appserver.bus.dao.house.HouseMapper;
import com.ycdc.appserver.bus.dao.user.IUserDao;
import com.ycdc.appserver.bus.service.house.HouseMgrService;
import com.ycdc.appserver.bus.service.syscfg.AES_CBCUtils;
import com.ycdc.appserver.bus.service.syscfg.Apis;
import com.ycdc.appserver.model.house.RankAgreement;
import com.ycdc.appserver.model.user.User;
//import com.ycdc.appserver.model.house.RankHouse;
import com.ycdc.core.plugin.sms.SmsSendMessage;

@Service("RankHouseService")
public class RankHouseService extends BaseService
{

	public static final int WAIT_SUBMIT = 0; // 待提交
	public static final int WAIT_APPROVE = 1; // 待审批
	public static final int DEING = 2; // 施工中
	public static final int COMPLETE = 3; // 已发布
	public static final int AGREE_ING = 4;// 签约中
	public static final int RANT_OUT = 5; // 已租
	public static final int LOST = 6; // 失效
	public static final int SOLDOUT = 7; // 已下架
	@Autowired
    private IWorkOrderService workOrderService;
	@Autowired
	HouseMapper houseMapper;
	@Autowired
	Financial financial;
	@Resource
	IUserDao userMapper;

	/**
	 * 查询租房信息
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void getRankHouse(HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		String houseId = req.getAjaxValue(request, "houseId");
		String type = req.getAjaxValue(request, "type");
		String status = req.getAjaxValue(request, "status");
		String sql = getSql("basehouse.getRankHosue.main");
		String id = req.getValue(request, "id");
		String areaid = req.getValue(request, "areaid");
		String groupid = req.getValue(request, "groupid");
		String houseName = req.getAjaxValue(request, "houseName");
		List<String> params = new ArrayList<String>();
		if (!"".equals(houseId))
		{
			sql += getSql("basehouse.getRankHosue.houseId");
			params.add(houseId);
		}
		if (!"".equals(areaid))
		{
			sql += getSql("basehouse.getRankHosue.areaid");
			params.add(areaid);
		}
		if (!"".equals(groupid))
		{
			sql += getSql("basehouse.getRankHosue.groupid");
			params.add(groupid);
		}
		if (!"".equals(status))
		{
			sql += getSql("basehouse.getRankHosue.status");
			params.add(status);
		}
		if (!"".equals(type))
		{
			sql += getSql("basehouse.getRankHosue.type");
			params.add(type);
		}
		if (!"".equals(houseName))
		{
			sql += getSql("basehouse.getRankHosue.houseName");
			params.add("%" + houseName + "%");
			params.add("%" + houseName + "%");
			params.add("%" + houseName + "%");
		}
		if (!"".equals(id))
		{
			sql += getSql("basehouse.getRankHosue.id");
			params.add(id);
		}
		getPageList(
				request,
				response,
				sql.replaceAll(
						"###",
						SystemConfig.getValue("FTP_HTTP")
								+ SystemConfig.getValue("FTP_RANKHOUSE_HTTP_PATH")),
				params.toArray(), " basehouse.getRankHosue.orderby ");
	}

	/**
	 * 查询租房信息
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getUphouse(HttpServletRequest request, HttpServletResponse response)
	{
		pccom.web.beans.User user = getUser(request);
		String ids = user.getRoles();
		String houseId = req.getAjaxValue(request, "houseId");
		String sql = getSql("basehouse.getRankHosue.main");
		String id = req.getValue(request, "id");
		List<String> params = new ArrayList<String>();
		if (!"".equals(houseId))
		{
			sql += getSql("basehouse.getRankHosue.houseId");
			params.add(houseId);
		}
		if (!"".equals(id))
		{
			sql += getSql("basehouse.getRankHosue.id");
			params.add(id);
		}
		Map<String, Object> returnMap = getReturnMap("1");
		List<Map<String, Object>> list = db.queryForList(sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH")),params.toArray());
		for (Map<String, Object> map : list)
		{
			map.put("roleIds", ids);
		}
		returnMap.put("list",JSONArray.fromObject(list));
		return returnMap;
	}

	/**
	 * 租房修改||发布房源
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public Object rankHouseUpdate(final HttpServletRequest request) throws IOException
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		@SuppressWarnings("unchecked")
		Object i = db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Integer execute() throws Exception
			{
				String house_id = req.getAjaxValue(request, "houseid");
				String id = req.getAjaxValue(request, "rankid");
				String rankName = req.getAjaxValue(request, "rankName");
				String fee = req.getAjaxValue(request, "fee");
				String rankArea = req.getAjaxValue(request, "rankArea");
				String images = req.getAjaxValue(request, "images");
				String type = req.getAjaxValue(request, "type");
				String rankid[] = id.split(",");
				String rankName1[] = rankName.split(",");
				String fee1[] = fee.split(",");
				String rankArea1[] = rankArea.split(",");
				String images1[] = images.split(",");
				// 修改租赁信息
				String sql = getSql("basehouse.rankHosue.update");
				for (int j = 0; j < rankid.length; j++)
				{
					// String images, int keyId, HttpServletRequest request
					String roomPath = "";
					// 图片上传
					if (!"0".equals(images1[j]))
					{
						Map<String, String> roomnewPath = ImageWork(images1[j],
								Integer.parseInt(rankid[j]), request);
						if ("1".equals(str.get(roomnewPath, "state")))
						{
							roomPath = str.get(roomnewPath, "newPath");
						}
						else
						{
							logger.debug("图片上传失败");
							this.rollBack();
							throw new Exception("网络图片上传失败");
						}
					}
					/*
					 * if (!"".equals(roomPath)) { roomPath=roomPath.substring(1); }
					 */
					// 判断是否发布房源type发布与否
					if ("1".equals(type))
					{
						this.update(
								sql,
								new Object[] { oper, fee1[j], roomPath, rankArea1[j],
										String.valueOf(COMPLETE),
										SystemConfig.getValue("RANKHOUSE.DESC"),
										SystemConfig.getValue("RANKHOUSE.MATCH"),
										rankid[j] });
						String sqlh = getSql("basehouse.houseInfo.updateHouserInfo")
								.replace("####", " publish= ?, update_time = now() ");
						this.update(sqlh, new Object[] { 1, house_id });
					}
					else
					{
						this.update(
								sql,
								new Object[] { oper, fee1[j], roomPath, rankArea1[j],
										String.valueOf(WAIT_APPROVE),
										SystemConfig.getValue("RANKHOUSE.DESC"),
										SystemConfig.getValue("RANKHOUSE.MATCH"),
										rankid[j] });
					}
				}
				// 发布房源
				if ("1".equals(type))
				{
					String csql = getSql("basehouse.houseInfo.checkState");
					int status = this.queryForInt(csql, new Object[] { house_id });
					if (status == 7)
					{
						String hsql = getSql("basehouse.houseInfo.updateHouserInfo")
								.replace("####",
										" house_status = ?, update_time = now() ");
						this.update(hsql, new Object[] { "8", house_id });
					}
				}
				return 1;
			}
		});
		// return this.getReturnMap(i);
		return getReturnMap(i);
	}

	/**
	 * 租房修改
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public Object rankSeve(final HttpServletRequest request) throws IOException
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		@SuppressWarnings("unchecked")
		Object i = db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Integer execute() throws Exception
			{
				String id = req.getAjaxValue(request, "id");
				String fee = req.getAjaxValue(request, "fee");
				String rankArea = req.getAjaxValue(request, "rankArea");
				String images = req.getAjaxValue(request, "images");
				// 修改租赁信息
				String sql = getSql("basehouse.rankHosue.update");
				// String images, int keyId, HttpServletRequest request
				String roomPath = "";
				// 图片上传
				Map<String, String> roomnewPath = ImageWork(images, Integer.parseInt(id),
						request);
				if ("1".equals(str.get(roomnewPath, "state")))
				{
					roomPath = str.get(roomnewPath, "newPath");
				}
				else
				{
					logger.debug("图片上传失败");
					this.rollBack();
					throw new Exception("网络图片上传失败");
				}
				/*
				 * if (!"".equals(roomPath)) { roomPath=roomPath.substring(1); }
				 */
				this.update(
						sql,
						new Object[] { oper, fee, roomPath, rankArea,
								String.valueOf(WAIT_APPROVE),
								SystemConfig.getValue("RANKHOUSE.DESC"),
								SystemConfig.getValue("RANKHOUSE.MATCH"), id });
				return 1;
			}
		});
		// return this.getReturnMap(i);
		return getReturnMap(i);
	}

	/**
	 * 租房修改
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public Object updateRankHouse(final HttpServletRequest request)
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		@SuppressWarnings("unchecked")
		Object i = db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Integer execute() throws Exception
			{
				String[] ids = req.getValues(request, "id"); // id
				String[] fees = req.getValues(request, "price"); // 价格
				String[] rankAreas = req.getValues(request, "areaid"); // 面积
				String[] imagess = req.getValues(request, "images"); // 图片
				// 修改租赁信息
				String sql = getSql("basehouse.rankHosue.update");
				for (int i = 0; i < ids.length; i++)
				{
					String id = ids[i];
					String images = imagess[i];
					String rankArea = rankAreas[i];
					String fee = fees[i];
					String roomPath = "";
					// 图片上传
					Map<String, String> roomnewPath = ImageWork(images,
							Integer.parseInt(id), request);
					if ("1".equals(str.get(roomnewPath, "state")))
					{
						roomPath = str.get(roomnewPath, "newPath");
					}
					else
					{
						logger.debug("图片上传失败");
						this.rollBack();
						return -3;
					}
					this.update(
							sql,
							new Object[] { oper, fee, roomPath, rankArea,
									String.valueOf(WAIT_APPROVE),
									SystemConfig.getValue("RANKHOUSE.DESC"),
									SystemConfig.getValue("RANKHOUSE.MATCH"), id });
				}
				return 1;
			}
		});
		// return this.getReturnMap(i);
		return getReturnMap(i);
	}

	/**
	 * 图片处理公共方法
	 */
	public Map<String, String> ImageWork(String images, int keyId,
			HttpServletRequest request)
	{
		String newPath = "";
		FtpUtil ftp = null;
		String houseId = req.getValue(request, "houseId"); // 房源id
		Map<String, String> gmap = new HashMap<String, String>();
		try
		{
			ftp = new FtpUtil();
			if (!"".equals(images))
			{
				String[] pathArray = images.split("\\|");
				for (int j = 0; j < pathArray.length; j++)
				{
					if (pathArray[j].contains(SystemConfig.getValue("FTP_HTTP")))
					{
						if (pathArray[j].contains(SystemConfig
								.getValue("FTP_GROUP_HTTP_PATH")))
						{
							// 构造URL
							URL url = new URL(pathArray[j].replace("upload/tmp/", ""));
							// 打开连接
							URLConnection con = url.openConnection();
							// 设置请求超时为5s
							con.setConnectTimeout(5 * 1000);
							// 输入流
							InputStream is = con.getInputStream();
							String newname = pathArray[j]
									.replace("upload/tmp/", "")
									.replace(
											SystemConfig.getValue("FTP_HTTP")
													+ SystemConfig
															.getValue("FTP_GROUP_HTTP_PATH"),
											"");
							String newname2 = newname.substring(
									newname.lastIndexOf("/") + 1, newname.length());
							newPath += "/" + houseId
									+ SystemConfig.getValue("FTP_RANKHOUSE_PATH") + keyId
									+ "/" + newname2 + "|";
							ftp.uploadFile(is, newname2,
									SystemConfig.getValue("FTP_HOUSE_PATH") + houseId
											+ SystemConfig.getValue("FTP_RANKHOUSE_PATH")
											+ keyId + "/");
							is.close();
							continue;
						}
						newPath += pathArray[j].replace("upload/tmp/", "").replace(
								SystemConfig.getValue("FTP_HTTP")
										+ SystemConfig.getValue("FTP_HOUSE_HTTP_PATH")
										+ "/", "")
								+ "|";
						continue;
					}
					String tmpPath = String.valueOf(keyId)
							+ UUID.randomUUID().toString().replaceAll("-", "") + ".png";
					newPath += "/" + houseId
							+ SystemConfig.getValue("FTP_RANKHOUSE_PATH") + keyId + "/"
							+ tmpPath + "|";
					boolean flag = ftp.uploadFile(
							request.getRealPath("/") + pathArray[j], tmpPath,
							SystemConfig.getValue("FTP_HOUSE_PATH") + houseId
									+ SystemConfig.getValue("FTP_RANKHOUSE_PATH") + keyId
									+ "/");
					if (!flag)
					{
						gmap.put("state", "-3");
						return gmap;
					}
				}
			}
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			gmap.put("state", "-3");
			return gmap;
		}
		finally
		{
			// 关闭流
			if (ftp != null)
			{
				ftp.closeServer();
			}
		}
		gmap.put("state", "1");
		gmap.put("newPath", newPath);
		return gmap;
	}

	/**
	 * 验证合约状态
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月23日
	 */
	public Object checkRankHouseStatus(HttpServletRequest request)
	{
		String id = req.getValue(request, "id"); // houseRank id
		String sql = getSql("basehouse.rankHosue.checkRankHouseStatus");
		logger.debug(str.getSql(sql, new Object[] { id, String.valueOf(COMPLETE) }));
		return getReturnMap(db.queryForInt(sql,
				new Object[] { id, String.valueOf(COMPLETE) }));
	}

	/**
	 * 保存租赁合约
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月23日
	 */
	@SuppressWarnings("unchecked")
	public Object saveRankAgreement(final HttpServletRequest request)
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				// TODO Auto-generated method stub
				String id = req.getValue(request, "id");
				String houseId = req.getValue(request, "houseId"); // 房源id
				String rank_id = req.getValue(request, "rank_id"); // 租赁id
				String agreement_name = req.getValue(request, "agreement_name"); // 合约名称
				String agreement_code = req.getValue(request, "agreement_code"); // 合约编号
				String name = req.getValue(request, "name"); // 租客姓名
				String mobile = req.getValue(request, "mobile"); // 租客号码
				String manageId = req.getValue(request, "manageId"); // 签约管家
				String rankPrice = req.getValue(request, "rankPrice"); // 合约租金
				String payType = req.getValue(request, "payType"); // 付款方式
				String validateDate = req.getValue(request, "validateDate"); // 生效时间
				String rankDate = req.getValue(request, "rankDate"); // 租期
				String picPath = req.getValue(request, "picPath"); // 图片
				String agreementDes = req.getValue(request, "agreementDes"); // 合约描述
				String certificateno = req.getValue(request, "certificateno"); // 身份证号码
				String houseSplit = "/" + houseId
						+ SystemConfig.getValue("FTP_RANK_AGREEMENT_PATH"); // 房源分隔
				logger.debug(houseSplit);
				String sql = "";
				String newPath = "";
				int zkId = 0; // 租客id
				// 更新房源信息
				sql = getSql("basehouse.rankHosue.updateRankHouseStatus").replace("####",
						" id = ?");
				this.update(sql, new Object[] { rank_id, rank_id });
				if ("".equals(id))
				{
					sql = getSql("basehouse.rankHosue.checkRankHouseStatus");
					logger.debug(str.getSql(sql,
							new Object[] { rank_id, String.valueOf(COMPLETE) }));
					if (this.queryForInt(sql,
							new Object[] { rank_id, String.valueOf(COMPLETE) }) == 0)
					{
						return -2;
					}
					// 验证租客号码是否存在，如果不存在，新增租客号码
					sql = getSql("basehouse.houseInfo.checkOwnerExist");
					logger.debug(str.getSql(sql, new Object[] { mobile }));
					Map<String, Object> userInfo = db.queryForMap(sql,
							new Object[] { mobile });
					if (userInfo.isEmpty())
					{
						sql = getSql("basehouse.houseInfo.insertOwner");
						zkId = this.insert(sql, new Object[] { name, mobile, 1,
								certificateno });
					}
					else
					{
						zkId = Integer.parseInt(StringHelper.get(userInfo, "id"));
						String newName = StringHelper.get(userInfo, "username");
						String newCertificateno = StringHelper.get(userInfo,
								"certificateno");
						if ("".equals(newName))
						{
							// 更新用户信息
							sql = getSql("basehouse.houseInfo.updateUserName").replace(
									"####", "username = ?");
							this.update(sql, new Object[] { name, zkId });
						}
						if ("".equals(newCertificateno))
						{
							// 更新用户信息
							sql = getSql("basehouse.houseInfo.updateUserName").replace(
									"####", "certificateno = ?");
							this.update(sql, new Object[] { certificateno, zkId });
						}
					}
					Map mp2 = this.queryForMap(
							getSql("basehouse.rankHosue.getHouseAgreementId"),
							new Object[] { houseId });
					String fathId = StringHelper.get(mp2, "id");
					String arearid = StringHelper.get(mp2, "areaid");
					logger.debug(fathId + ":" + houseId);
					String rankCode = this.queryForString(
							getSql("basehouse.rankHosue.getRankCode"),
							new Object[] { rank_id });
					sql = getSql("basehouse.rankHosue.insertRankAgreement");
					logger.debug(str.getSql(
							sql,
							new Object[] { agreement_name, 2, zkId, mobile, validateDate,
									validateDate, rankDate, fathId, rank_id,
									agreementDes, rankDate, rankPrice, manageId, payType,
									agreement_code, rankPrice, validateDate,
									getUser(request).getId(), arearid }));
					id = String.valueOf(this.insert(
							sql,
							new Object[] { agreement_name, 2, zkId, mobile, validateDate,
									validateDate, rankDate, fathId, rank_id,
									agreementDes, rankDate, rankPrice, manageId, payType,
									agreement_code, rankPrice, validateDate,
									getUser(request).getId(), arearid }));
					logger.debug(id);
					if ("-2".equals(id))
					{
						return -3;
					}
					FtpUtil ftp = null;
					try
					{
						ftp = new FtpUtil();
						if (!"".equals(picPath))
						{
							String[] pathArray = picPath.split(",");
							for (int j = 0; j < pathArray.length; j++)
							{
								String tmpPath = UUID.randomUUID().toString()
										.replaceAll("-", "")
										+ pathArray[j].substring(pathArray[j]
												.lastIndexOf("."));
								newPath += "," + houseSplit + id + "/" + tmpPath;
								// logger.debug(request.getRealPath("/")+pathArray[j]);
								boolean flag = ftp.uploadFile(request.getRealPath("/")
										+ pathArray[j], tmpPath,
										SystemConfig.getValue("FTP_HOUSE_PATH")
												+ houseSplit + id + "/");
								if (!flag)
								{
									this.rollBack();
									return -3;
								}
							}
						}
					}
					catch (Exception e)
					{
						// TODO: handle exception
						e.printStackTrace();
						this.rollBack();
						return -3;
					}
					finally
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
					sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
							.replace("####",
									" code = CONCAT(?,'A',DATE_FORMAT(NOW(),'%y'),'R',?), file_path = ? ");
					logger.debug(str.getSql(sql,
							new Object[] { rankCode, id, newPath, id }));
					this.update(sql, new Object[] { rankCode, id, newPath, id });
					// 调用刘飞接口
					String locksql = getSql("basehouse.rankHosue.lockRank");
					this.update(locksql, new Object[] { houseId, houseId });
					sql = getSql("basehouse.getRankHosue.main")
							+ getSql("basehouse.getRankHosue.houseId");
					@SuppressWarnings("unchecked")
					List<Map<String, Object>> list = this.queryForList(
							sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP")
									+ SystemConfig.getValue("FTP_RANKHOUSE_HTTP_PATH")),
							new Object[] { houseId });
					String usSql = getSql("basehouse.rankHosue.updateStatus");
					for (Map<String, Object> mp : list)
					{
						if (rank_id.equals(StringHelper.get(mp, "id")))
						{
							if ("已发布".equals(StringHelper.get(mp, "rank_status")))
							{
								if ("整租".equals(StringHelper.get(mp, "rank_type")))
								{
									for (Map<String, Object> mp1 : list)
									{
										if (!rank_id.equals(StringHelper.get(mp1, "id")))
										{
											// 撤销 合租房源
											this.update(
													usSql,
													new Object[] { String.valueOf(LOST),
															oper,
															StringHelper.get(mp1, "id") });
										}
										else
										{
											// 修改当前状态为签约中
											this.update(
													usSql,
													new Object[] {
															String.valueOf(AGREE_ING),
															oper,
															StringHelper.get(mp1, "id") });
										}
									}
								}
								else
								{
									for (Map<String, Object> mp1 : list)
									{
										if (!rank_id.equals(StringHelper.get(mp1, "id"))
												&& "整租".equals(StringHelper.get(mp1,
														"rank_type")))
										{
											// 撤销 整租房源
											this.update(
													usSql,
													new Object[] { String.valueOf(LOST),
															oper,
															StringHelper.get(mp1, "id") });
										}
									}
									// 修改当前房源为签约中
									this.update(usSql,
											new Object[] { String.valueOf(AGREE_ING),
													oper, StringHelper.get(mp, "id") });
								}
							}
							else
							{
								// throw new Exception("房源已签约！");
								this.rollBack();
								return -4;
							}
						}
					}
				}
				else
				{
					sql = getSql("basehouse.rankHosue.checkRankHouseStatus");
					logger.debug(str.getSql(sql,
							new Object[] { rank_id, String.valueOf(AGREE_ING) }));
					if (this.queryForInt(sql,
							new Object[] { rank_id, String.valueOf(AGREE_ING) }) == 0)
					{
						return -2;
					}
					sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
							.replace("####", " id = ? ");
					this.update(sql, new Object[] { id, id });
					// 需要验证出租状态是否改变
					sql = getSql("basehouse.rankHosue.checkRankAgreementStatus");
					if (this.queryForInt(sql, new Object[] { id, 0 }) == 0)
					{
						// 出租房源状态已经改变
						return -2;
					}
					FtpUtil ftp = null;
					try
					{
						ftp = new FtpUtil();
						if (!"".equals(picPath))
						{
							String[] pathArray = picPath.split(",");
							for (int j = 0; j < pathArray.length; j++)
							{
								if (pathArray[j].contains(SystemConfig
										.getValue("FTP_HTTP")))
								{
									newPath += ","
											+ pathArray[j]
													.replace("upload/tmp/", "")
													.replace(
															SystemConfig
																	.getValue("FTP_HTTP")
																	+ SystemConfig
																			.getValue("FTP_AGREEMENT_HTTP_PATH"),
															"")
													.replace(
															SystemConfig
																	.getValue("FTP_HTTP")
																	+ SystemConfig
																			.getValue("FTP_HOUSE_HTTP_PATH"),
															"");
									continue;
								}
								String tmpPath = UUID.randomUUID().toString()
										.replaceAll("-", "")
										+ pathArray[j].substring(pathArray[j]
												.lastIndexOf("."));
								newPath += "," + houseSplit + id + "/" + tmpPath;
								boolean flag = ftp.uploadFile(request.getRealPath("/")
										+ pathArray[j], tmpPath,
										SystemConfig.getValue("FTP_HOUSE_PATH")
												+ houseSplit + id + "/");
								if (!flag)
								{
									logger.debug("上传图片失败");
									this.rollBack();
									return -3;
								}
							}
						}
					}
					catch (Exception e)
					{
						// TODO: handle exception
						e.printStackTrace();
						this.rollBack();
						return -3;
					}
					finally
					{
						if (ftp != null)
						{
							ftp.closeServer();
						}
					}
					if (!"".equals(newPath))
					{
						newPath = newPath.substring(1).replace(",", "|");
						;
					}
					// 插入历史记录
					this.insertTableLog(request, "yc_agreement_tab", " and id = '" + id
							+ "'", "修改出租合约信息", new ArrayList<String>(), getUser(request)
							.getId());
					// 更新合约信息
					sql = getSql("basehouse.rankHosue.updateRankAgreement");
					// logger.debug(str.getSql(sql, new Object[]{agreement_name,
					// validateDate, validateDate, rankDate, newPath, agreementDes,
					// rankPrice, manageId, payType, agreement_code, rankPrice, rankDate,
					// validateDate, id}));
					this.update(sql, new Object[] { agreement_name, validateDate,
							validateDate, rankDate, newPath, agreementDes, rankPrice,
							manageId, payType, agreement_code, rankPrice, rankDate,
							validateDate, id });
				}
				return 1;
			}
		}));
	}

	/**
	 * 租房合约类别
	 * 
	 * @param request
	 * @author suntf
	 * @date 2016年8月23日
	 */
	public void rankAgreementList(HttpServletRequest request, HttpServletResponse response)
	{
		String status = req.getValue(request, "status"); // 合约状态
		String keyWord = req.getAjaxValue(request, "keyWord"); // 关键字
		String accountid = req.getValue(request, "accountid"); // 合约管家
		String arear = req.getValue(request, "arear"); // 区域
		String isSelf = req.getValue(request, "isSelf"); // 自己
		String trading = req.getValue(request, "trading"); // 商圈
		String endTime = req.getValue(request, "endtime"); // 结束时间
		String selectTime = req.getValue(request, "selectTime"); // 选择查询时间类型 0 到期时间 1 创建时间
		List<String> param = new ArrayList<String>();
		String sql = getSql("basehouse.rankHosue.main");
		if (!"".equals(endTime) && "0".equals(selectTime))
		{
			sql += getSql("basehouse.rankHosue.endtime");
			param.add(endTime.split("~")[0]);
			param.add(endTime.split("~")[1]);
		}
		if (!"".equals(endTime) && "1".equals(selectTime))
		{
			sql += getSql("basehouse.rankHosue.createtime");
			param.add(endTime.split("~")[0]);
			param.add(endTime.split("~")[1]);
		}
		if (!"".equals(status))
		{
			sql += getSql("basehouse.rankHosue.rankAgreementStatu");
			param.add(status);
		}
		if (!"".equals(accountid))
		{
			sql += getSql("basehouse.rankHosue.angentId");
			param.add(accountid);
		}
		if (!"".equals(arear))
		{
			sql += getSql("basehouse.rankHosue.arearId");
			param.add(arear);
		}
		if (!"".equals(trading))
		{
			sql += getSql("basehouse.rankHosue.trading");
			param.add(trading);
		}
		if ("1".equals(isSelf))
		{
			// 自己
			sql += getSql("basehouse.rankHosue.isself");
			param.add(this.getUser(request).getId());
		}
		if (!"".equals(keyWord))
		{
			sql += getSql("basehouse.rankHosue.keyWord");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
			param.add("%" + keyWord + "%");
		}
		logger.debug(str.getSql(sql, param.toArray()));
		this.getPageList(request, response, sql, param.toArray(),
				"basehouse.rankHosue.orderby");
	}

	/**
	 * 加载租赁信息
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月24日
	 */
	public Object loadAgreementInfo(HttpServletRequest request)
	{
		String id = req.getValue(request, "id"); // 租赁id
		String sql = getSql("basehouse.rankHosue.main")
				+ getSql("basehouse.rankHosue.rankId");
		logger.debug(str.getSql(sql, new Object[] { id }));
		Map<String, Object> mp = db.queryForMap(sql, new Object[] { id });
		String filePath = StringHelper.get(mp, "file_path");
		String qz = SystemConfig.getValue("FTP_HTTP")
				+ SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		filePath = qz + filePath.replaceAll("\\|", "\\|" + qz);
		mp.put("file_path", filePath);
		logger.debug(mp.toString());
		return mp;
	}

	/**
	 * 提交合约
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@SuppressWarnings("unchecked")
	public Object submitRankAgreement(final HttpServletRequest request)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id"); // 租赁id
				String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
						.replace("####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证房源状态 如果非1 房源状态改变
				if (this.queryForInt(
						getSql("basehouse.rankHosue.checkRankAgreementStatus"),
						new Object[] { id, 0 }) != 1)
				{
					return -2;
				}
				// 插入历史记录
				this.insertTableLog(request, "yc_agreement_tab",
						" and id = '" + id + "'", "提交租赁合约", new ArrayList<String>(),
						getUser(request).getId());
				this.update(getSql("basehouse.rankHosue.updateRankAgreementStatus")
						.replace("####", "  status = ? "), new Object[] { 1, id });
				return 1;
			}
		}));
	}

	public Map initDelRankHouse(HttpServletRequest request, RankHouse rankHouse,
			Financial financial)
	{
		String id = req.getValue(request, "id"); // 租赁id
		String house_rank_id = req.getValue(request, "house_rank_id"); // 租赁房源
		String houseId = req.getValue(request, "houseId"); // 房源id
		String status = req.getValue(request, "status");
		JSONObject mp = new JSONObject();
		mp.put("id", id);
		mp.put("house_rank_id", house_rank_id);
		mp.put("houseId", houseId);
		mp.put("status", status);
		mp.put("operId", this.getUser(request).getId());
		return this.delRankAgreement(request, rankHouse, mp, financial);
	}

	/**
	 * 定时任务删除租赁合约为待付款的合约
	 * 
	 * @param request
	 * @param rankHouse
	 * @param mp
	 * @param financial
	 */
	public void autoDelRankAgreement(final HttpServletRequest request,
			final RankHouse rankHouse, final Financial financial)
	{
		String sql = getSql("basehouse.rankHosue.autoDelRankAgreement");
		List<Map<String, String>> list = db.queryForList(sql);
		for (int i = 0; i < list.size(); i++)
		{
			Map mp = list.get(i);
			JSONObject json = JSONObject.fromObject(mp);
			logger.debug(json.toString());
			this.delRankAgreement(request, rankHouse, json, financial);
		}
	}

	/**
	 * 删除房源状态
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@SuppressWarnings("unchecked")
	public Map delRankAgreement(final HttpServletRequest request,
			final RankHouse rankHouse, final JSONObject mp, final Financial financial)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				// TODO Auto-generated method stub
				String id = StringHelper.get(mp, "id"); // 租赁合约id
				String house_rank_id = StringHelper.get(mp, "house_rank_id"); // 租赁房源
				String houseId = StringHelper.get(mp, "houseId"); // 房源id
				String status = StringHelper.get(mp, "status");
				String operId = StringHelper.get(mp, "operId"); // 操作人
				String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
						.replace("####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证房源状态 如果非1 房源状态改变
				if (this.queryForInt(
						getSql("basehouse.rankHosue.checkRankAgreementStatus"),
						new Object[] { id, status }) != 1)
				{
					return -2;
				}
				// 插入历史记录
				if (request != null)
				{
					this.insertTableLog(request, "yc_agreement_tab", " and id = '" + id
							+ "'", "删除租赁房源", new ArrayList<String>(), operId);
				}
				this.update(getSql("basehouse.rankHosue.updateRankAgreementStatus")
						.replace("####", "  isdelete = ? "), new Object[] { 0, id });
				Map<String, String> params = new HashMap<>();
				int res = 0;
				params.put("rankid", house_rank_id);
				params.put("houseid", houseId);
				params.put("oper", operId);
				// 验证此合约对应的房源是否还有其他有效合约，如果有，不能更新房源状态，如果没有需要更新房源状态
				if (this.queryForInt(
						getSql("basehouse.rankHosue.checkRankValidAgreement"),
						new Object[] { house_rank_id, id }) == 0)
				{
					// 更新租赁房源状态 刘飞
					res = rankHouse.approveNo(params, this);
					if (res != 1)
					{
						this.rollBack();
						return res;
					}
				}
				if ("12".equals(status))
				{
					// 雷杨撤销接口
					params = new HashMap<>();
					params.put("agreement_id", id);
					res = financial.repealRentHouse(params, this);
					logger.debug(res+"");
					if (res != 1)
					{
						this.rollBack();
						return -12;
					}
				}
				return 1;
			}
		}));
	}

	/**
	 * 租赁合约审核通过
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月24日
	 */
	@SuppressWarnings("unchecked")
	public Object approvalAreement(final HttpServletRequest request,
			final Financial financial, final RankHouse rankHouse)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id"); // 合约id
				String isPass = req.getValue(request, "isPass"); // 合约状态 0 合约审核不通过 2 审核通过
				String house_id = req.getValue(request, "house_id"); // 房源id
				String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
						.replace("####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证合约是否被审批
				sql = getSql("basehouse.rankHosue.checkRankAgreementStatus");
				if (this.queryForInt(sql, new Object[] { id, 1 }) == 0)
				{
					return -2;
				}
				this.insertTableLog(request, "yc_agreement_tab", " and id='" + id + "'",
						"0".equals(isPass) ? "租赁合约审核不通过" : "租赁审核通过",
						new ArrayList<String>(), getUser(request).getId());
				// 提交合约信息
				sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace(
						"####", " status = ? ");
				this.update(sql, new Object[] { isPass, id });
				// 调用 刘飞接口 雷杨接口
				if ("2".equals(isPass))
				{
					sql = getSql("financial.rankAgreementInfo");
					logger.debug(str.getSql(sql, new Object[] { id }));
					Map params = this.queryForMap(sql, new Object[] { id });
					logger.debug(params.toString());
					params.put("operid", getUser(request).getId());
					// 雷杨 财务信心
					int res = financial.rentHouse(params, this);
					if (res != 1)
					{
						this.rollBack();
						if (res == -1)
						{
							return -4; // 未查询到房源合约信息
						}
						else
						{
							return -5; // 未查询到财务项目
						}
					}
					params.put("house_id", house_id);
					// 刘飞
					res = rankHouse.approveOk(params, this);
					if (res != 1)
					{
						this.rollBack();
						return res;
					}
					// 发送短信
					String payType = StringHelper.get(params, "payType");
					String mobile = StringHelper.get(params, "mobile");
					Map<String, String> mp = new HashMap<>();
					mp.put("cost", payType);
					mp.put("cost_a", StringHelper.get(params, "cost_a"));
					mp.put("begin_time", StringHelper.get(params, "begin_time"));
					mp.put("end_time", StringHelper.get(params, "end_time"));
					mp.put("username", StringHelper.get(params, "username"));
					if (!"".equals(mobile))
					{
						SmsSendMessage.smsSendMessage(mobile, mp, 13);
					}
				}
				return 1;
			}
		}));
	}

	/**
	 * 获取房源地址
	 * 
	 * @param response
	 * @author liuf
	 * @date 2017年1月05日
	 */
	public Object getAddress(final HttpServletRequest request)
	{
		String id = req.getValue(request, "id"); // 合约id
		return db.queryForMap(getSql("basehouse.rankHosue.getAddress"),
				new Object[] { id });
	}

	/**
	 * 撤销租赁合约
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@SuppressWarnings("unchecked")
	public Object cancelRankAgreement(final HttpServletRequest request)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id"); // 合约id
				String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
						.replace("####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证合约是否已生效
				sql = getSql("basehouse.rankHosue.checkRankAgreementStatus");
				if (this.queryForInt(sql, new Object[] { id, 2 }) == 0)
				{
					return -2;
				}
				this.insertTableLog(request, "yc_agreement_tab", " and id='" + id + "'",
						"合约撤销待审核", new ArrayList<String>(), getUser(request).getId());
				// 提交合约信息
				sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace(
						"####", " status = ? ");
				this.update(sql, new Object[] { 4, id });
				return 1;
			}
		}));
	}

	/**
	 * 审批需撤销合约
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@SuppressWarnings("unchecked")
	public Object spRankAgeement(final HttpServletRequest request,
			final Financial financial, final RankHouse rankHouse)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			/*
			 * @see pccom.common.util.BatchBase#execute()
			 */
			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id"); // 合约id
				String isPass = req.getValue(request, "isPass"); // 通过 3 合同无效 不通过 2 已经生效
				String houseId = req.getValue(request, "houseId");
				String house_rank_id = req.getValue(request, "house_rank_id");
				logger.debug(isPass + ":" + houseId);
				String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
						.replace("####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证合约是否在已生效状态
				sql = getSql("basehouse.rankHosue.checkRankAgreementStatus");
				logger.debug(str.getSql(sql, new Object[] { id, 4 }));
				if (this.queryForInt(sql, new Object[] { id, 4 }) == 0)
				{
					return -2;
				}
				this.insertTableLog(request, "yc_agreement_tab", " and id='" + id + "'",
						"3".equals(isPass) ? "将租赁合约置为无效" : "租赁合同还是有效",
						new ArrayList<String>(), getUser(request).getId());
				// 更新租赁合约状态
				sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace(
						"####", " status = ? ");
				this.update(sql, new Object[] { isPass, id });
				if ("3".equals(isPass))
				{
					// 合约无效
					// 刘飞 雷杨
					// 雷杨撤销接口
					Map<String, String> params = new HashMap<>();
					params.put("agreement_id", id);
					int res = financial.repealRentHouse(params, this);
					logger.debug(res+"");
					if (res != 1)
					{
						this.rollBack();
						return -12;
					}
					// 刘飞
					params.clear();
					params.put("rankid", house_rank_id);
					params.put("houseid", houseId);
					params.put("oper", getUser(request).getId());
					res = rankHouse.approveNo(params, this);
					if (res != 1)
					{
						this.rollBack();
						return res;
					}
				}
				return 1;
			}
		}));
	}

	/**
	 * 结束合约
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@SuppressWarnings("unchecked")
	public Object overRankAgeement(final HttpServletRequest request)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			/*
			 * @see pccom.common.util.BatchBase#execute()
			 */
			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id"); // 合约id
				String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
						.replace("####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证合约是否在已生效状态
				sql = getSql("basehouse.rankHosue.checkRankAgreementStatus");
				if (this.queryForInt(sql, new Object[] { id, 5 }) == 0)
				{
					return -2;
				}
				this.insertTableLog(request, "yc_agreement_tab", " and id='" + id + "'",
						"提交合约待审批", new ArrayList<String>(), getUser(request).getId());
				// 更新租赁合约状态
				sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace(
						"####", " status = ? ");
				this.update(sql, new Object[] { 5, id });
				return 1;
			}
		}));
	}

	/**
	 * 审批待结束合约
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月19日
	 */
	@SuppressWarnings("unchecked")
	public Object spOverRankAgeement(final HttpServletRequest request)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			/*
			 * @see pccom.common.util.BatchBase#execute()
			 */
			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id"); // 合约id
				String isPass = "6"; // req.getValue(request, "isPass"); // 合同到期
				String houseId = req.getValue(request, "houseId");
				logger.debug(isPass + ":" + houseId);
				String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
						.replace("####", " id = ? ");
				this.update(sql, new Object[] { id, id });
				// 验证合约是否在已生效状态
				sql = getSql("basehouse.rankHosue.checkRankAgreementStatus");
				if (this.queryForInt(sql, new Object[] { id, 5 }) == 0)
				{
					return -2;
				}
				this.insertTableLog(request, "yc_agreement_tab", " and id='" + id + "'",
						"合约已到期,结束合约", new ArrayList<String>(), getUser(request).getId());
				// 更新租赁合约状态
				sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace(
						"####", " status = ? ");
				this.update(sql, new Object[] { isPass, id });
				if ("6".equals(isPass))
				{
					// 合约无效
					// 雷杨
				}
				return 1;
			}
		}));
	}

	/**
	 * 合约到期结束到期
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object overRankAgreement(final HttpServletRequest request,
			final RankHouse rankHouse)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id"); // 合约id
				String rankId = req.getValue(request, "rankId"); // 租赁id
				String houseId = req.getValue(request, "houseId"); // 房间编号
				String oper = getUser(request).getId();
				// TODO Auto-generated method stub
				this.insertTableLog(request, "yc_agreement_tab", " and id ='" + id + "'",
						"租赁合约到期,结束合同", new ArrayList<String>(), oper);
				this.insertTableLog(request, "yc_houserank_tab", " and id ='" + id + "'",
						"租赁合约到期,结束合同", new ArrayList<String>(), oper);
				// 结束合约
				String sql = getSql("basehouse.agreement.updateAgreement").replace(
						"####", " status = 3");
				logger.debug(str.getSql(sql, new Object[] { id }));
				this.update(sql, new Object[] { id });
				// 将房源更新为待发布
				sql = getSql("basehouse.rankHosue.updateStatus");
				this.update(sql, new Object[] { 0, oper, rankId });
				Map<String, String> params = new HashMap<>();
				params.put("rankid", rankId);
				params.put("houseid", houseId);
				params.put("oper", getUser(request).getId());
				int res = rankHouse.approveNo(params, this);
				if (res != 1)
				{
					this.rollBack();
					return -1;
				}
				return 1;
			}
		}));
	}

	/**
	 * 释放房源
	 * 
	 * @param request
	 * @param rankHouse
	 * @return
	 */
	public Object approveNo(final HttpServletRequest request, final RankHouse rankHouse)
	{
		Object obj = db.doInTransaction(new Batch()
		{

			@Override
			public Object execute() throws Exception
			{
				String orderCode = req.getAjaxValue(request, "orderCode");
				String sql = "select a.house_id rankid,(SELECT b.house_id from yc_houserank_tab b WHERE b.id = a.house_id) houseid ,a.id "
						+ "  from order_tab o,yc_agreement_tab a "
						+ " where o.correspondent=a.id AND a.type=2 and o.order_code =?";
				Map<String, Object> map = this.queryForMap(sql,
						new Object[] { orderCode });
				String houseid = str.get(map, "houseid");
				String rankid = str.get(map, "rankid");
				String agr_id = str.get(map, "id");
				Map params = new HashMap();
				params.put("oper", 1);
				params.put("houseid", houseid);
				params.put("rankid", rankid);
				int exc = rankHouse.approveNo(params, this);
				if (exc == 1)
				{
					sql = "UPDATE yc_agreement_tab a SET a.status = 3 where a.id = ?";
					return this.update(sql, new Object[] { agr_id });
				}
				return exc;
			}
		});
		if (obj == null)
		{
			return 0;
		}
		return (int) obj;
	}

	/**
	 * 退租信息
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object tzInfo(final HttpServletRequest request,
			final OrderInterfaces orderInterfaces)
	{
		final String operId = this.getUser(request).getId();
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{

			/*
			 * @see pccom.common.util.BatchBase#execute()
			 */
			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id"); // 合约编码
				String tjDate = req.getAjaxValue(request, "tjDate"); // 退租时间
				String tzType = req.getValue(request, "tzType"); // 退租类型
				Map<String, String> params = new HashMap<>();
				params.put("correspondent", id);
				params.put("oserviceTime", tjDate);
				params.put("childtype", tzType);
				params.put("oper", operId);
				orderInterfaces.appCreate(params, this);
				return 1;
			}
		}));
	}

	/**
	 * 退租
	 * 
	 * @param json
	 * @param orderInterfaces
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object tzHouse(final JSONObject json, final OrderInterfaces orderInterfaces)
	{
		return db.doInTransaction(new Batch<Object>()
		{

			/*
			 * @see pccom.common.util.BatchBase#execute()
			 */
			@Override
			public Object execute() throws Exception
			{
				orderInterfaces.appCreate(json, this);
				Map<String, String> param = new HashMap<>();
				param.put("state", "1");
				param.put("msg", "操作成功");
				return param;
			}
		});
	}
	/* 
	 * 处理合约信息
	 */
	@SuppressWarnings("unchecked")
	public Object dealFinance(final HttpServletRequest request,final Financial financial,final RankHouse rankHouse) 
	{
		try
		{
			final String encryptedkey = req.getValue(request, "key");
			String signed = req.getValue(request, "signed");
			String encryptcontent = req.getValue(request, "content");
			boolean verify;
			verify = RSAUtil.verify(MD5Utils.sign(encryptcontent + encryptedkey).toLowerCase(), Apis.notaryPublicKey, signed);
			logger.debug("握手验证结果:"+verify);
			logger.debug("encryptedkey:"+encryptedkey);
			logger.debug("encryptcontent:"+encryptcontent);
			logger.debug("signed:"+signed);
			logger.debug("notaryPublicKey:"+Apis.notaryPublicKey);
			if(verify)
			{ 
				return db.doInTransaction(new Batch<String>()
				{
					@Override
					public String execute() throws Exception
					{
						String decodeKey = RSAUtil.decryptByPrivateKey(encryptedkey, Apis.clientPrivateKey);
						logger.debug("解密后的AES秘钥 = " + decodeKey);
						String decryptResult = AES_CBCUtils.decrypt(decodeKey, request.getParameter("content"));
						logger.debug(decryptResult);
						JSONObject json = JSONObject.fromObject(decryptResult);
						final String notaryid  = StringHelper.get(json, "notaryid");
						logger.debug(notaryid);
						if("".equals(notaryid))
						{
							Map<String,String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "CA认证获取流水号失败");
							SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							Map<String,String> mp = new HashMap<>();
							return "-1";
						}
						logger.debug("===============================");
						Map<String, String> resultMap = new HashMap<String, String>();
						resultMap.put("resCode", "0");
						resultMap.put("resMsg", "操作成功");
						// TODO Auto-generated method stub
						String agreementId = req.getValue(request, "agreementId"); // 合约id
						String operId = req.getValue(request, "operId"); //操作人
						String houseId = req.getValue(request,"houseId"); // 房源id
						if("".equals(agreementId))
						{
							Map<String,String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "CA认证没有获取到房源编码");
							SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							resultMap.put("resCode", "-1");
							resultMap.put("resMsg", "房源合约编码为空");
							return "-1";
						}
						if("".equals(operId))
						{
							Map<String,String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "CA认证工号为空");
							SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							resultMap.put("resCode", "-1");
							resultMap.put("resMsg", "操作人工号为空");
							return "-1";
						}
						
						if("".equals(houseId))
						{
							Map<String,String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "CA认证房源编码为空");
							SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							resultMap.put("resCode", "-1");
							resultMap.put("resMsg", "房源编码为空");
							return "-1";
						}
						// 锁表
						String sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", " id = ?");
						logger.debug(str.getSql(sql, new Object[]{agreementId, agreementId}));
						this.update(sql, new Object[]{agreementId, agreementId});
						//验证合约是否被审批
						sql = getSql("basehouse.agreement.checkAgreementInfo").replace("####", "");
						logger.debug(str.getSql(sql, new Object[]{agreementId, 11}));
						if(this.queryForInt(sql, new Object[]{agreementId, "11"})!=1)
						{
							Map<String,String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "CA认证需要确定真正签约houseid:"+houseId+",agreementId:"+agreementId);
							SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							return "1";
						}
						
						// 更新合约信息
						sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", " status = 12, notaryid = ?");
						logger.debug(str.getSql(sql, new Object[]{notaryid,agreementId}));
						this.update(sql, new Object[]{notaryid,agreementId});
						
						sql = getSql("financial.rankAgreementInfo");
						logger.debug(str.getSql(sql, new Object[]{agreementId}));
						Map params = this.queryForMap(sql, new Object[]{agreementId});
						logger.debug(params.toString());
						params.put("operid",operId);
						// 雷杨 财务信心
						int res =  financial.rentHouse(params, this);
						if(res != 1)
						{
							Map<String,String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "调用财务接口失败，返回"+res);
							SmsSendMessage.smsSendMessage("18252028618", exceptionMap, 14);
						}
						params.put("house_id", houseId);
						if(this.queryForInt(getSql("basehouse.rankHosue.checkRankValidAgreement"),new Object[]{StringHelper.get(params, "house_rank_id"),StringHelper.get(params, "agreement_id")}) == 0)
						{
							// 刘飞
							res = rankHouse.approveWaitOk(params, this);
							logger.debug("房源改变res:"+res);
							if(res != 1)
							{
								Map<String,String> exceptionMap = new HashMap<>();
								exceptionMap.put("function", "在线签约");
								exceptionMap.put("describe", "调用合约审批成功接口失败返回"+res);
								SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							}
						}
						
						// 发送短信
						String payType = StringHelper.get(params, "payType");
						String mobile = StringHelper.get(params, "mobile");
						Map<String,String> mp = new HashMap<>();
						mp.put("cost", payType);
						mp.put("cost_a", StringHelper.get(params, "cost_a"));
						mp.put("begin_time", StringHelper.get(params, "begin_time"));
						mp.put("end_time", StringHelper.get(params, "end_time"));
						mp.put("username", StringHelper.get(params, "username"));
						if(!"".equals(mobile))
						{
							SmsSendMessage.smsSendMessage(mobile, mp, 13);
						}
						return "1";
					}
				}); 
			}
			else
			{
				Map<String,String> exceptionMap = new HashMap<>();
				exceptionMap.put("function", "在线签约");
				exceptionMap.put("describe", "在线签约握手失败");
				SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
				return "-1";
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-1";
	}
	
	/*
	 * 处理合约信息
	 */
	@SuppressWarnings("unchecked")
	public Object dealCFinance(final HttpServletRequest request,
			final Financial financial, final RankHouse rankHouse)
	{
		try
		{
			final String encryptedkey = req.getValue(request, "key");
			String signed = req.getValue(request, "signed");
			String encryptcontent = req.getValue(request, "content");
			boolean verify;
			verify = RSAUtil.verify(MD5Utils.sign(encryptcontent + encryptedkey)
					.toLowerCase(), Apis.notaryPublicKey, signed);
			logger.debug("握手验证结果:" + verify);
			logger.debug("encryptedkey:" + encryptedkey);
			logger.debug("encryptcontent:" + encryptcontent);
			logger.debug("signed:" + signed);
			logger.debug("notaryPublicKey:" + Apis.notaryPublicKey);
			if (verify)
			{
				return db.doInTransaction(new Batch<String>()
				{

					@Override
					public String execute() throws Exception
					{
						String decodeKey = RSAUtil.decryptByPrivateKey(encryptedkey,
								Apis.clientPrivateKey);
						logger.debug("解密后的AES秘钥 = " + decodeKey);
						String decryptResult = AES_CBCUtils.decrypt(decodeKey,
								request.getParameter("content"));
						logger.debug(decryptResult);
						JSONObject json = JSONObject.fromObject(decryptResult);
						final String notaryid = StringHelper.get(json, "notaryid");
						logger.debug(notaryid);
						if ("".equals(notaryid))
						{
							Map<String, String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "CA认证获取流水号失败");
							SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							Map<String, String> mp = new HashMap<>();
							return "-1";
						}
						logger.debug("===============================");
						Map<String, String> resultMap = new HashMap<String, String>();
						resultMap.put("resCode", "0");
						resultMap.put("resMsg", "操作成功");
						// TODO Auto-generated method stub
						String agreementId = req.getValue(request, "agreementId"); // 合约id
						String operId = req.getValue(request, "operId"); // 操作人
						String houseId = req.getValue(request, "houseId"); // 房源id
						if ("".equals(agreementId))
						{
							Map<String, String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "CA认证没有获取到房源编码");
							SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							resultMap.put("resCode", "-1");
							resultMap.put("resMsg", "房源合约编码为空");
							return "-1";
						}
						if ("".equals(operId))
						{
							Map<String, String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "CA认证工号为空");
							SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							resultMap.put("resCode", "-1");
							resultMap.put("resMsg", "操作人工号为空");
							return "-1";
						}
						if ("".equals(houseId))
						{
							Map<String, String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "CA认证房源编码为空");
							SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							resultMap.put("resCode", "-1");
							resultMap.put("resMsg", "房源编码为空");
							return "-1";
						}
						// 锁表
						String sql = getSql(
								"basehouse.rankHosue.updateRankAgreementStatus").replace(
								"####", " id = ?");
						logger.debug(str.getSql(sql, new Object[] { agreementId,
								agreementId }));
						this.update(sql, new Object[] { agreementId, agreementId });
						//验证合约是否被审批
						sql = getSql("basehouse.agreement.checkAgreementInfo").replace("####", "");
						logger.debug(str.getSql(sql, new Object[]{agreementId, 11}));
						if(this.queryForInt(sql, new Object[]{agreementId, "11"})!=1)
						{
							Map<String,String> exceptionMap = new HashMap<>();
							exceptionMap.put("function", "在线签约");
							exceptionMap.put("describe", "CA认证需要确定真正签约houseid:"+houseId+",agreementId:"+agreementId);
							SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
							return "1";
						}
						sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
								.replace("####", " status = 13, notaryid = ?");
						logger.debug(str.getSql(sql, new Object[] { notaryid,
								agreementId }));
						this.update(sql, new Object[] { notaryid, agreementId });
						//更新yc_recommend_info的state为1用户已确认
						houseMapper.upRecoState("1",agreementId);
						String brokerid=houseMapper.getBrokerId(agreementId);
						User us=new User();
						us=userMapper.getUserId("", brokerid);
						//插入代办流程
						Long staffid=Long.parseLong(houseMapper.selectAgentId(houseId));
						WorkOrderDto workOrderDto = new WorkOrderDto();
						workOrderDto.setStaffId(staffid);
					    workOrderDto.setSubAssignedDealerId(staffid);
						//workOrderDto.setName("经纪人合约");
				        workOrderDto.setType(WorkOrderTypeDef.BROKER_ORDER);
				        workOrderDto.setSubActualDealerId(staffid);
				        workOrderDto.setCode(SubOrderUtil.generatorSubOrderCode());
				        workOrderDto.setUserName(us.getName());
				        workOrderDto.setUserPhone(us.getMobile());
				        workOrderDto.setRentalLeaseOrderId(Long.parseLong(agreementId));
				        workOrderDto.setSubOrderState(SubOrderStateDef.DO_IN_ORDER);
				        workOrderDto.setCreatedStaffId(SystemAccountDef.SYSTEM);
				        workOrderDto.setSubComments("");
				        workOrderDto.setAppointmentDate(DateUtil.getDBDateTime());
						workOrderService.createWorkOrderWithTrans(workOrderDto);
						return "1";
					}
				});
			}
			else
			{
				Map<String, String> exceptionMap = new HashMap<>();
				exceptionMap.put("function", "在线签约");
				exceptionMap.put("describe", "在线签约握手失败");
				SmsSendMessage.smsSendMessage(Apis.CACHIEF, exceptionMap, 14);
				return "-1";
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-1";
	}
	
	
	@Transactional
	//管家审批通过经纪人合约，其余经纪人合约置为失效，更改房源状态和合约状态，以及添加财务数据
	public void approveCApplication(RankAgreement rankAgreement,String code)
	{	
		int res = houseMapper.checkRankHouseStatus(rankAgreement.getRankId(),
				String.valueOf(COMPLETE));
		//无已发布的合约
		logger.info("----into approveCApplication----");
		logger.debug("operId:"+rankAgreement.getOperId());
		if (res == 0 )
		{
			throw new BaseAppException("房源状态已改变！");
		}	
		else
		{
			//关闭其他任务订单
			List<Map<String,String>> otherCodes=houseMapper.getOtherOrder(rankAgreement.getRankId(),rankAgreement.getMobile());
			if(otherCodes.size() !=0 )
			{
				for(int i=0;i<otherCodes.size();i++)
				{
					Map<String,String> map =otherCodes.get(i);
					logger.info("-------------------------------code:"+map.get("code"));
					workOrderService.closeBrokerOrder(map.get("code"), Long.parseLong("-1"));
				}
			}
			//更新yc_recommend_info的state为2,将其他的推荐订单（state=0）置为3
			houseMapper.upOtherRecoState(rankAgreement.getId());
			//relation的state更新为2已结束
			houseMapper.upOtherRecoRelatState(rankAgreement.getId());
			//更改合约状态为生效待支付
			String sql="";
			String rankAgreementId=rankAgreement.getId();
			sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
					.replace("####", " status = 12,operid=?");
			logger.debug(str.getSql(sql, new Object[] { rankAgreement.getOperId(),
					rankAgreementId }));
			db.update(sql, new Object[] {  rankAgreement.getOperId(),rankAgreementId });
			//其余经纪人合约置为失效
            houseMapper.upCRankAreementStatus(rankAgreement.getRankId());
			List<com.ycdc.appserver.model.house.RankHouse> rankHouseList = houseMapper
					.loadRankHouseList(rankAgreement.getHouse_id());
			//更改房源状态
			String rankId = rankAgreement.getRankId();
			for (com.ycdc.appserver.model.house.RankHouse rankHouse : rankHouseList)
			{
				if (rankId.equals(rankHouse.getId()))
				{
					if ("已发布".equals(rankHouse.getRankstatus()))
					{
						if ("整租".equals(rankHouse.getRanktype()))
						{
							for (com.ycdc.appserver.model.house.RankHouse rank_House : rankHouseList)
							{
								if (!rankId.equals(rank_House.getId()))
								{
									// 撤销 合租房源，6失效
									houseMapper.updateRankHouseStatus(
											String.valueOf(LOST),
											rankAgreement.getOperId(),
											rank_House.getId());
								}
								//4签约中
								else
								{
									houseMapper.updateRankHouseStatus(
											String.valueOf(AGREE_ING),
											rankAgreement.getOperId(),
											rankHouse.getId());
								}
							}
						}
						//合租
						else
						{
							for (com.ycdc.appserver.model.house.RankHouse rank_House : rankHouseList)
							{
								if (!rankId.equals(rank_House.getId())
										&& "整租".equals(rank_House.getRanktype()))
								{
									// 撤销 整租房源
									houseMapper.updateRankHouseStatus(
											String.valueOf(LOST),
											rankAgreement.getOperId(),
											rank_House.getId());
								}
							}
							// 修改当前房源为签约中
							houseMapper.updateRankHouseStatus(
									String.valueOf(AGREE_ING),
									rankAgreement.getOperId(), rankHouse.getId());
						}
					}
				}
			}
			//添加财务数据
			//经纪人id
			String operId=rankAgreement.getOperId();
			sql = getSql("financial.rankAgreementInfo");
			logger.debug(str.getSql(sql, new Object[] { rankAgreementId }));
			Map params = db.queryForMap(sql,
					new Object[] { rankAgreementId });
			logger.debug(params.toString());
			params.put("operid", operId);
			// 雷杨 财务信心
			int resF = financial.rentHouse(params, this);
			//财务插入失败
			if (resF != 1)
			{
				throw new BaseAppException("财务数据添加失败！");
			}
			// 发送短信
			String payType = StringHelper.get(params, "payType");
			String mobile = StringHelper.get(params, "mobile");
			Map<String,String> mp = new HashMap<>();
			mp.put("cost", payType);
			mp.put("cost_a", StringHelper.get(params, "cost_a"));
			mp.put("begin_time", StringHelper.get(params, "begin_time"));
			mp.put("end_time", StringHelper.get(params, "end_time"));
			mp.put("username", StringHelper.get(params, "username"));
			if(!"".equals(mobile))
			{
				SmsSendMessage.smsSendMessage(mobile, mp, 13);
			}
		}
		
	}
	
	//管家拒绝经纪人合约
	public void rejectCApplication(RankAgreement rankAgreement)
	{
		logger.debug("into rejectCApplication:");
		logger.info("operid:"+rankAgreement.getOperId());
		String sql="";
		String rankAgreementId=rankAgreement.getId();
		logger.debug(rankAgreementId);
		sql = getSql("basehouse.rankHosue.updateRankAgreementStatus")
				.replace("####", " status = 3,operid=?");
		logger.debug("sql:"+sql);
		logger.debug(str.getSql(sql, new Object[] { rankAgreement.getOperId(),rankAgreementId }));
		db.update(sql,new Object[] { rankAgreement.getOperId(),rankAgreementId });
		//更新recommend_info的状态为3
		houseMapper.upRecoState("3", rankAgreementId);
		 int i=houseMapper.upRecoRelationState("2", rankAgreementId);
		 if(i==1)
		 {
			 logger.info("状态修改成功！id:"+rankAgreementId);
		 }
		 else
		 {
			 logger.info("状态修改失败！id:"+rankAgreementId);
		 }
	}
	
	/**
	 * 初始化RankAgreement
	 * 
	 * @param request
	 * @return
	 */
	public RankAgreement initRankAgreement(HttpServletRequest request)
	{
		// TODO Auto-generated method stub
		String houseId = req.getValue(request, "houseId"); // 房源id
		String rank_id = req.getValue(request, "rank_id"); // 租赁id
		String agreement_name = req.getValue(request, "agreement_name"); // 合约名称
		String name = req.getValue(request, "name"); // 租客姓名
		String mobile = req.getValue(request, "mobile"); // 租客号码
		String manageId = req.getValue(request, "manageId"); // 签约管家
		String rankPrice = req.getValue(request, "rankPrice"); // 合约租金
		String payType = req.getValue(request, "payType"); // 付款方式
		String validateDate = req.getValue(request, "validateDate"); // 生效时间
		String rankDate = req.getValue(request, "rankDate"); // 租期
		String picPath = req.getValue(request, "picPath"); // 图片
		String agreementDes = req.getValue(request, "agreementDes"); // 合约描述
		String certificateno = req.getValue(request, "certificateno"); // 身份证号码
		String address = req.getValue(request, "address"); // 住址
		String cardelectric = req.getValue(request, "cardelectric"); // 电卡帐号
		String eMeter = req.getValue(request, "eMeter"); // 电表总值
		String eMeterH = req.getValue(request, "eMeterH"); // 电表峰值
		String eMeterL = req.getValue(request, "eMeterL"); // 电表谷值
		String cardWarter = req.getValue(request, "cardWarter"); // 水卡帐号
		String warterDegrees = req.getValue(request, "warterDegrees"); // 水表读数
		String cardgas = req.getValue(request, "cardgas"); // 燃气帐号
		String gasDegrees = req.getValue(request, "gasDegrees"); // 燃气读数
		String email = req.getValue(request, "email"); // email
		String propertyMonery = req.getValue(request, "propertyMonery"); // 物业费
		String serviceMonery = req.getValue(request, "serviceMonery"); // 服务费
		String area = req.getValue(request, "area"); // 出租面积
		String payway = req.getValue(request, "payway"); // 付款类型 支付宝 0 微信1 银行卡2
		String roomcnt = req.getValue(request, "roomcnt");
		String userId = req.getValue(request, "userId");
		String rentRoomcnt = req.getValue(request, "rentroomcnt");
		String rank_code = req.getValue(request, "rank_code");
		String deposit = req.getValue(request, "deposit");
		String rent_deposit = req.getValue(request, "rent_deposit"); // 出租押金
		String[] outCnt = req.getValues(request, "outCnt"); // 公共区域物品
		String b = "";
		for (int i = 0; i < outCnt.length; i++)
		{
			b += "|b" + i + "=" + outCnt[i];
		}
		if (!"".equals(b))
		{
			b = b.substring(1);
		}
		logger.debug(b);
		String[] innerCnt = req.getValues(request, "innerCnt"); // 房间内部家具
		String a = "";
		for (int i = 0; i < innerCnt.length; i++)
		{
			a += "|a" + i + "=" + innerCnt[i];
		}
		if (!"".equals(a))
		{
			a = a.substring(1);
		}
		logger.debug(a);
		RankAgreement rankAgreement = new RankAgreement();
		rankAgreement.setAgents(manageId);
		rankAgreement.setAddress(address);
		rankAgreement.setBegin_time(validateDate);
		rankAgreement.setCertificateno(certificateno);
		rankAgreement.setCost_a(rankPrice);
		rankAgreement.setDesc_text(agreementDes);
		rankAgreement.setElectriccard(cardelectric);
		rankAgreement.setElectricity_meter(eMeter);
		rankAgreement.setElectricity_meter_h(eMeterH);
		rankAgreement.setElectricity_meter_l(eMeterL);
		rankAgreement.setEmail(email);
		rankAgreement.setG_id(this.getUser(request).getG_id());
		rankAgreement.setFile_path(picPath);
		rankAgreement.setGas_meter(gasDegrees);
		rankAgreement.setGascard(cardgas);
		rankAgreement.setHouse_id(houseId);
		rankAgreement.setMobile(mobile);
		rankAgreement.setName(agreement_name);
		rankAgreement.setOperId(this.getUser(request).getId());
		rankAgreement.setPay_type(payType);
		rankAgreement.setPrice(rankPrice);
		rankAgreement.setPropertyMonery(propertyMonery);
		rankAgreement.setRankId(rank_id);
		rankAgreement.setRent_month(rankDate);
		rankAgreement.setRoomarea(area);
		rankAgreement.setServiceMonery(serviceMonery);
		rankAgreement.setTotalRoomCnt(roomcnt);
		rankAgreement.setUser_id(userId);
		rankAgreement.setUsername(name);
		rankAgreement.setPayway(payway);
		rankAgreement.setWater_meter(warterDegrees);
		rankAgreement.setWatercard(cardWarter);
		rankAgreement.setOld_matched(a + "##" + b);
		rankAgreement.setRentRoomCnt(rentRoomcnt);
		rankAgreement.setRank_code(rank_code);
		rankAgreement.setDeposit(deposit);
		rankAgreement.setRent_deposit(rent_deposit);
		return rankAgreement;
	}

	/**
	 * 出租合约认证
	 * 
	 * @param houseMgr
	 * @param request
	 * @return
	 */
	public Object rankAgreementNotarization(HouseMgrService houseMgr,
			HttpServletRequest request)
	{
		String id = req.getValue(request, "id"); // 合约id
		String operId = this.getUser(request).getId(); // 登录人
		String userId = req.getValue(request, "userId"); // 用户信息
		String houseId = req.getValue(request, "houseId");
		return houseMgr.rankAgreementNotarization(id, userId, operId, houseId, "1");
	}

	/**
	 * ca参数回调处理
	 * 
	 * @param request
	 * @return
	 * @date 2016年12月28日
	 */
	public Object caCallBackUrl(HttpServletRequest request, RankHouse rankHouse,
			Financial financial)
	{
		String errorCode = "9";
		boolean verify;
		try
		{
			String encryptedkey = req.getValue(request, "key");
			;// request.getParameter("key");
			String encryptcontent = req.getValue(request, "content");// request.getParameter("key");
			String signed = req.getValue(request, "signed");// request.getParameter("key");
			logger.debug("encryptedkey:" + encryptedkey);
			logger.debug("encryptcontent:" + encryptcontent);
			logger.debug("signed:" + signed);
			logger.debug("notaryPublicKey:" + Apis.notaryPublicKey);
			verify = RSAUtil.verify(MD5Utils.sign(encryptcontent + encryptedkey)
					.toLowerCase(), Apis.notaryPublicKey, signed);
			logger.debug("握手验证结果:" + verify);
			if (verify)
			{
				String decodeKey = RSAUtil.decryptByPrivateKey(encryptedkey,
						Apis.clientPrivateKey);
				logger.debug("解密后的AES秘钥 = " + decodeKey);
				String decryptResult = AES_CBCUtils.decrypt(decodeKey, encryptcontent);
				logger.debug(decryptResult);
				JSONObject json = JSONObject.fromObject(decryptResult);
				String agreementId = StringHelper.get(json, "docid");
				String userId = StringHelper.get(json, "userid");
				errorCode = StringHelper.get(json, "errorCode");
				if (!"0".equals(errorCode))
				{
					String sql = getSql("basehouse.rankHosue.getHouseId");
					String houseId = StringHelper
							.get(db.queryForMap(sql, new Object[] { agreementId }),
									"house_id");
					String houseRankId = StringHelper.get(
							db.queryForMap(sql, new Object[] { agreementId }),
							"houseRankId");
					JSONObject mp = new JSONObject();
					mp.put("id", agreementId);
					mp.put("house_rank_id", houseRankId);
					mp.put("houseId", houseId);
					mp.put("status", "11");
					mp.put("operId", userId);
					this.delRankAgreement(request, rankHouse, mp, financial);
				}
				request.setAttribute("errorCode", "0".equals(errorCode) ? "ok" : "no");
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getReturnMap(errorCode);
	}

	/**
	 * ca参数回调处理 合约信息
	 * 
	 * @param request
	 * @return
	 * @date 2016年12月28日
	 */
	public Object caCallBackDealAgreement(HttpServletRequest request,
			RankHouse rankHouse, Financial financial,
			AgreementMgeService agreementMgeService)
	{
		String errorCode = "9";
		boolean verify;
		try
		{
			String encryptedkey = req.getValue(request, "key");
			;// request.getParameter("key");
			String encryptcontent = req.getValue(request, "content");// request.getParameter("key");
			String signed = req.getValue(request, "signed");// request.getParameter("key");
			logger.debug(encryptcontent);
			logger.debug("encryptedkey:" + encryptedkey);
			logger.debug("encryptcontent:" + encryptcontent);
			logger.debug("signed:" + signed);
			logger.debug("notaryPublicKey:" + Apis.notaryPublicKey);
			verify = RSAUtil.verify(MD5Utils.sign(encryptcontent + encryptedkey)
					.toLowerCase(), Apis.notaryPublicKey, signed);
			logger.debug("握手验证结果:" + verify);
			if (verify)
			{
				String decodeKey = RSAUtil.decryptByPrivateKey(encryptedkey,
						Apis.clientPrivateKey);
				logger.debug("解密后的AES秘钥 = " + decodeKey);
				String decryptResult = AES_CBCUtils.decrypt(decodeKey, encryptcontent);
				logger.debug(decryptResult);
				JSONObject json = JSONObject.fromObject(decryptResult);
				String agreementId = StringHelper.get(json, "docid");
				errorCode = StringHelper.get(json, "errorCode");
				if (!"0".equals(errorCode)) // 点击否
				{
					String sql = getSql("basehouse.rankHosue.getHouseIdByAgreementId");
					String houseId = StringHelper
							.get(db.queryForMap(sql, new Object[] { agreementId }),
									"house_id");
					String operid = StringHelper.get(
							db.queryForMap(sql, new Object[] { agreementId }), "operid");
					JSONObject mp = new JSONObject();
					mp.put("id", agreementId);
					mp.put("houseId", houseId);
					mp.put("status", "11");
					mp.put("operId", operid);
					agreementMgeService.newDelAgreement(mp, request);
				}
				request.setAttribute("errorCode", "0".equals(errorCode) ? "ok" : "no");
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getReturnMap(errorCode);
	}

	/**
	 * 老合同查看合约图片
	 * 
	 * @author 刘飞
	 * @param request
	 * @return
	 */
	public Object viewFile(HttpServletRequest request)
	{
		String id = req.getValue(request, "id");
		Map<String, Object> returnMap = getReturnMap("1");
		if (id == null || "".equals(id))
		{
			return returnMap;
		}
		FtpUtil ftp = null;
		try
		{
			ftp = new FtpUtil(SystemConfig.getValue("FTP_IP"), "tmp_root", "TMP_ROOT",
					"\\");
			List<Map<String, Object>> fileList = new ArrayList<Map<String, Object>>();
			fileList = ftp.getFileInfoList("\\" + id + "\\");
			StringBuffer pat = new StringBuffer();
			String FTP_HTTP = SystemConfig.getValue("FTP_HTTP_AGREE");
			for (Map<String, Object> map : fileList)
			{
				pat.append(FTP_HTTP).append("\\").append(id).append("\\")
						.append(str.get(map, "fileName")).append(",");
			}
			returnMap.put("path", pat.toString());
		}
		catch (Exception e)
		{
		}
		finally
		{
			// 关闭流
			if (ftp != null)
			{
				ftp.closeServer();
			}
		}
		return returnMap;
	}
}
