/*
 * Copyright (c) 2015  . All Rights Reserved.
 */
package pccom.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.SystemUtils;

/**
 * @author 雷杨
 * @date 2015-04-01
 */
public class FileHellp extends BaseLogger{
	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static String readFileByLines(String fileName) {
		File f = new File(fileName);
		StringBuffer str = new StringBuffer();
		InputStreamReader read;
		try {
			read = new InputStreamReader(new FileInputStream(f),
					"utf-8");
			BufferedReader reader = new BufferedReader(read);
			String line;
			while ((line = reader.readLine()) != null) {
				str.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return str.toString();
	}
	
	public static void main(String[] args) {
//		logger.debug(FileHellp.readFileByLines("E:\\Users\\Administrator\\Workspaces\\MyEclipse 10\\room\\src\\wxcom\\com\\sqldata.json"));
	}

}
