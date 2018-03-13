package pccom.web.job.jd.client;

import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;

import pccom.web.job.jd._132._60._168._192.ormrpc.services.EASLogin.EASLoginProxy;
import pccom.web.job.jd._132._60._168._192.ormrpc.services.EASLogin.EASLoginProxyServiceLocator;
import pccom.web.job.jd._132._60._168._192.ormrpc.services.EASLogin.EASLoginSoapBindingStub;
import pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSoapBindingStub;
import pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSrvProxy;
import pccom.web.job.jd._132._60._168._192.ormrpc.services.WSRoom1kBizFacade.WSRoom1KBizFacadeSrvProxyServiceLocator;

public class SendPost {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(SendPost.class);
	public static void send(String s) throws Exception {
		EASLoginProxyServiceLocator loginLocator = new EASLoginProxyServiceLocator();
		URL loginUrl = new URL(
				"http://192.168.60.132:6888/ormrpc/services/EASLogin");
		EASLoginSoapBindingStub loginstub = (EASLoginSoapBindingStub) loginLocator
				.getEASLogin(loginUrl);
		EASLoginProxy login = loginstub;
		/*
		 * param1 用户名 param2 密码 param3 默认eas param4 数据中心编码 param5 语言类型 默认L2
		 * param6 数据库类型 0 SQL Server 1 Oracle 2 DB2
		 * 
		 * 一下内容对应行狐EAS测试服务器配置，无需修改
		 */
		WSContext wc = login.login("fdcuser", "ycdc222", "eas", "01", "L2", 1);
		
//		String s = "{\"bills\":[{\"amount\":140196,\"billStatus\":3,\"contractType\":0,\"currency\":0,\"customer\":\"周来宝\",\"details\":[{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6600,\"detailCurrency\":0,\"periodStart\":\"2015-05-23 00:00:00\",\"periodEnd\":\"2015-08-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6600,\"detailCurrency\":0,\"periodStart\":\"2015-08-23 00:00:00\",\"periodEnd\":\"2015-11-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6600,\"detailCurrency\":0,\"periodStart\":\"2015-11-23 00:00:00\",\"periodEnd\":\"2016-02-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6600,\"detailCurrency\":0,\"periodStart\":\"2016-02-23 00:00:00\",\"periodEnd\":\"2016-05-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6600,\"detailCurrency\":0,\"periodStart\":\"2016-05-23 00:00:00\",\"periodEnd\":\"2016-08-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6600,\"detailCurrency\":0,\"periodStart\":\"2016-08-23 00:00:00\",\"periodEnd\":\"2016-11-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6600,\"detailCurrency\":0,\"periodStart\":\"2016-11-23 00:00:00\",\"periodEnd\":\"2017-02-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6600,\"detailCurrency\":0,\"periodStart\":\"2017-02-23 00:00:00\",\"periodEnd\":\"2017-05-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6930,\"detailCurrency\":0,\"periodStart\":\"2017-05-23 00:00:00\",\"periodEnd\":\"2017-08-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6930,\"detailCurrency\":0,\"periodStart\":\"2017-08-23 00:00:00\",\"periodEnd\":\"2017-11-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6930,\"detailCurrency\":0,\"periodStart\":\"2017-11-23 00:00:00\",\"periodEnd\":\"2018-02-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":6930,\"detailCurrency\":0,\"periodStart\":\"2018-02-23 00:00:00\",\"periodEnd\":\"2018-05-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":7278,\"detailCurrency\":0,\"periodStart\":\"2018-05-23 00:00:00\",\"periodEnd\":\"2018-08-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":7278,\"detailCurrency\":0,\"periodStart\":\"2018-08-23 00:00:00\",\"periodEnd\":\"2018-11-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":7278,\"detailCurrency\":0,\"periodStart\":\"2018-11-23 00:00:00\",\"periodEnd\":\"2019-02-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":7278,\"detailCurrency\":0,\"periodStart\":\"2019-02-23 00:00:00\",\"periodEnd\":\"2019-05-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":7641,\"detailCurrency\":0,\"periodStart\":\"2019-05-23 00:00:00\",\"periodEnd\":\"2019-08-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":7641,\"detailCurrency\":0,\"periodStart\":\"2019-08-23 00:00:00\",\"periodEnd\":\"2019-11-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":7641,\"detailCurrency\":0,\"periodStart\":\"2019-11-23 00:00:00\",\"periodEnd\":\"2020-02-23 00:00:00\"},{\"account\":\"租金\",\"billType\":1,\"detailAmount\":7641,\"detailCurrency\":0,\"periodStart\":\"2020-02-23 00:00:00\",\"periodEnd\":\"2020-05-23 00:00:00\"}],\"endDate\":\"2020-05-23\",\"gatherNumber\":\"\",\"lease_period\":60,\"unLease_period\":0,\"name\":\"莲花新城北苑\",\"number\":\"15FP025JY0261A15T334\",\"priceMonth\":\"2200|2200|2310|2426|2547|\",\"startDate\":\"2015-05-23\"}]}";
		
		WSRoom1KBizFacadeSrvProxyServiceLocator bizLocator = new WSRoom1KBizFacadeSrvProxyServiceLocator();
		URL bizurl = new URL(
				"http://192.168.60.132:6888/ormrpc/services/WSRoom1kBizFacade");
		WSRoom1KBizFacadeSoapBindingStub bizstub = (WSRoom1KBizFacadeSoapBindingStub) bizLocator.getWSRoom1kBizFacade(bizurl);
		bizstub.setHeader("http://login.webservice.bos.kingdee.com","SessionId", wc.getSessionId());
		WSRoom1KBizFacadeSrvProxy biz = bizstub;
		String result = biz.addGatherPay(s);
		logger.debug(result);
	}
	
}
