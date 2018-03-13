package com.ycdc.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import java.util.stream.Collectors;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.room1000.costImport.dao.CostImportMapper;
import com.thoughtworks.xstream.mapper.Mapper;

import pccom.web.interfaces.Base;

public class Test {
		static final int N=50000;
	    static long timeList(List list){
	        long start=System.currentTimeMillis();
	        Object o = new Object();
	        for(int i=0;i<N;i++) {
	            list.add(0, o);
	        }
	        return System.currentTimeMillis()-start;
	    }
	    static long readList(List list){
	        long start=System.currentTimeMillis();
	        for(int i=0,j=list.size();i<j;i++){

	        }
	        return System.currentTimeMillis()-start;
	    }

	    static List addList(List list){
	        Object o = new Object();
	        for(int i=0;i<N;i++) {
	            list.add(0, o);
	        }
	        return list;
	    }
	    public static void main(String[] args) {
//	        System.out.println("ArrayList添加"+N+"条耗时："+timeList(new ArrayList()));
//	        System.out.println("LinkedList添加"+N+"条耗时："+timeList(new LinkedList()));
//
//	        List list1=addList(new ArrayList<>());
//	        List list2=addList(new LinkedList<>());
//	        System.out.println("ArrayList查找"+N+"条耗时："+readList(list1));
//	        System.out.println("LinkedList查找"+N+"条耗时："+timeList(list2));
//	    	String aa = "2017070700001";
//	    	System.out.println(aa.substring(0,8));
	    	System.out.println(String.valueOf("0444"));
	    }
	public static String subMonth(String date, int mun) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = sdf.parse(date);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(dt);

		rightNow.add(Calendar.MONTH, mun);
		Date dt1 = rightNow.getTime();
		String reStr = sdf.format(dt1);

		return reStr;
	}

	public static String checkOption(String option, String _date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cl = Calendar.getInstance();
		Date date = null;

		try {
			date = (Date) sdf.parse(_date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		cl.setTime(date);
		if ("pre".equals(option)) {
			// 时间减一天
			cl.add(Calendar.DAY_OF_MONTH, -1);
		} else if ("next".equals(option)) {
			// 时间加一天
			cl.add(Calendar.DAY_OF_YEAR, 1);
		} else {
			// do nothing
		}
		date = cl.getTime();
		return sdf.format(date);
	}
//
//	public static void main(String[] args) throws Exception {
//		subMonth("2017-09-01", -2);
//		checkOption("pre","2017-09-01");
//	}
}

//	/**
//     * @MethodName  : setFieldValueByName
//     * @Description : 根据字段名给对象的字段赋值
//     * @param fieldName  字段名
//     * @param fieldValue    字段值
//     * @param o 对象
//     */
//    private static void setFieldValueByName(String fieldName,Object fieldValue,Object o) throws Exception{
//
//            Field field=getFieldByName(fieldName, o.getClass());
//            if(field!=null){
//                field.setAccessible(true);
//                //获取字段类型
//                Class<?> fieldType = field.getType();  
//                //根据字段类型给字段赋值
//                if (String.class == fieldType) {  
//                    field.set(o, String.valueOf(fieldValue));  
//                } else if ((Integer.TYPE == fieldType)  
//                        || (Integer.class == fieldType)) {  
//                    field.set(o, Integer.parseInt(fieldValue.toString()));  
//                } else if ((Long.TYPE == fieldType)  
//                        || (Long.class == fieldType)) {  
//                    field.set(o, Long.valueOf(fieldValue.toString()));  
//                } else if ((Float.TYPE == fieldType)  
//                        || (Float.class == fieldType)) {  
//                    field.set(o, Float.valueOf(fieldValue.toString()));  
//                } else if ((Short.TYPE == fieldType)  
//                        || (Short.class == fieldType)) {  
//                    field.set(o, Short.valueOf(fieldValue.toString()));  
//                } else if ((Double.TYPE == fieldType)  
//                        || (Double.class == fieldType)) {  
//                    field.set(o, Double.valueOf(fieldValue.toString()));  
//                } else if (Character.TYPE == fieldType) {  
//                    if ((fieldValue!= null) && (fieldValue.toString().length() > 0)) {  
//                        field.set(o, Character  
//                                .valueOf(fieldValue.toString().charAt(0)));  
//                    }  
//                }else if(Date.class==fieldType){
//                    field.set(o, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fieldValue.toString()));
//                }else{
//                    field.set(o, fieldValue);
//                }
//            }else{
//                throw new ExcelException(o.getClass().getSimpleName() + "类不存在字段名 "+fieldName);
//            }
//    }
//    
//    /**
//     * @MethodName  : getFieldByName
//     * @Description : 根据字段名获取字段
//     * @param fieldName 字段名
//     * @param clazz 包含该字段的类
//     * @return 字段
//     */
//    private static Field getFieldByName(String fieldName, Class<?>  clazz){
//        //拿到本类的所有字段
//        Field[] selfFields=clazz.getDeclaredFields();
//
//        //如果本类中存在该字段，则返回
//        for(Field field : selfFields){
//            if(field.getName().equals(fieldName)){
//                return field;
//            }
//        }
//
//        //否则，查看父类中是否存在此字段，如果有则返回
//        Class<?> superClazz=clazz.getSuperclass();
//        if(superClazz!=null  &&  superClazz !=Object.class){
//            return getFieldByName(fieldName, superClazz);
//        }
//
//        //如果本类和父类都没有，则返回空
//        return null;
//    }
//public static void main(String[] args) {
//try {
//	Date date = new Date();
//	System.out.println(date);
//	String filePath = "C:/Users/jenny/Desktop/work/9月电费拆账.xls";
//	InputStream is = null;
//	is = new FileInputStream(filePath);//定义文本输入流 
//	LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
//	fieldMap.put("roomName", "辅助账房间");
//	fieldMap.put("agerId", "新合同号");
//	fieldMap.put("year", "年");
//	fieldMap.put("month", "期");
//	fieldMap.put("startMeter", "起");
//	fieldMap.put("endMeter", "止");
//	fieldMap.put("amount", "用量");
//	fieldMap.put("cost", "费用");
//	fieldMap.put("state", "执行结果状态");
//	fieldMap.put("faildReson", "失败原因");
//	fieldMap.put("辅助账房间", "roomName");
//	fieldMap.put("新合同号", "agerId");
//	fieldMap.put("年", "year");
//	fieldMap.put("期", "month");
//	fieldMap.put("起", "startMeter");
//	fieldMap.put("止", "endMeter");
//	fieldMap.put("用量", "amount");
//	fieldMap.put("费用", "cost");
//	fieldMap.put("执行结果状态", "state");
//	fieldMap.put("失败原因", "faildReson");
//	String [] uniqueFields = new String[]{"新合同号"};
//	
//	List<WegBean> list = ExcelUtil.excelToList(is, "Sheet1", WegBean.class, fieldMap, uniqueFields);
//	System.out.println("---"+list.toString());
//	List<String> list3 = new ArrayList<String>();
//	for (WegBean wegBean : list) {
//			list3.add(wegBean.getAgerId());
//	}
//	String[] strings = new String[]{"123","123","133"};
//	for (int i = 0; i < strings.length; i++) {
//		System.out.println("--- i "+i+list.contains(strings[i]));
//	}
//	List<String> list2 = Arrays.asList("","");
//	list2.stream().distinct().collect(Collectors.toList());
	
	
	
//	 String [] indexArr ;
//	    Map<String, String> map = new HashMap<String, String>();
//	    for (int i = 0; i < list.size(); i++) {
//	      String key = list3.get(i);
//	      String old = map.get(key);
//	      if (old != null) {
//	        map.put(key, old + "," + (i + 1));
//	      } else {
//	        map.put(key, "" + (i + 1));
//	      }
//	    }
//	    Iterator<String> it = map.keySet().iterator();
//	    int index = -1;
//	    while (it.hasNext()) {
//	      String key = it.next();
//	      String value = map.get(key);
//	      if (value.indexOf(",") != -1) {
//	        System.out.println(key + " 重复,行： " + value);
//	        indexArr = value.split(",");
//	        for (int i = 0; i < indexArr.length; i++) {
//	          index = Integer.parseInt(indexArr[i])-1;
//	          list.set(index, list.get(index)+(1+i));
//	        }
//	      }
//	    }
//	    for(String val:list3){
//	    	System.out.println(val);
//	    }
//	    Date date2 = new Date();
//		System.out.println(date2);
//	    System.out.println("..................");
	
//	Date date = new Date();
//	System.out.println(date);
//    String filepath = "C:/Users/jenny/Desktop/work/9月电费拆账.xls";  
//    ReadExcelUtils excelReader = new ReadExcelUtils(filepath);  
//    LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
//    fieldMap.put("账单类型", "type");
//    fieldMap.put("辅助账房间", "roomName");
//	fieldMap.put("新合同号", "agerId");
//	fieldMap.put("年", "year");
//	fieldMap.put("期", "month");
//	fieldMap.put("起", "startMeter");
//	fieldMap.put("止", "endMeter");
//	fieldMap.put("用量", "amount");
//	fieldMap.put("费用", "cost");
//	fieldMap.put("执行结果状态", "state");
//	fieldMap.put("失败原因", "faildReson");
    // 对读取Excel表格内容测试  
//    Map<String,Object> result = excelReader.readExcelContent();  
//    List<WegBean> wegs = new ArrayList<WegBean>();
//    for (int i = 1; i <= result.size(); i++) {
//      List<String> list = (List<String>) result.get(i+"");
//      WegBean weg = new WegBean();
//      int index = 1;//费用第一列为空从第二列开始
//      	//给对象中的字段赋值
//      	for(Entry<String, String> entry : fieldMap.entrySet()){
//      		//获取英文字段名
//      		String enNormalName=entry.getValue();
//      		String content=list.get(index);
//      		//给对象赋值
//      		setFieldValueByName(enNormalName, content, weg);
//      		index++;
//      	}
//      	wegs.add(weg);
//  }  
//    Map<String, Object> map = new HashMap<String, Object>();
//    map.put("list", wegs);
//} catch (Exception e) {
//	e.printStackTrace();
//}

//}
//}