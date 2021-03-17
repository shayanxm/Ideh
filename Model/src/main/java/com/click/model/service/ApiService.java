package com.click.model.service;

import com.click.pc.Constant;
import com.click.pc.DataInfoModelResponse;
import com.click.pc.GenreModelResponse;
import com.click.pc.IdeaModelResponse;
import com.click.pc.ResponseModel;
import com.google.gson.JsonObject;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;
;import static com.click.pc.Constant.CHECK_CODE;
import static com.click.pc.Constant.DATA_INFO;
import static com.click.pc.Constant.GENRE_LIST;
import static com.click.pc.Constant.IDEA_LIST;
import static com.click.pc.Constant.LOGIN;
import static com.click.pc.Constant.REGISTER;
import static com.click.pc.Constant.REGISTER_IDEA;
import static com.click.pc.Constant.VERIFY_CODE;

/**
 * Created by Sadegh-Pc on 3/7/2018.
 */

public interface ApiService {

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST(VERIFY_CODE)
    Observable<ResponseModel> verifyCode(@Field("Mobile") String mobile);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST(CHECK_CODE)
    Observable<ResponseModel> checkCode(@Field("Mobile") String mobile, @Field("Code") String code);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST(REGISTER)
    Observable<ResponseModel> register(@Field("register_email") String email,
                                       @Field("register_username") String userName,
                                       @Field("register_password") String password,
                                       @Field("register_Mobile") String mobile,
                                       @Field("re_password") String retryPassword,
                                       @Field("code_Verifi") String verifyCode,
                                       @Field("Mobile_number_get_api") String apiMobile,
                                       @Field("Code_get_api") String apiVerifyCode);

    @FormUrlEncoded
    @Headers({"Accept: application/json"})
    @POST(LOGIN)
    Observable<ResponseModel> login(@Field("login_username") String userName,
                                    @Field("login_password") String password);


    @Headers({"Accept: application/json"})
    @POST(IDEA_LIST)
    Observable<IdeaModelResponse> getIdeaList(@Header("Auth-token") String token);

    @Headers({"Accept: application/json"})
    @POST(IDEA_LIST)
    Observable<IdeaModelResponse> getIdeaList(@Header("Auth-token") String token, @Body JsonObject params);

    @Headers({"Accept: application/json"})
    @GET(DATA_INFO)
    Observable<DataInfoModelResponse> getDataInfo(@Header("Auth-token") String token);

    @Headers({"Accept: application/json"})
    @GET(GENRE_LIST)
    Observable<GenreModelResponse> getGenreList(@Header("Auth-token") String token);

    @Headers({"Accept: application/json"})
    @POST(REGISTER_IDEA)
    Observable<ResponseModel> saveIdea(@Header("Auth-token") String token, @Body JsonObject params);
}
