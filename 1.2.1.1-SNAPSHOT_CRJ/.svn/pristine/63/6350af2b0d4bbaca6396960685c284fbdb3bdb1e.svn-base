/**
 * 针对org.apache.commons.codec.binary.Base64，
 * 需要导入架包commons-codec-1.9（或commons-codec-1.8等其他版本）
 * 官方下载地址：http://commons.apache.org/proper/commons-codec/download_codec.cgi
 */
package pccom.common.util.aes;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;

/**
 * 加密解密(UTF8编码的字符串).
 * <ol>
 * <li>回复消息</li>
 * <li>验证消息的安全性，并对消息进行解密。</li>
 * </ol>
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 * <ol>
 * <li>在官方网站下载JCE无限制权限策略文件（JDK7的下载地址：
 * http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html</li>
 * <li>下载后解压，可以看到local_policy.jar和US_export_policy.jar以及readme.txt</li>
 * <li>如果安装了JRE，将两个jar文件放到%JRE_HOME%\lib\security目录下覆盖原来的文件</li>
 * <li>如果安装了JDK，将两个jar文件放到%JDK_HOME%\jre\lib\security目录下覆盖原来文件</li>
 * </ol>
 */
public class YCBizMsgCrypt
{
	public final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	static Charset CHARSET = Charset.forName("utf-8");
	
	Base64         base64  = new Base64();
	
	byte[]         aesKey;
	
	String         token;
	
	
	/**
	 * 构造函数
	 * @param token 
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public YCBizMsgCrypt (String token) throws Exception
	{
		//token进行md5加密
		String mdToken = MD5(token);
		mdToken = mdToken+MD5(mdToken).substring(0,11);
		this.token = mdToken;
		aesKey = Base64.decodeBase64(this.token + "=");
		logger.debug(aesKey.length+"");
	}
	
	// 生成4个字节的网络字节序
	byte[] getNetworkBytesOrder (int sourceNumber)
	{
		byte[] orderBytes = new byte[4];
		orderBytes[3] = (byte) (sourceNumber & 0xFF);
		orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
		orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
		orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
		return orderBytes;
	}
	
	// 还原4个字节的网络字节序
	int recoverNetworkBytesOrder (byte[] orderBytes)
	{
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++)
		{
			sourceNumber <<= 8;
			sourceNumber |= orderBytes[i] & 0xff;
		}
		return sourceNumber;
	}
	
	// 随机生成16位字符串
	String getRandomStr ()
	{
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++)
		{
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/**
	 * 对明文进行加密.
	 * @param text 需要加密的明文
	 * @param randomStr 随机字符串
	 * @return 加密后base64编码的字符串
	 * @throws AesException aes加密失败
	 */
	String encrypt (String randomStr, String text) throws AesException
	{
		ByteGroup byteCollector = new ByteGroup();
		byte[] randomStrBytes = randomStr.getBytes(CHARSET);
		byte[] textBytes = text.getBytes(CHARSET);
		byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
		
		byteCollector.addBytes(randomStrBytes);
		byteCollector.addBytes(networkBytesOrder);
		byteCollector.addBytes(textBytes);
		
		// ... + pad: 使用自定义的填充方式对明文进行补位填充
		byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
		byteCollector.addBytes(padBytes);
		
		// 获得最终的字节流, 未加密
		byte[] unencrypted = byteCollector.toBytes();
		
		try
		{
			// 设置加密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
			
			// 加密
			byte[] encrypted = cipher.doFinal(unencrypted);
			
			// 使用BASE64对加密后的字符串进行编码
			String base64Encrypted = base64.encodeToString(encrypted);
			
			return base64Encrypted;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new AesException(AesException.EncryptAESError);
		}
	}
	
	/**
	 * 对密文进行解密.
	 * @param text 需要解密的密文
	 * @return 解密得到的明文
	 * @throws AesException aes解密失败
	 */
	String decrypt (String text) throws AesException
	{
		byte[] original;
		try
		{
			// 设置解密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
			
			// 使用BASE64对密文进行解码
			byte[] encrypted = Base64.decodeBase64(text);
			
			// 解密
			original = cipher.doFinal(encrypted);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new AesException(AesException.DecryptAESError);
		}
		
		String xmlContent;
		try
		{
			// 去除补位字符
			byte[] bytes = PKCS7Encoder.decode(original);
			
			// 分离16位随机字符串,网络字节序和AppId
			byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
			
			int xmlLength = recoverNetworkBytesOrder(networkOrder);
			
			xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new AesException(AesException.IllegalBuffer);
		}
		return xmlContent;
		
	}
	
	/**
	 * 将接口消息进行打包加密字符串
	 * <ol>
	 * <li>对要发送的消息进行AES-CBC加密</li>
	 * <li>生成安全签名</li>
	 * <li>将消息密文和安全签名打包成json格式</li>
	 * </ol>
	 * @param replyMsg 公众平台待回复用户的消息，xml格式的字符串
	 * @param timeStamp 时间戳，可以自己生成，也可以用URL参数的timestamp
	 * @param nonce 随机串，可以自己生成，也可以用URL参数的nonce
	 * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce, encrypt的json格式的字符串
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public String encryptMsg (String replyMsg, String timeStamp, String nonce) throws AesException
	{
		// 加密
		String encrypt = encrypt(getRandomStr(), replyMsg);
		
		// 生成安全签名
		if (timeStamp == "")
		{
			timeStamp = Long.toString(System.currentTimeMillis());
		}
		
		String signature = SHA1.getSHA1(token, timeStamp, nonce, encrypt);
		
		// logger.debug("发送给平台的签名是: " + signature[1].toString());
		// 生成发送的xml
		String result = generante(encrypt, signature, timeStamp, nonce).toString();
		return result;
	}
	
	/** 
     * MD5 16bit Encrypt Methods. 
     * @param readyEncryptStr ready encrypt string 
     * @return String encrypt result string 
     * @throws NoSuchAlgorithmException  
     * @throws UnsupportedEncodingException  
     * */  
    public String MD5(String readyEncryptStr) throws NoSuchAlgorithmException, UnsupportedEncodingException{  
    	if(readyEncryptStr != null){  
            //Get MD5 digest algorithm's MessageDigest's instance.  
            MessageDigest md = MessageDigest.getInstance("md5");  
            //Use specified byte update digest.  
            md.update(readyEncryptStr.getBytes());  
            //Get cipher text  
            byte [] b = md.digest();  
            //The cipher text converted to hexadecimal string  
            StringBuilder su = new StringBuilder();  
            //byte array switch hexadecimal number.  
            for(int offset = 0,bLen = b.length; offset < bLen; offset++){  
                String haxHex = Integer.toHexString(b[offset] & 0xFF);  
                if(haxHex.length() < 2){  
                    su.append("0");  
                }  
                su.append(haxHex);  
            }  
            return su.toString();  
        }else{  
            return null;  
        }   
    }  
	
	/**
	 * 检验消息的真实性，并且获取解密后的明文.
	 * <ol>
	 * <li>利用收到的密文生成安全签名，进行签名验证</li>
	 * <li>若验证通过，则提取json中数据信息</li>
	 * <li>对消息进行解密</li>
	 * </ol>
	 * @param postData json数据字符串
	 * @return 解密后的原文
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public String decryptMsg (String postData) throws AesException
	{
		Map<String, String> encryp = JSONObject.fromObject(postData);
		String msgSignature = encryp.get("signature");
		String timeStamp = encryp.get("timestamp");
		String nonce = encryp.get("nonce");
		
		if("".equals(msgSignature)||"".equals(timeStamp)||"".equals(nonce)){
			throw new AesException(AesException.ValidateSignatureError);
		}
		
		// 验证安全签名
		String signature = SHA1.getSHA1(token, timeStamp, nonce, encryp.get("encrypt"));
		
		logger.debug("json中的签名：" + msgSignature);
		logger.debug("校验签名：" + signature);
		//解密出来的不相等
		if(!signature.equals(msgSignature)){
			throw new AesException(AesException.ValidateError);
		}
		// 解密
		String result = decrypt(encryp.get("encrypt").toString());
		return result;
	}
	
	/**
	 * 检验消息的真实性，并且获取解密后的明文.
	 * <ol>
	 * <li>利用收到的密文生成安全签名，进行签名验证</li>
	 * <li>若验证通过，则提取json中数据信息</li>
	 * <li>对消息进行解密</li>
	 * </ol>
	 * @param postData json数据字符串
	 * @return 解密后的原文
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public JSONObject decryptJSONMsg(String postData) throws Exception{
		return JSONObject.fromObject(decryptMsg(postData));
	}
	
	public JSONArray decryptJSONArrayMsg(String postData) throws Exception{
		return JSONArray.fromObject(decryptMsg(postData));
	}
	
	/**
	 * 生成json消息
	 * @param encrypt 加密后的消息密文
	 * @param signature 安全签名
	 * @param timestamp 时间戳
	 * @param nonce 随机字符串
	 * @return 生成的json消息
	 */
	public JSONObject generante(String encrypt, String signature, String timestamp, String nonce){
		JSONObject json = new JSONObject();
		json.put("encrypt", encrypt);
		json.put("signature", signature);
		json.put("timestamp", timestamp);
		json.put("nonce", nonce);
		return json;
	}
	
}
