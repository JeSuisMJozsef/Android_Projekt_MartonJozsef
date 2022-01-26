package com.example.project.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;

    public Category() {
    }

    public Category(Integer id, String categoryName, String categoryDesc) {
        super();
        this.id = id;
        this.name = categoryName;
        this.desc = categoryDesc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return name;
    }

    public void setCategoryName(String categoryName) {
        this.name = categoryName;
    }

    public String getCategoryDesc() {
        return desc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.desc = categoryDesc;
    }

}
