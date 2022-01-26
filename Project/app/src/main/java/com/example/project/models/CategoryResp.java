package com.example.project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResp {
    @Expose
    @SerializedName("success")
    public boolean success;
    @Expose
    @SerializedName("categories")
    public List<Category> categories;

    public CategoryResp() {
    }

    public CategoryResp(boolean success, List<Category> categories) {
        this.success = success;
        this.categories = categories;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
