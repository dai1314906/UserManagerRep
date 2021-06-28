package com.dl.web.servlet;

import com.dl.domain.PageBean;
import com.dl.domain.User;
import com.dl.service.UserService;
import com.dl.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置参数
        request.setCharacterEncoding("utf-8");
        //当前页码
        String currentPage = request.getParameter("currentPage");
        //当管理员用户登录进去后login.jsp->list.jsp显示页面条数
        //优化当前界面的页面
        String rows = request.getParameter("rows");
        if(currentPage == null||"".equals(currentPage)){
            currentPage = "1";
        }
        if(rows == null || "".equals(rows)){
            rows = "5";
        }

        
        //获取查询条件参数
        Map<String, String[]> condition = request.getParameterMap();

        //使用userService查询
        UserService service = new UserServiceImpl();
        PageBean<User> pb = service.findUserByPage(currentPage, rows, condition);
        System.out.println(pb);
        //将pageBean存入到request
        request.setAttribute("pb",pb);

        //将查询条件存入request
        request.setAttribute("condition",condition);

        request.getRequestDispatcher("/list.jsp").forward(request,response);
    }
}
