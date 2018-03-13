package com.yc.rm.caas.appserver.util;

/**
 * 
 * 项目名称：	qsign
 * 所属包：	
 * 类名称：	AESPlus.java
 * 类描述：	TODO
 * 创建人：	张志林
 * 创建时间：	2015年12月23日-下午12:38:59
 * 修改人：	张志林
 * 修改时间：	2015年12月23日-下午12:38:59
 * 修改备注：	
 * 
 */

/**
 * @author 张志林
 *
 */
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES_CBCUtils {
	private static byte[] ips = "0000000000000000".getBytes();
	private static IvParameterSpec iv = new IvParameterSpec(ips);
	private static String defaultencoding = "UTF-8";

	public static Cipher initCipher(String password, int mode) throws Exception {
		SecretKey skeySpec = getKey(password);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(mode, skeySpec, iv);
		return cipher;
	}

	public static String encrypt(String password, byte[] content)
			throws Exception {
		Cipher cipher = initCipher(password, Cipher.ENCRYPT_MODE);
		byte[] encrypted = cipher.doFinal(content);
		return Base64.encodeBase64String(encrypted);
	}

	public static String encrypt(Cipher cipher, byte[] content)
			throws Exception {
		byte[] encrypted = cipher.doFinal(content);
		return Base64.encodeBase64String(encrypted);
	}

	public static String decrypt(String password, String strIn)
			throws Exception {
		Cipher cipher = initCipher(password, Cipher.DECRYPT_MODE);
		byte[] encrypted1 = Base64.decodeBase64(strIn);
		byte[] original = cipher.doFinal(encrypted1);
		return new String(original, defaultencoding);
	}

	private static SecretKey getKey(String password) throws Exception {
		byte[] arrBTmp = password.getBytes();
		byte[] arrB = new byte[16]; // 创建一个空的16位字节数组（默认值为0）
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
		return skeySpec;
		// KeyGenerator kgen = KeyGenerator.getInstance("AES");
		// kgen.init(128, new SecureRandom(strKey.getBytes()));
		// SecretKey secretKey = kgen.generateKey();
		// return secretKey;
	}

	public static void main(String[] args) throws Exception {
		String Code = "测试加密";
		String password = "YgtJA3AKbKwoy2Ur";
		// Cipher cipher = initCipher(password, Cipher.ENCRYPT_MODE);
		long aaa = System.currentTimeMillis();
		// for (int i = 0; i < 1000000; i++) {
		// String t = encrypt(cipher, Code.getBytes("UTF-8"));
		// }
		// sYZPFymN4XVHcBNvrGMc1w==
		String codE = encrypt(password, Code.getBytes("UTF-8"));
		System.out.println("1000000加密时长 " + (System.currentTimeMillis() - aaa));
		System.out.println("加密时长 " + (System.currentTimeMillis() - aaa));
		System.out.println("原文：" + Code);
		System.out.println("密钥：" + password);
		System.out.println("密文：" + codE);
		System.out.println("解密：" + decrypt(password + "2", codE));
		String aaaaa = "{\"act\":\"U\",\"company_register_name\":\"仙鹤茗苑次卧C\",\"is_certification\":\"1\",\"register_email\":\"soppre0505@1.com\",\"register_mobile\":\"14442223332\",\"user_type\":\"1\",\"userid\":\"70058527\",\"username\":\"soppre0505@1.com\"}";
		String text = "仙鹤茗苑次卧C";
		System.out.println("原文 = " + text);
		System.out.println(new String(text.getBytes("GBK"), "UTF-8"));
	}
}
