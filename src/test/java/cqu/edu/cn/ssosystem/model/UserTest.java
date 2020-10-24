package cqu.edu.cn.ssosystem.model;


import cqu.edu.cn.ssosystem.global.DBConnection;
import cqu.edu.cn.ssosystem.global.ResData;

import java.util.ArrayList;

public class UserTest {

    public static void main (String[] args) throws Exception {
        DBConnection mysqlConnect = new DBConnection();
        User myUser = new User(mysqlConnect.getConn()); // 将用户数据库模型实例化

        // 进行登录检测
        ResData loginRes = myUser.doLoginCheck("13969003119", "wangsy1990085");

        System.out.println(loginRes.isError());
        System.out.println(loginRes.getData());
    }

}
