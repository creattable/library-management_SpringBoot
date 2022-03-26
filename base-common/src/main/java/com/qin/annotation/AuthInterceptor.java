package com.qin.annotation;

import com.qin.exception.BusinessException;
import com.qin.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.prefs.BackingStoreException;

/**
 * @author 秦家乐
 * @date 2022/3/26 13:39
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {
    
    @Autowired
    private JwtUtils jwtUtils;
    
    
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        
    
        Auth annotation;
        
        //只有写在Method上才能获取，
        if(handler instanceof HandlerMethod){
            //如果存在于此元素，则返回该元素注释指定的注释类型，否则返回为null。
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Auth.class);
        }else {
            return true;
        }
        
        //防止为空，如果不写就可能死循环
        //具体为啥，我又没看源码，我哪能知道
        if(annotation==null){
            return true;
        }
        
        //先从Header获取token
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            //如果请求里面没有的话，就试着从参数中获取
            token=request.getParameter("token");
        }
        //如果都为空，则返回
        if(StringUtils.isEmpty(token)){
            throw  new BusinessException(600,"token不能为空");
        }
        
        //判断token是否过期
        Boolean expired = jwtUtils.isTokenExpired(token);
        if(expired){
            throw new BusinessException(600,"token过期");
        }
        
        //解析token
        String username = jwtUtils.getUsernameFromToken(token);
        //在login页面的时候，username已经有了
        if(StringUtils.isEmpty(username)){
            throw new BusinessException(600,"token解析失败");
        }

        return true;
    }
    
    
}
