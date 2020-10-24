package cqu.edu.cn.ssosystem.rsa;




import java.util.HashMap;
import java.util.Map;

/**
 * rsa密钥类，会管理一个素数表
 */
public class Rsa {

    PrimeNumber myPrimeNumber = null;

    HashMap<Pair<Long, Long>, RsaKey> rsaTable = null; // 公钥-- > RSAKey

    /**
     * 初始化密钥类，管理一个素数表
     */
    public Rsa(){
        myPrimeNumber = new PrimeNumber(0x4fffff); // 约等于5e6的范围
        this.rsaTable = new HashMap<Pair<Long, Long>, RsaKey>(); // 哈希
    }

    /**
     * 随机生成一对密钥
     * @return 返回一个密钥，RsaKey类型的
     */
    public RsaKey generateKey(){
        long publicKeyNumber = 0;
        long privateKeyNumber = 0;
        long mulNum = 0;
        do {

            // 随机取两个素数
            long firstPrime = this.myPrimeNumber.getPrimeNumber();
            long secondPrime = this.myPrimeNumber.getPrimeNumber();

            // 计算乘积以及乘积的欧拉函数(这里的欧拉非常好算， 就直接两个数减一相乘即可，因为这两个数都是素数)
            long multi = firstPrime * secondPrime;
            long olaMulti = (firstPrime - 1) * (secondPrime - 1);

            // 获取一个中间值，与欧拉互质
            long e = this.myPrimeNumber.getPrimeNumber(olaMulti);
            // 计算 d * e = 1 (mod olaMulti) 这个问题可以转化成 d * e + k * ola = 1 的问题， 是一个exgcd
            long d = this.myPrimeNumber.getRsaD(e, olaMulti);
            // 到此为止，密钥获得完毕

            publicKeyNumber = e;
            privateKeyNumber = d;
            mulNum = multi;

        }while (this.rsaTable.containsKey(new Pair<Long, Long>(publicKeyNumber, mulNum))); // 做到这个不含有相同的公钥为止


        RsaKey insertKey = new RsaKey(mulNum, publicKeyNumber, privateKeyNumber);
        // 将钥匙插入
        this.rsaTable.put(new Pair<Long, Long>(publicKeyNumber, mulNum), insertKey);

        return insertKey; // 将钥匙返回
    }

    /**
     * 通过公钥查询私钥，如果查询不到私钥，那么就直接返回空指针
     * @param publicKey 用于查询的公钥
     * @return 查询到的私钥
     */
    RsaKey searchKey(Pair<Long, Long> publicKey){
        if(this.rsaTable.containsKey(publicKey)){ // 如果有相应的key，那么就直接返回
            return this.rsaTable.get(publicKey);
        }
        else return null; // 如果没有就返回空指针
    }



}
