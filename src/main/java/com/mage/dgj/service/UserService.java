package com.mage.dgj.service;

import com.mage.base.BaseService;
import com.mage.dgj.dao.UserMapper;
import com.mage.dgj.model.UserModel;
import com.mage.dgj.util.AssertUtil;
import com.mage.dgj.util.Md5Util;
import com.mage.dgj.util.UserIDBase64;
import com.mage.dgj.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService extends BaseService<User, Integer> {

    @Resource
    private UserMapper userMapper;

    public UserModel login(String userName, String userPwd) {
        /**
         * 1.参数校验
         * 用户名和密码非空
         * 2.根据用户名 查询用户记录
         * 3.校验用户存不存在
         *  如果不存在 --方法结束 跳回登录页面
         * 4.用户存在
         *  校验密码-->密码不正确 方法结束
         * 5.密码正确
         *    用户登录成功 返回用户相关信息
         * */

        checkLoginParams(userName, userPwd);

        User user = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(user == null, "用户不存在或已注销");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))),"用户名或密码错误");
        return buildUserModelInfo(user);
    }

    private UserModel buildUserModelInfo(User user) {
        /**
         * UserIDBase64,用Base64加密算法给用户Id加密
         * */
        return new UserModel(UserIDBase64.encoderUserID(user.getId()),user.getUserName(),user.getTrueName());
    }

    /**
     * 校验参数是否为空
     * */
    private void checkLoginParams(String userName, String userPwd) {

        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "密码不能为空!");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        /**
         * 1.参数校验
         *  userId 非空 而且必须要有记录
         *  oldPassword 非空 新密码不能与老密码一致
         *  newPassword非空 新密码不能与老密码一致
         *  confirmPassword 非空 与新密码必须一致
         * 2.设置用户新密码
         *  新密码加密
         * 3.执行更新
         * */
        checkParams(userId,oldPassword,newPassword,confirmPassword);
        User user = selectByPrimaryKey(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"修改失败!");
    }

    public  void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        User user = selectByPrimaryKey(userId);
        /**
         * 用户ID不能为空
         * */
        AssertUtil.isTrue(userId == null || user ==null,"用户不存在");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"请输入原始密码");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"请输入新密码");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"请确认新密码");
        AssertUtil.isTrue(oldPassword.equals(newPassword),"新密码与原始密码不能相同");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"两次输入的新密码不一致");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(oldPassword))),"原始密码有误");

    }
}
