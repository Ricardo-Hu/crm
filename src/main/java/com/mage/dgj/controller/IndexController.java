package com.mage.dgj.controller;

import com.mage.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {
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
        request.setAttribute("ctx",request.getContextPath());
        return "main";
    }

    @RequestMapping("success")
    @ResponseBody
    public String success(){
        return "success";
    }

}
