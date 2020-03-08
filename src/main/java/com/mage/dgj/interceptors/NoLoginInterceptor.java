package com.mage.dgj.interceptors;

import com.mage.dgj.exception.NoLoginException;
import com.mage.dgj.service.UserService;
import com.mage.dgj.util.LoginUserUtil;
import com.mage.dgj.vo.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoLoginInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 获取cookie
         *  如果用户id存在,数据库存在对应用户记录 泛型 否则进行拦截,重定向到登录页面
         * */
        int userId = LoginUserUtil.releaseUserIdFromCookie(request);
        User user = userService.selectByPrimaryKey(userId);
       /* if (userId==0 || user==null){
            response.sendRedirect(request.getContextPath()+"/index");
            return false;
        }*/
        if (userId == 0 || user == null) {
            throw new NoLoginException();
        }

        return true;
    }
}
