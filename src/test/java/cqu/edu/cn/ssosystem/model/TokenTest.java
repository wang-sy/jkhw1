package cqu.edu.cn.ssosystem.model;

import cqu.edu.cn.ssosystem.global.DBConnection;
import cqu.edu.cn.ssosystem.global.ResData;

import java.util.ArrayList;

public class TokenTest {
    public static void main (String[] args) throws Exception {
        DBConnection mysqlConnect = new DBConnection();
        Token myToken = new Token(mysqlConnect.getConn()); // 将用户数据库模型实例化

        // 进行登录检测
        ResData newTokenRes = myToken.insertToken("13969003119", "qq");

        System.out.println(newTokenRes.getData());
        // check token
        ResData resList = myToken.checkToken(newTokenRes.getData(), "qq");
        System.out.println(resList.getData());

    }
}
