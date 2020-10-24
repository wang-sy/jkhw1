package cqu.edu.cn.ssosystem.rsa;


import java.math.BigInteger;
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
    private static BigInteger fastPower(BigInteger base, BigInteger power, BigInteger mod){
        BigInteger ans = BigInteger.valueOf(1);
        BigInteger cur = base.mod(mod);
        while(!power.equals(BigInteger.ZERO)){
            if(power.and(BigInteger.ONE).equals(BigInteger.ONE)){
                ans = (ans.multiply(cur).mod(mod));
            }
            cur = cur.multiply(cur).mod(mod);
            power = power.shiftRight(1); // div2
        }
        return ans;
    }

    /**
     * Rsa 解密（服务端只需要进行解密即可） ，静态函数，可以直接掉
     * 暂时只支持解密字符串， 逐字节进行解密即可
     * @return 一个字符串，解密后的字符串
     */
    static public String  openLock(ArrayList<BigInteger> lockedStr, RsaKey openKey){
        StringBuffer res = new StringBuffer();
        int len = lockedStr.size(); // 长度
        for(int i = 0; i < len; i++){ // 遍历字符串，逐字节解密
            BigInteger curChar = lockedStr.get(i); // 获取当前位置字符
            System.out.println("dounlock::: curchar = " + curChar.toString() + "privatekey::" + BigInteger.valueOf(openKey.getPrivateKey()).toString() + "mul ::" + BigInteger.valueOf(openKey.getOla()).toString());
            System.out.println("pow = " + fastPower(curChar, BigInteger.valueOf(openKey.getPrivateKey()), BigInteger.valueOf(openKey.getOla())).toString());
            res.append((char)fastPower(curChar, BigInteger.valueOf(openKey.getPrivateKey()), BigInteger.valueOf(openKey.getOla())).intValue());
        }

        return res.toString();
    }

    static public ArrayList<BigInteger> doLock(String beforeLockStr, RsaKey lockKey){
        ArrayList<BigInteger> res = new ArrayList<BigInteger>();
        int len = beforeLockStr.length();
        for(int i = 0; i < len; i++){
            BigInteger curChar = BigInteger.valueOf(beforeLockStr.charAt(i));
            System.out.println("doclock::: curchar = " + curChar.toString() + "publicKey::" + BigInteger.valueOf(lockKey.getPublicKey()).toString() + "mul ::" + BigInteger.valueOf(lockKey.getOla()).toString());
            System.out.println("pow = " + fastPower(curChar, BigInteger.valueOf(lockKey.getPublicKey()), BigInteger.valueOf(lockKey.getOla())).toString());
            res.add(fastPower(curChar, BigInteger.valueOf(lockKey.getPublicKey()), BigInteger.valueOf(lockKey.getOla())));
        }
        return res;
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
