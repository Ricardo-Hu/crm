package com.mage.crm.controller;

import com.mage.base.BaseController;
import com.mage.crm.dao.UserMapper;
import com.mage.crm.model.ResultInfo;
import com.mage.crm.model.UserModel;
import com.mage.crm.query.UserQuery;
import com.mage.crm.service.UserService;
import com.mage.crm.util.LoginUserUtil;
import com.mage.crm.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @RequestMapping("queryUserByUserId")
    @ResponseBody
    public User queryUserByUserId(Integer userId){
        return userMapper.selectByPrimaryKey(userId);
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

    @RequestMapping("index")
    public String index(){
        return "/user";
    }

    @RequestMapping("save")
    @ResponseBody
    public ResultInfo saveUser(User user){
        userService.saveUser(user);
        return success("用户添加成功!");
    }

    @RequestMapping("list")
    @ResponseBody
    public Map<String,Object> queryUsersByParams(UserQuery userQuery){
        return userService.queryByParamsForDataGrid(userQuery);
    }

    @RequestMapping("update")
    @ResponseBody
    public ResultInfo updateUser(User user){
        userService.updateUser(user);
        return success("修改用户记录成功!");
    }

    @RequestMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer userId){
        userService.deleteUser(userId);
        return success("删除用户记录成功");
    }

    @RequestMapping("queryAllCustomerManager")
    @ResponseBody
    public List<Map<String,Object>> queryAllCustomerManager(){
        return userService.queryAllCustomerManager();
    }

}
