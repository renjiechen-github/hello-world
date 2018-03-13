package com.room1000.workorder.dto.response;

import java.util.List;

import com.room1000.core.model.ResponseModel;
import com.room1000.workorder.dto.TypeCountDto;

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
public class GetTypeCountResponse extends ResponseModel {

    /**
     * serialVersionUID 
     */
    private static final long serialVersionUID = 1L;
    /**
     * typeCountList
     */
    List<TypeCountDto> typeCountList;

    public List<TypeCountDto> getTypeCountList() {
        return typeCountList;
    }

    public void setTypeCountList(List<TypeCountDto> typeCountList) {
        this.typeCountList = typeCountList;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GetTypeCountResponse [typeCountList=");
        builder.append(typeCountList);
        builder.append("]");
        return builder.toString();
    }
    
    
}
