package com.mage.dgj.controller;

import com.mage.base.BaseController;
import com.mage.dgj.exception.ParamsException;
import com.mage.dgj.model.ResultInfo;
import com.mage.dgj.model.UserModel;
import com.mage.dgj.service.UserService;
import com.mage.dgj.util.LoginUserUtil;
import com.mage.dgj.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @GetMapping("user/queryUserByUserId")
    @ResponseBody
    public User queryUserByUserId(Integer userId) {
        return userService.selectByPrimaryKey(userId);
    }

    /**
     * 用户登录
     */
    @PostMapping("user/login")
    @ResponseBody
    public ResultInfo login(String userName, String userPwd) {

        UserModel userModel = userService.login(userName, userPwd);
        return  success("用户登录成功",userModel);

    }

    /**
     * 更新密码
     */
    @PostMapping("user/updatePassword")
    @ResponseBody
    public ResultInfo login(HttpServletRequest request, String oldPassword, String newPassword, String confirmPassword) {
        userService.updateUserPassword(LoginUserUtil.releaseUserIdFromCookie(request), oldPassword, newPassword, confirmPassword);
        return success("密码更新成功");
    }
}
