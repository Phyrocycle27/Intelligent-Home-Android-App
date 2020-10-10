package com.example.application.internet;

import com.example.application.internet.deserializer.ProcessingObjectTypeAdapter;
import com.example.application.internet.deserializer.TriggerObjectTypeAdapter;
import com.example.application.models.task.processing.objects.ProcessingObject;
import com.example.application.models.task.trigger.objects.TriggerObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final String API_BASE_URL = "http://192.168.1.75:8080/";

    private static final RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory
            .createWithScheduler(Schedulers.io());

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ProcessingObject.class, new ProcessingObjectTypeAdapter())
            .registerTypeAdapter(TriggerObject.class, new TriggerObjectTypeAdapter())
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addCallAdapterFactory(rxAdapter)
            .addConverterFactory(GsonConverterFactory.create(gson));

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static final HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static final Retrofit retrofit;

    static {
        httpClient.addInterceptor(logging)
                .addInterceptor(chain -> {
                    Request newRequest =
                            chain.request().newBuilder()
                                    .addHeader("Accept", "application/json")
                                    .addHeader("Content-Type", "application/json")
                                    .build();

                    return chain.proceed(newRequest);
                });
        builder.client(httpClient.build());
        retrofit = builder.build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
