package com.nsystem.service;

import com.nsystem.vo.LoginVo;
import com.nsystem.vo.LoginreturnVo;

import javax.servlet.http.HttpSession;

public interface LoginService {
    public LoginreturnVo findByName(LoginVo loginVo, HttpSession session);
}
