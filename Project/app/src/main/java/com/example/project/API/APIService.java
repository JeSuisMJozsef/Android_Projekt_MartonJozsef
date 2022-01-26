package com.example.project.API;

import com.example.project.models.AuthResp;
import com.example.project.models.Category;
import com.example.project.models.CategoryResp;
import com.example.project.models.Product;
import com.example.project.models.ProductResp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {
    @FormUrlEncoded
    @POST("login")
    Call<AuthResp> login(
      @Field("name") String name,
      @Field("password") String password
    );
    @FormUrlEncoded
    @POST("register")
    Call<AuthResp> register(
            @Field("name") String name,
            @Field("email") String email,
            @Field("role") String role,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation

    );
    @POST("logout")
    Call <AuthResp> logout(@Header("Authorization") String token);

    @GET("categories")
    Call<CategoryResp> getCategories(@Header("Authorization") String token);

    @GET("products")
    Call<ProductResp> getProducts(@Header("Authorization") String token);

    @GET("products/search/{name}")
    Call<ProductResp> getSearchResults(@Path("name") String name, @Header("Authorization") String token);

    @FormUrlEncoded
    @POST("products")
    Call<ProductResp> addProduct(
            @Header("Authorization") String token,
            @Field("name") String name,
            @Field("category_id") Integer cat_id,
            @Field("sku") String sku,
            @Field("price") Double price,
            @Field("packaging") String packaging,
            @Field("stock") Double stock
    );
}
