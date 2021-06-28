package com.dl.web.servlet;

import com.dl.service.UserService;
import com.dl.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/delUserServlet")
public class DelUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取id
        String id = request.getParameter("id");
        //调用service
        UserService service = new UserServiceImpl();
        service.deleteUser(id);

        //跳转到userListServlet
        response.sendRedirect(request.getContextPath()+"/userListServlet");
    }
}
