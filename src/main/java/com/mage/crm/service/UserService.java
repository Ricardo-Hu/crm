package com.mage.crm.service;

import com.mage.base.BaseService;
import com.mage.crm.dao.UserMapper;
import com.mage.crm.model.UserModel;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.Md5Util;
import com.mage.crm.util.UserIDBase64;
import com.mage.crm.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService extends BaseService<User,Integer> {

    @Resource
    private UserMapper userMapper;

    /**
     * 登录
     * */
    public UserModel login(String userName, String userPwd) {
        /**
         *1.参数校验
         *  用户名 非空
         *  密码 非空
         * 2.根据用户名 查询用户记录
         * 3.检验用户存在性
         *  不存在 -->记录不存在方法结束
         * 4.用户存在
         *  校验密码
         *      密码错误-->密码正确 方法结束
         * 5.密码正确
         *  用户登录成功 返回用户信息
         * */
        checkLoginParams(userName,userPwd);
        User user = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(user==null,"用户已注销或不存在");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))),"用户名或密码错误");
        return buildUserModelInfo(user);

    }

    public UserModel buildUserModelInfo(User user){
        return new UserModel(UserIDBase64.encoderUserID(user.getId()),user.getUserName(),user.getTrueName());
    }
    public void checkLoginParams(String userName,String userPwd){
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd),"密码不能为空");
    }

    /**
     * 更新用户密码
     * */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId,String oldPassword,String newPassword,String confirmPassword){
        /**
         * 1.参数校验
         *  userId 非空 记录必须存在
         *  oldPassword 非空 必须与数据库一致
         *  newPassword 非空 新密码与旧密码不能相同
         *  confirmPassword 非空 与新密码必须一致
         * 2.设置用户新密码
         *  新密码加密
         * 3.执行更新
         * */
        checkParams(userId,oldPassword,newPassword,confirmPassword);
        User user = selectByPrimaryKey(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(updateByPrimaryKeySelective(user)<1,"密码更新失败");
    }

    private void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        AssertUtil.isTrue(userId == null || selectByPrimaryKey(userId)==null,"用户未登录或不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword),"原始密码不能为空");
        AssertUtil.isTrue(selectByPrimaryKey(userId).equals(Md5Util.encode(oldPassword)),"原始密码错误");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword),"新密码不能为空");
        AssertUtil.isTrue(newPassword.equals(oldPassword),"新密码不能与旧密码相同");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword),"确认密码不能为空");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)),"两次输入的密码不一致");
    }
}
