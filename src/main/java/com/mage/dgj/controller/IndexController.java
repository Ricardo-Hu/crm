package com.mage.dgj.controller;

import com.mage.base.BaseController;
import com.mage.dgj.service.UserService;
import com.mage.dgj.util.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;
    /**
     * 登录页面
     * */
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        request.setAttribute("ctx",request.getContextPath());
        return "index";
    }

    /**
     * 后端管理页面
     * */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        /**
         * 获取userId
         * */
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        request.setAttribute("user",userService.selectByPrimaryKey(userId));
        return "main";
    }


}
