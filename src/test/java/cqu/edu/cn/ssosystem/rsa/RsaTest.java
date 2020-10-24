package cqu.edu.cn.ssosystem.rsa;

import cqu.edu.cn.ssosystem.controller.TokenController;
import cqu.edu.cn.ssosystem.global.DBConnection;
import cqu.edu.cn.ssosystem.global.ResData;
import cqu.edu.cn.ssosystem.model.Token;
import cqu.edu.cn.ssosystem.model.User;

import java.math.BigInteger;
import java.util.ArrayList;

public class RsaTest {
    public static void main (String[] args) {
        Rsa myRsa = new Rsa();
        RsaKey newKey = myRsa.generateKey();
        System.out.println(newKey); // 测试生成key
        System.out.println(myRsa.searchKey(new Pair(newKey.getPublicKey(), newKey.getOla()))); // test search key

        // test the true key
        RsaKey myKey = new RsaKey(143, 103, 7);
        // test do lock
        ArrayList<BigInteger> Str = RsaKey.doLock("i am your father", myKey);
        for(BigInteger i: Str){
            System.out.print(i);
            System.out.print(",");
        }System.out.println();

        // test do unlock
        String res = RsaKey.openLock(Str, myKey);
        System.out.println(res);

        // test the generated key
        Str = RsaKey.doLock("i am your father", newKey);
        for(BigInteger i: Str){
            System.out.print(i);
            System.out.print(",");
        }System.out.println();

        // test do unlock
        res = RsaKey.openLock(Str, newKey);
        System.out.println(res);

    }
}
