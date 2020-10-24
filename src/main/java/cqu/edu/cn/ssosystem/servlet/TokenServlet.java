package cqu.edu.cn.ssosystem.servlet;

import cqu.edu.cn.ssosystem.controller.TokenController;
import cqu.edu.cn.ssosystem.global.ResData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class TokenServlet extends HttpServlet {

    public TokenController myTokenController = null;

    public TokenServlet() throws Exception {
        TokenController.createGlobalTokenController();
        this.myTokenController = TokenController.getGlobalTokenController();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("我收到了请求");

        String userToken = req.getParameter("token");
        String appId = req.getParameter("appId");

        ResData res = null;

        try {
            res = this.myTokenController.checkToken(userToken, appId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        assert res != null;



        UserServlet.remakeJsonResp(resp, "{" +
                "\"error\":" + " \"" + (res.isError() ? "true" : "false") + "\"," +
                " \"data\":" + " \"" + res.getData() + "\"" +
                "}"
        );
    }
}
