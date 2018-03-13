/**
 * 
 */
package pccom.web.mobile.service.rankagreement;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import pccom.web.server.BaseService;

/**
 * 租赁合约
 * @author suntf
 * @date 2016年9月10日
 */
@Service("miRankAgreementService")
public class MiRankAgreementService extends BaseService
{
	/**
	 * 获取合约列表信息
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年9月10日
	 */
	public List<?> getRankAgreementList(HttpServletRequest request)
	{
		String status = req.getValue(request, "status"); // 合约状态 
		String keyWord = req.getAjaxValue(request, "keyword"); // 关键字
		String accountid = req.getValue(request, "accountid"); // 合约管家
		String arear = req.getValue(request, "arear"); // 区域
		String houseId = req.getValue(request, "houseId"); // 租赁房源id
		String newStatus = req.getValue(request, "newStatus"); // 是否到期
		String isSelf = req.getValue(request, "isSelf"); // 查看自己
		String trading = req.getValue(request, "trading"); // 查看商圈
		String agreeId = req.getValue(request, "agreeId"); // 合约Id
		logger.debug(trading);
		List<String> param = new ArrayList<String>();
		String sql = getSql("basehouse.rankHosue.main2");
		if(!"".equals(status))
		{
			sql += getSql("basehouse.rankHosue.rankAgreementStatu");
			param.add(status);
		}
		
		if(!"".equals(agreeId))
		{
			sql += getSql("basehouse.rankHosue.agreeId");
			param.add(agreeId);
		}
		
		if(!"".equals(keyWord))
		{
			sql +=  getSql("basehouse.rankHosue.keyWord");
			param.add("%"+keyWord+"%");
			param.add("%"+keyWord+"%");
			param.add("%"+keyWord+"%");
			param.add("%"+keyWord+"%");
			param.add("%"+keyWord+"%");
		}
		if("12".equals(newStatus))
		{
			// 合约到期
			sql += getSql("basehouse.rankHosue.isdq");
		}
		else if("11".equals(newStatus))
		{	
			// 催租
			sql += getSql("basehouse.rankHosue.cz");
		}
		if("1".equals(isSelf))
		{
			// 自己
			sql += getSql("basehouse.rankHosue.isself");
			param.add(this.getUser(request).getId());
		}
		if(!"".equals(trading))
		{
			// 自己
			sql += getSql("basehouse.rankHosue.trading");
			param.add(trading);
		}
		if(!"".equals(accountid))
		{
			sql += getSql("basehouse.rankHosue.angentId");
			param.add(accountid);
		}
		if(!"".equals(arear))
		{
			sql += getSql("basehouse.rankHosue.arearId");
			param.add(arear);
		}
		if(!"".equals(houseId))
		{
			sql += getSql("basehouse.rankHosue.houseId");
			param.add(houseId);
		}
		sql += getSql("basehouse.rankHosue.orderby");
		return this.getMobileList(request, sql, param);
	}
}
