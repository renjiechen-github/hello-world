package com.room1000.suborder.repairorder.define;

/**
 * 
 * Description: 维修类型
 *  
 * Created on 2017年6月1日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public final class RepairTypeDef {

    /**
     * 构造函数
     */
    private RepairTypeDef() {
    }

    /**
     * 装修问题
     */
    public static final String DECORATION_PROBLEM = "A";

    /**
     * 家具问题
     */
    public static final String FURNITURE_PROBLEM = "B";

    /**
     * 家电问题
     */
    public static final String APPLIANCE_PROBLEM = "C";

    /**
     * 堵塞问题
     */
    public static final String BLOCKING_PROBLEM = "D";

    /**
     * 门锁问题
     */
    public static final String DOOR_LOCK_PROBLEM = "E";

    /**
     * 漏水问题
     */
    public static final String LEAKAGE_PROBLEM = "F";
}
