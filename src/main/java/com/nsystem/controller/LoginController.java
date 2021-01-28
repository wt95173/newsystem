package com.nsystem.controller;

import com.nsystem.service.LoginService;
import com.nsystem.vo.LoginVo;
import com.nsystem.vo.LoginreturnVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/juge")
    public LoginreturnVo login(LoginVo loginVo, HttpSession session){
        return loginService.findByName(loginVo,session);
    }


}