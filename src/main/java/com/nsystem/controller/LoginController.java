package com.nsystem.controller;

import com.nsystem.entity.LoginInformation;
import com.nsystem.mapper.LoginInformationMapper;
import com.nsystem.service.LoginService;
import com.nsystem.util.MD5Utils;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.LoginreturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    //@Autowired
    //private LoginInformationMapper loginInformationMapper;

    @RequestMapping("/juge")
    public LoginreturnVo login(LoginVo loginVo, HttpSession session){
        /*List<LoginInformation> loginInformationList=loginInformationMapper.selectList(null);
        for(LoginInformation loginInformation:loginInformationList){
            String password=loginInformation.getPassword();
            loginInformation.setPassword(MD5Utils.inputPassToFormPass(password));
            loginInformationMapper.updateById(loginInformation);
        }*/
        return loginService.findByName(loginVo,session);
    }

    @RequestMapping("/logout")
    public int Logout(HttpSession session, SessionStatus sessionStatus){
        session.invalidate();
        sessionStatus.setComplete();
        return 1;
    }


}