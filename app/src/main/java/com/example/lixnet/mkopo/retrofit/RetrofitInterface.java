package com.example.lixnet.mkopo.retrofit;

/**
 * Created by Lixnet on 2017-08-22.
 */

import com.example.lixnet.mkopo.Constants;
import com.example.lixnet.mkopo.models.Loan;
import com.example.lixnet.mkopo.models.LoanType;
import com.example.lixnet.mkopo.models.Loanhistory;
import com.example.lixnet.mkopo.models.Loanstatus;
import com.example.lixnet.mkopo.models.MyLoans;
import com.example.lixnet.mkopo.models.Password;
import com.example.lixnet.mkopo.models.Profile;
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

    @GET(Constants.APPLY_LOAN)
    Call<Loan> applyLoan(@Query("loanee_id") String loanee_id, @Query("amount") double amount);

    @GET(Constants.LOAN_TYPES)
    Call<List<LoanType>> getLoanTypes();

    @GET(Constants.LOAN_HISTORY)
    Call<List<Loanhistory>> getLoanHistory(@Query("loan_id") int loan_id);

    @GET(Constants.LOAN_STATUS)
    Call<List<Loanstatus>> getLoanStatus(@Query("user_id") String user_id);

    @GET(Constants.GET_LOANS)
    Call<List<MyLoans>> getAllLoans(@Query("user_id") String user_id);

    @GET(Constants.GET_BALANCE)
    Call<Loanhistory> getBalance(@Query("id") int id);

    @GET(Constants.GET_APPROVED_LOANS)
    Call<List<MyLoans>> getApprovedLoans(@Query("user_id") String user_id);

    @GET(Constants.SAVE_SMS)
    Call<Sms> saveSMS(@Query("sms") String sms);

    @GET(Constants.UPDATE_USER)
    Call<UserAuth> updateUser(@Query("user") String user);

    @GET(Constants.UPDATE_PASSWORD)
    Call<Password> updatePassword(@Query("id") String id, @Query("current_password") String current_password, @Query("confirm_password") String confirm_password);

}
