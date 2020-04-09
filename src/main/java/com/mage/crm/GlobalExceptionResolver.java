package com.mage.crm;

import com.alibaba.fastjson.JSON;
import com.mage.crm.exception.NoLoginException;
import com.mage.crm.exception.ParamsException;
import com.mage.crm.interceptors.AuthFailedException;
import com.mage.crm.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        /**
         * 首先判断异常类型
         *  如果异常类型为未登录异常  执行视图转发
         * */
        ModelAndView mv = new ModelAndView();
        if (ex instanceof NoLoginException) {
            NoLoginException ne = (NoLoginException) ex;
            mv.setViewName("no_login");
            mv.addObject("msg", ne.getMsg());
            mv.addObject("ctx", request.getContextPath());
            return mv;
        }

        /**
         * 返回值类型判断
         *  入宫方法级别存在@responseBody方法相应内容为json 否则为视图
         * 返回值
         *      视图:默认错误页面
         *      json:错误的json信息
         * */

        mv.setViewName("errors");
        mv.addObject("code", 400);
        mv.addObject("msg", "系统错误请稍后再试!");
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            ResponseBody responseBody = hm.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if (responseBody == null) {
                /**
                 * 方法返回视图
                 * */
                if (ex instanceof ParamsException) {
                    ParamsException pe = (ParamsException) ex;
                    mv.addObject("msg", pe.getMsg());
                    mv.addObject("code", pe.getCode());
                }
                if (ex instanceof AuthFailedException) {
                    AuthFailedException afe = (AuthFailedException) ex;
                    mv.addObject("msg", afe.getMsg());
                    mv.addObject("code", afe.getCode());
                }
                return mv;
            } else {
                /**
                 * 方法返回json
                 * */
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("系统错误,请稍后再试!");
                if (ex instanceof ParamsException) {
                    ParamsException pe = (ParamsException) ex;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }
                if (ex instanceof AuthFailedException) {
                    AuthFailedException afe = (AuthFailedException) ex;
                    resultInfo.setCode(afe.getCode());
                    resultInfo.setMsg(afe.getMsg());
                }
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter pw = null;
                try {
                    pw = response.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                    pw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (pw != null) {
                        pw.close();
                    }
                }
                return null;
            }
        } else {
            return mv;
        }
    }
}
