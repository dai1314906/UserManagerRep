package com.dl.web.servlet;

import com.dl.service.UserService;
import com.dl.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/delSelectedServlet")
public class DelSelectedServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获得id
        String[] ids = request.getParameterValues("uid");
        //调用service删除
        UserService service = new UserServiceImpl();
        service.delSelectedUser(ids);

        //跳转动作
        response.sendRedirect(request.getContextPath()+"/userListServlet");
    }
}
