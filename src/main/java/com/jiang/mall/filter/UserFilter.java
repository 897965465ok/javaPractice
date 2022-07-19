package com.jiang.mall.filter;

import com.jiang.mall.common.Constant;
import com.jiang.mall.model.pojo.User;
import com.jiang.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户过滤器
 */
public class UserFilter implements Filter {

    @Autowired
    UserService userService;
    public  static  User currentUser;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpSession session = httpRequest.getSession();
         currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            PrintWriter out =  new HttpServletResponseWrapper( (HttpServletResponse) response).getWriter();
            out.write("{\"status\":10007,\"msg\":\"user not login\",\"data\":null}");
            out.flush();
            out.close();
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

