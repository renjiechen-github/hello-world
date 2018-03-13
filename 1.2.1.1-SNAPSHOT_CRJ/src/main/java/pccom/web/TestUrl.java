package pccom.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;

import pccom.web.listener.SessionListener;

/**
 * 手动生成获取用户信息url测试类
 * @author 雷杨
 *
 */
public class TestUrl {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(TestUrl.class);
	public static void main(String[] args) {
//		String server_id = "gh_687009b000e8";
//		String appid = "wx7542d2c36d260646";
//		try {
//			String tzUrl = URLEncoder.encode("http://ayngeli.vicp.cc/WXWeb/log/getOper.do", "utf-8");
//			String url = "http://ycqwj.vicp.cc/WXWeb/log/getOper.do?server_id=gh_687009b000e8&redirect="+tzUrl;
//			logger.debug(url);
//			
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		
		String a = "(报修订单)用户:%s(%s),报修内容:%s。上门日期:%s【银城千万间】";
		logger.debug(String.format(a, "21","2","3","3"));
	}
	
}
