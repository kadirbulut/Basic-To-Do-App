package com.todoapp.Helper;

import com.todoapp.Services.ApiServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static Retrofit retrofit;
    private static String BASE_URL="https://todo.ly/api/";

    public static Retrofit getRetrofitInstance(){

        if (retrofit == null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static ApiServices getApiServices(){
        return getRetrofitInstance().create(ApiServices.class);
    }
}
