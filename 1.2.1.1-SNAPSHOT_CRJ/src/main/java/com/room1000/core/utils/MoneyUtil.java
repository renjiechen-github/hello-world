package com.room1000.core.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * Description: 
 *  
 * Created on 2017年5月26日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public final class MoneyUtil {
    
    /**
     * 构造函数
     */
    private MoneyUtil() {
    }

    /**
     * 
     * Description: 把不带精度的String型转为带精度的String型
     * 如"12345"转为"123.45"
     * 
     * @author jinyanan
     *
     * @param withoutDecimalMoney withoutDecimalMoney
     * @return String
     */
    public static String withoutDecimalStrMoney2DecimalStr(String withoutDecimalMoney) {
        if (StringUtils.isEmpty(withoutDecimalMoney)) {
            return null;
        }
        Long longMoney = Long.valueOf(withoutDecimalMoney);
        Double doubleMoney = (double) (longMoney / 100);
        return doubleMoney.toString();
    }
    
    /**
     * 
     * Description: 把带精度的String型转为不带精度的String型
     * 如"123.45"转为"12345"
     * 
     * @author jinyanan
     *
     * @param decimalMoney decimalMoney
     * @return String
     */
    public static String decimalStrMoney2WithoutDecimalStr(String decimalMoney) {
        if (StringUtils.isEmpty(decimalMoney)) {
            return null;
        }
        
        Double doubleMoney = Double.valueOf(decimalMoney);
        Long longMoney = (long) (doubleMoney * 100);
        return longMoney.toString();
    }
    
    /**
     * 
     * Description: 把不带精度的String型转为Long型
     * 如"12345"转为12345
     * 
     * @author jinyanan
     *
     * @param withoutDecimalMoney withoutDecimalMoney
     * @return Long
     */
    public static Long withoutDecimalStrMoney2Long(String withoutDecimalMoney) {
        if (StringUtils.isEmpty(withoutDecimalMoney)) {
            return null;
        }
        return Long.valueOf(withoutDecimalMoney);
    }
}
