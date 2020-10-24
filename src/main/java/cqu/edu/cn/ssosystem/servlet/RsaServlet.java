package cqu.edu.cn.ssosystem.servlet;

import cqu.edu.cn.ssosystem.global.ResData;
import cqu.edu.cn.ssosystem.rsa.Rsa;
import cqu.edu.cn.ssosystem.rsa.RsaKey;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * RsaServlet用于管理密钥相关的请求
 */
public class RsaServlet extends HttpServlet {
    private Rsa myRsa = null;

    /**
     * 初始化，初始化的时候会新建一个Rsa对象，进行管理
     */
    RsaServlet(){
        myRsa = new Rsa();
    }

    @Override
    /**
     * get请求，获取一个公钥，同时会在服务器创建一个私钥与之对应
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("我收到了请求");

        String userToken = req.getParameter("token");
        String appId = req.getParameter("appId");

        ResData res = null;
        RsaKey newKey = this.myRsa.generateKey();
        Long n = newKey.getOla();
        Long e = newKey.getPublicKey();

        res = new ResData(false, "{n:" + n.toString() + ", e:" + e.toString() + "}");

        assert res != null;

        UserServlet.remakeJsonResp(resp, "{" +
                "\"error\":" + " \"" + (res.isError() ? "true" : "false") + "\"," +
                " \"data\":" + " \"" + res.getData() + "\"" +
                "}"
        );
    }
}
