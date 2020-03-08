package com.mage.dgj;

import com.alibaba.fastjson.JSON;
import com.mage.dgj.exception.NoLoginException;
import com.mage.dgj.exception.ParamsException;
import com.mage.dgj.interceptors.NoLoginInterceptor;
import com.mage.dgj.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常
 * */
@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        /**
         * 首先判断异常类型
         * */
        ModelAndView mv=new ModelAndView();
        if (ex instanceof NoLoginException){
            NoLoginException ne= (NoLoginException)ex;
            mv.setViewName("no_login");
            mv.addObject("msg",ne.getMsg());
            mv.addObject("ctx",request.getContextPath());
            return mv;
        }
        /**方法返回值类型判断
         *  如果方法存在@ResponseBody方法响应内容为json,否则为视图
         * 返回值
         *  如果是视图:返回错误页面
         *
         *  如果是json:返回错误的json信息
         * */
        mv.setViewName("errors");
        mv.addObject("code",400);
        mv.addObject("msg","系统异常,请稍后再试...");
        if (handler instanceof HandlerMethod){
            /**
             * 判断ResponseBody是否存在
             * */
            HandlerMethod hm = (HandlerMethod) handler;
            ResponseBody responseBody = hm.getMethod().getDeclaredAnnotation(ResponseBody.class);
            if (responseBody==null){
                /**
                 * 方法返回视图
                 * */
                if (ex instanceof ParamsException){
                    ParamsException pe = (ParamsException) ex;
                    mv.addObject("msg",pe.getMsg());
                    mv.addObject("code",pe.getCode());
                }
                return mv;
            }else{
                /**
                 * 方法返回json
                 * */
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(300);
                resultInfo.setMsg("系统错误,请稍后再试!");
                if (ex instanceof ParamsException){
                    ParamsException pe = (ParamsException)ex;
                    resultInfo.setCode(pe.getCode());
                    resultInfo.setMsg(pe.getMsg());
                }

                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json;charset=utf-8");
                PrintWriter pw =null;
                try {
                    pw=response.getWriter();
                    pw.write(JSON.toJSONString(resultInfo));
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (pw !=null){
                        pw.close();
                    }
                }
                return null;
            }
        }else{
            return mv;
        }
    }
}
