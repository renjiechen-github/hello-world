/**
 * 
 */
package pccom.web.mobile.service.houseagreement;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import pccom.web.server.BaseService;

/**
 * 收房合约管理
 * @author suntf
 * @date 2016年9月10日
 */
@Service("miHouseAgreementService")
public class MiHouseAgreementService extends BaseService
{
	/**
	 * 获取收房合约列表
	 * @param request
	 * @return
	 * @author suntf
	 * @date 2016年9月10日
	 */
	public List<?> getHouseAgreementList(HttpServletRequest request)
	{
		String status = req.getValue(request, "status"); // 合约状态 
		String keyWord = req.getAjaxValue(request, "keyword"); // 关键字 
		String arear = req.getValue(request, "arear"); // 区域
		String accountid = req.getValue(request, "accountid"); // 管家
		List<String> param = new ArrayList<String>();
		String sql = getSql("basehouse.agreement.agreementList");
		if(!"".equals(status))
		{
			sql += getSql("basehouse.agreement.condition_status");
			param.add(status);
		}
		if(!"".equals(arear))
		{
			sql += getSql("basehouse.agreement.condition_arear");
			param.add(arear);
		}
		if(!"".equals(accountid))
		{
			sql += getSql("basehouse.agreement.condition_account");
			param.add(accountid);
		}
		if(!"".equals(keyWord))
		{
			sql +=  getSql("basehouse.agreement.condition_keyWord");
			param.add("%"+keyWord+"%");
			param.add("%"+keyWord+"%");
			param.add("%"+keyWord+"%");
			param.add("%"+keyWord+"%");
			param.add("%"+keyWord+"%");
		}
		sql += getSql("basehouse.agreement.orderBy");
//		logger.debug(str.getSql(sql, param.toArray())); 
		return this.getMobileList(request, sql, param);
	}
	
	
	/**
	 * 获取银行列表信息
	 * @param request
	 * @param response
	 * @author suntf
	 * @date 2016年9月1日
	 */
	public List<?> getBankList(HttpServletRequest request)
	{
		String bank_name = req.getAjaxValue(request, "group_name"); // 银行名称
		String bankId = req.getValue(request, "bankId"); // 银行大类
		String sql = getSql("basehouse.agreement.bank_list");
		List<String> list = new ArrayList<>();
		if(!"".equals(bank_name))
		{
			sql += getSql("basehouse.agreement.bank_name");
			list.add("%"+bank_name+"%");
		}
		if(!"".equals(bankId))
		{
			sql += getSql("basehouse.agreement.father_id");
			list.add(bankId);
		}
		return this.getMobileList(request, sql, list);
	}
}
