package cqu.edu.cn.ssosystem.rsa;

import com.sun.tools.javac.util.Pair;
import cqu.edu.cn.ssosystem.controller.TokenController;
import cqu.edu.cn.ssosystem.global.DBConnection;
import cqu.edu.cn.ssosystem.global.ResData;
import cqu.edu.cn.ssosystem.model.Token;
import cqu.edu.cn.ssosystem.model.User;

public class RsaTest {
    public static void main (String[] args) throws Exception {
        Rsa myRsa = new Rsa();
        RsaKey newKey = myRsa.generateKey();
        System.out.println(newKey); // 测试生成key
        System.out.println(myRsa.searchKey(new Pair<Long, Long>(newKey.getPublicKey(), newKey.getOla())));



    }
}
