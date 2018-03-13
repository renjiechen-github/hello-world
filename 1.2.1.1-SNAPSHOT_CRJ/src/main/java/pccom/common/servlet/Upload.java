package pccom.common.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;

import pccom.common.SQLconfig;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.SpringHelper;

public class Upload extends HttpServlet {
	public final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	/**
	 * Constructor of the object.
	 */
	public Upload() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			DiskFileItemFactory factory = new DiskFileItemFactory();
			String path = request.getRealPath("/upload/");
			String tmpPath = request.getRealPath("/upload/tmp/");
			logger.debug(tmpPath);
			factory.setRepository(new File(tmpPath));//设置setSizeThreshold方法中提到的临时文件的存放目录
			factory.setSizeThreshold(1024);//用于设置是否使用临时文件保存解析出的数据的那个临界值
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = (List) upload.parseRequest(request);
			String operId = "";
			String name = "";
			for (FileItem item : items) {
				if (item.isFormField()) {// 一般
					name = item.getFieldName();
					String value = java.net.URLDecoder.decode(item.getString("UTF-8"), "UTF-8");
					logger.debug("value:"+value);
					if(name.equals("path")) {
						path = request.getRealPath("/" + value);
					}
				} else {// Field
					String clientType=request.getParameter("clientType");
					String uploaddir=request.getParameter("dir");
					operId = request.getParameter("operId");
					String uploadAddress="";
					String contentType = item.getContentType();
//					logger.debug("clientType:" + clientType);
					if(clientType!=null&&clientType.equals("IOS"))
					{
					    uploaddir=request.getParameter("fileSavePath");
						uploadAddress="/"+uploaddir+"/";	//在Linux系统下的路径用“/”
						path = request.getRealPath("/");
					}
					long size = item.getSize();
					String name_ = item.getName();
					String value = java.net.URLDecoder.decode(item.getName(), "UTF-8");
					int start = value.lastIndexOf("\\");
					String filename = value.substring(start + 1);
					logger.debug("path:"+path+uploadAddress+"/"+filename);
					item.write(new File(path+uploadAddress, filename));
					DBHelperSpring db  = (DBHelperSpring) SpringHelper.getBean("dbHelper");
					String sql = SQLconfig.getSql("systemconfig.insertfilelog");
					db.update(sql,new Object[]{name_,contentType,name,"",size,path,request.getRemoteAddr()});
				}
			}
			response.getWriter().println("successed");
		} catch (Exception ex) {
			ex.printStackTrace();
			response.getWriter().println("false");
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		// Put your code here
	}
}
