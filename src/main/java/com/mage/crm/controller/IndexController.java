package com.mage.crm.controller;

import com.mage.base.BaseController;
import com.mage.crm.dao.UserMapper;
import com.mage.crm.exception.ParamsException;
import com.mage.crm.service.ModuleService;
import com.mage.crm.service.PermissionService;
import com.mage.crm.service.UserService;
import com.mage.crm.util.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PermissionService permissionService;

    @Resource
    private ModuleService moduleService;

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
        List<String> permissions=permissionService.queryUserHasRolesHasPermissions(userId);
        request.setAttribute("user", userMapper.selectByPrimaryKey(userId));
        request.getSession().setAttribute("permissions",permissions);
        request.getSession().setAttribute("modules",moduleService.queryUserHasRoleHasModuleDtos(userId));
        return "main";
    }
}
