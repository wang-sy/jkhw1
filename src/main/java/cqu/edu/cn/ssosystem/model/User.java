package cqu.edu.cn.ssosystem.model;

import cqu.edu.cn.ssosystem.global.ResData;

import java.lang.reflect.Array;
import java.lang.reflect.Member;
import java.sql.*;
import java.util.ArrayList;


/**
 * 用户的数据库模型,主要用于用户登录验证
 * @author wangsy
 */
public class User {


    private Connection conn = null;

    // 创建用户模型时链接数据库
    public User(Connection newConn) throws Exception {
        this.conn = newConn;
    }

    /**
     * 用户登录时的查询方法
     * @param userPhoneNumber 用户给出的手机号码
     * @param userPassword 用户给出的密码
     * @return 返回一个列表，代表查询到的用户的user_nick_name
     * @throws SQLException SQL发出的异常
     */
    public ResData doLoginCheck(String userPhoneNumber, String userPassword) throws SQLException {
        String sql =  "SELECT user_nick_name FROM user_table " +
                "where user_password = ? and user_phone_number = ?;";

        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, userPassword);
        ptmt.setString(2, userPhoneNumber);

        ResultSet rs = ptmt.executeQuery();

        ResData res = null;

        ArrayList<String> resList = ModelTools.getStrings(rs, "user_nick_name");
        if(resList.size() == 0){
            res = new ResData(true, "用户名或密码错误");
            return res;
        }
        res = new ResData(false, resList.get(0));
        return res;
    }

    /**
     * 通过用户的phoneNumber查询用户的nickName
     * @param userPhoneNumber 用户的手机号
     * @return ArrayList\<String\>， 用户的nickName
     */
    public ResData getUsetNickName(String userPhoneNumber) throws SQLException {
        String sql =  "SELECT user_nick_name FROM user_table " +
                "where user_phone_number = ?;";

        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, userPhoneNumber);

        ResultSet rs = ptmt.executeQuery();
        ResData res = null;

        ArrayList<String> resList = ModelTools.getStrings(rs, "user_nick_name");
        if(resList.size() == 0){
            res = new ResData(true, "验证的用户不存在");
        }
        res = new ResData(false, resList.get(0));

        return res;
    }
}