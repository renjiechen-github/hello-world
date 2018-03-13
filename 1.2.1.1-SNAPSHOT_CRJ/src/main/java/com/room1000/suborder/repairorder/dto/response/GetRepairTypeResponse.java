package com.room1000.suborder.repairorder.dto.response;

import java.util.List;

import com.room1000.core.model.ResponseModel;
import com.room1000.suborder.repairorder.dto.RepairTypeDto;

/**
 * 
 * Description: 
 *  
 * Created on 2017年6月1日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class GetRepairTypeResponse extends ResponseModel {

    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * repairTypeList
     */
    List<RepairTypeDto> repairTypeList;

    public List<RepairTypeDto> getRepairTypeList() {
        return repairTypeList;
    }

    public void setRepairTypeList(List<RepairTypeDto> repairTypeList) {
        this.repairTypeList = repairTypeList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GetRepairTypeResponse [repairTypeList=");
        builder.append(repairTypeList);
        builder.append("]");
        return builder.toString();
    }
    

}
