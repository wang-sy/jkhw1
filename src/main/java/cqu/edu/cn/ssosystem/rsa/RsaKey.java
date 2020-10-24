package cqu.edu.cn.ssosystem.rsa;


import java.util.ArrayList;

/**
 * 描述一个RsaKey
 */
public class RsaKey {

    long publicKey = 0;
    long privateKey = 0;
    long mulNum = 0;

    /**
     * 初始化函数
     * @param publicKey 公钥
     * @param privateKey 私钥
     */
    public RsaKey(long mulNum, long publicKey, long privateKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.mulNum = mulNum;
    }

    /**
     * 获取公钥
     * @return 公钥， 一个长整数
     */
    public long getPublicKey() {
        return publicKey;
    }

    /**
     * 获取私钥
     * @return 私钥， 一个长整数
     */
    public long getPrivateKey() {
        return privateKey;
    }
    /**
     * 获取欧拉值
     * @return 私钥， 一个长整数
     */
    public long getOla() {
        return this.mulNum;
    }

    /**
     * 快速幂算法， 时间复杂度logn
     * @param base 底数
     * @param power 指数
     * @param mod 需要取模的数
     * @return 返回 base^power % mod的结果
     */
    private static long fastPower(long base, long power, long mod){
        long ans = 1;
        long cur = base % mod;
        while(power != 0){
            if(power % 2 == 1){
                ans = (ans * cur) % mod;
            }
            cur = (cur * cur) % mod;
            power /= 2;
        }
        return ans;
    }

    /**
     * Rsa 解密（服务端只需要进行解密即可） ，静态函数，可以直接掉
     * 暂时只支持解密字符串， 逐字节进行解密即可
     * @return 一个字符串，解密后的字符串
     */
    static public String  openLock(ArrayList<Long> lockedStr, RsaKey openKey){
        StringBuffer res = new StringBuffer();
        int len = lockedStr.size(); // 长度
        for(int i = 0; i < len; i++){ // 遍历字符串，逐字节解密
            Long curChar = lockedStr.get(i); // 获取当前位置字符
            res.append((char)fastPower(curChar, openKey.getPrivateKey(), openKey.getOla()));
        }

        return res.toString();
    }

    @Override
    public String toString() {
        return "RsaKey:{" +
                "publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                ", mulNum=" + mulNum +
                '}';
    }
}
