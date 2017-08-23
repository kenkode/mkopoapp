package com.example.lixnet.mkopo.retrofit;

/**
 * Created by Lixnet on 2017-08-22.
 */

import com.example.lixnet.mkopo.Constants;
import com.example.lixnet.mkopo.models.UserAuth;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET(Constants.ADD_USER)
    Call<UserAuth> addUser(@Query("user") String user);

    @GET(Constants.AUTH_USER)
    Call<UserAuth> authUser(@Query("username") String username, @Query("password") String password);

}
