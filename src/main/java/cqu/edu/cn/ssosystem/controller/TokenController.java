package cqu.edu.cn.ssosystem.controller;

import cqu.edu.cn.ssosystem.global.DBConnection;
import cqu.edu.cn.ssosystem.global.ResData;
import cqu.edu.cn.ssosystem.model.Token;
import cqu.edu.cn.ssosystem.model.User;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Token控制器，这里主要负责使用token进行登录验证
 */
public class TokenController {


    private User userModel = null;
    private Token tokenModel = null;

    private static  TokenController GlobalTokenController = null;


    public static TokenController getGlobalTokenController() {
        return GlobalTokenController;
    }
    public static void createGlobalTokenController() throws Exception {
        DBConnection mysqlConnect = new DBConnection();
        Token myToken = new Token(mysqlConnect.getConn()); // 将用户数据库模型实例化
        User myUser = new User(mysqlConnect.getConn());// 将token的数据库模型实例化
        GlobalTokenController = new TokenController(myUser, myToken);
    }

    /**
     * 构造函数，初始化的过程中需要给出两个应用的模型
     * @param newUserModel User Database Model
     * @param newTokenModel Token Database Model
     */
    public TokenController (User newUserModel, Token newTokenModel){

        // 初始化模型
        this.userModel = newUserModel;
        this.tokenModel = newTokenModel;

    }

    /**
     * 通过token验证用户是否登录，同时获取用户的nickname
     * @param userToken 用户的token
     * @param appId 用户的id
     * @return
     */
    public ResData checkToken(String userToken, String appId) throws SQLException {
        ResData resToken = this.tokenModel.checkToken(userToken, appId);
        if(resToken.isError()){// 查询结构有误
            return new ResData(true, "给出令牌有误");
        }
        ResData resUser = this.userModel.getUsetNickName(resToken.getData());

        return resUser;
    }
}
