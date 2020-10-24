package cqu.edu.cn.ssosystem.controller;

import cqu.edu.cn.ssosystem.global.DBConnection;
import cqu.edu.cn.ssosystem.global.ResData;
import cqu.edu.cn.ssosystem.model.Token;
import cqu.edu.cn.ssosystem.model.User;
import cqu.edu.cn.ssosystem.controller.*;

import java.util.ArrayList;

public class LoginControllerTest {
    public static void main (String[] args) throws Exception {
        DBConnection mysqlConnect = new DBConnection();
        Token myToken = new Token(mysqlConnect.getConn()); // 将用户数据库模型实例化
        User myUser = new User(mysqlConnect.getConn());// 将token的数据库模型实例化

        LoginController LoginControll = new LoginController(myUser, myToken);

        ResData loginres = LoginControll.doLogin("13969003119", "wangsy1990085", "qq");
        if(!loginres.isError()) System.out.println(loginres.getData());
    }
}
