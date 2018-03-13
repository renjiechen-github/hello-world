package pccom.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;

import org.slf4j.Logger;
/**
 * 属性文件处理类
 * @author Administrator
 *
 */
public class PropertiesHellp {
	public final Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private Properties prop = null;
	private String path = "";
	
	public PropertiesHellp(){
		
	}
	
	/**
	 * 加载文件
	 */
	public PropertiesHellp(String file_name){
		prop = new Properties();
		path = this.getClass().getResource("").toString().replace("file:/", "")+file_name;
		log.debug(this.getClass().getResource("").toString()+"|文件路径：|"+path);
		//在测试环境固定住。上真实环境续注释掉
		//path = "/opt/www/webapps/njweixin/WEB-INF/classes/com/weixin/util/configuration.properties";
		try {
			prop.load(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			log.debug("chucuo1"+e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.debug("chucuo2"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取属性
	 * @param key
	 * @return
	 */
	public String getProperty(String key){
		return prop.getProperty(key);
	}
	
	/**
     *采用Properties类取得属性文件对应值
     *@parampropertiesFileNameproperties文件名，如a.properties
     *@parampropertyName属性名
     *@return根据属性名得到的属性值，如没有返回""
     */
    public String getValueByPropertyName(String propertiesFileName,String propertyName) {
        String s="";
        //Properties p=new Properties();//加载属性文件读取类
        //FileInputStream in;
        try {
            //in = new FileInputStream(System.getProperty("user.dir")+"/"+propertiesFileName);//以流的形式读入属性文件
            //p.load(in);//属性文件将该流加入的可被读取的属性中
            //in.close();//读完了关闭
            s=prop.getProperty(propertyName);//取得对应的属性值
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

	
	/**
     *采用ResourceBundel类取得属性文件对应值，这个只能够读取，不可以更改及写新的属性
     *@parampropertiesFileNameWithoutPostfixproperties文件名，不带后缀
     *@parampropertyName属性名
     *@return根据属性名得到的属性值，如没有返回""
     */
    public static String getValueByPropertyName_(String propertiesFileNameWithoutPostfix,String propertyName) {
        //如属性文件是test.properties，那此时propertiesFileNameWithoutPostfix的值就是test
        ResourceBundle bundel = ResourceBundle.getBundle(System.getProperty("user.dir")+"/"+propertiesFileNameWithoutPostfix);
        return bundel.getString(propertyName);
    }
    /** *更改属性文件的值，如果对应的属性不存在，则自动增加该属性 
     *@parampropertiesFileNameproperties文件名，如a.properties 
     *@parampropertyName属性名 *@parampropertyValue将属性名更改成该属性值 
     *@return是否操作成功 
     */
    public boolean setProperty(String propertyName,String propertyValue) {
        boolean writeOK=true;
        //Properties p=new Properties();
        //InputStream in;
        try {
            //in = new FileInputStream(System.getProperty("user.dir")+"/"+propertiesFileName);
            //p.load(in);//
            //in.close();
            prop.setProperty(propertyName,propertyValue);//设置属性值，如不属性不存在新建
            FileOutputStream out=new FileOutputStream(path);//输出流
            prop.store(out,"");//设置属性头，如不想设置，请把后面一个用""替换掉
            out.flush();//清空缓存，写入磁盘
            out.close();//关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writeOK;
    }
    /** *更改属性文件的值，如果对应的属性不存在，则自动增加该属性 
     *@parampropertiesFileNameproperties文件名，如a.properties 
     *@parampropertyName属性名 *@parampropertyValue将属性名更改成该属性值 
     *@return是否操作成功 
     */
    public static boolean setPropertys(String propertiesFileName,String[] propertyName,String[] propertyValue) {
        boolean writeOK=true;
        Properties p=new Properties();
        InputStream in;
        try {
            in = new FileInputStream(System.getProperty("user.dir")+"/"+propertiesFileName);
            p.load(in);//
            in.close();
            for(int i=0;i<propertyName.length;i++){
            	p.setProperty(propertyName[i],propertyValue[i]);//设置属性值，如不属性不存在新建
            }
            FileOutputStream out=new FileOutputStream(System.getProperty("user.dir")+"/"+propertiesFileName);//输出流
            p.store(out,"");//设置属性头，如不想设置，请把后面一个用""替换掉
            out.flush();//清空缓存，写入磁盘
            out.close();//关闭输出流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return writeOK;
    }
    
}
