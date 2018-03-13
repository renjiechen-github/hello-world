/**
 * date: 2017年5月19日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: HomeState.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月19日
 */
public class HomeState {
	/**
	 * 1:未安装 2:可管理
	 */
	private int sp_state;
	/**
	 * 3:已分配安装 4:未分配安装 5:已完成安装
	 */
	private int install_state;
	public int getSp_state() {
		return sp_state;
	}
	public void setSp_state(int sp_state) {
		this.sp_state = sp_state;
	}
	public int getInstall_state() {
		return install_state;
	}
	public void setInstall_state(int install_state) {
		this.install_state = install_state;
	}
	
}
