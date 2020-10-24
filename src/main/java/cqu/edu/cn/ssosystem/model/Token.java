package cqu.edu.cn.ssosystem.model;

import cqu.edu.cn.ssosystem.global.ResData;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * 完成对令牌相关的数据库操作的模型类
 * @author wangsy
 */
public class Token {
    private Connection conn = null;

    // 创建用户模型时链接数据库
    public Token(Connection newConn) throws Exception {
        this.conn = newConn;
    }

    /**
     * 获取16进制随机数
     * @param len 想要生成的十六进制随机数的长度
     * @return
     */
    private String randomHexString(int len) {
        try {
            // 逐步生成16进数字
            StringBuffer result = new StringBuffer();
            for(int i=0;i<len;i++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            return result.toString().toUpperCase();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 用户登陆后，为用户生成令牌，使用时，需提供电话号码以及appid，将建立用户在对应平台的token，相同用户更换平台后无法使用token
     * @param userPhoneNumber 登录验证成功的用户的手机号码
     * @param appId 用户登录的app的编码
     * @return 返回一个字符串，表示用户生成的令牌
     * @throws SQLException SQL发出的异常
     */
    private String generateToken(String userPhoneNumber, String appId) throws SQLException {
        // 随机生成一个256位的十六进制数字,然后将生成的十六进制数字与用户的手机号和appId做拼接即可
        return randomHexString(16) + userPhoneNumber + appId;
    }


    /**
     * 删除token， 在更新时使用，将原有的token删除，然后再添加新的token
     * @param userPhoneNumber 用户的手机号
     * @param appId appid
     */
    private void deleteToken(String userPhoneNumber, String appId) throws SQLException {
        String sqlDeleteOldToken = "DELETE FROM token_table WHERE user_phone_number = ? and app_id = ?;";
        PreparedStatement ptmtDeleteOldToken = this.conn.prepareStatement(sqlDeleteOldToken);

        // insert base information
        ptmtDeleteOldToken.setString(1, userPhoneNumber);
        ptmtDeleteOldToken.setString(2, appId);

        ptmtDeleteOldToken.execute();
    }

    /**
     * inster token
     * @param userPhoneNumber user's phone number
     * @param appId the appid of the app which user want to login
     * @return return the token for user && appid
     */
    public ResData insertToken(String userPhoneNumber, String appId) throws SQLException {

        deleteToken(userPhoneNumber, appId);

        String newToken = generateToken(userPhoneNumber, appId); // generate new token

        String sql = "INSERT INTO token_table " +
                "(user_phone_number, app_id, user_token) VALUES" +
                "(?, ?, ?);";
        PreparedStatement ptmt = this.conn.prepareStatement(sql);

        ptmt.setString(1, userPhoneNumber);
        ptmt.setString(2, appId);
        ptmt.setString(3, newToken);

        ptmt.execute();

        return new ResData(false, newToken);
    }


    /**
     * 根据用户的token查询用户是否登录
     * @param submitToken app服务器向sso服务器提交的token
     * @param appId app的号码
     * @throws SQLException
     */
    public ResData checkToken(String submitToken, String appId) throws SQLException {
        String sql = "SELECT * FROM token_table WHERE user_token = ? and app_id = ?;";

        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, submitToken);
        ptmt.setString(2, appId);
        ResultSet rs = ptmt.executeQuery();

        ArrayList<String> res = ModelTools.getStrings(rs, "user_phone_number");

        if(res.size() != 1) {
            return new ResData(true, "该用户未登录");
        }

        return new ResData(false, res.get(0));
    }


}
