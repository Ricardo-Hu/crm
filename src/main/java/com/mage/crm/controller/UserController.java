package com.mage.crm.controller;

import com.mage.base.BaseController;
import com.mage.crm.model.ResultInfo;
import com.mage.crm.model.UserModel;
import com.mage.crm.service.UserService;
import com.mage.crm.util.LoginUserUtil;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping("queryUserByUserId")
    @ResponseBody
    public User queryUserByUserId(Integer userId){
        return userService.selectByPrimaryKey(userId);
    }

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(String userName, String userPwd) {
        UserModel userModel = userService.login(userName, userPwd);
        return success("用户登录成功",userModel);
    }

    @RequestMapping("updatePassword")
    @ResponseBody
    public ResultInfo update(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword) {
        userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request), oldPassword, newPassword, confirmPassword);
        return success("密码更新成功!");
    }
}
