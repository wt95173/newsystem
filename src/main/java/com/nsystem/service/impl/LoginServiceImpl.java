package com.nsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nsystem.mapper.LoginInformationMapper;
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

    @Override
    public LoginreturnVo findByName(LoginVo loginVo) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("relative_id",loginVo.getUsername());
        LoginreturnVo loginreturnVo=new LoginreturnVo();
        if(loginInformationMapper.selectOne(wrapper).getPassword().equals(loginVo.getPassword())){
            loginreturnVo.setState(1);
            loginreturnVo.setUrl("ddd");
            return loginreturnVo;
        }
        else{
            loginreturnVo.setState(0);
            return loginreturnVo;
        }
    }
}
