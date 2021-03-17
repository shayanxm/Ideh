package com.click.model.service;


import static com.click.pc.Constant.BASE_URL;
import static com.click.pc.Constant.VERSION;

/**
 * Created by Sadegh-Pc on 3/7/2018.
 */

public class ApiUtil {


    public static ApiService getApiService() {
        return RetrofitClient.getClient(BASE_URL + VERSION).create(ApiService.class);
    }
}
