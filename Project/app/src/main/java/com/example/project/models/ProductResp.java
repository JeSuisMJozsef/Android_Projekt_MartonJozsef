package com.example.project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResp {
    @Expose
    @SerializedName("success")
    public boolean success;
    @Expose
    @SerializedName("products")
    public List<Product> products;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("packaging")
    @Expose
    private String packaging;
    @SerializedName("stock")
    @Expose
    private String stock;

    public ProductResp() {
    }

    public ProductResp(boolean success, List<Product> products, String categoryId, String name, String sku, String price, String packaging, String stock) {
        this.success = success;
        this.products = products;
        this.categoryId = categoryId;
        this.name = name;
        this.sku = sku;
        this.price = price;
        this.packaging = packaging;
        this.stock = stock;
    }

    public ProductResp(boolean success, List<Product> products) {
        this.success = success;
        this.products = products;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getCategoryId() {
        return categoryId;
    }


    public String getName() {
        return name;
    }


    public String getSku() {
        return sku;
    }


    public String getPrice() {
        return price;
    }


    public String getPackaging() {
        return packaging;
    }


    public String getStock() {
        return stock;
    }
}
