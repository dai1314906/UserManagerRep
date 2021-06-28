package com.dl.web.servlet;

import com.dl.domain.User;
import com.dl.service.UserService;
import com.dl.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取数据
        //先获取验证码
        String verifycode = request.getParameter("verifycode");

        //验证码校验
        HttpSession session = request.getSession();
        //强制类型转换
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");//删除验证码
        if(!checkcode_server.equalsIgnoreCase(verifycode)){//若验证码错误则不登陆
            //验证码错误
            //提示信息
            request.setAttribute("login_msg","验证码错误");
            //跳转到登录界面
            request.getRequestDispatcher("/login.jsp").forward(request,response);

            return;
        }
        //封装user对象
        User user = new User();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service方法
        UserService service = new UserServiceImpl();
        User loginUser = service.login(user);

        if(loginUser!=null){
            //登录成功
            //将用户存入session
            session.setAttribute("user",loginUser);
            //跳转到首页
            response.sendRedirect(request.getContextPath()+"/index.jsp");

        }else {
            //提示信息
            request.setAttribute("login_msg","用户名或密码错误");
            //跳转到登录界面
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }
    }
}
