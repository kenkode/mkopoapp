package com.example.lixnet.mkopo.retrofit;

/**
 * Created by Lixnet on 2017-08-22.
 */

import com.example.lixnet.mkopo.Constants;
import com.example.lixnet.mkopo.models.Loan;
import com.example.lixnet.mkopo.models.LoanType;
import com.example.lixnet.mkopo.models.MyLoans;
import com.example.lixnet.mkopo.models.Sms;
import com.example.lixnet.mkopo.models.User;
import com.example.lixnet.mkopo.models.UserAuth;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET(Constants.ADD_USER)
    Call<UserAuth> addUser(@Query("user") String user);

    @GET(Constants.AUTH_USER)
    Call<UserAuth> authUser(@Query("username") String username, @Query("password") String password);

    @GET(Constants.LOAN_TYPES)
    Call<List<LoanType>> getLoanTypes();

    @GET(Constants.LOAN_DETAILS)
    Call<MyLoans> getLoanDetails(@Query("id") int id );

    @GET(Constants.LOAN_HISTORY)
    Call<List<Loan>> getLoanHistory(@Query("id") String user_id);

    @GET(Constants.LOAN_STATUS)
    Call<List<Loan>> getLoanHistory(@Query("id") int user_id);

    @GET(Constants.GET_LOANS)
    Call<List<MyLoans>> getAllLoans(@Query("user_id") String user_id);

    @GET(Constants.GET_LOAN)
    Call<List<Loan>> getLoan(@Query("id") int user_id);

    @GET(Constants.SAVE_SMS)
    Call<Sms> saveSMS(@Query("sms") String sms);

    @GET(Constants.USER_DETAILS)
    Call<List<User>> getUser(@Query("id") int user_id);

    @GET(Constants.UPDATE_USER)
    Call<List<User>> updateUser(@Query("id") int user_id, @Query("email") String email, @Query("phone") String phone);

    @GET(Constants.UPDATE_PASSWORD)
    Call<List<User>> updatePassword(@Query("id") int user_id, @Query("password") String password);

}
