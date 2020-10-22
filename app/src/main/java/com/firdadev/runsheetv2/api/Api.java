package com.firdadev.runsheetv2.api;

import com.firdadev.runsheetv2.model.DefaultResponse;
import com.firdadev.runsheetv2.model.LoginResponse;
import com.firdadev.runsheetv2.model.courier.CourierResponse;
import com.firdadev.runsheetv2.model.prarunsheet.GeneratePraResponse;
import com.firdadev.runsheetv2.model.runsheet.GenerateRunsheetNoResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    //Default Interface API for Request API Mobile Runsheet
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> auth(
            @Field("username") String userApi,
            @Field("api_key") String apiKey,
            @Field("user_id") String username,
            @Field("pass") String password);

    @FormUrlEncoded
    @POST("getcourier")
    Call<CourierResponse>getCourier(
            @Field("username") String usernameApi,
            @Field("api_key") String apiKey,
            @Field("zone_code") String zoneCode
    );
    //End

    //Start Interface API for Pra Runsheet
    @FormUrlEncoded
    @POST("insertpra")
    Call<DefaultResponse> prarunsheet(
            @Field("username") String usernameApi,
            @Field("api_key") String api_key,
            @Field("PRA_RUNSHEET_NO") String generateNumberPra,
            @Field("BRANCH") String kodeCbg,
            @Field("PRA_RUNSHEET_DATE") String date,
            @Field("COURIER_ZONE") String zoneN,
            @Field("NOTICE") String remark,
            @Field("USER_ID") String username,
            @Field("CNOTE_NO") String connoteNo,
            @Field("COURIER_ID") String courierId
    );

    @FormUrlEncoded
    @POST("generate")
    Call<GeneratePraResponse> generatePra (
            @Field("username") String usernameApi,
            @Field("api_key") String apiKey,
            @Field("branch") String kodeCabang
    );
    //End

    //Start Interface API for Runsheet
    /**
     * Created by Firda on 27/07/2020.
     */
    @FormUrlEncoded
    @POST("")
    Call<GenerateRunsheetNoResponse> generateRunsheet (
            @Field("username") String usernameApi,
            @Field("api_key") String apiKey,
            @Field("branch") String kodeCabang
    );

    //End

}
