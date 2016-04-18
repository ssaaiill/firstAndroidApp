package me.zhf.myapplication.API;

import me.zhf.myapplication.model.gitmodel;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by ZF on 2016/4/13.
 */
public interface gitapi {
    // here is the other url part.best way is to start using /
    @GET("/users/{user}")
    void getFeed(@Path("user") String user, Callback<gitmodel> response);
    // string user is for passing values from edittext for eg: user=basil2style,google
    // response is the response from the server which is now in the POJO
}
