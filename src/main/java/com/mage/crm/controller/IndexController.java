package com.mage.crm.controller;

import com.mage.base.BaseController;
import com.mage.crm.exception.ParamsException;
import com.mage.crm.service.UserService;
import com.mage.crm.util.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 登录页
     * */
    @RequestMapping("index")
    public String index(){
        return "index";
    }

    /**
     * 后台管理主页面
     * */
    @RequestMapping("main")
    public String main(HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        request.setAttribute("user",userService.selectByPrimaryKey(userId));
        return "main";
    }
}
