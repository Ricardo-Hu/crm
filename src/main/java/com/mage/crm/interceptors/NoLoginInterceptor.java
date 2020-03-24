package com.mage.crm.interceptors;

import com.mage.crm.exception.NoLoginException;
import com.mage.crm.service.UserService;
import com.mage.crm.util.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 * */
public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /**
         * 获取cookie 解析用户id
         *   如果用户id存在 并且 数据库存在对应用户记录就放行 否则进行拦截
         * */
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        if (userId==0 || userService.selectByPrimaryKey(userId)==null){
            throw new NoLoginException();
        }
        return true;
    }
}
