package com.ycdc.util;

import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;  
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.DateUtil;  
import org.apache.poi.ss.usermodel.Row;  
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;  
  
/** 
 * 读取Excel-兼容2003 2007 
 *  
 * @author chenrj 
 */  
public class ReadExcelUtils {  
    private Logger logger = LoggerFactory.getLogger(ReadExcelUtils.class);  
    private Workbook wb;  
    private Sheet sheet;  
    private Row row;  
  
    public ReadExcelUtils(String fileName,MultipartFile multipartFile) {  
        if(fileName==null){  
            return;  
        }  
        String ext = fileName.substring(fileName.lastIndexOf("."));  
        try {  
            InputStream is = multipartFile.getInputStream();  
            if(".xls".equals(ext)){  
                wb = new HSSFWorkbook(is);  
            }else if(".xlsx".equals(ext)){  
                wb = new XSSFWorkbook(is);  
            }else{  
                wb=null;  
            }  
        } catch (FileNotFoundException e) {  
            logger.error("FileNotFoundException", e);  
        } catch (IOException e) {  
            logger.error("IOException", e);  
        }  
    }  
      
    /** 
     * 读取Excel表格表头的内容 
     *  
     * @param InputStream 
     * @return String 表头内容的数组 
     * @author chenrj 
     */  
    public String[] readExcelTitle() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        sheet = wb.getSheetAt(0);  
        row = sheet.getRow(0);  
        // 标题总列数  
        int colNum = row.getPhysicalNumberOfCells();  
        System.out.println("colNum:" + colNum);  
        String[] title = new String[colNum];  
        for (int i = 0; i < colNum; i++) {  
            // title[i] = getStringCellValue(row.getCell((short) i)); 
        	row.getCell(i).setCellType(Cell.CELL_TYPE_STRING);
            title[i] = row.getCell(i).getStringCellValue();  
        }  
        return title;  
    }  
  
    /**
     * 获取总列数
     * @return
     * @throws Exception
     * @author chenrj
     */
    public int getColNum() throws Exception{
    	if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        sheet = wb.getSheetAt(0);  
        row = sheet.getRow(0);  
        // 标题总列数  
        int colNum = row.getPhysicalNumberOfCells();  
        return colNum;
    }
    
    /**
     * 获取总行数
     * @return RowNum
     * @throws Exception
     * @author chenrj
     */
    public int getRowNum() throws Exception{
    	if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        sheet = wb.getSheetAt(0);  
        // 标题总行数  
        int RowNum = sheet.getLastRowNum(); 
        return RowNum;
    }
    
    /** 
     * 读取Excel数据内容 
     *  
     * @param InputStream 
     * @return Map 包含单元格数据内容的Map对象 
     * @author chenrj 
     */  
    public Map<String,Object> readExcelContent() throws Exception{  
        if(wb==null){  
            throw new Exception("Workbook对象为空！");  
        }  
        Map<Integer, Map<Integer,Object>> content = new HashMap<Integer, Map<Integer,Object>>();  
        Map<String,Object> result = new HashMap<String,Object>();
        sheet = wb.getSheetAt(0);  
        // 得到总行数  
        int rowNum = sheet.getLastRowNum();  
        row = sheet.getRow(0);  
        int colNum = row.getPhysicalNumberOfCells();  
        // 正文内容应该从第二行开始,第一行为表头的标题  
        for (int i = 1; i <= rowNum; i++) {  
            row = sheet.getRow(i);  
            int j = 0;  
            Map<Integer,Object> cellValue = new HashMap<Integer, Object>(); 
            List<Object> list = new ArrayList<Object>();
            while (j < colNum) {  
                Object obj = getCellFormatValue(row.getCell(j));  
                list.add(obj);
                cellValue.put(j, obj);  
                j++;  
            }  
            result.put(i+"", list);
            content.put(i, cellValue);  
        }  
        return result;  
    }  
  
    /** 
     *  
     * 根据Cell类型设置数据 
     *  
     * @param cell 
     * @return 
     * @author chenrj 
     */  
    private Object getCellFormatValue(Cell cell) {  
        Object cellvalue = "";  
        if (cell != null) {  
            // 判断当前Cell的Type  
            switch (cell.getCellType()) {  
            case Cell.CELL_TYPE_NUMERIC:// 如果当前Cell的Type为NUMERIC  
            case Cell.CELL_TYPE_FORMULA: {  
                // 判断当前的cell是否为Date  
                if (DateUtil.isCellDateFormatted(cell)) {  
                    // 如果是Date类型则，转化为Data格式  
                    // data格式是带时分秒的：2013-7-10 0:00:00  
                    // cellvalue = cell.getDateCellValue().toLocaleString();  
                    // data格式是不带带时分秒的：2013-7-10  
                    Date date = cell.getDateCellValue();  
                    cellvalue = date;  
                } else {// 如果是纯数字  
                    // 取得当前Cell的数值  
                    cellvalue = String.valueOf((int)cell.getNumericCellValue());  
                }  
                break;  
            }  
            case Cell.CELL_TYPE_STRING:// 如果当前Cell的Type为STRING  
                // 取得当前的Cell字符串  
                cellvalue = cell.getRichStringCellValue().getString();  
                break;  
            default:// 默认的Cell值  
                cellvalue = "";  
            }  
        } else {  
            cellvalue = "";  
        }  
        return cellvalue;  
    }  
  
//    public static void main(String[] args) {  
//        try {  
//        	Date date = new Date();
//			System.out.println(date);
//            String filepath = "C:/Users/jenny/Desktop/work/9月电费拆账.xls";  
//            ReadExcelUtils excelReader = new ReadExcelUtils(filepath);  
//            LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
//            fieldMap.put("账单类型", "type");
//            fieldMap.put("辅助账房间", "roomName");
//			fieldMap.put("新合同号", "agerId");
//			fieldMap.put("年", "year");
//			fieldMap.put("期", "month");
//			fieldMap.put("起", "startMeter");
//			fieldMap.put("止", "endMeter");
//			fieldMap.put("用量", "amount");
//			fieldMap.put("费用", "cost");
//			fieldMap.put("执行结果状态", "state");
//			fieldMap.put("失败原因", "faildReson");
//            // 对读取Excel表格内容测试  
//            Map<String,Object> result = excelReader.readExcelContent();  
//            List<WegBean> wegs = new ArrayList<WegBean>();
//            
//            System.out.println("获得Excel表格的内容:");  
//            for (int i = 1; i <= result.size(); i++) {
//                List<String> list = (List<String>) result.get(i+"");
//                WegBean weg = new WegBean();
//                int index = 1;//费用第一列为空从第二列开始
//                	//给对象中的字段赋值
//                	for(Entry<String, String> entry : fieldMap.entrySet()){
//                		//获取英文字段名
//                		String enNormalName=entry.getValue();
//                		String content=list.get(index);
//                		//给对象赋值
//                		setFieldValueByName(enNormalName, content, weg);
//                		index++;
//                	}
//                	wegs.add(weg);
//            }  
////            List<String> list3 = new ArrayList<String>();
////			for (WegBean wegBean : wegs) {
////					list3.add(wegBean.getAgerId());
////			}
////		    Map<String, String> map = new HashMap<String, String>();
////		    for (int i = 0; i < wegs.size(); i++) {
////		      String key = list3.get(i);
////		      String old = map.get(key);
////		      if (old != null) {
////		        map.put(key, old + "," + (i + 1));
////		      } else {
////		        map.put(key, "" + (i + 1));
////		      }
////		    }
////		    Iterator<String> it = map.keySet().iterator();
////		    while (it.hasNext()) {
////		      String key = it.next();
////		      String value = map.get(key);
////		      if (value.indexOf(",") != -1) {
////		        System.out.println(key + " 重复,行： " + value);
//////		        indexArr = value.split(",");
//////		        for (int i = 0; i < indexArr.length; i++) {
//////		          index = Integer.parseInt(indexArr[i])-1;
//////		          list.set(index, list.get(index)+(1+i));
//////		        }
////		      }
////		    }
//		    Date date2 = new Date();
//			System.out.println(date2);
//		    System.out.println("..................");
//        } catch (FileNotFoundException e) {  
//            System.out.println("未找到指定路径的文件!");  
//            e.printStackTrace();  
//        }catch (Exception e) {  
//            e.printStackTrace();  
//        }  
//    }  
    
    /**
     * @MethodName  : setFieldValueByName
     * @Description : 根据字段名给对象的字段赋值
     * @param fieldName  字段名
     * @param fieldValue    字段值
     * @param o 对象
     */
    private static void setFieldValueByName(String fieldName,Object fieldValue,Object o) throws Exception{

            Field field=getFieldByName(fieldName, o.getClass());
            if(field!=null){
                field.setAccessible(true);
                //获取字段类型
                Class<?> fieldType = field.getType();  
                //根据字段类型给字段赋值
                if (String.class == fieldType) {  
                    field.set(o, String.valueOf(fieldValue));  
                } else if ((Integer.TYPE == fieldType)  
                        || (Integer.class == fieldType)) {  
                    field.set(o, Integer.parseInt(fieldValue.toString()));  
                } else if ((Long.TYPE == fieldType)  
                        || (Long.class == fieldType)) {  
                    field.set(o, Long.valueOf(fieldValue.toString()));  
                } else if ((Float.TYPE == fieldType)  
                        || (Float.class == fieldType)) {  
                    field.set(o, Float.valueOf(fieldValue.toString()));  
                } else if ((Short.TYPE == fieldType)  
                        || (Short.class == fieldType)) {  
                    field.set(o, Short.valueOf(fieldValue.toString()));  
                } else if ((Double.TYPE == fieldType)  
                        || (Double.class == fieldType)) {  
                    field.set(o, Double.valueOf(fieldValue.toString()));  
                } else if (Character.TYPE == fieldType) {  
                    if ((fieldValue!= null) && (fieldValue.toString().length() > 0)) {  
                        field.set(o, Character  
                                .valueOf(fieldValue.toString().charAt(0)));  
                    }  
                }else if(Date.class==fieldType){
                    field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
                }else{
                    field.set(o, fieldValue);
                }
            }else{
                throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 "+fieldName);
            }
    }
    
    /**
     * @MethodName  : getFieldByName
     * @Description : 根据字段名获取字段
     * @param fieldName 字段名
     * @param clazz 包含该字段的类
     * @return 字段
     */
    private static Field getFieldByName(String fieldName, Class<?>  clazz){
        //拿到本类的所有字段
        Field[] selfFields=clazz.getDeclaredFields();

        //如果本类中存在该字段，则返回
        for(Field field : selfFields){
            if(field.getName().equals(fieldName)){
                return field;
            }
        }

        //否则，查看父类中是否存在此字段，如果有则返回
        Class<?> superClazz=clazz.getSuperclass();
        if(superClazz!=null  &&  superClazz !=Object.class){
            return getFieldByName(fieldName, superClazz);
        }

        //如果本类和父类都没有，则返回空
        return null;
    }

}  
