package pccom.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.springframework.web.multipart.MultipartFile;
 
@SuppressWarnings("unchecked")
public class XssfHelper {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(Validator.class);
	public XSSFFont headFont;
	public XSSFCellStyle headStyle;
	public XSSFWorkbook xb;

	public XssfHelper() {
		xb = new XSSFWorkbook();
		headFont = xb.createFont();
		headFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		//headFont.setColor(XSSFColor.BLACK.index);

		headStyle = xb.createCellStyle();
		headStyle.setFont(headFont);
		headStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		headStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
	}

	/**
	 * 
	 * @param wb
	 *            excel文件
	 * @param row
	 *            行
	 * @param column
	 *            列号
	 * @param align
	 *            横向对齐方式
	 * @param valign
	 *            纵向对齐方式
	 * @return 格式化后的单元格
	 */
	public XSSFCell createCell(XSSFWorkbook xb, XSSFRow row, int column,
			int align, int valign) {
		XSSFCell cell = row.createCell((short) column);
		XSSFCellStyle cellStyle = xb.createCellStyle();
		cellStyle.setAlignment((short) align);
		cellStyle.setVerticalAlignment((short) valign);
		cell.setCellStyle(cellStyle);
//		cell.setEncoding((short) 1); // 支持中文导出
		return cell;
	}

	/**
	 * 缺省对齐方式为:居中
	 * 
	 * @param wb
	 * @param row
	 * @param column
	 * @return
	 */
	public XSSFCell createCenterMiddleCell(XSSFWorkbook xb, XSSFRow row,
			int column) {
		XSSFCell cell = row.createCell((short) column);
		XSSFCellStyle cellStyle = xb.createCellStyle();
		cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		cell.setCellStyle(cellStyle);
//		cell.setEncoding((short) 1); // 支持中文导出
		return cell;
	}

	public XSSFCell createCell(XSSFWorkbook xb, XSSFRow row, int column) {
		XSSFCell cell = row.createCell((short) column);
//		cell.setEncoding((short) 1); // 支持中文导出
		return cell;
	}

	public static void main(String args[]) throws Exception {
		XssfHelper xssfHelper = new XssfHelper();
		XSSFWorkbook xb = new XSSFWorkbook();
		XSSFSheet sheet = xb.createSheet("new sheet");
		XSSFRow row = sheet.createRow((short) 2);
		// HSSFCell cell=hssfHelper.createCell(wb, row,
		// 0,HSSFCellStyle.ALIGN_CENTER,HSSFCellStyle.VERTICAL_CENTER);
		XSSFCell cell = xssfHelper.createCell(xb, row, 2);
		cell.setCellValue("中文测试");
		FileOutputStream fileOut = new FileOutputStream("workbook.xlsx");
		logger.debug("BOLD:" + XSSFFont.BOLDWEIGHT_BOLD);
		xb.write(fileOut);
		fileOut.close();
	}

	/**
	 * 
	 * @param columnIndex
	 *            列号（从0开始)
	 * @param columnWidth
	 *            列宽
	 */
	public void setColumnWidth(XSSFSheet sheet, int columnIndex, int columnWidth) {
		sheet.setColumnWidth((short) columnIndex, (short) (35.7 * columnWidth));
	}

	public XSSFFont createFont(XSSFWorkbook xb, short boldWeight, short color) {
		XSSFFont font = xb.createFont();
		if (boldWeight != -1)
			font.setBoldweight(boldWeight);
		if (color != -1)
			font.setColor(color);
		return font;
	}

	public XSSFCellStyle createCellStyle(XSSFWorkbook xb, XSSFFont font,
			short valign, short align) {
		XSSFCellStyle cellStyle1 = xb.createCellStyle();
		if (font != null)
			cellStyle1.setFont(font);
		if (valign != -1)
			cellStyle1.setVerticalAlignment(valign);
		if (align != -1)
			cellStyle1.setAlignment(align);
		return cellStyle1;
	}

	public void merge(XSSFSheet sheet, int row1, int col1, int row2, int col2) {
		sheet.addMergedRegion(new CellRangeAddress(row1, (short) col1, row2,(short) col2));
	}

	public XSSFRow createRow(XSSFSheet sheet, int rowIndex) {
		XSSFRow row = sheet.createRow(rowIndex);
		return row;
	}

	/**
	 * eg: new HssfHelper().export(list, new String[][]{ {"用户号码", "MSISDN"},
	 * {"姓名", "NAME"}, {"投诉类型", "COMPLAIN_TYPE"}, {"工单流水号","TASKNO"},
	 * {"录音流水号","RECORDNO"}, {"投诉事由","COMPLAIN_CONTENT"}});
	 * 
	 * @param list
	 * @param map
	 * @return
	 */
	public XSSFWorkbook export(List list, String[][] map, String sheetName) {
		StringHelper str = new StringHelper();
		XssfHelper xssfHelper = new XssfHelper();
		XSSFSheet sheet = xb.createSheet(sheetName);
		XSSFRow row = sheet.createRow((short) 0);
		XSSFCell cell = null;
		for (int i = 0; i < map.length; i++) {
			cell = xssfHelper.createCell(xb, row, i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(map[i][0]);
		}
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((short) i + 1);
			Map hash = (Map) list.get(i);
			for (int j = 0; j < map.length; j++) {
				xssfHelper.createCell(xb, row, j).setCellValue(
						str.notEmpty(hash.get(map[j][1])));

			}
		}
		return xb;
	}

	public XSSFWorkbook export2(List list, String[][] map, String sheetName) {
		StringHelper str = new StringHelper();
		XssfHelper xssfHelper = new XssfHelper();
		XSSFSheet sheet = xb.createSheet(sheetName);
		XSSFRow row = sheet.createRow((short) 0);
		XSSFCell cell = null;
		for (int i = 0; i < map.length; i++) {
			cell = xssfHelper.createCell(xb, row, i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(map[i][0]);
		}
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((short) i + 1);
			Map hash = (Map) list.get(i);
			for (int j = 0; j < map.length; j++) {
				if (map[j][2].equals("double")) {
					if (!str.notEmpty(hash.get(map[j][1])).equals("")) {
						xssfHelper.createCell(xb, row, j).setCellValue(
								Double.valueOf(str
										.notEmpty(hash.get(map[j][1]))));
					}
				} else {
					xssfHelper.createCell(xb, row, j).setCellValue(
							str.notEmpty(hash.get(map[j][1])));
				}
			}
		}
		return xb;
	}

	public XSSFWorkbook exportNumber(List list, String[][] map, String sheetName) {
		StringHelper str = new StringHelper();
		XssfHelper xssfHelper = new XssfHelper();
		XSSFSheet sheet = xb.createSheet(sheetName);
		XSSFRow row = sheet.createRow((short) 0);
		XSSFCell cell = null;
		for (int i = 0; i < map.length; i++) {
			cell = xssfHelper.createCell(xb, row, i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(map[i][0]);
		}
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((short) i + 1);
			Map hash = (Map) list.get(i);
			for (int j = 0; j < map.length; j++) {
				try {
					xssfHelper.createCell(xb, row, j).setCellValue(
							Float.valueOf(str.notEmpty(hash.get(map[j][1]))));
				} catch (Exception e) {
					xssfHelper.createCell(xb, row, j).setCellValue(
							str.notEmpty(hash.get(map[j][1])));
				}
			}
		}
		return xb;
	}

	public XSSFWorkbook exportByPageSize(List list, String[][] map, int pageSize) {
		if (pageSize <= 0)
			pageSize = 65530;
		StringHelper str = new StringHelper();
		XssfHelper xssfHelper = new XssfHelper();
		XSSFSheet sheet = null;
		XSSFRow row = null;
		XSSFCell cell = null;

		int currPage = 0;
		int pages = list.size() / pageSize + 1;
		for (int i = 0; i < list.size(); i++) {
			if (i % pageSize == 0) {
				int currPageSize = currPage == pages - 1 ? pageSize * currPage
						+ list.size() % pageSize : pageSize * (currPage + 1);
				String sheetName = (pageSize * currPage + 1) + "~"
						+ currPageSize;
				sheet = xb.createSheet(sheetName);
				row = sheet.createRow((short) 0);
				for (int j = 0; j < map.length; j++) {
					cell = xssfHelper.createCell(xb, row, j);
					cell.setCellStyle(headStyle);
					cell.setCellValue(map[j][0]);
				}
				currPage++;
			}
			Map hash = (Map) list.get(i);
			for (int j = 0; j < map.length; j++) {
				row = sheet.createRow((short) (i % pageSize) + 1);
				xssfHelper.createCell(xb, row, j).setCellValue(
						str.notEmpty(hash.get(map[j][1])));
			}
		}
		return xb;
	}

	/**
	 * 
	 * @param file
	 * @param map
	 *            导入行号的数组
	 * @param insertSql
	 * @return -1:文件上传错误，该文件不存在！ -2:Excel读取错误！ -3:Excel导入错误，未找到Sheet！ insert
	 *         into t_test (id,msisdn) values (?,?) map=new short[]{0,1};
	 */
	public int importExcelToTable(File file, short[] map, String insertSql,
			DBHelperSpring db) {
		if (file == null) {
			return -1;
		}
		XSSFWorkbook xb = null;
		try {
			xb = new XSSFWorkbook(new FileInputStream(file));
		} catch (Exception ex) {
			return -2;
		}
		List questionList = new ArrayList<Hashtable>();
		Hashtable question = null;
		List answers = null;

		XSSFSheet sheet = xb.getSheetAt(0);
		if (sheet == null) {
			return -3;
		}
		BatchSql batch = new BatchSql();
		Iterator iter = sheet.rowIterator();
		for (int i = 0; iter.hasNext(); i++) {
			XSSFRow row = (XSSFRow) iter.next();
			if (i == 0) {
				continue;
			}
			Object[] params = new Object[map.length];
			for (int j = 0; j < map.length; j++) {
				XSSFCell cell = row.getCell(map[j]);
				params[j] = getCellStringValue(cell);
			}
			batch.addBatch(insertSql, params);
		}
		return db.doInTransaction(batch);
	}

	/**
	 * 
	 * @param file
	 * @param map
	 *            导入行号的数组
	 * @param insertSql
	 * @return -1:文件上传错误，该文件不存在！ -2:Excel读取错误！ -3:Excel导入错误，未找到Sheet！ insert
	 *         into t_test (id,msisdn) values (?,?) map=new short[]{0,1};
	 */
	public int importExcelToTable(MultipartFile file, short[] map, String insertSql,
			BatchSql batchSql) {
		return this.importExcelToTable(file,map,insertSql,batchSql,0);
	}
	
	/**
	 * 
	 * @param file
	 * @param map 导入行号的数组
	 * @param insertSql
	 * @param row_index 从第几行开始导入
	 * @return -1:文件上传错误，该文件不存在！ -2:Excel读取错误！ -3:Excel导入错误，未找到Sheet！ insert
	 *         into t_test (id,msisdn) values (?,?) map=new short[]{0,1};
	 */
	public int importExcelToTable(MultipartFile file, short[] map, String insertSql,
			BatchSql batchSql, int row_index) {
		if (file == null) {
			return -11;
		}
		XSSFWorkbook xb = null;
		try {
			xb = new XSSFWorkbook(file.getInputStream());
		} catch (Exception ex) {
			return -12;
		}

		XSSFSheet sheet = xb.getSheetAt(0);
		if (sheet == null) {
			return -13;
		}

		Iterator iter = sheet.rowIterator();
		for (int i = 0; iter.hasNext(); i++) {
			XSSFRow row = (XSSFRow) iter.next();
			if (i <= row_index) {
				continue;
			}
			Object[] params = new Object[map.length];
			for (int j = 0; j < map.length; j++) {
				XSSFCell cell = row.getCell(map[j]);
				params[j] = getCellStringValue(cell);
			}
			batchSql.addBatch(insertSql, params);
			// StringHelper str=new StringHelper();
			// logger.debug(str.getSql(insertSql,params));
		}
		return 1;
	}

	public String getCellStringValue(XSSFCell cell) {
		DecimalFormat df = new DecimalFormat();
		if (cell == null)
			return "";
		switch (cell.getCellType()) {
		case XSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue().trim();
		case XSSFCell.CELL_TYPE_NUMERIC:
			// 判断是否是日期型的单元格HSSFDateUtil.isCellDateFormatted(cell)
			if (DateUtil.isCellDateFormatted(cell)) {
				return new DateHelper().getDateString(cell.getDateCellValue(),
						"yyyy-MM-dd HH:mm:ss");
			} else {
				try {
					return df.parse(String.valueOf(cell.getNumericCellValue()))
							.toString().trim();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		default:
			return "";
		}
	}

	public int importExcelToTableWithOrder(File file, short[] map,
			String insertSql, BatchSql batchSql) {
		if (file == null) {
			return -1;
		}

		XSSFWorkbook xb = null;
		try {
			xb = new XSSFWorkbook(new FileInputStream(file));
		} catch (Exception ex) {
			return -2;
		}

		XSSFSheet sheet = xb.getSheetAt(0);
		if (sheet == null) {
			return -3;
		}
		int index = 0;
		Iterator iter = sheet.rowIterator();
		for (int i = 0; iter.hasNext(); i++) {
			XSSFRow row = (XSSFRow) iter.next();
			if (i == 0) {
				continue;
			}
			Object[] params = new Object[map.length + 1];
			int j = 0;
			for (; j < map.length; j++) {
				XSSFCell cell = row.getCell(map[j]);
				params[j] = getCellStringValue(cell);
			}
			params[j] = index++;
			batchSql.addBatch(insertSql, params);
		}
		return 1;
	}

	public int importExcelToTableWithOrderIncludeHead(File file, short[] map,
			String insertSql, BatchSql batchSql) {
		if (file == null) {
			return -1;
		}
		XSSFWorkbook xb = null;
		try {
			xb = new XSSFWorkbook(new FileInputStream(file));
		} catch (Exception ex) {
			return -2;
		}

		XSSFSheet sheet = xb.getSheetAt(0);
		if (sheet == null) {
			return -3;
		}
		int index = 0;
		Iterator iter = sheet.rowIterator();
		for (int i = 0; iter.hasNext(); i++) {
			XSSFRow row = (XSSFRow) iter.next();
			Object[] params = new Object[map.length + 1];
			int j = 0;
			for (; j < map.length; j++) {
				XSSFCell cell = row.getCell(map[j]);
				params[j] = getCellStringValue(cell);
			}
			params[j] = index++;
			batchSql.addBatch(insertSql, params);
			// StringHelper str=new StringHelper();
			// logger.debug(str.getSql(insertSql,params));
		}
		return 1;
	}

	public XSSFWorkbook export(List list, String sheet_name) {
		StringHelper str = new StringHelper();
		XssfHelper xssfHelper = new XssfHelper();
		XSSFSheet sheet = xb.createSheet(sheet_name);
		XSSFRow row;
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((short) i);
			Map map = (Map) list.get(i);
			Object[] values = map.values().toArray();
			for (int j = 0; j < values.length; j++) {
				xssfHelper.createCell(xb, row, j).setCellValue(
						str.notEmpty(values[j]));
			}
		}
		return xb;
	}

	public String getCellCode(int index) {
		String rows = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		if (index / 25 < 1) {
			return String.valueOf(rows.charAt(index));
		} else {
			int cj = index / 26;
			if (cj == 0) {
				return String.valueOf(rows.charAt(index));
			}
			int mod = index % 26;
			return String.valueOf(rows.charAt(cj - 1))
					+ String.valueOf(rows.charAt(mod));

		}
	}
	/**
	 * 通过给定的模板导出
	 * @param dataList 
	 * @param filename 文件名 
	 * @param dir_get 获取模板的路径 
	 * @param dir_to 保存文件的路径 
	 * @param response 
	 * @throws Exception
	 */
	public void exportBytemplet(List dataList,String filename,String dir_get,String dir_to) throws Exception {
		Map beans = new HashMap();
		beans.put("dataList", dataList);
		XLSTransformer transformer = new XLSTransformer();
		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(dir_get));
			HSSFWorkbook workbook = (HSSFWorkbook) transformer.transformXLS(is, beans);
			FileOutputStream out=new FileOutputStream(dir_to+filename);
			workbook.write(out);
			is.close();
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	  /**
	 * 导出csv文件
	 * 
	 * @param sql
	 * @param map
	 *  map[0][2]==0  带序号
	 *  map[0][2]==1  科学计数法（数字转文本）
	 *  map[0][2]==2  替换,为，
	 *  map[0][2]==3  日期型
	 * @return
	 * @throws Exception
	 */
	public static void exportCsv(String sql, String[][] map, PrintWriter out) throws Exception {
		DBHelperSpring db = (DBHelperSpring) SpringHelper.getBean("dbHelper");
		StringHelper str = new StringHelper();
		Connection conn = db.getDataSource().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
        try{
        	for (int i = 0; i < map.length; i++) {
				out.print(map[i][0]+",");
			}
			out.print("\r\n");
    		ResultSet rs = ps.executeQuery();
    		int r =  0;
			while(rs.next()) {
				for (int j = 0; j < map.length; j++) {
					Object ob = new Object();
					if(map[j][2].equals("0")){
						r = r + 1;
						ob = r;
					}else if(map[j][2].equals("3")){
						if(rs.getObject((map[j][1]))==null){
							ob="";
						}else{
							ob = str.notEmpty(rs.getDate(map[j][1])+" "+rs.getTime(map[j][1]));
						}
					}else{
						ob = str.notEmpty(rs.getObject((map[j][1])));
					}
					if(map[j][2].equals("1")){
						out.print("	"+ob+",");
					}else if(map[j][2].equals("2")){
						if(ob==null){
							out.print(ob+",");
						}else {
							out.print(ob.toString().replaceAll(",", "，")+",");
						}
					}else {
						out.print(ob+",");
					}
				}
				out.print("\r\n");
			}
			rs.close();
        }
        catch(Exception e){   
        	e.printStackTrace();        	
        }
        finally{
			ps.close();
			conn.close();
        }
	}

	public int importExcelToTableWithOrder2(File file, short[] map,
			String insertSql, BatchSql batchSql,int row_num) {
		if (file == null) {
			return -1;
		}

		XSSFWorkbook xb = null;
		try {
			xb = new XSSFWorkbook(new FileInputStream(file));
		} catch (Exception ex) {
			return -2;
		}

		XSSFSheet sheet = xb.getSheetAt(0);
		if (sheet == null) {
			return -3;
		}
		int index = 0;
		int iter = sheet.getLastRowNum();

		for (int i = 0; i<iter; i++) {
			XSSFRow  row=sheet.getRow(i+row_num);
			if (i == 0) {
				continue;
			}
			Object[] params = new Object[map.length+1];
			int j = 0;
			for (; j < map.length; j++) {
				XSSFCell cell = row.getCell(map[j]);
				params[j] = getCellStringValue(cell);
			}
			params[j] = index++;
			batchSql.addBatch(insertSql, params);
		}
		return 1;
	}

	public XSSFCell createHeadCell(XSSFWorkbook xb, XSSFRow row, int column) {
		XSSFFont headFont = xb.createFont();
		headFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		headFont.setColor(HSSFColor.BLACK.index);
		XSSFCellStyle headStyle = xb.createCellStyle();
		headStyle.setFont(headFont);
		headStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		headStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		XSSFCell cell = row.createCell((short) column);
		//cell.setEncoding((short) 1); // 支持中文导出
		cell.setCellStyle(headStyle);
		return cell;
	}
	
	public void export3(XSSFWorkbook xb, List list, String[][] map, int headRows, int headCols, int sheetIndex) {
		StringHelper str = new StringHelper();
		XssfHelper xssfHelper = new XssfHelper(); 
		XSSFSheet sheet = xb.getSheetAt(sheetIndex);
		XSSFRow row = null; 
		XSSFCell cell = null;
		XSSFCellStyle cellStyle = xb.createCellStyle();
		cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((short) i + headRows);
			Map hash = (Map) list.get(i);
			for (int j = 0; j < map.length; j++) {
				cell = xssfHelper.createCell(xb, row, j + headCols); 
				String val = StringHelper.notEmpty(hash.get(map[j][0]));
				if(map[j][1].equals("double")){
					if(!val.equals("")){
						cell.setCellValue(Double.valueOf(val));
					}				
				} else if(map[j][1].equals("date")) {//传入完整日期date类型字段,去除最后的.0后缀
					if(!val.equals("")){
						val = val.substring(0, val.length()-2);
						cell.setCellValue(val);
					}	
				} else{
					cell.setCellValue(val);
				}
				cell.setCellStyle(cellStyle);
			}
		}
	}
}
