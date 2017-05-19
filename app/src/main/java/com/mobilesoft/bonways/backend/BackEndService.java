package com.mobilesoft.bonways.backend;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mobilesoft.bonways.core.models.Category;
import com.mobilesoft.bonways.core.models.Product;
import com.mobilesoft.bonways.core.models.User;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BackEndService {

    int DEFAULT_PAGE = 0;
    int DEFAULT_SIZE = 30;

    //    String APP_URL = "http://libre-exchange.awswouri.com";
//    String APP_URL = "http://192.168.43.228:9898";
//    String APP_URL = "http://192.168.43.107:8090/";
    String APP_URL = "http://192.168.137.1:8090/";
//    String APP_URL = "http://34.193.132.208:9898";


    JsonDeserializer<Date> deserializer = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
            return json == null ? null : new Date(json.getAsLong());
        }
    };

    Gson gson = new GsonBuilder()
            .setLenient()
            .registerTypeAdapter(Date.class, deserializer)
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .client(Credentials.getClient())
            .baseUrl(APP_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    @GET("user/")
    Call<User> getUser();

    @POST("user/")
    Call<User> createUser(@Body User user);

    @GET("product/")
    Call<Product[]> getProduct();

    @GET("category/")
    Call<Category[]> getCategories();


}
