package com.example.lixnet.mkopo.retrofit;

import com.example.lixnet.mkopo.Constants;
import com.example.lixnet.mkopo.models.Pojodemo;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.Call;

/**
 * Created by Lixnet on 2017-08-29.
 */

public interface LoanAPI {
    @FormUrlEncoded
    @POST(Constants.APPLY_LOAN)
    Call<Pojodemo> serverCall(

            @Field("user_id") String user_id,
            @Field("amount") double amount );
}
