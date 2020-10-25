package cqu.edu.cn.ssosystem.servlet;

import cqu.edu.cn.ssosystem.controller.LoginController;
import cqu.edu.cn.ssosystem.global.ResData;
import cqu.edu.cn.ssosystem.model.Token;
import cqu.edu.cn.ssosystem.rsa.Pair;
import cqu.edu.cn.ssosystem.rsa.Rsa;
import cqu.edu.cn.ssosystem.rsa.RsaKey;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;

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
        resp.setHeader("Access-Control-Allow-Origin", "http://192.168.43.172:8080");
        resp.setHeader("Access-Control-Max-Age", "3600");
        resp.setHeader("Access-Control-Allow-Headers", "app_token");
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
        String userPassword = req.getParameter("user_password"); // 加密后的
        String appId = req.getParameter("app_id");
        String rsaN = req.getParameter("n");
        String rsaE = req.getParameter("e");

        ArrayList<BigInteger> passwordList = new ArrayList<BigInteger>();
        String [] splitedPassword = userPassword.split(";");
        for(int i = 0; i < splitedPassword.length; i ++){
            passwordList.add(new BigInteger(splitedPassword[i]));
        }
        RsaKey myKey = Rsa.getGlobalRsa().searchKey(new Pair(new Long(rsaE), new Long(rsaN)));
        if(myKey == null){ // 如果有错是恶意攻击，是
            remakeJsonResp(resp, "{error: true, message: '发生了未知错误'}");
            return ;
        }

        String groundTruthPassword = RsaKey.openLock(passwordList, myKey);
        System.out.println(groundTruthPassword);

        ResData loginRes = null;
        try {
            loginRes = this.myLoginController.doLogin(userPhoneNumber, groundTruthPassword, appId);
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
        );

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("asdasd");
        remakeJsonResp(resp, "");
    }
}
