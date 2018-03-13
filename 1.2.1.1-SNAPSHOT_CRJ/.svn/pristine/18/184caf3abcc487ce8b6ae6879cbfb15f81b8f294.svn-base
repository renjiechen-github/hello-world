package pccom.common.util;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;

//form name-value notgetParemter
public class RequestHelper {

	public String getValue(HttpServletRequest request, String paramName) {
		String obj = request.getParameter(paramName);
		if (obj == null) {
			obj = "";
		}
		return obj.trim();
	}

	public String[] getValues(HttpServletRequest request, String paramName) {
		String[] value;
		value = request.getParameterValues(paramName);
		if (value == null)
			value = new String[] {};
		return value;
	}

	public String getValuesString(HttpServletRequest request, String paramName) {
		return getValuesString(request, paramName, ",");
	}

	public String getValuesString(HttpServletRequest request, String paramName,
			String delims) {
		String temp = "";
		String[] values = request.getParameterValues(paramName);
		if (values == null) {
			return "";
		} else {
			for (int i = 0; i < values.length; i++) {
				if (i == values.length - 1) {
					temp += values[i].trim();
				} else {
					temp += values[i].trim() + delims;
				}
			}
		}
		return temp;
	}

	public String getAjaxValue(HttpServletRequest request, String paramName) {
		String obj = request.getParameter(paramName);
		if (obj == null) {
			obj = "";
		}
		try {
			obj = java.net.URLDecoder.decode(obj, "UTF-8");
		} catch (Exception e) {
			obj = "";
		}
		return obj.trim();
	}

	public String[] getAjaxValues(HttpServletRequest request, String paramName) {
		String[] value, newValue;
		String tmp = "";
		value = request.getParameterValues(paramName);
		if (value == null)
			value = new String[] {};
		newValue = value;
		if (value.length > 0) {
			newValue = new String[value.length];

			try {
				for (int i = 0; i < value.length; i++) {
					tmp = value[i];
					tmp = java.net.URLDecoder.decode(tmp, "UTF-8");
					newValue[i] = tmp;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		return newValue;
	}
	
	public String getAjaxValuesString(HttpServletRequest request, String paramName) {
		return getAjaxValuesString(request, paramName, ",");
	}
	
	public String getAjaxValuesString(HttpServletRequest request, String paramName,
			String delims) {
		String temp = "";
		String[] values = getAjaxValues(request, paramName);
		if (values == null) {
			return null;
		} else {
			for (int i = 0; i < values.length; i++) {
				if (i == values.length - 1) {
					temp += values[i].trim();
				} else {
					temp += values[i].trim() + delims;
				}
			}
		}
		return temp;
	}
	
	/**
	 * @description: ��ȡϵͳ��Ŀ¼��ַ
	 * @author ���� 2012-12-27
	 * @param request
	 * @return
	 */
	public String getRealPath(HttpServletRequest request)
	{
	    return request.getSession().getServletContext().getRealPath("/");
	}
	/**
	 * ��ȡ��Ŀ¼����ʵ·��
	 * @param request
	 * @return
	 */
	public String getWebRootRealPath()
	{
		String rootPath = this.getClass().getResource("").getPath();
		rootPath = rootPath.substring(0, rootPath.indexOf("WEB-INF")-1);
		
		return rootPath;
	}
}
