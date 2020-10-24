package cqu.edu.cn.ssosystem.servlet;

import cqu.edu.cn.ssosystem.controller.LoginController;
import cqu.edu.cn.ssosystem.global.ResData;
import cqu.edu.cn.ssosystem.model.Token;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * 登录的servlet，处理登录相关的请求
 */
public class UserServlet extends HttpServlet{



    private LoginController myLoginController = null;

    public UserServlet() throws Exception {
        LoginController.createGlobalLoginController();
        this.myLoginController = LoginController.getGlobalLoginController();
    }

    // 初始化方法
    public UserServlet (LoginController myLoginController) {
        this.myLoginController = myLoginController;
    }

    public  static void  remakeJsonResp(HttpServletResponse resp, String message) {
        resp.setCharacterEncoding("UTF-8");
        resp.setHeader("Content-Type", "application/json");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json;charset=UTF-8");

        try (PrintWriter writer = resp.getWriter()) {
            writer.write(message);
            writer.flush();
        } catch (IOException ex) {
            System.out.println("error in UserServlet resp");
        }

    }

    @Override
    // 这里写的是用户登陆的方法
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userPhoneNumber = req.getParameter("user_phone_number");
        String [] userPassword = req.getParameterValues("user_password");
        String appId = req.getParameter("app_id");
        String n = req.getParameter("multiply");
        String e = req.getParameter("publicKey");

        for(String temp: userPassword){
            System.out.println(temp);
        }


        /*ResData loginRes = null;
        try {
            loginRes = this.myLoginController.doLogin(userPhoneNumber, userPassword, appId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            remakeJsonResp(resp, "{error: true, message: '发生了未知错误'}");
            return ;
        }
        System.out.println(loginRes.isError());
        UserServlet.remakeJsonResp(resp, "{" +
                "\"error\":" + " \"" + (loginRes.isError() ? "true" : "false") + "\"," +
                " \"data\":" + " \"" + loginRes.getData() + "\"" +
                "}"
        );*/

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("asdasd");
        remakeJsonResp(resp, "");
    }
}
