package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.project.API.APIService;
import com.example.project.API.RetrofitClient;
import com.example.project.models.AuthResp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button categories, products, logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        categories = (Button) findViewById(R.id.getCategoryesButt);
        products = (Button) findViewById(R.id.productsButt);
        logout = (Button) findViewById(R.id.logOutbutton);

        categories.setOnClickListener(this);
        products.setOnClickListener(this);
        logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == categories) {
            startActivity(new Intent(getApplicationContext(),CategoriesActivity.class));
        } else if (view == products) {
            startActivity(new Intent(getApplicationContext(), ProductActivity.class));

        } else if (view == logout) {
            String token = getSharedPreferences("bearer", 0).getString("token", "");
            APIService service = RetrofitClient.getRetrofitInstance().create(APIService.class);
            Call<AuthResp> call = service.logout("Bearer " + token);
            call.enqueue(new Callback<AuthResp>() {
                @Override
                public void onResponse(Call<AuthResp> call, Response<AuthResp> response) {
                    if (response.body().getSuccess()) {
                        Toast.makeText(getApplicationContext(), "Logged out!!!", Toast.LENGTH_SHORT).show();
                        SharedPreferences settings = getSharedPreferences("bearer", 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.clear();
                        editor.apply();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<AuthResp> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}