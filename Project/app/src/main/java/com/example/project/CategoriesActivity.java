package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project.API.APIService;
import com.example.project.API.RetrofitClient;
import com.example.project.adadapters.CategoryAdapter;
import com.example.project.models.Category;
import com.example.project.models.CategoryResp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        List<Category> categories= new ArrayList<>();

        String token= getSharedPreferences("bearer",0).getString("token","");
        APIService service= RetrofitClient.getRetrofitInstance().create(APIService.class);

        Call<CategoryResp> call=service.getCategories("Bearer "+token);
        call.enqueue(new Callback<CategoryResp>() {
            @Override
            public void onResponse(Call<CategoryResp> call, Response<CategoryResp> response) {
                if(response.body().isSuccess()){
                    categories.addAll(response.body().getCategories());
                    if (categories.isEmpty()){
                        Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        RecyclerView rvCategories=findViewById(R.id.rvCategories);
                        rvCategories.setHasFixedSize(true);
                        rvCategories.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        CategoryAdapter adapter=new CategoryAdapter(getApplicationContext(),categories);
                        rvCategories.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<CategoryResp> call, Throwable t) {

            }
        });
    }
}