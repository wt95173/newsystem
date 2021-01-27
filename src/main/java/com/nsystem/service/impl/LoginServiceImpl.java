package com.nsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nsystem.mapper.LoginInformationMapper;
import com.nsystem.mapper.UserMapper;
import com.nsystem.service.LoginService;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.LoginreturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginInformationMapper loginInformationMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginreturnVo findByName(LoginVo loginVo) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("relative_id",loginVo.getUsername());
        LoginreturnVo loginreturnVo=new LoginreturnVo();
        if(loginInformationMapper.selectOne(wrapper).getPassword().equals(loginVo.getPassword())){
            loginreturnVo.setState(1);
            switch(userMapper.selectOne(wrapper).getRole()){
                case 0:
                    loginreturnVo.setUrl("0");
                    break;
                case 1:
                    loginreturnVo.setUrl("1");
                    break;
                case 2:
                    loginreturnVo.setUrl("2");
                    break;
            }
        }
        else{
            loginreturnVo.setState(0);
        }
        return loginreturnVo;
    }
}
