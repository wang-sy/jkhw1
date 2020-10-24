package cqu.edu.cn.ssosystem.servlet;

import cqu.edu.cn.ssosystem.global.ResData;
import cqu.edu.cn.ssosystem.rsa.Rsa;
import cqu.edu.cn.ssosystem.rsa.RsaKey;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RsaServlet extends HttpServlet {
    private Rsa myRsa = null;
    public RsaServlet() {
        Rsa.generateGlobalRsa();
        myRsa = Rsa.getGlobalRsa();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RsaKey newKey = this.myRsa.generateKey();
        ResData res = null;

        res = new ResData(false, "{\"n\":\"" + Long.valueOf(newKey.getOla()).toString() +
                "\", \"e\":\"" + Long.valueOf(newKey.getPublicKey()).toString() + "\"}");

        UserServlet.remakeJsonResp(resp, "{" +
                "\"error\":" + " \"" + (res.isError() ? "true" : "false") + "\"," +
                " \"data\":" + " " + res.getData() + "}"
        );
        System.out.println(resp.toString());
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("asdasd");
        UserServlet.remakeJsonResp(resp, "");
        System.out.println(resp.toString());
    }
}
