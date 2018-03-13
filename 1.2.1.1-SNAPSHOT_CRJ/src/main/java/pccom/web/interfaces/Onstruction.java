package pccom.web.interfaces;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import org.springframework.stereotype.Service;

import pccom.common.util.Batch;

import com.room1000.core.utils.DateUtil;
/**
 * 施工对外接口
 * @author 刘飞
 *
 */
@Service("onstruction")
public class Onstruction extends Base {

	/**
	 * 收房合约成功新增施工记录
	 * @author 刘飞
	 * @return 
	 * 		  -10新增失败
	 *         1  成功
	 * @throws ParseException 
	 */
	public int insertOn(Map params, Object obj) throws ParseException
	{
		String houseid=str.get(params, "house_id");//房源id
		String spec=str.get(params, "spec");//户型
		String agreementid=str.get(params, "agreement_id");//合约id
		String constructiontime=str.get(params, "constructiontime");//开工时间 =合同生效时间+免租期+一天
		int onstid = insert(obj, getSql("onstruction.info.insert"), new Object[]{houseid,spec,constructiontime,agreementid});
		if(onstid == -1)
		{ //语句执行错误
			return -10;
		}
		return 1;
	}
	
	/**
	 * 合约置为无效
	 * @author 刘飞
	 * @return 
	 * 		  -10 失败
	 *         1  成功
	 * @throws ParseException 
	 */
	public int deleteOn(Map params, Object obj) throws ParseException
	{
		String houseid=str.get(params, "houseid");//房源id
		String sql = getSql("onstruction.info.checkstatus");
		int res1 = queryForInt(obj, sql, new Object[]{houseid});
		if (res1<1) {
			return -10;
		}
	    int res = update(obj, getSql("onstruction.info.deleteUp"), new Object[]{houseid});
		//key=1 成功 key=0失败
		if (res != 1)
		{
			return -10;	
		}
		return res;
	}
	
	
	/**
	 * 验证是否开工
	 * @author 刘飞
	 * @return 
	 * 		  -10 失败
	 *         1  成功
	 * @throws ParseException 
	 */
	public int checkstatus(Map params, Object obj) throws ParseException
	{
		String houseid=str.get(params, "houseid");//房源id
		String sql = getSql("onstruction.info.checkstatus");
		int res1 = queryForInt(obj, sql, new Object[]{houseid});
		logger.debug("----------------------------------------"+res1);
		if (res1>0) {
			return -11;
		}
		return 1;
	}
	
	/**
	 * 交接通过
	 * @author 刘飞
	 * @return 
	 * 		  -10 失败
	 *         1  成功
	 * @throws ParseException 
	 */
	public int connectOK(Map params, Object obj) throws ParseException
	{
		String houseid=str.get(params, "houseid");//房源id
	    int res = update(obj, getSql("onstruction.info.connectOK"), new Object[]{6,houseid});
		//key=1 成功 key=0失败
		if (res != 1)
		{
			return -10;	
		}
		return res;
	}
	
	/**
	 * 交接不通过
	 * @author 刘飞
	 * @return 
	 * 		  -10 失败
	 *         1  成功
	 * @throws ParseException 
	 */
	public int connectNO(Map params, Object obj) throws ParseException
	{
		String houseid=str.get(params, "houseid");//房源id
	    int res = update(obj, getSql("onstruction.info.connectNO"), new Object[]{2,houseid});
		//key=1 成功 key=0失败
		if (res != 1)
		{
			return -10;	
		}
		return res;
	}

	
	
	
	
	
	
}
