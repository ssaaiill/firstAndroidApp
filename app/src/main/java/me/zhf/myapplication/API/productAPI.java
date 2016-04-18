package me.zhf.myapplication.API;

import java.util.List;

import me.zhf.myapplication.model.Qhtwxproduct;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ZF on 2016/4/17.
 */
public interface productAPI {
    @GET("/business/main/product/list")
    void getProduct(@Query("ptype") String ptype, Callback<List<Qhtwxproduct>> response);
}
