package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.project.API.APIService;
import com.example.project.API.RetrofitClient;
import com.example.project.models.AuthResp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        Button login = (Button) findViewById(R.id.loginButt);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                APIService service = RetrofitClient.getRetrofitInstance().create(APIService.class);
                Call<AuthResp> call = service.login(username, password);
                call.enqueue(new Callback<AuthResp>() {

                    @Override
                    public void onResponse(Call<AuthResp> call, Response<AuthResp> response) {
                        if (!response.body().getSuccess()) {
                            if (response.body().getName() != null)
                                usernameEditText.setError(response.body().getName());
                            if (response.body().getPassword() != null) {
                                passwordEditText.setError(response.body().getPassword());
                            }
                            if (response.body().getName() == null && response.body().getPassword() == null) {
                                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                            }

                        } else if (response.body().getSuccess()) {
                            SharedPreferences settings = getSharedPreferences("bearer", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("token", response.body().getToken());
                            editor.apply();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResp> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
//                }
            }
        });
    }
}