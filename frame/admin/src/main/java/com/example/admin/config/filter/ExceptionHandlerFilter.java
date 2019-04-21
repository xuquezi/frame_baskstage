package com.example.admin.config.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constant.FrameConstant;
import entity.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.core.annotation.Order;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//处理filter中异常
@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "exceptionHandlerFilter")
@Order(1)
public class ExceptionHandlerFilter extends org.springframework.web.filter.OncePerRequestFilter {

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        }catch (Exception e){
            log.error("异常信息：", e.getCause());
            if (e.getCause() instanceof AuthenticationException){
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                ResponseResult responseResult = new ResponseResult("登录权限不足！",FrameConstant.AUTHENTICATION_FAIL,false);
                String json = convertObjectToJson(responseResult);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(json.getBytes("UTF-8"));
                outputStream.flush();
            } else {
                response.setContentType("application/json; charset=utf-8");
                response.setCharacterEncoding("UTF-8");
                ResponseResult responseResult = new ResponseResult("系统内部异常,请求失败",FrameConstant.REQUEST_FAIL,false);
                String json = convertObjectToJson(responseResult);
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(json.getBytes("UTF-8"));
                outputStream.flush();
            }

        }

    }
}
