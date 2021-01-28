package com.nsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nsystem.entity.LoginInformation;
import com.nsystem.mapper.LoginInformationMapper;
import com.nsystem.mapper.UserMapper;
import com.nsystem.service.LoginService;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.LoginreturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginInformationMapper loginInformationMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginreturnVo findByName(LoginVo loginVo, HttpSession session) {
        QueryWrapper wrapper=new QueryWrapper();
        wrapper.eq("relative_id",loginVo.getUsername());
        LoginreturnVo loginreturnVo=new LoginreturnVo();
        if(loginInformationMapper.selectOne(wrapper)!=null){
            if(loginInformationMapper.selectOne(wrapper).getPassword().equals(loginVo.getPassword())){
                loginreturnVo.setState(1);
                LoginInformation loginInformation =loginInformationMapper.selectOne(wrapper);
                switch(userMapper.selectOne(wrapper).getRole()){
                    case 0:
                        session.setAttribute("student",loginInformation);
                        loginreturnVo.setUrl("/html/student/home.html");
                        break;
                    case 1:
                        session.setAttribute("teacher",loginInformation);
                        loginreturnVo.setUrl("/html/teacher/home.html");
                        break;
                    case 2:
                        session.setAttribute("admin",loginInformation);
                        loginreturnVo.setUrl("/html/admin/home.html");
                        break;
                }
            }
            else{
                loginreturnVo.setState(0);
            }
        }
        return loginreturnVo;
    }
}
