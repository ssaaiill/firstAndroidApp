package me.zhf.myapplication.model;

/**
 * Created by ZF on 2016/4/13.
 */
public class ipmodel {
    private int code;

    private ipmodel_data data;

    public void setCode(int code){
        this.code = code;
    }
    public int getCode(){
        return this.code;
    }
    public void setData(ipmodel_data data){
        this.data = data;
    }
    public ipmodel_data getData(){
        return this.data;
    }
}
