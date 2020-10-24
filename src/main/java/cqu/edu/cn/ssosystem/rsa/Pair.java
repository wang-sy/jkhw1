package cqu.edu.cn.ssosystem.rsa;

import java.util.Objects;

public class Pair {
    private Long first;
    private Long second;
    public Pair(Long first, Long second){
        this.first = first;
        this.second = second;
    }

    /**
     * 重写了判断相等的方法，即判断其中每一个对象相同
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return Objects.equals(first, pair.first) &&
                Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
