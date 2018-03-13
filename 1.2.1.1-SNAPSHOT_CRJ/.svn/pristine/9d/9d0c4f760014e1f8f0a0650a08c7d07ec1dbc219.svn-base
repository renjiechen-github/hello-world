/**
 * date: 2017年5月18日
 */
package com.ycdc.dingding.service;

import java.util.List;
import java.util.Map;

import com.ycdc.dingding.model.entity.*;

/**
 * @name: ILockService.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月18日
 */
public interface ILockService {
	/**
	 * 添加公寓
	 * @param home 公寓信息
	 * @return 0:成功 非0：失败
	 * @Description: 添加一个公寓，房间在添加完公寓之后添加到公寓下面。网关添加到公寓下面。门锁添加到房间下面，并且绑定到公寓下面的某一个网关。saas 设定初始状态为未安装，待分配。
	 * 注意:home_id 是由第三方自己生成传入的，请调用方自行确保其所有公寓的 home_id 全局唯一。
	 */
	public int addHome(Home home);
	/**
	 * 更新公寓信息
	 * @param homeInfo 更新的公寓信息
	 * @return 0:成功 非0：失败
	 * @Description: 第三方刚添加公寓时，sp_state 是未安装(值为 1)。丁盯门锁安装完成后，第三方验收通过，需要设置公寓状态为可管理(值为 2)。
	 */
	public int updateHome(HomeUpdateInfo homeInfo);
	/**
	 * 删除一个公寓
	 * @param homeId 公寓 id
	 * @return 0:成功 非0：失败
	 * @Description: 如果有设备绑定，则直接删除网关即可。
	 */
	public int deleteHome(String homeId);
	/**
	 * 查询单个房屋的状态
	 * @param homeId 公寓 id
	 * @return 公寓状态信息
	 * @Description: 
	 */
	public HomeState findHomeState(String homeId);
	/**
	 * 查看多个公寓状态
	 * @param homeIds 多个公寓 id组成的数组
	 * @return 多个公寓的状态，Map的key为公寓id，value为公寓 id对应的公寓的状态
	 * @Description: 
	 */
	public Map<String, HomeState> findHomeStates(List<String> homeIds);
	/**
	 * 查询单个房屋的设备列表
	 * @param homeId 公寓 id
	 * @return 公寓的所有设备信息
	 * @Description: 
	 */
	public List<HomeDevice> findHomeDevice(String homeId);
	/**
	 * 查看多个公寓的设备列表
	 * @param homeIds 多个公寓 id 组成的数组
	 * @return 多个公寓的所有设备，Map的key为公寓id，value为公寓 id对应的公寓的状态
	 * @Description: 
	 */
	public Map<String, List<HomeDevice>> findHomeDevices(List<String> homeIds);
	/**
	 * 给指定的公寓添加房间
	 * @param room 房间信息
	 * @return 0:成功 非0：失败
	 * @Description: 
	 */
	public int addRoom(Room room);
	/**
	 * 更新房间信息
	 * @param roomInfo 房间更新信息
	 * @return 0:成功 非0：失败
	 * @Description: 
	 */
	public int updateRoom(RoomUpdateInfo roomInfo);
	/**
	 * 删除一个房间
	 * @param homeId 公寓 id
	 * @param roomId 房间 id
	 * @return 0:成功 非0：失败
	 * @Description: 如果下房间绑定了门锁，同时会删除门锁。
	 */
	public int deleteRoom(String homeId, String roomId);
	/**
	 * 根据一些条件，获取相关的公寓和设备信息
	 * @param condition 条件
	 * @return 公寓和设备列表
	 * @Description: 不传参数返回所有
	 */
	public List<SearchHomeInfoResult> searchHomeInfo(SearchHomeCondition condition);
	/**
	 * 获取查询公寓结果的总数
	 * @param condition 条件
	 * @return 获取到的公寓总数
	 * @Description:
	 */
	public int getHomeCount(HomeCountCondition condition);
	/**
	 * 获取网关信息
	 * @param homeId 公寓 id
	 * @param uuid 设备uuid
	 * @return
	 * @Description: home_id 与 uuid 至少有一个，且优先取 uuid
	 */
	public HomeCenterInfo getCenterInfo(String homeId, String uuid);
	/**
	 * 获取公寓的所有网关信息
	 * @param homeId 公寓 id
	 * @return 公寓的网关列表
	 * @Description:
	 */
	public List<HomeCenterInfo> getCentersInfo(String homeId);
	/**
	 * 获取门锁信息
	 * @param homeId 公寓 id
	 * @param roomId 房间 id，如果第三方的 room_id 是全局唯一，可以不传 home_id
	 * @param uuid 设备uuid，room_id 与 uuid 至少有一个，且优先取 uuid
	 * @return 门锁信息
	 * @Description: 只传 home_id 时获取外门锁设备信息，此时相当于获取room_id为home_id的公共房间门锁即外门锁。 
	 */
	public LockInfo getLockInfo(String homeId, String roomId, String uuid);
	/**
	 * 获取门锁密码列表。
	 * @param homeId 公寓 id
	 * @param roomId 房间 id
	 * @param uuid 设备uuid
	 * @return 密码列表
	 * @Description: 管理员密码的 password id 固定为 999。
	 */
	public List<LockPasswordInfo> fetchPassword(String homeId, String roomId, String uuid);
	/**
	 * 获取管理员密码明文。
	 * @param homeId 公寓 id
	 * @param roomId 房间 id
	 * @param uuid 设备uuid room_id 与 uuid 至少有一个，且优先取 uuid
	 * @return 明文密码
	 * @Description: 只传 home_id 时获取外门锁设备信息，此时相当于获取room_id为home_id的公共房间门锁即外门锁。
	 */
	public String getDefaultPasswordPlaintext(String homeId, String roomId, String uuid);
	/**
	 * 获取动态密码明文。
	 * @param homeId 公寓 id
	 * @param roomId 房间 id
	 * @param uuid 设备uuid room_id 与 uuid 至少有一个，且优先取 uuid
	 * @return 明文密码
	 * @Description: 只传 home_id 时获取外门锁设备信息，此时相当于获取room_id为home_id的公共房间门锁即外门锁。
	 */
	public DynamicPasswordInfo getDynamicPasswordPlaintext(String homeId, String roomId, String uuid);
	/**
	 * 给房间添加密码
	 * @param password 密码信息
	 * @return 密码 id，密码 id 在此设备中唯一
	 * @Description: 新设备添加完成后，如果没有管理员密码，必须先调用该接口设置管理员密码(is_default 为 1)
	 */
	public int addPassword(Password password);
	/**
	 * 修改密码
	 * @param passwordInfo 密码更新信息
	 * @return 0:成功 非0：失败
	 * @Description: 只有有效期、名称或描述可更改。
	 */
	public int updatePassword(PasswordUpdateInfo passwordInfo);
	/**
	 * 删除密码。
	 * @param homeId 公寓 id，如果第三方的 room_id 是全局唯一， 可以不传 home_id
	 * @param roomId 房间 id
	 * @param uuid 设备uuid，room_id 与 uuid 至少有一个，且优先取 uuid
	 * @param passwordId 密码 id
	 * @return 0:成功 非0：失败
	 * @Description: 只传 home_id 时删除外门锁密码
	 */
	public int deletePassword(String homeId, String roomId, String uuid, int passwordId);
	/**
	 * 冻结密码。
	 * @param homeId 公寓 id，如果第三方的 room_id 是全局唯一， 可以不传 home_id
	 * @param roomId 房间 id
	 * @param uuid 设备uuid，room_id 与 uuid 至少有一个，且优先取 uuid
	 * @param passwordId 密码 id
	 * @return 0:成功 非0：失败
	 * @Description: 只传 home_id 时冻结外门锁密码
	 */
	public int frozenPassword(String homeId, String roomId, String uuid, int passwordId);
	/**
	 * 解冻结密码。
	 * @param homeId 公寓 id，如果第三方的 room_id 是全局唯一， 可以不传 home_id
	 * @param roomId 房间 id
	 * @param uuid 设备uuid，room_id 与 uuid 至少有一个，且优先取 uuid
	 * @param passwordId 密码 id
	 * @return 0:成功 非0：失败
	 * @Description: 只传 home_id 时解冻结外门锁密码
	 */
	public int unfrozenPassword(String homeId, String roomId, String uuid, int passwordId);
	/**
	 * 查询开门历史记录
	 * @param condition 查询条件
	 * @return 开门事件列表
	 * @Description: 如果第三方的 room_id 是全局唯一，可以不传 home_id，只传 home_id 时获取外门锁记录，获取内门锁时，room_id 与 uuid 至少有一个，且优先取 uuid
	 */
	public List<LockOpenEventInfo> getLockEvents(LockEventsCondition condition);
	/**
	 * 查询开门历史记录数量。
	 * @param condition 查询条件
	 * @return 开门历史记录数量
	 * @Description: 如果第三方的 room_id 是全局唯一，可以不传 home_id，只传 home_id 时获取外门锁记录，获取内门锁时，room_id 与 uuid 至少有一个，且优先取 uuid
	 */
	public int getLockEventsCount(LockOpenCountCondition condition);
	/**
	 * 下发激活码。
	 * @param passwordInfo 密码信息
	 * @return 激活码id、激活码和密码
	 * @Description: 激活码下发不是异步的，可以立刻获取到。
	 * 1:绑定门锁时，不与网关进行绑定，即无网关门锁，可下发激活码，在门锁本地进行密码激活。激活后，密码生效，后台无法冻结、解冻密码，在有效期内，也无法删除密码。但是可以通过修改 激活码去修改密码。
	 * 2:绑定门锁时，与网关进行绑定，门锁版本支持的情况下，可下发激活码，在门锁本地进行密码激 活，但不建议使用，除非网关离线、正常下发密码无法开门等特殊情况。激活码下发后在门锁上激 活，密码生效，网关正常在线，可冻结、解冻、删除、修改，此时跟下发的密码并无太大区别。
	 */
	public ActivationPasswordResult activationPasswordWithoutCenter(ActivationPassword passwordInfo);
	/**
	 * 修改激活码。
	 * @param passwordInfo 修改信息
	 * @return 激活码id、激活码和密码
	 * @Description: 激活码修改不是异步的，可以立刻获取到。
	 */
	public ActivationPasswordResult updateActivationCodeWithoutCenter(ActivationPassword passwordInfo);
	/**
	 * 查询设备操作记录
	 * @param condition 查询条件
	 * @return 操作列表
	 * @Description: 查询条件包括操作人、操作类型、以及操作时间段，支持单个条件或多条件联合查询。
	 */
	public List<LockOperation> searchDeviceOpLog(SearchDeviceOpCondition condition);
	/**
	 * 查询设备操作记录数量。
	 * @param condition 查询条件
	 * @return 操作数量
	 * @Description:
	 */
	public int getDeviceOpLogCount(DeviceOpCountCondition condition);
	/**
	 * 添加一个工单
	 * @param ticket 工单信息
	 * @return 工单 id
	 * @Description:
	 */
	public String addTicket(Ticket ticket);
	/**
	 * 修改工单信息
	 * @param ticketInfo 工单修改信息
	 * @return 0:成功 非0：失败
	 * @Description: 只有待分配的工单可以修改
	 */
	public int updateTicket(TicketUpdateInfo ticketInfo);
	/**
	 * 删除工单
	 * @param ticketId 工单 id
	 * @return 0:成功 非0：失败
	 * @Description: 
	 */
	public int deleteTicket(String ticketId);
	/**
	 * 获取一条工单的信息
	 * @param ticketId 工单 id
	 * @return 工单详细信息
	 * @Description: 
	 */
	public TicketDetailInfo getTicketById(String ticketId);
	/**
	 * 获取公寓的所有工单信息
	 * @param homeId 公寓 id
	 * @param serviceType 工单类型，工单类型: 预约安装:1 售后维修:2
	 * @return 工单列表
	 * @Description:
	 */
	public List<TicketDetailInfo> getTicketByHome(String homeId, int serviceType);
	/**
	 * 修改工单状态
	 * @param ticketId 工单 id
	 * @param ticketState 工单状态， 工单状态:1: 待处理。2:待分配。3:已分配。4:已完成。5:已 关闭
	 * @return 0:成功 非0：失败
	 * @Description:
	 */
	public int updateTicketState(String ticketId, int ticketState);
	
}
