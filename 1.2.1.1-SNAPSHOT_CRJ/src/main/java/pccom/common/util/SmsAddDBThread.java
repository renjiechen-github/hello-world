package pccom.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import pccom.common.util.Constants;

/**
 * 短信发送线程
 * @author 雷杨
 *
 */
public class SmsAddDBThread implements Runnable {

	private static Log logger = LogFactory.getLog(SmsAddDBThread.class);
	
	public void run() {
		  try {
			  logger.debug("短信线程启动成功.....................................................................");
			  while(true)
			  {
				  	SmsBean sb = Constants.addSmsDBQueue.take(); 
				  	int exc = sb.insertSMS();
				  	logger.debug("短信发送成功....................................................................."+exc);
			  }
		} catch (Exception e) {
			logger.error(e);
		}
	}

}