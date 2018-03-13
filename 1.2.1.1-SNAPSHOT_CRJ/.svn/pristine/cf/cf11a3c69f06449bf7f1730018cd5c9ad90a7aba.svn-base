/**
 * 
 */
package com.yc.rm.caas.appserver.base.office;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author stephen
 * 
 */
public class ExcelUtil {
	/**
	 * @param string
	 * @param string2
	 * @param rpt
	 * @throws FileNotFoundException
	 */
	public static String makeExcel(String dir, String name,
			List<Map<String, Object>> rpt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr =  sdf.format(new Date());
		String path = null;
		String fileName = null;
		try {
			path = System.getProperty("user.home")
					+ System.getProperty("file.separator") + "exceldata" 
					+ System.getProperty("file.separator") + dateStr 
					+ System.getProperty("file.separator") + dir
					+ System.getProperty("file.separator");
			fileName = name + "_" + dateStr + ".xlsx";
			//System.out.println(path);
			File fileDir = new File(path);
			if (!fileDir.exists()) {

				fileDir.mkdirs();
			}
			File file = new File(path + fileName);
			//System.out.println(file.createNewFile());
			OutputStream os = new FileOutputStream(file);
			// 工作区
			XSSFWorkbook wb = new XSSFWorkbook();
			// 创建第一个sheet
			XSSFSheet sheet = wb.createSheet("sheet1");
			List<String> title = new ArrayList<String>();
			if (rpt != null && rpt.size() > 0) {
				// 生成title
				XSSFRow row = sheet.createRow(0);
				int i = 0;
				for (Iterator<String> iterator = rpt.get(0).keySet().iterator(); iterator
						.hasNext();) {
					String key = iterator.next();
					title.add(key);
					row.createCell(i).setCellValue(key);
					i++;
					//System.out.println(i);
				}
			}
			//System.out.println(rpt.size());
			for (int i = 0; i < rpt.size(); i++) {
				XSSFRow row = sheet.createRow(i + 1);
				Map<String, Object> temp = rpt.get(i);
				for (int j = 0; j < title.size(); j++) {
					row.createCell(j).setCellValue(String.valueOf(temp.get(title.get(j))));
				}
			}
			// 写文件
			wb.write(os);
			// 关闭输出流
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path + fileName;
	}
}
