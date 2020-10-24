package cqu.edu.cn.ssosystem.controller;

import cqu.edu.cn.ssosystem.global.DBConnection;
import cqu.edu.cn.ssosystem.global.ResData;
import cqu.edu.cn.ssosystem.model.Token;
import cqu.edu.cn.ssosystem.model.User;

public class TokenControllerTest {
    public static void main (String[] args) throws Exception {
        DBConnection mysqlConnect = new DBConnection();
        Token myToken = new Token(mysqlConnect.getConn()); // 将用户数据库模型实例化
        User myUser = new User(mysqlConnect.getConn());// 将token的数据库模型实例化

        TokenController tokenControll = new TokenController(myUser, myToken);

        ResData tokenRes = tokenControll.checkToken("5A8F56141005CA2D13969003119qq", "qq");

        System.out.println(tokenRes.isError());
        System.out.println(tokenRes.getData());
    }
}
