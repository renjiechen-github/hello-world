package com.plugin.poi.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.plugin.poi.sample.ExcelUtil;
import com.plugin.poi.sample.User;
 

public class PoiTest {
	
	 /*excel column formate:column_#_width, excel中每一列的名称*/
    public static final String[] USER_RECORES_COLUMNS = new String[]{
            "User Name_#_3000",
            "Address_#_7000"           
            };
    /*the column will display on xls files. must the same as the entity fields.对应上面的字段.*/
    public static final String[] USER_RECORES_FIELDS = new String[]{
        "name","address"
    };
	
	public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		List<User> users = new ArrayList<User>();
		for(int i=0; i<10;i++){
			User u = new User();
			u.setAddress("address :" + i);
			u.setName("name: "+ i);
			u.setAge(i);
			users.add(u);
		}
		
		//实际项目中，这个list 估计是从数据库中得到的
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		ExcelUtil<User> userSheet = new ExcelUtil<User>();
		userSheet.creatAuditSheet(workbook, "user sheet xls", 
                users, USER_RECORES_COLUMNS, USER_RECORES_FIELDS);
		
		FileOutputStream fileOut = new FileOutputStream("d:/yihaomen_user_test.xls"); 
		workbook.write(fileOut); 
        fileOut.close(); 
	}

}
