package pccom.web.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;

import pccom.web.job.jd.client.SendPost;

public class SessionListener implements HttpSessionAttributeListener,HttpSessionListener,HttpSessionBindingListener {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(SessionListener.class);
	public void attributeAdded(HttpSessionBindingEvent arg0) {
		logger.debug("添加attributeAdded:"+arg0.getName()+"--"+arg0.getValue());
		SessionManage.setSession(arg0.getSession(),arg0.getName(), arg0.getValue());
	}

	public void attributeRemoved(HttpSessionBindingEvent arg0) {
		logger.debug("attributeRemovedsession:"+arg0.getName()+"--"+arg0.getValue());
		SessionManage.removeSession(arg0.getSession(),arg0.getName());
	}

	public void attributeReplaced(HttpSessionBindingEvent arg0) {
		logger.debug("attributeReplaced:"+arg0.getName()+"--"+arg0.getValue());
		//SessionManage.updateSession(arg0.getSession(),arg0.getName(), arg0.getValue());
	}

	public void sessionCreated(HttpSessionEvent se) {
		
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		
	}

	public void valueBound(HttpSessionBindingEvent event) {
		
	}

	public void valueUnbound(HttpSessionBindingEvent event) {
		
	}

}
