package cqu.edu.cn.ssosystem.rsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;

/**
 * 描述素数的类，可以生成素数表，并且随机选择素数
 * 生成素数的方法：欧式筛素数法
 * 生成的素数存储在: primeNumberList之中，该变量不是一个静态变量所以请不要实例化过多的对象
 */
public class PrimeNumber {
    ArrayList<Integer> primeNumberList= null;

    private long exgcdX = 0;
    private long exgcdY = 0;

    /**
     * 初始化函数，给定一个范围,用于生成一个素数表，
     * @param to 素数的最大范围，会生成2~to范围内的素数
     */
    public PrimeNumber(int to) {

        this.primeNumberList = new ArrayList<Integer>();

        boolean[] isPrime = new boolean[to + 1];
        Arrays.fill(isPrime, true);
        for(int i = 2; i <= to; i++){
            if(isPrime[i]){// 如果是素数，就直接添加，并且向后筛
                this.primeNumberList.add(i);
                for(int j = i + i; j <= to; j += i){ // 将所有后面的倍数筛掉
                    isPrime[j] = false;
                }
            }
        }
    }

    /**
     * 从生成的素数表中随机获取一个素数
     * @return 整型变量，一定是一个素数
     */
    public int getPrimeNumber(){
        // 人为控制随机出来的素数不会太小
        int index = Math.min((int) (Math.random() * this.primeNumberList.size()) - 1 + 20, this.primeNumberList.size() - 1);
        return this.primeNumberList.get(index);
    }

    /**
     * 二分查找函数， 定义在这里并不规范，但是我懒得管了
     * 寻找比goalNumber小的里面最大的
     * @param goalNumber 寻找比这个数字小的里面最大的
     * @return 返回一个index
     */
    private int binarySearch(long goalNumber){
        int left = 0, right = this.primeNumberList.size() - 1;
        while(left <= right){
            int mid = (left  + right) / 2;
            if(this.primeNumberList.get(mid) == goalNumber) return mid - 1;
            if(this.primeNumberList.get(mid) < goalNumber) left = mid + 1;
            else right = mid - 1;
        }
        return left;
    }

    /**
     * 扩展欧几里得算法
     */
    private void exgcd(long a,long b){
        if(b == 0){
            this.exgcdX = 1;
            this.exgcdY = 0;
            return ;
        }
        exgcd(b,a%b);
        long x1 = this.exgcdX;
        this.exgcdX = this.exgcdY;
        this.exgcdY = x1 - (a / b) * this.exgcdY;
    }

    /**
     * 获取rsa密钥中的d
     * @param e 介于0和欧拉之间，与欧拉互质的数
     * @param ola 两随机素数之乘积的欧拉
     * @return 计算出来的d
     */
    public long getRsaD(long e, long ola){
        this.exgcd(e, ola);// exgcdX * e + ola * e = gcd(e, ola) = 1
        // exgcdx = D, 接下来转化为最小正整数解
        return ((this.exgcdX % ola) + ola) % ola;
    }

    /**
     * 从生成的素数表中随机获取一个素数，生成的素数一定小于maxNumber
     * @param maxNumber  能够生成的最大的素数
     * @return 整型变量，一定是一个素数
     */
    public int getPrimeNumber(long maxNumber){
        // 先二分查找，然后再在二分查找的范围内进行随机即可
        int maxIndex = this.binarySearch(maxNumber);
        int index =  Math.min((int) (Math.random() * maxIndex) - 1 + 2, maxIndex - 1);
        return this.primeNumberList.get(index);
    }
}
