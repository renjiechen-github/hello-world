package com.room1000.attr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room1000.attr.dao.AttrCatgDtoMapper;
import com.room1000.attr.dao.AttrDtoMapper;
import com.room1000.attr.dao.AttrValueDtoMapper;
import com.room1000.attr.dto.AttrCatgDto;
import com.room1000.attr.dto.AttrDto;
import com.room1000.attr.dto.AttrValueDto;
import com.room1000.attr.service.IAttrService;

/**
 * 
 * Description: 
 *  
 * Created on 2017年2月7日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service("AttrService")
public class AttrServiceImpl implements IAttrService {
    
    /**
     * attrDtoMapper
     */
    @Autowired
    private AttrDtoMapper attrDtoMapper;
    
    /**
     * attrCatgDtoMapper
     */
    @Autowired
    private AttrCatgDtoMapper attrCatgDtoMapper;
    
    /**
     * attrValueDtoMapper
     */
    @Autowired
    private AttrValueDtoMapper attrValueDtoMapper;

    @Override
    public AttrDto getAttrById(Long attrId) {
        return attrDtoMapper.selectByPrimaryKey(attrId);
    }

    @Override
    public AttrCatgDto getSingleAttrCatgDtoById(Long catgId) {
        return attrCatgDtoMapper.selectByPrimaryKey(catgId);
    }

    @Override
    public AttrCatgDto getAttrCatgIncludeChildrenById(Long catgId) {
        return attrCatgDtoMapper.selectAllChildrenById(catgId);
    }

    @Override
    public List<AttrValueDto> getAttrValueDtoListByAttrId(Long attrId) {
        return attrValueDtoMapper.selectByAttrId(attrId);
    }

    @Override
    public AttrDto getAttrByCode(String attrCode) {
        return attrDtoMapper.selectByCode(attrCode);
    }

    @Override
    public AttrCatgDto getSingleAttrCatgDtoByCode(String catgCode) {
        return attrCatgDtoMapper.selectByCode(catgCode);
    }

    @Override
    public AttrCatgDto getAttrCatgIncludeChildrenByCode(String catgCode) {
        AttrCatgDto attrCatg = this.getSingleAttrCatgDtoByCode(catgCode);
        return this.getAttrCatgIncludeChildrenById(attrCatg.getId());
    }

    @Override
    public List<AttrValueDto> getAttrValueDtoListByAttrCode(String attrCode) {
        AttrDto attr = this.getAttrByCode(attrCode);
        return this.getAttrValueDtoListByAttrId(attr.getId());
    }

}
