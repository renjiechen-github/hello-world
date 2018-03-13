package pccom.common.util;

import com.ycqwj.ycqwjApi.Initialize;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream; 
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pccom.common.SQLconfig;
import pccom.common.reloadSpring.ReloadSpring;
import pccom.web.beans.SystemConfig;

/**
 * 常量
 * @description
 * @author 雷杨
 * @date Apr 11, 2014
 */
public class Constants {
    
    public static final Logger logger = LoggerFactory.getLogger(Constants.class);
     
    public static String PUSH_ANDROID_API_KEY = ""; // android key
    public static String PUSH_ANDROID_SECRET_KEY = ""; // android 秘钥
    
    public static String PUSH_IOS_API_KEY = ""; // ios key
    public static String PUSH_IOS_SECRET_KEY = ""; // ios 秘钥

    /**
     * 消息处理线程池
     */
    public static ExecutorService pool = Executors.newFixedThreadPool(1000);
    
    /**
     * 支付通知短信发送消息
     */
    public static ExecutorService payMsmService = Executors.newFixedThreadPool(10);

    /**
     * 群发处理线程池
     */
    public static ExecutorService sendPool = Executors.newFixedThreadPool(5);
    
    /**
     * 群发处理线程池
     */
    public final static String TOKEN="ycroom";
    
    public static String root = "";
    
    /**
     * 图片对应的路径
     */
    public static String IMG_ROOT = "";

    /**
     * 项目所在的路径地址
     */
    public static String ROOPATH = "";

    /**
     * 临时存储文件夹
     */
    public static String TMP_FILE_PATH = "";

    //public static final String    TMP_FILE_PATH = "D:/apache-tomcat-7.0.42/apache-tomcat-7.0.42/webapps/ryweix_file/upload/userAttachment";

    /**
     * 手机端显示地址
     */
    public static String SHOW_SYS_URL = "";

    /**
     * 下载远程图片等内容的临时存放路径
     */
    public static String TEMP_PATH = "";

    //是否是测试环境
    public static String IS_TEST = "0";

    //用于获取网页验证的信息
    public static String GET_OPER_ID_GZH = "";


    public static String MSG_SHOW_PATH_URL = "";//用于图文消息展示的服务器地址
    
    public static boolean is_test = true;
    
    /**
     * 监控的配置文件路径
     */
    public static String WATCHFILE = "";
    
    public static String COOKIE_CLIENT_ID = "YCQWJID";
    
    /**
     * 短信处理线程
     */
    public static BlockingQueue<pccom.common.util.SmsBean> addSmsDBQueue = new LinkedBlockingQueue<pccom.common.util.SmsBean>();

    /**
     * 是否发送短信
     */
    public static String isSendMsm = "";
    
    public static String YCQWJ_API = ""; 
    public static String YCQWJ_URL = ""; 
    public static String ROOTURL = "";
    
    /**
     * 设置属性
     * @description
     * @author 雷杨 Apr 11, 2014
     */
    public void setProperties() {
    	set();
        //启动sql语句文件读取配置文件
        SQLconfig.init();
        //启动文件监控
//        logger.debug("需要监控的文件地址："+WATCHFILE);
//        String[] watch = WATCHFILE.split("，");
        String pathname = (Constants.class.getResource("/").getPath());
        if(System.getProperty("os.name").toLowerCase().indexOf("win") > -1){
			pathname = pathname.substring(1);
		}
		logger.debug("监控地址："+pathname);
        new Thread(new watchFile(pathname)).start();
        //加載系統配置信息
        SystemConfig.setSystemConf();
        
        //短信验证码线程
        new Thread(new SmsAddDBThread()).start();
        Initialize.setRootUrl(YCQWJ_URL);
    }
    
    public class watchFile implements Runnable{

    	private String name;
    	
    	public watchFile(String name){
    		this.name = name;
    	}
    	
		public void run() {
			new WatchFile().watch(name);
		}
    	
    }
    
    public static void set(){
    	logger.debug("-------------------读取配置文件--start---------------------");
    	Properties prop =  new  Properties();    
        //InputStream in =  null;
        try  {
        	String pathname =   Constants.class.getResource("/pccommon.properties" ).getPath();
        	if(System.getProperty("os.name").toLowerCase().indexOf("win") > -1){
    			pathname = pathname.substring(1);
    		}
    		logger.debug("读取配置文件："+pathname);
    		InputStream in = new FileInputStream(pathname);
            prop.load(in);    
            isSendMsm =  prop.getProperty( "isSendMsm" ).trim();
            is_test = Boolean.valueOf(prop.getProperty( "is_test" ).trim());
            WATCHFILE = prop.getProperty( "WATCHFILE" ).trim();
            root = prop.getProperty( "root" ).trim();
            PUSH_ANDROID_API_KEY = prop.getProperty( "push_android_api_key" ).trim();
            PUSH_ANDROID_SECRET_KEY = prop.getProperty( "push_android_secret_key" ).trim();
            PUSH_IOS_API_KEY = prop.getProperty( "push_ios_api_key" ).trim();
            PUSH_IOS_SECRET_KEY = prop.getProperty( "push_ios_secret_key" ).trim();
            YCQWJ_API = prop.getProperty( "YCQWJ_API" ).trim();
            YCQWJ_URL = prop.getProperty( "YCQWJ_URL" ).trim();
            ROOTURL = prop.getProperty( "ROOTURL" ).trim();
        }  catch  (IOException e) {    
            e.printStackTrace();    
        } 
        
        //开启重新加载spring bean
        ReloadSpring reloadSpring = SpringHelper.getApplicationContext().getBean("reloadSpring",ReloadSpring.class);
        reloadSpring.reload();
        
        //new Payable().execute();
        logger.debug("-------------------读取配置文件--end---------------------");
    }
}
