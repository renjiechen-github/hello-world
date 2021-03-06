package pccom.web.server.house;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycdc.appserver.bus.service.house.HouseMgrService;
import com.ycdc.appserver.model.house.Agreement;

import net.sf.json.JSONObject;
import pccom.common.util.Batch;
import pccom.common.util.FtpUtil;
import pccom.common.util.StringHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.Onstruction;
import pccom.web.server.BaseService;
import pccom.web.server.house.rankhouse.RankHouseService;

@Service("baseHouseService")
public class BaseHouseService extends BaseService
{
	@Autowired
	public RankHouseService rankHouseService;

  /**
   * 根据房源列表信息，获取小区列表
   * @return
   */
  public Object getGroupList(HttpServletRequest request, HttpServletResponse response)
  {
    String keyword = req.getAjaxValue(request, "keyword");
    String status = req.getAjaxValue(request, "status");
    String areaid = req.getAjaxValue(request, "areaid");
    String groupid = req.getAjaxValue(request, "groupid");
    String accountid = req.getAjaxValue(request, "accountid");
    String createtime = req.getAjaxValue(request, "createtime");
    String publish = req.getValue(request, "publish"); // 发布状态
    String isSelf = req.getValue(request, "isSelf"); // 自己
    String trading = req.getValue(request, "trading"); // 商圈
    String kzhouse = req.getValue(request, "kzhouse"); // 空置房源

    String sql = getSql("basehouse.getHouseList.queryMainbg") + getSql("basehouse.getHouseList.queryMain");
    List<String> params = new ArrayList<String>();

    if (!"".equals(createtime))
    {
      sql += getSql("basehouse.getHouseList.createtime");
      params.add(createtime.split("~")[0]);
      params.add(createtime.split("~")[1]);
    }

    if (!"".equals(accountid))
    {
      sql += getSql("basehouse.getHouseList.accountid");
      params.add(accountid);
    }

    if ("1".equals(isSelf))
    {
      sql += getSql("basehouse.getHouseList.user_id");
      params.add(this.getUser(request).getId());
    }

    if (!"".equals(areaid) && !"null".equals(areaid))
    {
      logger.info(areaid);
      sql += getSql("basehouse.getHouseList.areaid").replaceAll("\\?", areaid);
      logger.info("sql = " + sql);
    }

    if (!"".equals(groupid) && !"null".equals(groupid))
    {
      logger.info(areaid);
      sql += getSql("basehouse.getHouseList.groupid").replaceAll("\\?", groupid);
      logger.info("sql = " + sql);
    }

    if (!"".equals(status))
    {
      sql += getSql("basehouse.getHouseList.status");
      params.add(status);
    }

    if (!"".equals(publish))
    {
      sql += getSql("basehouse.getHouseList.publish");
      params.add(publish);
    }

    if (!"".equals(keyword))
    {
      sql += getSql("basehouse.getHouseList.keyword");
      params.add("%" + keyword + "%");
      params.add("%" + keyword + "%");
      params.add("%" + keyword + "%");
      params.add("%" + keyword + "%");
      params.add("%" + keyword + "%");
    }

    if (!"".equals(trading) && !"null".equals(trading))
    {
      sql += getSql("basehouse.getHouseList.trading").replaceAll("\\?", trading);
    }
    sql += getSql("basehouse.getHouseList.groupBy") + getSql("basehouse.getHouseList.selectMainend");
    if ("1".equals(kzhouse))
    {
      sql += getSql("basehouse.getHouseList.kzhouse");
    }
    logger.debug(str.getSql(sql, params.toArray()));

    Map<String, Object> returnMap = new HashMap<>();
    List<Map<String, Object>> list = db.queryForList(sql, params.toArray());
    returnMap.put("state", 1);
    returnMap.put("data", JSONArray.fromObject(list));
    return returnMap;
  }

	/**
	 * 获取项目列表
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public void getHouseList(HttpServletRequest request, HttpServletResponse response)
	{
		String keyword = req.getAjaxValue(request, "keyword");
		String status = req.getAjaxValue(request, "status");
		String areaid = req.getAjaxValue(request, "areaid");
    String groupid = req.getAjaxValue(request, "groupid");
		String accountid = req.getAjaxValue(request, "accountid");
		String createtime = req.getAjaxValue(request, "createtime");
		String publish = req.getValue(request, "publish"); // 发布状态
		String isSelf = req.getValue(request, "isSelf"); // 自己
		String trading = req.getValue(request, "trading"); // 商圈
		String kzhouse = req.getValue(request, "kzhouse"); // 空置房源

		String sql = getSql("basehouse.getHouseList.selectMainbg") + getSql("basehouse.getHouseList.main");
		List<String> params = new ArrayList<String>();

		if (!"".equals(createtime))
		{
			sql += getSql("basehouse.getHouseList.createtime");
			params.add(createtime.split("~")[0]);
			params.add(createtime.split("~")[1]);
		}

		if (!"".equals(accountid))
		{
			sql += getSql("basehouse.getHouseList.accountid");
			params.add(accountid);
		}

		if ("1".equals(isSelf))
		{
			sql += getSql("basehouse.getHouseList.user_id");
			params.add(this.getUser(request).getId());
		}

		if (!"".equals(areaid) && !"null".equals(areaid))
		{
			logger.info(areaid);
			sql += getSql("basehouse.getHouseList.areaid").replaceAll("\\?", areaid);
			logger.info("sql = " + sql);
		}

    if (!"".equals(groupid) && !"null".equals(groupid))
    {
      logger.info(areaid);
      sql += getSql("basehouse.getHouseList.groupid").replaceAll("\\?", groupid);
      logger.info("sql = " + sql);
    }

		if (!"".equals(status))
		{
			sql += getSql("basehouse.getHouseList.status");
			params.add(status);
		}

		if (!"".equals(publish))
		{
			sql += getSql("basehouse.getHouseList.publish");
			params.add(publish);
		}

		if (!"".equals(keyword))
		{
			sql += getSql("basehouse.getHouseList.keyword");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
		}

		if (!"".equals(trading) && !"null".equals(trading))
		{
			sql += getSql("basehouse.getHouseList.trading").replaceAll("\\?", trading);
		}
		sql += getSql("basehouse.getHouseList.groupBy") + getSql("basehouse.getHouseList.selectMainend");
		if ("1".equals(kzhouse))
		{
			sql += getSql("basehouse.getHouseList.kzhouse");
		}
		logger.debug(str.getSql(sql, params.toArray()));


		getPageList(request, response, sql, params.toArray(), "basehouse.getHouseList.orderBy");
	}

	/**
	 * 导出开工房源
	 * 
	 * @param request
	 * @return
	 */
	public void exportHouse(HttpServletRequest request, HttpServletResponse response)
	{
		List<Map<String, Object>> workList = this.getHouseList(request, "1"); // 已经开工
		List<Map<String, Object>> notWorkList = this.getHouseList(request, "0"); // 未开工
		for (Map<String, Object> mp : notWorkList)
		{
			String roomcnt = StringHelper.get(mp, "roomcnt");
			String regx = "^\\d+$";
			Pattern pattern = Pattern.compile(regx);
			Matcher matcher = pattern.matcher(roomcnt);
			if (!matcher.matches())
			{
				roomcnt = "1";
			}
			for (int i = 0; i < Integer.parseInt(roomcnt) + 1; i++)
			{
				workList.add(mp);
			}
		}
		try
		{
			String expname = req.getAjaxValue(request, "expname");
			String fileName = "";
			String osName = System.getProperty("os.name");
			if (osName.toLowerCase().startsWith("win"))
			{
				fileName = expname + new Random().nextInt(10000000) + "";
			} else
			{
				fileName = new Random().nextInt(10000000) + "";
			}
			// String excle ="";
			// logger.debug(fileName);
			// excle = "/upload/excel/"+fileName+".xls";
			// logger.debug(excle);
			// File file1 = new File(request.getRealPath("/")+excle);
			// if(!file1.exists()){
			// file1.createNewFile();
			// }
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("房源数据");
			sheet.setDefaultColumnWidth((short) 15);
			HSSFRow row = sheet.createRow((int) 0);
			HSSFCell cell = row.createCell((short) 0);
			HSSFCellStyle style = wb.createCellStyle();
			// 设置这些样式
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			String[] title = new String[]
			{ "房源编码", "房源名称", "收房月租", "区域", "商圈", "小区名称", "户主", "原始户型", "开工后户型", "房源状态", "发布状态", "装修类型", "房屋面积", "出租房间名称", "出租状态",
					"出租费用", "出租面积", "出租类型" };
			for (int i = 0; i < title.length; i++)
			{
				cell.setCellValue(title[i]);
				cell.setCellStyle(style);
				row.createCell((short) i).setCellValue(title[i]);
			}
			String[] columnKey = new String[]
			{ "house_code", "house_name", "cost_a", "areaName", "sqname", "group_name", "username", "specName", "newSpecName", "houseStatus",
					"publishname", "decorateType", "area", "title", "rank_status", "fee", "rank_area", "rankType" };
			for (int i = 0; i < workList.size(); i++)
			{
				row = sheet.createRow((int) i + 1);
				Map<String, Object> mp = workList.get(i);
				for (int n = 0; n < columnKey.length; n++)
				{
					cell = row.createCell((short) n);
					cell.setCellValue(title[n]);
					cell.setCellStyle(style);
					row.createCell((short) n).setCellValue(StringHelper.get(mp, columnKey[n]));
				}
//				if (i != 0)
//				{
//					Map<String, Object> mp2 = workList.get(i - 1);
//					if (mp2.get("house_code").equals(mp.get("house_code")))
//					{
//						for (int n = 0; n < 12; n++)
//						{
//							// 重点在这里动态合并
//							String m = String.valueOf(mp.get("m"));
//							if (!"null".equals(m))
//							{
//								CellRangeAddress cra = new CellRangeAddress(i, i + Integer.parseInt(m), n, n);
//								// 在sheet里增加合并单元格
//								sheet.addMergedRegion(cra);
//							}
//						}
//						cell.setCellStyle(style);
//					}
//				}
			}
			response.setCharacterEncoding("UTF-8");
			response.reset();
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((fileName.toString() + ".xls").getBytes(), "iso-8859-1"));
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.close();
			wb.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 导出房源信息
	 * 
	 * @param response
	 * @param request
	 */
	public List<Map<String, Object>> getHouseList(HttpServletRequest request, String isWork)
	{
		String keyword = req.getAjaxValue(request, "keyword");
		String status = req.getAjaxValue(request, "status");
		String areaid = req.getAjaxValue(request, "areaid");
		String accountid = req.getAjaxValue(request, "accountid");
		String createtime = req.getAjaxValue(request, "createtime");
		String publish = req.getValue(request, "publish"); // 发布状态
		String isSelf = req.getValue(request, "isSelf"); // 自己
		String trading = req.getValue(request, "trading"); // 商圈
		String kzhouse = req.getValue(request, "kzhouse"); // 空置房源
		List<String> params = new ArrayList<String>();
		String sql = "";
		if ("1".equals(isWork) && isWork != null && !"null".equals(isWork))
		{
			sql = getSql("basehouse.getHouseList.workHouse");
		} else if (isWork != null && !"null".equals(isWork))
		{
			sql = getSql("basehouse.getHouseList.notworkHouse");
		}
		if (!"".equals(createtime) && createtime != null && !"null".equals(createtime))
		{
			sql += getSql("basehouse.getHouseList.createtime");
			params.add(createtime.split("~")[0]);
			params.add(createtime.split("~")[1]);
		}

		if ("1".equals(kzhouse) && "1".equals(isWork) && kzhouse != null && isWork != null && !"null".equals(kzhouse)
				&& !"null".equals(isWork))
		{
			sql += getSql("basehouse.getHouseList.exportkzhouse");
		}

		if (!"".equals(accountid) && accountid != null && !"null".equals(accountid))
		{
			sql += getSql("basehouse.getHouseList.accountid");
			params.add(accountid);
		}

		if ("1".equals(isSelf) && isSelf != null && !"null".equals(isSelf))
		{
			sql += getSql("basehouse.getHouseList.exportuser_id");
			params.add(this.getUser(request).getId());
		}
		if (!"".equals(areaid) && areaid != null && !"null".equals(areaid))
		{
			sql += getSql("basehouse.getHouseList.areaid");
			params.add(areaid);
		}
		if (!"".equals(status) && status != null && !"null".equals(status))
		{
			sql += getSql("basehouse.getHouseList.status");
			params.add(status);
		}

		if (!"".equals(publish) && publish != null && !"null".equals(publish))
		{
			sql += getSql("basehouse.getHouseList.publish");
			params.add(publish);
		}

		if (!"".equals(keyword) && keyword != null && !"null".equals(keyword))
		{
			sql += getSql("basehouse.getHouseList.keyword");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
		}
		if (!"".equals(trading) && trading != null && !"null".equals(trading))
		{
			sql += getSql("basehouse.getHouseList.exprottrading");
			params.add(trading);
		}
		sql += getSql("basehouse.getHouseList.orderBy");
		return db.queryForList(sql, params);
	}

	/**
	 * 获取空置房源列表
	 * 
	 * @param response
	 * @param request
	 */
	public void getKZHouseList(HttpServletResponse response, HttpServletRequest request)
	{
		String keyword = req.getAjaxValue(request, "keyword");
		String areaid = req.getAjaxValue(request, "areaid");
		String createtime = req.getAjaxValue(request, "createtime");
		String isSelf = req.getValue(request, "isSelf"); // 自己
		String trading = req.getValue(request, "trading"); // 商圈
		String publish = req.getValue(request, "publish"); // 发布状态
		String status = req.getAjaxValue(request, "status");
		String sql = getSql("basehouse.getkzHouseList.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(createtime))
		{
			sql += getSql("basehouse.getkzHouseList.createtime");
			params.add(createtime.split("~")[0]);
			params.add(createtime.split("~")[1]);
		}

		if ("1".equals(isSelf))
		{
			sql += getSql("basehouse.getkzHouseList.user_id");
			params.add(this.getUser(request).getId());
		}

		if (!"".equals(areaid))
		{
			sql += getSql("basehouse.getkzHouseList.areaid");
			params.add(areaid);
		}

		if (!"".equals(keyword))
		{
			sql += getSql("basehouse.getkzHouseList.keyword");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
			params.add("%" + keyword + "%");
		}
		if (!"".equals(trading))
		{
			sql += getSql("basehouse.getkzHouseList.trading");
			params.add(trading);
		}

		if (!"".equals(status))
		{
			sql += getSql("basehouse.getkzHouseList.status");
			params.add(status);
		}

		if (!"".equals(publish))
		{
			sql += getSql("basehouse.getkzHouseList.publish");
			params.add(publish);
		}
		logger.debug(params.toString());
		logger.debug(str.getSql(sql, params.toArray()));
		getPageList(request, response, sql, params.toArray(), "basehouse.getkzHouseList.orderBy");
	}

	/**
	 * 保存房源部分信息
	 * 
	 * @param request
	 * @return
	 */
	public Object savePartInfoOfHouse(HttpServletRequest request)
	{
		// houseRankId
		String houseRankId = req.getValue(request, "id");
		// 房源id
		String houseId = req.getValue(request, "houseId");
		// 图片路径
		String images = req.getAjaxValue(request, "path");
		// /data/img
		String housePath = SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		// /pub/
		String houseRankPath = SystemConfig.getValue("FTP_RANKHOUSE_PATH");
		// 房源描述信息
		String houseRankDesc = req.getAjaxValue(request, "propertyDesc");
		// 上传图片
		String newPath = "";
		FtpUtil ftp = null;
		int info = 0;
		try
		{
			ftp = new FtpUtil();

			String[] pathArray = images.split(",");
			String realPath = request.getSession().getServletContext().getRealPath("/");
			logger.info("真实地址：" + realPath);
			for (String path : pathArray)
			{
				// 修改禅道问题单1273，若文件路径为空会导致后续处理失败
				if (!path.contains("."))
				{
					// 如果没有后缀，那么跳过本文件
					continue;
				}
				// 如果文件已存在
				if (path.contains(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH")))
				{
					newPath += "/" + path.replace(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH") + "/",StringUtils.EMPTY) + "|";
					continue;
				}
				
				logger.info("原图地址 = " + path);

				// 文件后缀
				String imageSuffix = path.substring(path.lastIndexOf("."));
				String name = UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY);
				String imgName = name + "_0" + imageSuffix;
				boolean success = ftp.uploadFile(realPath + path, imgName, "/img/" + houseId + houseRankPath + houseRankId + "/");
				if (!success)
				{
					return null;
				}
				
				// 压缩图片（小图）
				String smallImgName = name + "_0_small" + imageSuffix;
				String smallPath = imgCompress(286, 220, realPath, path, smallImgName);
				success = ftp.uploadFile(realPath + smallPath, smallImgName, "/img/" + houseId + houseRankPath + houseRankId + "/");
				if (!success)
				{
					return null;
				}
				
				// 压缩图片（大图）
				String bigImgName = name + "_0_big" + imageSuffix;
				String bigPath = imgCompress(956, 735, realPath, path, bigImgName);
				success = ftp.uploadFile(realPath + bigPath, bigImgName, "/img/" + houseId + houseRankPath + houseRankId + "/");
				if (!success)
				{
					return null;
				}
				newPath += "/" + houseId + houseRankPath + houseRankId + "/" + imgName + "|";
			}

			newPath = newPath.replace("\\", "\\\\");
			logger.info("newPath = " + newPath);
			String sql = "update yc_houserank_tab set images='" + newPath + "', property_desc='" + houseRankDesc
					+ "' where id=" + houseRankId;
			info = db.update(sql);
		} catch (Exception e)
		{
			logger.error("上传错误：" + e);
		} finally
		{
			// 关闭流
			if (ftp != null)
			{
				ftp.closeServer();
			}
		}
		return this.getReturnMap(info);
	}

	/**
	 * 
	 * @param w
	 *          最大宽度
	 * @param h
	 *          最大高度
	 * @param realPath
	 *          文件存放本地地址
	 * @param path
	 *          上传图片的地址
	 * @param imgName
	 *          新文件名称
	 * @return 压缩后的文件路径
	 * @throws IOException
	 */
	private String imgCompress(int w, int h, String realPath, String path, String imgName)
			throws IOException
	{
		// 读入文件
		File file = new File(realPath + path);
		// 获取Image对象
		Image img = ImageIO.read(file);
		// 源图宽度
		int width = img.getWidth(null);
		// 原图高度
		int height = img.getHeight(null);

		String imgPath = "";

		// 按照宽度还是高度进行压缩
		if (width / height > w / h)
		{
			int new_h = (int) (height * w / width);
			imgPath = resize(w, new_h, img, realPath, path, imgName);
		} else
		{
			int new_w = (int) (width * h / height);
			imgPath = resize(new_w, h, img, realPath, path, imgName);
		}
		return imgPath;
	}

	/**
	 * 
	 * @param w
	 * @param h
	 * @param img
	 * @param realPath
	 * @param path
	 * @param imgName
	 * @return
	 * @throws IOException 
	 */
	private String resize(int w, int h, Image img, String realPath, String path, String imgName) throws IOException
	{
		// 返回文件新路径
		String suffix = path.substring(path.lastIndexOf(".") + 1);
		logger.info("后缀= " + suffix);
		path = path.substring(0, path.lastIndexOf("/") + 1);
		logger.info("path 1111111== " + path);
		imgName = path + imgName;
		BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
		// 绘制缩小后的图
		image.getGraphics().drawImage(img, 0, 0, w, h, null);
		File destFile = new File(realPath + imgName);
		logger.info("realPath + imgName = " + realPath + imgName);
		FileOutputStream out = new FileOutputStream(destFile);
		// 输出到文件流
		ImageIO.write(image, suffix, out);
		out.close();
		return imgName;
	}

	/**
	 * 保存房源图片
	 * 
	 * @param request
	 * @return
	 */
	public Object saveHousePic(HttpServletRequest request)
	{

		// houseRankId
		String houseRankId = req.getValue(request, "id");
		// 房源id
		String houseId = req.getValue(request, "houseId");
		// 图片路径
		String images = req.getAjaxValue(request, "path");
		// /data/img
		String housePath = SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		// /pub/
		String houseRankPath = SystemConfig.getValue("FTP_RANKHOUSE_PATH");

		// 上传图片
		String newPath = "";
		FtpUtil ftp = null;
		int info = 0;
		try
		{
			ftp = new FtpUtil();
			String[] pathArray = images.split(",");
			String realPath = request.getSession().getServletContext().getRealPath("/");
			logger.info("realPath  == " + realPath);
			for (String path : pathArray)
			{
				// 修改禅道问题单1273，若文件路径为空会导致后续处理失败
				if (!path.contains("."))
				{
					// 如果没有后缀，那么跳过本文件
					continue;
				}
				// 如果文件已存在
				if (path.contains(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH")))
				{
					newPath += "/"
							+ path.replace(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH") + "/",
									StringUtils.EMPTY)
							+ "|";
					continue;
				}

				// 文件后缀
				String imageSuffix = path.substring(path.lastIndexOf("."));
				// 新文件名
				String tmpPath = UUID.randomUUID().toString().replaceAll("-", StringUtils.EMPTY) + "_0" + imageSuffix;

				newPath += "/" + houseId + houseRankPath + houseRankId + "/" + tmpPath + "|";
				logger.debug("localFileName:" + realPath + path + " newFileName:" + tmpPath + " remotePath:" + "/img/" + houseId
						+ houseRankPath + houseRankId + "/");
				boolean success = ftp.uploadFile(realPath + path, tmpPath,
						"/img/" + houseId + houseRankPath + houseRankId + "/");
				logger.info("success == " + success);
				if (!success)
				{
					return null;
				}
			}

			logger.info("newPath == " + newPath);
			newPath = newPath.replace("\\", "\\\\");
			logger.info("newPath == " + newPath);
			String sql = "update yc_houserank_tab set images='" + newPath + "' where id=" + houseRankId;
			info = db.update(sql);
		} catch (Exception e)
		{
			logger.error("上传错误：" + e);
		} finally
		{
			// 关闭流
			if (ftp != null)
			{
				ftp.closeServer();
			}
		}
		logger.info("info ==== " + info);
		return this.getReturnMap(info);
	}

	/**
	 * 保存房源信息
	 * 
	 * @param request
	 * @return
	 */
	public Object saveHouseInfo(final HttpServletRequest request)
	{
		@SuppressWarnings("unchecked")
		Object i = db.doInTransaction(new Batch<Object>()
		{
			@Override
			public Integer execute() throws Exception
			{
				String houseId = req.getValue(request, "id");
				String house_name = req.getAjaxValue(request, "house_name"); // 房源名称
				String shi = req.getAjaxValue(request, "shi"); // 室
				String ting = req.getAjaxValue(request, "ting"); // 厅
				String wei = req.getAjaxValue(request, "wei"); // 卫
				String chu = req.getAjaxValue(request, "chu"); // 厨房
				String yang = req.getAjaxValue(request, "yang"); // 阳台
				String dianti = req.getAjaxValue(request, "dianti"); // 电梯
				String purpose = req.getAjaxValue(request, "purpose"); // 原先用途
				String decorate = req.getAjaxValue(request, "decorate"); // 装修
				String area = req.getAjaxValue(request, "area"); // 房屋面积
				String floor = req.getAjaxValue(request, "floor"); // 楼层
				String group_id = req.getAjaxValue(request, "group_id"); // 所属小区
				String groupAddress = req.getAjaxValue(request, "groupAddress"); // 房源地址
				String groupCoordinate = req.getAjaxValue(request, "groupCoordinate"); // 房源坐标
				String descInfo = req.getAjaxValue(request, "descInfo"); // 房源说明
				String regionId = req.getAjaxValue(request, "regionId"); // 区域
				String path = req.getAjaxValue(request, "path"); // 图片路径
				String userMobile = req.getAjaxValue(request, "userMobile"); // 用户号码
				String userName = req.getAjaxValue(request, "userName"); // 用户姓名
				String value_date = req.getAjaxValue(request, "value_date"); // 生效时间
				String lease_period = req.getAjaxValue(request, "lease_period"); // 可租年限
				String cost_a = req.getAjaxValue(request, "cost_a"); // 每年租金
				String payment = req.getAjaxValue(request, "payment"); // 付款方式(数字代表月)
				String free_rent = req.getAjaxValue(request, "free_rent"); // 免租期
				String is_cubicle = req.getAjaxValue(request, "is_cubicle"); // 是否可隔(0:否,1:是)
				String house_type = req.getAjaxValue(request, "house_type"); // 房源类型
				String houseSplit = SystemConfig.getValue("FTP_HOUSE_PATH"); // 房源前缀 /img/
				String houseSoure = SystemConfig.getValue("FTP_HOUSE_SRC_PATH"); // 房源前缀 /src/
				String newPath = "";
				int owerId = 0; // 业主编号
				// 验证业主号码是否存在，如果不存在，新增业主号码
				String sql = getSql("basehouse.houseInfo.checkOwnerExist");
				logger.debug(str.getSql(sql, new Object[]
				{ userMobile }));
				Map<String, Object> userInfo = db.queryForMap(sql, new Object[]
				{ userMobile });
				if (userInfo.isEmpty())
				{
					sql = getSql("basehouse.houseInfo.insertOwner");
					owerId = this.insert(sql, new Object[]
					{ userName, userMobile, 0, "" });
				} else
				{
					owerId = Integer.parseInt(StringHelper.get(userInfo, "id"));
				}
				// 验证房源名称是否存在
				sql = getSql("basehouse.houseInfo.checkHouserNameExist");
				if ("".equals(houseId))
				{
					sql = sql.replace("####", "1 = 1");
					logger.debug(str.getSql(sql, new Object[]
					{ house_name }));
					if (db.queryForInt(sql, new Object[]
					{ house_name }) > 0)
					{
						return -4;
					}
				} else
				{
					sql = sql.replace("####", " id <> ? ");
					logger.debug(str.getSql(sql, new Object[]
					{ house_name, houseId }));
					if (db.queryForInt(sql, new Object[]
					{ house_name, houseId }) > 0)
					{
						return -4;
					}
				}

				if ("".equals(houseId))
				{
					int res = -1;
					sql = getSql("basehouse.houseInfo.insertHouseInfo");
					logger.debug(str.getSql(sql, new Object[]
					{ house_type, house_name, shi + "|" + ting + "|" + wei, chu + "|" + yang + "|" + dianti, decorate, area,
							floor, value_date, owerId, "", descInfo, groupAddress, group_id, getUser(request).getId(), "0", "",
							groupCoordinate, lease_period, cost_a, payment, free_rent, is_cubicle, getUser(request).getId(),
							purpose }));
					int id = this.insert(sql, new Object[]
					{ house_type, house_name, shi + "|" + ting + "|" + wei, chu + "|" + yang + "|" + dianti, decorate, area,
							floor, value_date, owerId, "", descInfo, groupAddress, group_id, getUser(request).getId(), "0", "",
							groupCoordinate, lease_period, cost_a, payment, free_rent, is_cubicle, getUser(request).getId(),
							purpose });
					FtpUtil ftp = null;
					try
					{
						ftp = new FtpUtil();
						if (!"".equals(path))
						{
							String[] pathArray = path.split(",");
							for (int j = 0; j < pathArray.length; j++)
							{
								String tmpPath = UUID.randomUUID().toString().replaceAll("-", "")
										+ pathArray[j].substring(pathArray[j].lastIndexOf("."));
								newPath += ",/" + id + houseSoure + tmpPath;
								// logger.debug(request.getRealPath("/")+pathArray[j]);
								boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
										houseSplit + id + houseSoure);
								if (!flag)
								{
									this.rollBack();
									return -3;
								}
							}
						}
					} catch (Exception e)
					{
						// TODO: handle exception
						e.printStackTrace();
						this.rollBack();
						return -3;
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
					// logger.debug(newPath);
					logger.debug(id + "");
					if (id != -2)
					{
						sql = getSql("basehouse.houseInfo.updateHouseCode");
						this.update(sql, new Object[]
						{ regionId, id, newPath, id });
						return 1;
					} else
					{
						return res;
					}
				} else
				{
					this.update(getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " id = ?"), new Object[]
					{ houseId, houseId });
					this.insertTableLog(request, "yc_house_tab", " and id ='" + houseId + "'", "修改房源信息", new ArrayList<String>(),
							getUser(request).getId());
					// 查看图片是否已经在上传，如果上传，就不需上传，
					Map mp = db.queryForMap(getSql("basehouse.getHouseList.main") + getSql("basehouse.getHouseList.houseId")
							+ getSql("basehouse.getHouseList.groupBycolums"), new Object[]
					{ houseId });
					String appendix = StringHelper.get(mp, "appendix");
					FtpUtil ftp = null;
					try
					{
						ftp = new FtpUtil();
						if (!"".equals(path))
						{
							String[] pathArray = path.split(",");
							for (int j = 0; j < pathArray.length; j++)
							{
								// logger.debug(SystemConfig.getValue("FTP_HTTP")+SystemConfig.getValue("FTP_HOUSE_HTTP_PATH"));
								if (appendix.contains(pathArray[j].replace("upload/tmp/", "")
										.replace(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH"), "")))
								{
									newPath += "," + pathArray[j].replace("upload/tmp/", "")
											.replace(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH"), "");
									continue;
								}
								String tmpPath = UUID.randomUUID().toString().replaceAll("-", "")
										+ pathArray[j].substring(pathArray[j].lastIndexOf("."));
								newPath += ",/" + houseId + houseSoure + tmpPath;
								boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
										houseSplit + houseId + houseSoure);
								if (!flag)
								{
									this.rollBack();
									return -3;
								}
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
						this.rollBack();
						return -3;
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
						;
					}

					sql = getSql("basehouse.houseInfo.updateHouser");
					// if("".equals(regionId))
					// {
					//
					// sql = sql.replace("####", "house_type = ?");
					logger.debug(str.getSql(sql, new Object[]
					{ house_type, house_name, shi + "|" + ting + "|" + wei, chu + "|" + yang + "|" + dianti, decorate, area,
							floor, value_date, owerId, descInfo, groupAddress, group_id, "0", newPath, groupCoordinate, lease_period,
							cost_a, payment, free_rent, is_cubicle, purpose, houseId }));
					this.update(sql, new Object[]
					{ house_type, house_name, shi + "|" + ting + "|" + wei, chu + "|" + yang + "|" + dianti, decorate, area,
							floor, value_date, owerId, descInfo, groupAddress, group_id, "0", newPath, groupCoordinate, lease_period,
							cost_a, payment, free_rent, is_cubicle, purpose, houseId });

					// }
					// else
					// {
					// sql = sql.replace("####", "house_code =
					// CONCAT(DATE_FORMAT(NOW(),\'%y\'),\'FP\',?,\'0\',?), house_type = ?");
					// logger.debug(str.getSql(sql, new
					// Object[]{regionId,houseId,0,house_name,shi+"|"+ting+"|"+wei, decorate, area,
					// floor, value_date, userId, descInfo, groupAddress, group_id, "", "", newPath,
					// groupCoordinate, 0, houseId}));
					// this.update(sql, new
					// Object[]{regionId,houseId,0,house_name,shi+"|"+ting+"|"+wei, decorate, area,
					// floor, value_date, userId, descInfo, groupAddress, group_id, "", "", newPath,
					// groupCoordinate, 0, houseId});
					// }
					return 1;
				}
			}
		});
		return this.getReturnMap(i);
	}

	/**
	 * 加载房源信息
	 * 
	 * @param request
	 * @return
	 */
	public Object loadHouseInfo(HttpServletRequest request)
	{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String id = req.getValue(request, "id"); // 房源编号
		String flag = req.getValue(request, "flag");
		String sql = getSql("basehouse.getHouseList.main") + getSql("basehouse.getHouseList.houseId");
		logger.debug(str.getSql(sql, new Object[]
		{ id }));
		Map<String, Object> mp = db.queryForMap(sql, new Object[]
		{ id });
		String qz = SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		logger.debug(StringHelper.get(mp, "appendix"));
		if (StringHelper.get(mp, "appendix").length() > 1)
		{
			String appendix = qz + StringHelper.get(mp, "appendix").replaceAll("\\|", "\\|" + qz);
			mp.put("appendix", appendix);
		} else
		{
			mp.put("appendix", "");
		}
		resultMap.put("houseInfo", mp);
		// logger.debug(mp);
		logger.debug(flag);
		// qz =
		// SystemConfig.getValue("FTP_HTTP")+SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
		if ("".equals(flag))
		{
			sql = getSql("basehouse.houseInfo.houseAgreementInfo");
			logger.debug(str.getSql(sql, new Object[]
			{ qz, "|" + qz, qz, "|" + qz, qz, "|" + qz, id }));
			mp = db.queryForMap(sql, new Object[]
			{ qz, "|" + qz, qz, "|" + qz, qz, "|" + qz, id });
			resultMap.put("agreementInfo", mp);
			qz = SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH");
			sql = getSql("basehouse.houseInfo.rankAgreementInfo");
			logger.debug(str.getSql(sql, new Object[]
			{ qz, "|" + qz, id }));
			List list = db.queryForList(sql, new Object[]
			{ qz, "|" + qz, id });
			resultMap.put("zlAgreementInfo", list);
		}
		return resultMap;
	}

	/**
	 * 删除房子
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object deleteHouse(final HttpServletRequest request)
	{
		Object i = db.doInTransaction(new Batch<Object>()
		{
			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id");
				String sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " id = ? ");
				logger.debug(str.getSql(sql, new Object[]
				{ id, id }));
				this.update(sql, new Object[]
				{ id, id });
				sql = getSql("basehouse.houseInfo.checkHouseState");
				// 房源处于无效状态，可以直接删除
				if (this.queryForInt(sql, new Object[]
				{ 10, id }) == 0)
				{
					sql = getSql("basehouse.houseInfo.checkHouseState");
					if (this.queryForInt(sql, new Object[]
					{ 0, id }) == 0)
					{
						return -2; // 状态已经变了不能删除
					}

					// 验证是否签约
					sql = getSql("basehouse.houseInfo.checkHouseQY");
					logger.debug(str.getSql(sql, new Object[]
					{ id }));
					if (this.queryForInt(sql, new Object[]
					{ id }) > 0)
					{
						return -3; // 房屋已经签约，不能删除
					}
				}
				this.insertTableLog(request, "yc_house_tab", " and id='" + id + "'", "删除房屋信息", new ArrayList<String>(),
						getUser(request).getId());
				sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
						"`delete_state` = 0, update_time = now() ");
				this.update(sql, new Object[]
				{ id });
				return 1;
			}
		});
		return getReturnMap(i);
	}

	/**
	 * 提交房源
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月10日
	 */
	@SuppressWarnings("unchecked")
	public Object submitHouse(final HttpServletRequest request)
	{

		Object res = db.doInTransaction(new Batch<Object>()
		{
			/*
			 * @see pccom.common.util.BatchBase#execute()
			 */
			@Override
			public Object execute() throws Exception
			{
				// TODO Auto-generated method stub
				String id = req.getValue(request, "id"); // 房源id

				String sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " id = ?");
				this.update(sql, new Object[]
				{ id, id });
				// logger.debug(str.getSql(getSql("basehouse.houseInfo.checkHouseState"), new
				// Object[]{0,id}));
				// 验证房源状态，如果状态不为0 ，提示用户
				if (this.queryForInt(getSql("basehouse.houseInfo.checkHouseState"), new Object[]
				{ 0, id }) != 1)
				{
					return -2;
				}

				this.insertTableLog(request, "yc_house_tab", " and id ='" + id + "'", "房屋状态更该为待审核", new ArrayList<String>(),
						getUser(request).getId());

				sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " house_status = ?, update_time = now() ");
				this.update(sql, new Object[]
				{ 1, id });

				return 1;
			}
		});
		return getReturnMap(res);
	}

	/**
	 * 发布房源
	 * 
	 * @param request
	 * @return
	 * @author liuf
	 * @date 2016年8月26日
	 */
	@SuppressWarnings("unchecked")
	public Object houseRelease(final HttpServletRequest request)
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		Object res = db.doInTransaction(new Batch<Object>()
		{
			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id"); // 房源id
				// 锁定本次操作房源
				String sql = getSql("basehouse.rankHosue.lockRank");
				this.update(sql, new Object[]
				{ id, id });

				int status = this.queryForInt(getSql("basehouse.houseInfo.checkState"), new Object[]
				{ id });

				// 查询当前状态是否为待发布
				if (status >= 5 || status == 6 || status == 9)
				{
					// 当租赁房源状态为0时返回-2提示需要修改
					if (this.queryForInt(getSql("basehouse.rankHosue.getrankstatus"), new Object[]
					{ id, 0 }) > 0)
					{
						return -2;
					}
					if (status == 7)
					{
						// 修改当前房源为发布待审批
						sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
								" house_status= ?, update_time = now(),operid=?");
						this.update(sql, new Object[]
						{ 8, oper, id });
					}
					/*
					 * sql = getSql("basehouse.rankHosue.updateRankStatus"); this.update(sql, new
					 * Object[]{String.valueOf(rankHouseService.COMPLETE),oper, id});
					 */

					// 修改房源当前为已发布
					sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
							" publish= ?, update_time = now(), operid=?");
					this.update(sql, new Object[]
					{ 1, oper, id });
				} else
				{
					return -3;
				}
				return 1;
			}
		});
		return getReturnMap(res);
	}

	/**
	 * 设置精品||取消精品
	 * 
	 * @param request
	 * @return
	 * @author 刘飞
	 * @date 2016年9月1日
	 */
	public Object rankIstop(final HttpServletRequest request)
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		@SuppressWarnings("unchecked")
		Object obj = db.doInTransaction(new Batch()
		{
			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id");
				String istop = req.getValue(request, "istop");
				String sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " id = ?");
				this.update(sql, new Object[]
				{ id, id });
				// 修改房源精品状态
				sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
						" is_top = ?, update_time = now(),operid=?");
				int status = this.update(sql, new Object[]
				{ istop, oper, id });
				// 判断执行是否成功
				if (status == 0)
				{
					return -2;
				}
				// 修改租赁房源精品状态
				sql = getSql("basehouse.rankHosue.updateRankistop");
				int res = this.update(sql, new Object[]
				{ istop, oper, id });
				// 判断执行是否成功
				if (res == 0)
				{
					return -2;
				}
				return 1;
			}
		});
		return getReturnMap(obj);
	}

	/**
	 * 撤销发布房源
	 * 
	 * @param request
	 * @return
	 * @author 刘飞
	 * @date 2016年9月1日
	 */
	public Object soldOut(final HttpServletRequest request)
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		@SuppressWarnings("unchecked")
		Object obj = db.doInTransaction(new Batch()
		{
			@Override
			public Object execute() throws Exception
			{
				String id = req.getValue(request, "id");
				String sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " id = ?");
				this.update(sql, new Object[]
				{ id, id });
				// 查出当前房源下的出租房源
				sql = getSql("basehouse.getRankHosue.main") + getSql("basehouse.getRankHosue.houseId");
				@SuppressWarnings("unchecked")
				List<Map<String, Object>> list = this.queryForList(
						sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_RANKHOUSE_HTTP_PATH")),
						new Object[]
				{ id });
				String usSql = getSql("basehouse.rankHosue.updateRankHouseStatus").replace("####", " rank_status=?,operid = ?");
				for (Map<String, Object> mp : list)
				{
					if (String.valueOf(rankHouseService.COMPLETE).equals(StringHelper.get(mp, "status")))
					{
						this.update(usSql, new Object[]
						{ String.valueOf(rankHouseService.WAIT_APPROVE), oper, StringHelper.get(mp, "id") });
					}
				}
				sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
						" publish = ?,operid=? , update_time = now()");
				this.update(sql, new Object[]
				{ 0, oper, id });
				return 1;
			}
		});
		return getReturnMap(obj);
	}

	/**
	 * 发布房源审批
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月12日
	 */
	public Object approvalPublish(final HttpServletRequest request)
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		@SuppressWarnings("unchecked")
		Object obj = db.doInTransaction(new Batch()
		{
			@Override
			public Object execute() throws Exception
			{
				// TODO Auto-generated method stub
				String isPass = req.getAjaxValue(request, "isPass"); // 审核是否成功 0 审核不通过 2 审核通过
				String id = req.getValue(request, "id");
				String sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " id = ?");
				this.update(sql, new Object[]
				{ id, id });
				String status = this.queryForString(getSql("basehouse.houseInfo.checkPulish"), new Object[]
				{ id });
				// 验证房源状态,状态不是待审批，需要提示用户
				if (!"1".equals(status))
				{
					return -2;
				}
				// 更改房源发布信息
				String psql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
						"publish=?, update_time = now(),operid=?");
				this.update(psql, new Object[]
				{ isPass, oper, id });

				String rsql = getSql("basehouse.rankHosue.updateRankStatus");
				if ("0".equals(isPass))
				{
					// 修改租赁房源状态
					this.update(rsql, new Object[]
					{ String.valueOf(rankHouseService.WAIT_SUBMIT), oper, id });
				} else
				{
					// 修改租赁房源状态
					this.update(rsql, new Object[]
					{ String.valueOf(rankHouseService.COMPLETE), oper, id });
				}
				return 1;
			}
		});
		return getReturnMap(obj);
	}

	/**
	 * 审批操作
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月12日
	 */
	public Object approvalHouse(final HttpServletRequest request)
	{
		final String oper = this.getUser(request).getId();// 获取操作人
		@SuppressWarnings("unchecked")
		Object obj = db.doInTransaction(new Batch()
		{
			@Override
			public Object execute() throws Exception
			{
				// TODO Auto-generated method stub
				String isPass = req.getAjaxValue(request, "isPass"); // 审核是否成功 0 审核不通过 2 审核通过
				String id = req.getValue(request, "id");
				String sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " id = ?");
				this.update(sql, new Object[]
				{ id, id });

				int status = this.queryForInt(getSql("basehouse.houseInfo.checkState"), new Object[]
				{ id });

				// 验证房源状态,状态不是待审批，需要提示用户
				if (status != 1 && status != 8)
				{
					return -2;
				}

				this.insertTableLog(request, "yc_house_tab", " and id ='" + id + "'",
						"房屋状态更该为审核" + ("2".equals(isPass) ? "通过" : "不通过"), new ArrayList<String>(), getUser(request).getId());
				String usqls = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
						" house_status = ?, update_time = now() ");
				// 判断当前状态
				if (status == 1)
				{
					this.update(usqls, new Object[]
					{ isPass, id });
				}
				return 1;
			}
		});
		return getReturnMap(obj);
	}

	/**
	 * 房源交接
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月12日
	 */
	public Object houseTransfer(final HttpServletRequest request, final Onstruction onstruction)
	{

		@SuppressWarnings("unchecked")
		Object obj = db.doInTransaction(new Batch<Object>()
		{
			@Override
			public Object execute() throws Exception
			{
				// TODO Auto-generated method stub
				String id = req.getValue(request, "id");
				String isPass = req.getValue(request, "isPass"); // 提交通过 7 提交不通过 5
				// String path = req.getAjaxValue(request, "path"); //图片路径
				// String desc = req.getAjaxValue(request, "desc"); // 房源提交说明
				// String newPath = "";
				String sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " id = ?");
				this.update(sql, new Object[]
				{ id, id });
				// 上传图片
				// FtpUtil ftp = null;
				// try
				// {
				// if(!"".equals(path))
				// {
				// ftp = new FtpUtil();
				// String[] pathArray = path.split(",");
				// for (int j = 0; j < pathArray.length; j++)
				// {
				// String tmpPath = UUID.randomUUID().toString().replaceAll("-",
				// "")+pathArray[j].substring(pathArray[j].lastIndexOf("."));
				// newPath += ",/" + id +"/"+tmpPath;
				// boolean flag =
				// ftp.uploadFile(request.getRealPath("/")+pathArray[j],tmpPath,SystemConfig.getValue("FTP_HOUSE_PATH")+id+"/");
				// if(!flag)
				// {
				// return -3;
				// }
				// }
				// }
				// }
				// catch(Exception e)
				// {
				// e.printStackTrace();
				// return -3;
				// }
				// finally
				// {
				// if(ftp != null)
				// {
				// ftp.closeServer();
				// }
				// }
				//
				// if(!"".equals(newPath))
				// {
				// newPath = newPath.substring(1);
				// }

				// 验证房源状态，如果状态不为6 ，提示用户 房源已经被交接
				if (this.queryForInt(getSql("basehouse.houseInfo.checkHouseState"), new Object[]
				{ 6, id }) != 1)
				{
					return -2;
				}

				this.insertTableLog(request, "yc_house_tab", " and id ='" + id + "'", "5".equals(isPass) ? "房源交接不通过" : "房源交接通过",
						new ArrayList<String>(), getUser(request).getId());

				sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " house_status = ?, update_time = now() ");
				this.update(sql, new Object[]
				{ isPass, id });
				Map params = new HashMap<>();
				params.put("houseid", id);
				if ("5".equals(isPass))
				{

					int res = onstruction.connectNO(params, this);
					if (res != 1)
					{
						this.rollBack();
						return res;
					}
				} else
				{
					int res = onstruction.connectOK(params, this);
					if (res != 1)
					{
						this.rollBack();
						return res;
					}
				}

				return 1;
			}

		});
		return getReturnMap(obj);
	}

	/**
	 * 签约房源
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月15日
	 */
	@SuppressWarnings("unchecked")
	public Object signHouse(final HttpServletRequest request)
	{

		Object obj = db.doInTransaction(new Batch<Object>()
		{
			@Override
			public Object execute() throws Exception
			{
				String agreementId = req.getValue(request, "agreementId"); // 合同编号
				String houseId = req.getValue(request, "houseId"); // 房源id
				String houseCode = req.getValue(request, "houseCode"); // 房源编码
				String user_id = req.getValue(request, "user_id"); // 用户user_id
				String user_mobile = req.getValue(request, "user_mobile"); // 用户手机号码
				String contractId = req.getValue(request, "contractId"); // 合约编号
				String contractName = req.getValue(request, "contractName"); // 合约名称
				String contractManage = req.getValue(request, "contractManage"); // 签约管家
				String doorkey = req.getValue(request, "doorkey"); // 门禁钥匙
				String is_kg = req.getValue(request, "is_kg"); // 是否可以分隔
				String areaid = req.getValue(request, "areaid"); // 归属小区
				String fooler = req.getValue(request, "fooler"); // 楼层
				String certificateno = req.getValue(request, "certificateno"); // 身份证号码
				String personCount = req.getValue(request, "personCount"); // 入户
				String[] room_pz_Array = req.getValues(request, "room_pz_"); // 房间配置
				String payType = req.getValue(request, "payType"); // 付款方式
				String fee_date = req.getValue(request, "fee_date"); // 免租期
				String effectDate = req.getValue(request, "effectDate"); // 生效时间
				String leaseTime = req.getValue(request, "leaseTime"); // 租约时长
				String[] rentMonth = req.getValues(request, "rentMonth"); // 每月租金
				String descInfo = req.getValuesString(request, "descInfo"); // 合约描述
				String cardText = req.getValuesString(request, "cardText"); // 业主卡号
				String cardPressonName = req.getValuesString(request, "cardPressonName"); // 开卡人姓名
				String carBank = req.getValuesString(request, "carBank"); // 开户银行
				String bankId = req.getValuesString(request, "bankId"); // 开户银行id
				String cardWarter = req.getValuesString(request, "cardWarter"); // 水卡帐号
				String warterDegrees = req.getValuesString(request, "warterDegrees"); // 水表读数
				String cardgas = req.getValuesString(request, "cardgas"); // 燃气帐号
				String gasDegrees = req.getValuesString(request, "gasDegrees"); // 燃气读数
				String cardelectric = req.getValuesString(request, "cardelectric"); // 电卡帐号
				String eMeterH = req.getValuesString(request, "eMeterH"); // 电表峰值
				String eMeterL = req.getValuesString(request, "eMeterL"); // 电表谷值
				String eMeter = req.getValuesString(request, "eMeter"); // 电表总值
				String path = req.getValuesString(request, "picPath"); // 产权证附件
				String propertyPath = req.getValuesString(request, "propertyPath"); // 产权人附件
				String agentPath = req.getValuesString(request, "agentPath"); // 代理人附件
				String newPath = "";
				String newPropertyPath = "";
				String newAgentPath = "";
				String idCard = "idCard";
				String agentIdCard = "agentIdCard";
				String propertyCard = "propertyCard";
				String houseSplit = "/" + houseId + SystemConfig.getValue("FTP_AGREEMENT_PATH"); // 房源分割
				String sql = ""; // 验证房源状态 锁定房源

				BigInteger totalMonery = new BigInteger("0");
				String rentMonths = "";
				for (int i = 0; i < rentMonth.length; i++)
				{
					String tmpRentMonth = rentMonth[i];
					rentMonths += tmpRentMonth + "|";
					totalMonery = totalMonery.add(new BigInteger(tmpRentMonth).multiply(new BigInteger("12")));
				}
				String room_pz_ = "";
				for (int i = 0; i < room_pz_Array.length; i++)
				{
					// room_pz_
					String tmp_room_pz = room_pz_Array[i];
					if (tmp_room_pz == null || "".equals(tmp_room_pz))
					{
						room_pz_ += "()";
					} else
					{
						room_pz_ += tmp_room_pz;
					}
				}
				// logger.debug(room_pz_);
				// 验证房东号码是否存在，如果不存在，新增房东号码
				sql = getSql("basehouse.houseInfo.checkOwnerExist");
				logger.debug(str.getSql(sql, new Object[]
				{ user_mobile }));
				Map<String, Object> userInfo = db.queryForMap(sql, new Object[]
				{ user_mobile });
				if ("".equals(StringHelper.get(userInfo, "certificateno")))
				{
					sql = getSql("basehouse.houseInfo.updateUserName").replace("####", "certificateno = ?");
					logger.debug(str.getSql(sql, new Object[]
					{ certificateno, user_id }));
					this.update(sql, new Object[]
					{ certificateno, user_id });
				}

				if ("".equals(agreementId))
				{
					sql = getSql("basehouse.houseInfo.checkHouseState"); // 验证房源状态
					logger.debug(str.getSql(sql, new Object[]
					{ 2, houseId }));
					if (this.queryForInt(sql, new Object[]
					{ 2, houseId }) == 0)
					{
						return -2; // 说明房间已经被签约
					}
					sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####", " id = ? ");
					this.update(sql, new Object[]
					{ houseId, houseId });
					// else
					// {
					// owerId = Integer.parseInt(StringHelper.get(userInfo, "id"));
					// }

					sql = getSql("basehouse.agreement.insertAgreement"); // 插入数据库
					// logger.debug(str.getSql(sql, new
					// Object[]{contractName,user_id,user_mobile,effectDate,effectDate,leaseTime,houseId,"",descInfo,totalMonery,contractManage,cardText,carBank,cardPressonName,cardWarter,cardelectric,cardgas,payType,contractId,rentMonths,effectDate,fee_date,warterDegrees,gasDegrees,eMeterH,eMeterL,eMeter,is_kg,doorkey+"|"+fooler+"|"+personCount,room_pz_,areaid,
					// getUser(request).getId(),bankId}));
					int id = this.insert(sql, new Object[]
					{ contractName, user_id, user_mobile, effectDate, effectDate, leaseTime, houseId, "", descInfo, totalMonery,
							contractManage, cardText, carBank, cardPressonName, cardWarter, cardelectric, cardgas, payType,
							contractId, rentMonths, effectDate, fee_date, warterDegrees, gasDegrees, eMeterH, eMeterL, eMeter, is_kg,
							doorkey + "|" + fooler + "|" + personCount, room_pz_, areaid, getUser(request).getId(), bankId });
					// logger.debug(id);
					if (id == -2)
					{
						return 0;
					}

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
								// logger.debug(request.getRealPath("/")+pathArray[j]);
								boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
										SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + id + "/");
								if (!flag)
								{
									logger.debug("上传图片失败");
									// logger.debug(1/0);
									this.rollBack();
									return -3;
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
								String tmpPath = "idCard" + tmpCard + pathArray[j].substring(pathArray[j].lastIndexOf("."));
								newPropertyPath += "," + houseSplit + id + "/" + tmpPath;
								// logger.debug(request.getRealPath("/")+pathArray[j]);
								// SystemConfig.getValue("FTP_HOUSE_PATH")+houseSplit+agreementId+"/"
								boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
										SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + id + "/");
								if (!flag)
								{
									logger.debug("上传图片失败");
									// logger.debug(1/0);
									this.rollBack();
									return -3;
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
								// logger.debug(request.getRealPath("/")+pathArray[j]);
								// SystemConfig.getValue("FTP_HOUSE_PATH")+houseSplit+agreementId+"/"
								boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
										SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + id + "/");
								if (!flag)
								{
									logger.debug("上传图片失败");
									// logger.debug(1/0);
									this.rollBack();
									return -3;
								}
							}
						}
					} catch (Exception e)
					{
						// TODO: handle exception
						e.printStackTrace();
						this.rollBack();
						return -3;
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
					// logger.debug(newPath);
					// logger.debug(id);
					sql = getSql("basehouse.agreement.updateAgreement").replace("####",
							" code = CONCAT(?,'A',DATE_FORMAT(NOW(),'%y'),'T',?), file_path = ?, propertyPath = ?, agentPath = ? ");
					;
					logger.debug(str.getSql(sql, new Object[]
					{ houseCode, id, newPath, newPropertyPath, newAgentPath, id }));
					this.update(sql, new Object[]
					{ houseCode, id, newPath, newPropertyPath, newAgentPath, id });

					this.insertTableLog(request, "yc_house_tab", " and id ='" + id + "'", "房源签约中", new ArrayList<String>(),
							getUser(request).getId());

					sql = getSql("basehouse.houseInfo.updateHouserInfo").replace("####",
							" house_status = ?, update_time = now() ");
					this.update(sql, new Object[]
					{ 3, houseId });
					return 1;
				} else
				{
					sql = getSql("basehouse.agreement.checkAgreementInfo").replace("####", " and type = 1 "); // 验证合约状态
					logger.debug(str.getSql(sql, new Object[]
					{ agreementId, 0 }));
					if (this.queryForInt(sql, new Object[]
					{ agreementId, 0 }) != 1)
					{
						return -4; // 说明房间状态
					}
					// 获取图片路径
					sql = getSql("basehouse.agreement.agreementInfo");
					Map mp = db.queryForMap(sql, new Object[]
					{ "", "|", "", "|", "", "|", agreementId });
					String filePath = str.get(mp, "pic_path"); // 房产证
					String cardPath = str.get(mp, "propertyPath"); // 产权人
					String agentManPath = str.get(mp, "agentPath"); // 代理人

					// logger.debug(mp);
					// sql = "update yc_agreement_tab set id = ? where id = ?";
					sql = getSql("basehouse.agreement.updateAgreement").replace("####", " id = ? ");
					this.update(sql, new Object[]
					{ agreementId, agreementId });
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
								if (pathArray[j].contains(SystemConfig.getValue("FTP_HTTP")))
								{
									newPath += "," + pathArray[j].replace("upload/tmp/", "")
											.replace(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH"), "");
									continue;
								}

								String tmpPath = propertyCard + tmpCard + pathArray[j].substring(pathArray[j].lastIndexOf("."));
								// List<Map<String,Object>> fileList =
								// ftp.getFileInfoList(SystemConfig.getValue("FTP_HOUSE_PATH")+houseSplit+agreementId+"/");
								// logger.debug(fileList);
								while (checkFileExist(filePath.split("\\|"), tmpPath))
								{
									// logger.debug(j);
									tmpPath = propertyCard + (j + 1) + pathArray[j].substring(pathArray[j].lastIndexOf("."));
								}
								// logger.debug(tmpPath);
								// logger.debug(fileList.contains(tmpPath));
								newPath += "," + houseSplit + agreementId + "/" + tmpPath;
								// logger.debug(newPath);
								// if(pathArray[j].contains(SystemConfig.getValue("FTP_AGREEMENT_PATH")))
								// {
								// continue;
								// }
								// newPath += "," + houseSplit + agreementId +"/"+tmpPath;
								boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
										SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + agreementId + "/");
								if (!flag)
								{
									logger.debug("上传图片失败");
									// logger.debug(1/0);
									this.rollBack();
									return -3;
								}
							}
						}
						// logger.debug(newPath);
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
								if (pathArray[j].contains(SystemConfig.getValue("FTP_HTTP")))
								{
									newPropertyPath += "," + pathArray[j].replace("upload/tmp/", "")
											.replace(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH"), "");
									continue;
								}
								String tmpPath = idCard + tmpCard + pathArray[j].substring(pathArray[j].lastIndexOf("."));
								while (checkFileExist(cardPath.split("\\|"), tmpPath))
								{
									// logger.debug(j);
									tmpPath = idCard + (j + 1) + pathArray[j].substring(pathArray[j].lastIndexOf("."));
								}
								newPropertyPath += "," + houseSplit + agreementId + "/" + tmpPath;
								// logger.debug(request.getRealPath("/")+pathArray[j]);
								boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
										SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + agreementId + "/");
								if (!flag)
								{
									logger.debug("上传图片失败");
									// logger.debug(1/0);
									this.rollBack();
									return -3;
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
								if (pathArray[j].contains(SystemConfig.getValue("FTP_HTTP")))
								{
									newAgentPath += "," + pathArray[j].replace("upload/tmp/", "")
											.replace(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH"), "");
									continue;
								}
								String tmpPath = agentIdCard + tmpCard + pathArray[j].substring(pathArray[j].lastIndexOf("."));
								while (checkFileExist(agentManPath.split("\\|"), tmpPath))
								{
									tmpPath = agentIdCard + (j + 1) + pathArray[j].substring(pathArray[j].lastIndexOf("."));
								}
								newAgentPath += "," + houseSplit + agreementId + "/" + tmpPath;
								// logger.debug(request.getRealPath("/")+pathArray[j]);
								boolean flag = ftp.uploadFile(request.getRealPath("/") + pathArray[j], tmpPath,
										SystemConfig.getValue("FTP_HOUSE_PATH") + houseSplit + agreementId + "/");
								if (!flag)
								{
									logger.debug("上传图片失败");
									// logger.debug(1/0);
									this.rollBack();
									return -3;
								}
							}
						}
					} catch (Exception e)
					{
						// TODO: handle exception
						e.printStackTrace();
						this.rollBack();
						return -3;
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

					this.insertTableLog(request, "yc_agreement_tab", " and id = '" + agreementId + "'", "修改合约信息",
							new ArrayList<String>(), getUser(request).getId());

					// sql = "UPDATE yc_agreement_tab SET NAME = ?, begin_time = ?, end_time =
					// ADDDATE(?,INTERVAL ? * 12 MONTH), file_path = ?, desc_text = ?, cost = ?,
					// agents = ?, bankcard = ?, cardbank = ?, cardowen = ?, watercard = ?,
					// electriccard = ?, gascard = ?, pay_type = ?, sbcode = ?, cost_a = ?, pay_time
					// = ?, free_period = ?, water_meter = ?, gas_meter = ?, electricity_meter_h =
					// ?, electricity_meter_l = ?, electricity_meter = ?, is_cubicle = ?,
					// old_matched = ? WHERE id = ?";
					sql = getSql("basehouse.agreement.udpateAgreementInfo");
					logger.debug(str.getSql(sql, new Object[]
					{ contractName, effectDate, effectDate, leaseTime, newPath, descInfo, totalMonery, contractManage, cardText,
							carBank, cardPressonName, cardWarter, cardelectric, cardgas, payType, contractId, rentMonths, effectDate,
							fee_date, warterDegrees, gasDegrees, eMeterH, eMeterL, eMeter, is_kg,
							doorkey + "|" + fooler + "|" + personCount, room_pz_, bankId, newPropertyPath, newAgentPath,
							agreementId }));
					this.update(sql, new Object[]
					{ contractName, effectDate, effectDate, leaseTime, newPath, descInfo, totalMonery, contractManage, cardText,
							carBank, cardPressonName, cardWarter, cardelectric, cardgas, payType, contractId, rentMonths, effectDate,
							fee_date, warterDegrees, gasDegrees, eMeterH, eMeterL, eMeter, is_kg,
							doorkey + "|" + fooler + "|" + personCount, room_pz_, bankId, newPropertyPath, newAgentPath,
							agreementId });
					return 1;
				}
			}

		});
		return getReturnMap(obj);
	}

	/**
	 * 验证房源状态
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月17日
	 */
	public Object validateHouseStatus(HttpServletRequest request)
	{
		int res = 0;
		String id = req.getValue(request, "id"); // 房源id
		// 验证房源状态，如果状态不为2 ，提示用户 房源已经被交接
		if (db.queryForInt(getSql("basehouse.houseInfo.checkHouseState"), new Object[]
		{ 2, id }) == 1)
		{
			res = 1;
		}
		return getReturnMap(res);
	}

	public static void main(String[] args)
	{
		String sct = "aaa/aac/aa.png";
		// logger.debug(sct.substring(sct.lastIndexOf("/")+1));
	}

	/**
	 * 查看待出租房源
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月22日
	 */
	public Object seeRentHouse(HttpServletRequest request)
	{
		String id = req.getValue(request, "id"); // 房源id
		String rank_id = req.getValue(request, "rank_id"); // 租赁房源id
		String sql = getSql("basehouse.rankHosue.seeRentHouse");
		if (rank_id != null && !"".equals(rank_id))
		{
			sql += getSql("basehouse.rankHosue.seeRentHouse_id");
			logger.debug(str.getSql(
					sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH")),
					new Object[]
					{ id, rank_id }));
			return db.queryForList(
					sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH")),
					new Object[]
					{ id, rank_id });
		} else
		{
			logger.debug(str.getSql(
					sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH")),
					new Object[]
					{ id }));
			return db.queryForList(
					sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH")),
					new Object[]
					{ id });
		}

	}

	/**
	 * 获取用户姓名
	 * 
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年8月29日
	 */
	public Object getUserName(HttpServletRequest request)
	{
		String mobile = req.getValue(request, "userMobile");
		String sql = getSql("basehouse.houseInfo.newCheckOwnerExist");
		List list = db.queryForList(sql, new Object[]
		{ mobile });
		Map mp = new HashMap();
		if (list.size() > 1)
		{
			mp.put("state", "-1");
			return mp;
		} else if (list.size() == 1)
		{
			mp = (Map) list.get(0);
			mp.put("state", "1");
		} else
		{
			mp.put("state", "0");
		}
		return mp;
	}

	/**
	 * 获取租赁房源信息
	 * 
	 * @param request
	 * @return
	 * @author liuf
	 * @date 2016年10月30日
	 */
	public Object queryRankHouse(HttpServletRequest request)
	{
		String id = req.getValue(request, "rank_id");
		String sql = getSql("basehouse.rankHosue.queryRank");
		return db.queryForMap(sql, new Object[]
		{ id });
	}

	/**
	 * 获取商圈
	 * 
	 * @return
	 */
	public Object getTrading()
	{
		String sql = getSql("basehouse.getHouseList.getTrading");
		return db.queryForList(sql);
	}

	/**
	 * 查看房源发布参考价格
	 * 
	 * @param request
	 * @return
	 */
	public Object checkHouseMenory(HttpServletRequest request)
	{
		String houseId = req.getValue(request, "houseId");
		Map agreementMonery = db.queryForMap(getSql("basehouse.getHouseList.agreementMonery"), new Object[]
		{ houseId });
		String currentyYear = str.get(agreementMonery, "currentyYear");
		String agreeYear = str.get(agreementMonery, "agreeYear");
		String agreementId = str.get(agreementMonery, "id"); // 合约id
		String[] cost_a = str.get(agreementMonery, "cost_a").split("\\|");
		String newMonery = "0";
		for (int i = 0; i < cost_a.length; i++)
		{
			if (String.valueOf(i).equals(currentyYear))
			{
				newMonery = cost_a[i];
				break;
			}
		}
		String sql = getSql("basehouse.getHouseList.projectCost"); //
		Map mp = db.queryForMap(sql, new Object[]
		{ agreementId });
		String appliance_cost = "0";
		String furniture_cost = "0";
		String other_cost = "0";
		if (!mp.isEmpty())
		{
			appliance_cost = StringHelper.get(mp, "appliance_cost");
			furniture_cost = StringHelper.get(mp, "furniture_cost");
			other_cost = StringHelper.get(mp, "other_cost");
		}
		sql = getSql("basehouse.getHouseList.onstructionMonery");
		Map onstructionMonery = db.queryForMap(sql, new Object[]
		{ appliance_cost, furniture_cost, other_cost, 30, houseId });
		String onstructionMoneryF = str.get(onstructionMonery, "monery");
		String allOnstructionMonery = StringHelper.get(db.queryForMap(sql, new Object[]
		{ appliance_cost, furniture_cost, other_cost, 1, houseId }), "monery"); // 工程总价格
		sql = getSql("basehouse.getHouseList.addSql");
		// logger.debug(str.getSql(sql.replace("####", " round((? + ?)*1.1, 2) "),new
		// Object[]{onstructionMoneryF, newMonery}));
		Map result = db.queryForMap(sql.replace("####", " round((? + ?)*1, 2) "), new Object[]
		{ onstructionMoneryF, newMonery });
		sql = getSql("basehouse.getHouseList.addSql").replace("sumcnt", "").replace("####",
				" (CASE WHEN SUM(CASE WHEN a.rank_type = 1 THEN a.fee ELSE 0 END) > ? THEN 1 ELSE 0 END)  + (CASE WHEN SUM(CASE WHEN a.rank_type = 0 THEN a.fee ELSE 0 END) >= ? THEN 1 ELSE 0 END) cnt FROM yc_houserank_tab a WHERE a.house_id = ? ");
		logger.debug(str.getSql(sql, new Object[]
		{ str.get(result, "sumcnt"), str.get(result, "sumcnt"), houseId }));
		Map res = db.queryForMap(sql, new Object[]
		{ str.get(result, "sumcnt"), str.get(result, "sumcnt"), houseId });
		result.put("flag", str.get(res, "cnt"));
		result.put("agreementMenory", str.get(agreementMonery, "cost_a"));
		result.put("allOnstructionMonery", allOnstructionMonery); // 工程总价
		return result;
	}

	/**
	 * 验证列表中是否包含一个文件名称
	 * 
	 * @param list
	 * @param fileName
	 * @return
	 */
	public boolean checkFileExist(String[] filePath, String fileName)
	{
		logger.debug(fileName);
		for (int i = 0; i < filePath.length; i++)
		{
			logger.debug(filePath[i].substring(filePath[i].lastIndexOf("/") + 1));
			if (filePath[i].substring(filePath[i].lastIndexOf("/") + 1).equals(fileName))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * 删除合约远程图片，同时更新数据库表中字段
	 * 
	 * @param request
	 * @return
	 */
	public Object removeRemotFile(HttpServletRequest request)
	{
		String res = "0";
		String id = req.getAjaxValue(request, "id"); // 合约id
		String src = req.getAjaxValue(request, "src"); // 图片路径
		String type = req.getValue(request, "type"); // 合约字段
		String[] filePath = req.getAjaxValue(request, "filePath").split(","); // 图片路径
		// logger.debug(req.getAjaxValue(request, "filePath"));
		String newPath = "";
		for (int i = 0; i < filePath.length; i++)
		{
			// logger.debug(filePath[i]);
			// logger.debug(src);
			if (filePath[i].indexOf(SystemConfig.getValue("FTP_HTTP")) > -1 && !src.equals(filePath[i]))
			{
				newPath += "|"
						+ filePath[i].replace(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH"), "");
			}
		}
		if (!"".equals(newPath))
		{
			newPath = newPath.substring(1);
		}
		// logger.debug(newPath);
		FtpUtil ftp = null;
		try
		{
			String sql = getSql("basehouse.agreement.updateAgreement").replace("####", type + " = ?");
			// logger.debug(str.getSql(sql, new Object[]{newPath,id}));
			int result = db.update(sql, new Object[]
			{ newPath, id });
			if (result == 1)
			{
				String fileName = src.substring(src.lastIndexOf("/") + 1);
				String remotePath = SystemConfig.getValue("FTP_HOUSE_PATH")
						+ src.replace(SystemConfig.getValue("FTP_HTTP") + SystemConfig.getValue("FTP_HOUSE_HTTP_PATH"), "")
								.replace(fileName, "");
				ftp = new FtpUtil();
				boolean flag = ftp.delFile(fileName, remotePath);
				if (flag)
				{
					res = "1";
				} else
				{
					res = "0";
				}
			} else
			{
				res = "-1";
			}
		} catch (Exception e)
		{
			res = "-1";
			e.printStackTrace();
		}
		// logger.debug(res);
		return getReturnMap(res);
	}

	/**
	 * 获取工程信息
	 * 
	 * @param request
	 * @return
	 */
	public Object getProjectInfo(HttpServletRequest request)
	{
		String agreementId = req.getValue(request, "agreementId"); // 合约编码
		String sql = getSql("basehouse.getHouseList.projectCost"); //
		Map mp = db.queryForMap(sql, new Object[]
		{ agreementId });
		String appliance_cost = "0";
		String furniture_cost = "0";
		String other_cost = "0";
		if (!mp.isEmpty())
		{
			appliance_cost = StringHelper.get(mp, "appliance_cost");
			furniture_cost = StringHelper.get(mp, "furniture_cost");
			other_cost = StringHelper.get(mp, "other_cost");
		}
		sql = getSql("basehouse.getHouseList.projectInfo");
		logger.debug(str.getSql(sql, new Object[]
		{ furniture_cost, appliance_cost, other_cost, furniture_cost, appliance_cost, other_cost, agreementId }));
		return db.queryForMap(sql, new Object[]
		{ furniture_cost, appliance_cost, other_cost, furniture_cost, appliance_cost, other_cost, agreementId });
	}

	/**
	 * 获取工程列表信息
	 * 
	 * @param request
	 */
	public Object getProjectList(HttpServletRequest request, HttpServletResponse response)
	{
		String flag = req.getValue(request, "flag"); //
		String agreement = req.getValue(request, "agreementId"); // 合约id
		String isMobile = req.getValue(request, "isMobile"); // 从客户端来的数据
		if ("1".equals(flag) || "2".equals(flag))
		{
			flag = " = " + flag;
		} else
		{
			flag = " not in (1,2)";
		}
		String sql = getSql("basehouse.getHouseList.projectListInfo").replace("####", flag);
		logger.debug(str.getSql(sql, new Object[]
		{ agreement }));
		if ("1".equals(isMobile))
		{
			return db.queryForList(sql, new Object[]
			{ agreement });
		} else
		{
			this.getPageList(request, response, sql, new Object[]
			{ agreement });
			return null;
		}
	}

	/**
	 * 保存水电煤
	 * 
	 * @param request
	 * @return
	 */
	public Object saveSDM(final HttpServletRequest request, final Financial financial)
	{
		return getReturnMap(db.doInTransaction(new Batch<Object>()
		{
			/**
			 * 
			 * @return
			 * @throws Exception
			 * @date 2017年1月6日
			 */
			@Override
			public Object execute() throws Exception
			{
				String waterD = req.getValue(request, "waterD"); // 水读数
				String waterF = req.getValue(request, "waterF"); // 水费用
				String waterDate = req.getValue(request, "waterDate"); // 水费时间
				String eMeterD = req.getValue(request, "eMeterD"); // 电读数
				String eMeterF = req.getValue(request, "eMeterF"); // 电费用
				String eMeterDate = req.getValue(request, "eMeterDate"); // 点费时间
				String cardgasD = req.getValue(request, "cardgasD"); // 煤读数
				String cardgasF = req.getValue(request, "cardgasF"); // 煤气费用
				String cardgasDate = req.getValue(request, "cardgasDate"); // 煤气时间
				String id = req.getValue(request, "id"); // 房源id
				Map<String, Object> mp = new HashMap<>();
				if (!"".equals(waterD) && !"".equals(waterF) && !"".equals(waterDate))
				{
					mp.put("water", waterD + "$$" + waterF + "$$" + waterDate.replace("~", "#"));
				}
				if (!"".equals(eMeterD) && !"".equals(eMeterF) && !"".equals(eMeterDate))
				{
					mp.put("eMeter", eMeterD + "$$" + eMeterF + "$$" + eMeterDate.replace("~", "#"));
				}
				if (!"".equals(cardgasD) && !"".equals(cardgasF) && !"".equals(cardgasDate))
				{
					mp.put("cardgas", cardgasD + "$$" + cardgasF + "$$" + cardgasDate.replace("~", "#"));
				}
				String sql = getSql("basehouse.agreement.getAgreementId");
				String agreementId = StringHelper.get(db.queryForMap(sql, new Object[]
				{ id }), "id");
				mp.put("agreement_id", agreementId);
				mp.put("operator", getUser(request).getId());
				logger.debug("mp:" + mp);
				return financial.jiaonashuidianfei(mp, this);
			}
		}));
	}

	/**
	 * 保存新签约信息
	 * 
	 * @param request
	 */
	public Map<String, String> initNewAgreement(HttpServletRequest request, HouseMgrService miHService)
	{
		Agreement agreement = new Agreement();
		String roomCnt = req.getValue(request, "roomCnt"); // 房间总数
		String houseId = req.getValue(request, "houseId"); // 房源id
		String houseCode = req.getValue(request, "houseCode"); // 房源编码
		String user_id = req.getValue(request, "user_id"); // 用户user_id
		String user_mobile = req.getValue(request, "user_mobile"); // 用户手机号码
		// String contractId = req.getValue(request, "contractId"); // 合约编号
		String contractName = req.getValue(request, "contractName"); // 合约名称
		String contractManage = req.getValue(request, "contractManage"); // 签约管家
		String purpose = req.getValue(request, "purpose"); // 原先用途
		String doorkey = req.getValue(request, "doorkey"); // 门禁钥匙
		String is_kg = req.getValue(request, "is_kg"); // 是否可以分隔
		String areaid = req.getValue(request, "areaid"); // 归属小区
		String floor = req.getValue(request, "fooler"); // 楼层
		String certificateno = req.getValue(request, "certificateno"); // 身份证号码
		String personCount = req.getValue(request, "personCount"); // 入户
		String payType = req.getValue(request, "payType"); // 付款方式
		String fee_date = req.getValue(request, "fee_date"); // 免租期
		String effectDate = req.getValue(request, "effectDate"); // 生效时间
		String leaseTime = req.getValue(request, "leaseTime"); // 租约时长
		String rentMonthMenory = req.getValuesString(request, "rentMonth", "|"); // 每年每月租金
		String descInfo = req.getValuesString(request, "descInfo"); // 合约描述
		String cardText = req.getValuesString(request, "cardText"); // 业主卡号
		String cardPressonName = req.getValuesString(request, "cardPressonName"); // 开卡人姓名
		String carBank = req.getValuesString(request, "carBank"); // 开户银行
		String bankId = req.getValuesString(request, "bankId"); // 开户银行id
		String cardWarter = req.getValuesString(request, "cardWarter"); // 水卡帐号
		String warterDegrees = req.getValuesString(request, "warterDegrees"); // 水表读数
		String cardgas = req.getValuesString(request, "cardgas"); // 燃气帐号
		String gasDegrees = req.getValuesString(request, "gasDegrees"); // 燃气读数
		String cardelectric = req.getValuesString(request, "cardelectric"); // 电卡帐号
		String eMeterH = req.getValuesString(request, "eMeterH"); // 电表峰值
		String eMeterL = req.getValuesString(request, "eMeterL"); // 电表谷值
		String eMeter = req.getValuesString(request, "eMeter"); // 电表总值
		String path = req.getValuesString(request, "picPath"); // 产权证附件
		String propertyPath = req.getValuesString(request, "propertyPath"); // 产权人附件
		String agentPath = req.getValuesString(request, "agentPath"); // 代理人附件
		String freeType = req.getValuesString(request, "freeType"); // 免租方式
		String propertyFile = req.getValuesString(request, "propertyFile"); // 产权证明文件
		String mortgage = req.getValuesString(request, "mortgage"); // 抵押
		String propertyCode = req.getValuesString(request, "propertyCode"); // 产权证明文件编号
		String propertMan = req.getValuesString(request, "propertMan"); // 产权人
		String co_owner = req.getValuesString(request, "co_owner"); // 共有人
		String wtmobile = req.getValuesString(request, "wtmobile"); // 委托人号码
		String wtAddress = req.getValuesString(request, "wtAddress"); // 委托人住址
		String wtIDCard = req.getValuesString(request, "wtIDCard"); // 身份证
		String wtname = req.getValuesString(request, "wtname"); // 姓名
		String wtemail = req.getValuesString(request, "wtemail"); // 邮件
		String dlmobile = req.getValuesString(request, "dlmobile"); // 代理人号码
		String dlAddress = req.getValuesString(request, "dlAddress"); // 代理人住址
		String dlIDCard = req.getValuesString(request, "dlIDCard"); // 代理人身份证
		String dlname = req.getValuesString(request, "dlname"); // 代理人姓名
		String dlemail = req.getValuesString(request, "dlemail"); // 代理人邮件
		String address = req.getValue(request, "address"); // 用户真实居住地址
		String spec2 = req.getValuesString(request, "spec2"); // 厨房阳台电梯
		String spec = req.getValuesString(request, "spec"); // 室厅卫
		String decorate = req.getValue(request, "decorate"); // 装修类型
		String area = req.getValue(request, "area"); // 房屋面积
		String[] array =
		{ "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
				"x", "y", "z" };
		String regx = "^\\d+$";
		Pattern pattern = Pattern.compile(regx);
		Matcher matcher = pattern.matcher(roomCnt);
		if (!matcher.matches())
		{
			roomCnt = "0";
		}
		int cnt = Integer.parseInt(roomCnt);
		String old_m = "";
		for (int i = 0; i < cnt; i++)
		{
			String name = array[i];
			String[] values = req.getValues(request, name);
			for (int j = 0; j < values.length; j++)
			{
				if (j == 0 && i > 0)
				{
					old_m += "##";
				}
				if (j > 0)
				{
					old_m += "|";
				}
				old_m += name + j + "=" + values[j];
			}
		}
		logger.debug(old_m);
		String[] kt = req.getValues(request, "kt"); // 客厅
		for (int i = 0; i < kt.length; i++)
		{
			if (i == 0)
			{
				old_m += "@@" + "kt" + i + "=" + kt[i];
			} else
			{
				old_m += "|" + "kt" + i + "=" + kt[i];
			}
		}
		agreement.setPurpose(purpose);
		agreement.setHouse_id(houseId);
		agreement.setNew_old_matched(old_m);
		agreement.setHouseCode(houseCode);
		agreement.setUser_id(user_id);
		agreement.setUser_mobile(user_mobile);
		agreement.setName(contractName);
		agreement.setAgents(contractManage);
		agreement.setWatercard(cardWarter);
		agreement.setWater_meter(warterDegrees);
		agreement.setGascard(cardgas);
		agreement.setGas_meter(gasDegrees);
		agreement.setElectriccard(cardelectric);
		agreement.setElectricity_meter_l(eMeterL);
		agreement.setElectricity_meter_h(eMeterH);
		agreement.setElectricity_meter(eMeter);
		agreement.setBankId(bankId);
		agreement.setBankcard(cardText);
		agreement.setCardowen(cardPressonName);
		agreement.setCardbank(carBank);
		agreement.setDesc_text(descInfo);
		agreement.setCost_a(rentMonthMenory);
		agreement.setRentMonth(leaseTime);
		agreement.setBegin_time(effectDate);
		agreement.setFree_period(fee_date);
		agreement.setPay_type(payType);
		agreement.setCertificateno(certificateno);
		agreement.setAreaid(areaid);
		agreement.setIs_cubicle(is_kg);
		agreement.setKeys_count(doorkey + "|" + floor + "|" + personCount);
		agreement.setPropertyPath(propertyPath);
		agreement.setPic_path(path);
		agreement.setAgentPath(agentPath);
		agreement.setDlemail(dlemail);
		agreement.setDlname(dlname);
		agreement.setDlIDCard(dlIDCard);
		agreement.setDlAddress(dlAddress);
		agreement.setDlmobile(dlmobile);
		agreement.setWtemail(wtemail);
		agreement.setWtname(wtname);
		agreement.setWtIDCard(wtIDCard);
		agreement.setWtAddress(wtAddress);
		agreement.setWtmobile(wtmobile);
		agreement.setCo_owner(co_owner);
		agreement.setPropertyPerson(propertMan);
		agreement.setPropertyCode(propertyCode);
		agreement.setMortgage(mortgage);
		agreement.setPropertyType(propertyFile);
		agreement.setFreeRankType(freeType);
		agreement.setSpec(spec);
		agreement.setSpec2(spec2);
		agreement.setDecorate(decorate);
		agreement.setArea(area);
		agreement.setAddress(address);
		// agreement.setWtrealAddress(wtrealAddress);
		agreement.setOperid(this.getUser(request).getId());
		logger.debug(JSONObject.fromObject(agreement).toString());
		return miHService.saveHouseAgreement(agreement, request);
	}

	/**
	 * 修改价格
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> updateRankPrice(HttpServletRequest request)
	{
		String price = req.getValue(request, "fee"); // 价格
		String rankCode = req.getValue(request, "rankCode");
		String sql = getSql("basehouse.rankHosue.updateHousePrice");
		pccom.common.util.BatchSql batchSql = new pccom.common.util.BatchSql();
		batchSql.addBatch(sql, new Object[]
		{ price, rankCode });
		this.insertTableLog(request, batchSql, "yc_houserank_tab", " and rank_code ='" + rankCode + "'", "修改房源价格",
				new Object[]
				{});
		return getReturnMap(db.doInTransaction(batchSql));
	}


}
