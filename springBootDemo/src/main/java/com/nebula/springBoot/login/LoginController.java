package com.nebula.springBoot.login;

import com.nebula.springBoot.support.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by SmallAnn on 2018/6/25
 */

@Controller
public class LoginController {

    @Autowired LoginService loginService;

    /**
     * 初始化
     */
    @PostConstruct
    public void init() {
        loginService.init();
    }

    /**
     * 获取登录页面
     */
    @GetMapping({"/login"})
    public ModelAndView login(ModelAndView mv) {
        mv.setViewName("login");
        return mv;
    }

    /**
     * 处理登录请求
     */
    @ResponseBody
    @PostMapping({"/login"})
    public AjaxResult login(String name, String password, HttpServletRequest request) {
        return loginService.login(name, password, request);
    }
}
