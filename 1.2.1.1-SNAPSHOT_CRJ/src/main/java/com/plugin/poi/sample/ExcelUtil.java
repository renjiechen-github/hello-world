package com.plugin.poi.sample;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;


/**
 * Excel util, create excel sheet, cell and style.
 * 
 * @param <T>
 *            generic class.
 */
public class ExcelUtil<T> {

	public HSSFCellStyle getCellStyle(HSSFWorkbook workbook, boolean isHeader) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setLocked(true);
		if (isHeader) {
			style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}
		return style;
	}

	public void generateHeader(HSSFWorkbook workbook, HSSFSheet sheet,
			String[] headerColumns) {
		HSSFCellStyle style = getCellStyle(workbook, true);
		Row row = sheet.createRow(0);
		row.setHeightInPoints(30);
		for (int i = 0; i < headerColumns.length; i++) {
			Cell cell = row.createCell(i);
			String[] column = headerColumns[i].split("_#_");

			sheet.setColumnWidth(i, Integer.valueOf(column[1]));
			cell.setCellValue(column[0]);
			cell.setCellStyle(style);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HSSFSheet creatAuditSheet(HSSFWorkbook workbook, String sheetName,
			List<T> dataset, String[] headerColumns, String[] fieldColumns)
			throws NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		HSSFSheet sheet = workbook.createSheet(sheetName);
		sheet.protectSheet("");

		generateHeader(workbook, sheet, headerColumns);
		HSSFCellStyle style = getCellStyle(workbook, false);
		// style.setWrapText(true);
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		int rowNum = 0;
		//String[] oldValue = new String[fieldColumns.length];
		for (T t : dataset) {
			rowNum++;
			Row row = sheet.createRow(rowNum);
			row.setHeightInPoints(25);
			for (int i = 0; i < fieldColumns.length; i++) {
 
				String fieldName = fieldColumns[i];
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Class clazz = t.getClass();
					Method getMethod;
					getMethod = clazz.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					String cellValue = "";
					if (value instanceof Date) {
						Date date = (Date) value;
						cellValue = sd.format(date);
					} else {
						cellValue = null != value ? value.toString() : "";
						if (cellValue.indexOf("|") > -1) {
							String[] str = cellValue.split("\\|");
							cellValue = "";
							for (int j = 1; j <= str.length; j++) {
								cellValue += str[j - 1] + " | ";
							}
						}
					}
					Cell cell = row.createCell(i);

					cell.setCellValue(cellValue);

					/*if (!oldValue[i].equals(cellValue)) {
						oldValue[i] = cellValue;
					} else {
						sheet.addMergedRegion(new CellRangeAddress((rowNum - 1),
								rowNum, 0, 0));
					}*/

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return sheet;
	}

}
