package com.jiang.mall.filter;

import com.jiang.mall.common.Constant;
import com.jiang.mall.model.pojo.User;
import com.jiang.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;


public class AdminFilter implements Filter {

    @Autowired
    UserService userService;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);

        if (currentUser == null) {
            PrintWriter out =  new HttpServletResponseWrapper( (HttpServletResponse) response).getWriter();
            out.write("{\"status\":10007,\"msg\":\"user not login\",\"data\":null}");
            out.flush();
            out.close();
            return;
        }

        // 校验是否管理员
        boolean admin = userService.checkAdminRole(currentUser);
        if (admin) {
            // 放行。。 执行下一个过滤器
            chain.doFilter(request, response);
        }else{
            PrintWriter out =  new HttpServletResponseWrapper( (HttpServletResponse) response).getWriter();
            out.write("{\"status\":10009,\"msg\":\"you are not admin\",\"data\":null}");
            out.flush();
            out.close();
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
