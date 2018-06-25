package com.nebula.springBoot.login;

import com.nebula.springBoot.support.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by SmallAnn on 2018/6/25
 */
@Service
public class LoginService {

    @Autowired LoginMapper loginMapper;

    /**
     * 初始化
     */
    void init() {
        loginMapper.createTable();
    }

    /**
     * 登录
     */
    AjaxResult login(String name, String password, HttpServletRequest request) {
        //TODO 验证帐号密码
        request.getSession().setAttribute("userName", name);
        return new AjaxResult().success();
    }
}
