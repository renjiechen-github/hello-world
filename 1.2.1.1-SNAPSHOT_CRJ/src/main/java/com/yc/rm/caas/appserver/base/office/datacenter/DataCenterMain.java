/**
 * 
 */
package com.yc.rm.caas.appserver.base.office.datacenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yc.rm.caas.appserver.base.office.ExcelUtil;
import com.yc.rm.caas.appserver.base.office.MailBean;
import com.yc.rm.caas.appserver.base.office.MailUtil;
import com.yc.rm.caas.appserver.base.office.datacenter.entity.DataRptCfgBean;
import com.yc.rm.caas.appserver.base.office.datacenter.serv.IDataCenterServ;

/**
 * @author stephen
 * 
 */
@Component("dataCenterMain")
public class DataCenterMain {
	public static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(DataCenterMain.class);
	private static IDataCenterServ dataCenterServ;

	// private static IDataCenterServ dataCenterServ = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		makeRpt();
	}

	public static void makeRpt() {
		// 获取当前时间点需要生成并发送mail的报表
		List<DataRptCfgBean> list = dataCenterServ.getRptCfgList();
		System.out.println(list == null);
		for (int i = 0; i < list.size(); i++) {
			DataRptCfgBean cfgBean = list.get(i);
			try {
				List<Map<String, Object>> rpt = dataCenterServ.getRptInfo(
						cfgBean.getId(), cfgBean.getTeamid());
				// System.out.println(cfgBean.toString());
				// 生成excel存放到固定目录
				String filePath = ExcelUtil.makeExcel(
						String.valueOf(cfgBean.getTeamid())
								+ String.valueOf(cfgBean.getId()),
						cfgBean.getName(), rpt);
				System.out.println(filePath);
				// 发送email给指定人
				MailBean mailBean = new MailBean();
				mailBean.setFilePath(filePath);
				mailBean.setTitle(cfgBean.getName());
				List<String> recAccount = new ArrayList<String>();
				for (int j = 0; j < cfgBean.getMailTo().split("\\;").length; j++) {
					recAccount.add(cfgBean.getMailTo().split("\\;")[j]);
				}
				mailBean.setRecAccount(recAccount);
				MailUtil.sendMail(mailBean);
			} catch (Exception e) {
				logger.error("执行:" + cfgBean.getId() + ":" + cfgBean.getName()
						+ "生成报表失败");
				e.printStackTrace();
			}
		}
	}

	@Autowired
	public void setDataCenterServ(IDataCenterServ _dataCenterServImpl) {
		DataCenterMain.dataCenterServ = _dataCenterServImpl;
	}

}
