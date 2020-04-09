package com.mage.crm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mage.crm.dao.UserMapper;
import com.mage.crm.dao.UserRoleMapper;
import com.mage.crm.model.UserModel;
import com.mage.crm.query.UserQuery;
import com.mage.crm.util.AssertUtil;
import com.mage.crm.util.Md5Util;
import com.mage.crm.util.PhoneUtil;
import com.mage.crm.util.UserIDBase64;
import com.mage.crm.vo.User;
import com.mage.crm.vo.UserRole;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@SuppressWarnings("all")
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    /**
     * 登录
     */
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
        checkLoginParams(userName, userPwd);
        User user = userMapper.queryUserByUserName(userName);
        AssertUtil.isTrue(user == null, "用户已注销或不存在");
        AssertUtil.isTrue(!(user.getUserPwd().equals(Md5Util.encode(userPwd))), "用户名或密码错误");
        return buildUserModelInfo(user);

    }

    public UserModel buildUserModelInfo(User user) {
        return new UserModel(UserIDBase64.encoderUserID(user.getId()), user.getUserName(), user.getTrueName());
    }

    public void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "密码不能为空");
    }

    /**
     * 更新用户密码
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserPassword(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
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
        checkParams(userId, oldPassword, newPassword, confirmPassword);
        User user = userMapper.selectByPrimaryKey(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "密码更新失败");
    }

    private void checkParams(Integer userId, String oldPassword, String newPassword, String confirmPassword) {
        AssertUtil.isTrue(userId == null || userMapper.selectByPrimaryKey(userId) == null, "用户未登录或不存在!");
        AssertUtil.isTrue(StringUtils.isBlank(oldPassword), "原始密码不能为空");
        AssertUtil.isTrue(userMapper.selectByPrimaryKey(userId).equals(Md5Util.encode(oldPassword)), "原始密码错误");
        AssertUtil.isTrue(StringUtils.isBlank(newPassword), "新密码不能为空");
        AssertUtil.isTrue(newPassword.equals(oldPassword), "新密码不能与旧密码相同");
        AssertUtil.isTrue(StringUtils.isBlank(confirmPassword), "确认密码不能为空");
        AssertUtil.isTrue(!(newPassword.equals(confirmPassword)), "两次输入的密码不一致");
    }

    /**
     * 用户添加
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(User user) {
        /**
         * 1.参数校验
         *  用户名 非空
         *  email 非空 格式合法
         *  phone 非空  格式合法
         * 2.设置默认参数
         *  isValid 1
         *  createDate updateDate
         *  userPwd 123456-->md5加密
         * 3.执行添加 判断结果
         * */
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        User temp = userMapper.queryUserByUserName(user.getUserName());
        AssertUtil.isTrue(temp != null && (temp.getIsValid() == 1), "该用户已存在!");
        user.setIsValid(1);
        user.setCreateDate(new Date());
        user.setUpdateDate(new Date());
        user.setUserPwd(Md5Util.encode("123456"));
        AssertUtil.isTrue(userMapper.insertHasKey(user) == null, "创建用户失败!");
        int userId = user.getId();

        /**
         * 用户角色分配
         * userId
         * roleIds
         * */
        relaionUserRole(user.getId(), user.getRoleIds());

    }

    private void checkParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户名不能为空!");
        AssertUtil.isTrue(StringUtils.isBlank(email), "email不能为空!");
        AssertUtil.isTrue(!(PhoneUtil.isMobile(phone)), "手机号格式不合法!");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {
        /**
         * 1.参数校验
         *  id 非空 记录必须存在
         *  用户名 非空 唯一
         *  email非空 格式合法
         *  手机号 非空 格式合法
         * 2.设置默认参数
         *  updateDate
         * 3.执行更新 判断结果
         * */
        AssertUtil.isTrue(user.getId() == null || userMapper.selectByPrimaryKey(user.getId()) == null, "待更新用户不存在!");
        checkParams(user.getUserName(), user.getEmail(), user.getPhone());
        User temp = userMapper.queryUserByUserName(user.getUserName());
        if (temp != null && temp.getIsValid() == 1) {
            AssertUtil.isTrue(!(temp.getId().equals(user.getId())), "该用户已存在!");
        }

        user.setUpdateDate(new Date());
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "更新用户信息失败!");

        /**
         * 用户角色分配
         * 如果用户原始角色存在 首先清空原始所有角色
         * 添加新角色记录到用户角色表
         * */
        relaionUserRole(user.getId(), user.getRoleIds());
    }

    private void relaionUserRole(int userId, List<Integer> roleIds) {
        int count = userRoleMapper.countUserRoleByUserId(userId);
        if (count > 0) {
            AssertUtil.isTrue(userRoleMapper.deleteUserRoleByUserId(userId) != count, "用户角色分配失败!");
        }
        if (roleIds != null && roleIds.size() > 0) {
            List<UserRole> userRoles = new ArrayList<>();
            for (Integer i : roleIds) {
                UserRole userRole = new UserRole();
                userRole.setRoleId(i);
                userRole.setUserId(userId);
                userRole.setCreateDate(new Date());
                userRole.setUpdateDate(new Date());
                userRoles.add(userRole);
            }
            AssertUtil.isTrue(userRoleMapper.insertBatch(userRoles) < 1, "用户角色分配失败!");
        }
    }

    public void deleteUser(Integer userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(user == null || userId == null, "待删除的用户不存在!");
        user.setIsValid(0);
        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user) < 1, "用户路删除失败!");
    }

    public Map<String, Object> queryByParamsForDataGrid(UserQuery userQuery) {
        Map<String, Object> result = new HashMap<>();
        PageHelper.startPage(userQuery.getPage(), userQuery.getRows());
        PageInfo<User> pageInfo = new PageInfo<>(userMapper.selectByParams(userQuery));
        result.put("total", pageInfo.getTotal());
        result.put("rows", pageInfo.getList());
        return result;
    }

    public List<Map<String, Object>> queryAllCustomerManager() {
        return userMapper.queryAllCustomerManager();
    }
}
