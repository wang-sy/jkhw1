package cqu.edu.cn.ssosystem.controller;

import cqu.edu.cn.ssosystem.global.DBConnection;
import cqu.edu.cn.ssosystem.global.ResData;
import cqu.edu.cn.ssosystem.model.Token;
import cqu.edu.cn.ssosystem.model.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 用于用户的登录操作控制器
 * @author wangsy
 */
public class LoginController {
    private User userModel = null;
    private Token tokenModel = null;

    private static  LoginController GlobalLoginController = null;

    /**
     * 构造函数，初始化的过程中需要给出两个应用的模型
     * @param newUserModel User Database Model
     * @param newTokenModel Token Database Model
     */
    public LoginController (User newUserModel, Token newTokenModel){

        // 初始化模型
        this.userModel = newUserModel;
        this.tokenModel = newTokenModel;

    }

    public static void createGlobalLoginController() throws Exception {
        DBConnection mysqlConnect = new DBConnection();
        Token myToken = new Token(mysqlConnect.getConn()); // 将用户数据库模型实例化
        User myUser = new User(mysqlConnect.getConn());// 将token的数据库模型实例化
        setGlobalLoginController(new LoginController(myUser, myToken));
    }

    public static void setGlobalLoginController(LoginController globalLoginController) {
        GlobalLoginController = globalLoginController;
    }

    public static LoginController getGlobalLoginController() {
        return GlobalLoginController;
    }

    /**
     * 用户登录的操作
     * @param userPhoneNumber 用户给出的手机号码
     * @param userPassword 用户给出的密码
     * @param appId 用户当前登录的站点
     * @throws SQLException
     */
    public ResData doLogin(String userPhoneNumber, String userPassword, String appId) throws SQLException {

        ResData logResData = this.userModel.doLoginCheck(userPhoneNumber, userPassword); // 进行登录验证
        if(logResData.isError()) return logResData;
        return this.tokenModel.insertToken(userPhoneNumber, appId);

    }

}
