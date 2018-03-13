package com.room1000.core.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.room1000.core.user.dao.StaffDtoMapper;
import com.room1000.core.user.dao.UserDtoMapper;
import com.room1000.core.user.dto.StaffDto;
import com.room1000.core.user.dto.UserDto;
import com.room1000.core.user.service.IUserService;

/**
 * 
 * Description: 
 *  
 * Created on 2017年3月17日 
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
@Service("UserService")
public class UserServiceImpl implements IUserService {
    
    /**
     * staffDtoMapper
     */
    @Autowired
    private StaffDtoMapper staffDtoMapper;
    
    /**
     * userDtoMapper
     */
    @Autowired
    private UserDtoMapper userDtoMapper;

    @Override
    public StaffDto qryStaffByName(String name) {
        return staffDtoMapper.selectByName(name);
    }

    @Override
    public List<StaffDto> qryStaffByCond(StaffDto staffDto) {
        return staffDtoMapper.selectByCond(staffDto);
    }

    @Override
    public List<UserDto> qryUserByCond(UserDto userDto) {
        return userDtoMapper.selectByCond(userDto);
    }
}
