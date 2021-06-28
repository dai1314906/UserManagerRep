package com.dl.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //强制转换 httpServletRequest 是 ServletRequest的子接口
        HttpServletRequest req = (HttpServletRequest) request;

        //获取资源请求路径
        String uri = req.getRequestURI();
        //判断是否包含登录相关资源
        if(uri.contains("/login.jsp") || uri.contains("/loginServlet") || uri.contains("/css/") || uri.contains("/js/") || uri.contains("/fonts/") || uri.contains("/checkCodeServlet")){
            //用户包含上述操作则证明用户是要登录
            //放行
            chain.doFilter(request, response);
        }else {
            //没有包含相关资源登录的用户登录操作。则验证用户是否登录
            //从session中获取user
            Object user = req.getSession().getAttribute("user");
            if(user != null){
                chain.doFilter(request,response);
            }else {
                request.setAttribute("login_msg","您还未登录，请登录");
                request.getRequestDispatcher("/login.jsp").forward(req,response);
            }

        }
        //
    }
}
