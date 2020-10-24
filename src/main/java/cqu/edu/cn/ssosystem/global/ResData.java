package cqu.edu.cn.ssosystem.global;

/**
 * 用于管理返回的数据的对象
 */
public class ResData {
    private boolean error;
    private String data;

    public ResData(boolean haveError, String newData){
        this.error = haveError;
        this.data = newData;
    }

    public String getData() {
        return data;
    }

    public boolean isError() {
        return error;
    }
}
