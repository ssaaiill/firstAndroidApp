package me.zhf.myapplication.API;

import me.zhf.myapplication.model.ipmodel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ZF on 2016/4/13.
 */
public interface ipapi {
    @GET("/service/getIpInfo.php")
    void getIpInfo(@Query("ip") String ip, Callback<ipmodel> response);
}
