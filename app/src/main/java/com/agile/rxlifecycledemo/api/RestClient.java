package com.agile.rxlifecycledemo.api;

import com.agile.rxlifecycledemo.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by agile-01 on 8/17/2016.
 */
public class RestClient {

    private static WebServices webServices;


    public static WebServices getWebServices() {
        if (webServices == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okHttpClientBuilder.writeTimeout(15, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(15, TimeUnit.SECONDS);

     /*       HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(loggingInterceptor);*/

            Retrofit retrofitBuilder = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
            webServices = retrofitBuilder.create(WebServices.class);
        }

        return webServices;
    }
}
