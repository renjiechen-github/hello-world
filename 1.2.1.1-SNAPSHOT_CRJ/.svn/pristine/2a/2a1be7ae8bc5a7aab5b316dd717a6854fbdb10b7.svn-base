/**
 * date: 2017年4月7日
 */
package com.ycdc.huawei.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ycdc.huawei.model.entity.CallEventInfo;
import com.ycdc.huawei.model.entity.VirBindEntity;

/**
 * @name: IHuaWeiDao.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月7日
 */
@Repository
public interface IHuaWeiDao {
	/**
	 * 向数据库插入绑定记录
	 * @param bindEntity
	 * @Description:
	 */
	void bindVirtual(VirBindEntity bindEntity);
	/**
	 * 获取绑定关系id
	 * @param virtualNumber 虚拟号码
	 * @param aParty A方号码
	 * @param bParty B方号码
	 * @return 绑定关系id
	 * @Description:
	 */
	String getSubscriptionId(@Param(value = "virtualNumber") String virtualNumber, @Param(value = "aParty") String aParty, @Param(value = "bParty") String bParty);
	
	/**
	 * 解绑虚拟号码绑定关系
	 * @param subscriptionId 绑定关系id
	 * @param operId 操作人id
	 * @Description:
	 */
	void unbindVirtual(@Param(value = "subscriptionId") String subscriptionId, @Param(value="operId") String operId, @Param(value = "createTime") String createTime);
	void deleteBindVirtual(@Param(value = "subscriptionId") String subscriptionId);
	/**
	 * 新增拨号事件
	 * @param callEvent 拨号事件
	 * @Description: 
	 */
	void createCalledEvent(CallEventInfo callEvent);
	
	/**
	 * 更改虚拟号码使用状态
	 * @param status 使用状态
	 * @param virtualNumber 虚拟号码
	 * @Description:
	 */
	void updateVirtualNumberStatus(@Param(value = "status") int status, @Param(value = "virtualNumber") String virtualNumber);
	
	/**
	 * 转绑号码时更新绑定信息
	 * @param subscriptionId
	 * @param bPartyNew
	 * @param operId
	 * @Description:
	 */
	void modifyBind(@Param(value = "subscriptionId") String subscriptionId, @Param(value = "bPartyNew") String bPartyNew);
	void insertBindRecord(@Param(value = "subscriptionId") String subscriptionId, @Param(value = "bPartyNew") String bPartyNew, @Param(value="operId") String operId,  @Param(value = "createTime") String createTime);
	/**
	 * 下载文件路径记录
	 * @param subscriptionId 绑定关系id
	 * @param callIdentifier 呼叫唯一标示(有限按此字段匹配)
	 * @param operId 操作人id
	 * @param StartTime 通话开始时间
	 * @Description:
	 */
	void addRecordFilePath(@Param(value = "subscriptionId") String subscriptionId, @Param(value = "callIdentifier") String callIdentifier, @Param(value="startTime") String startTime, @Param(value="path") String path, @Param(value="operId") String operId);
}
