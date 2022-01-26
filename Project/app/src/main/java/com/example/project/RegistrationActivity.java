package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Button register = (Button) findViewById(R.id.prodaddButt);
        EditText nameET = ((EditText) findViewById(R.id.prodnameEditText));
        EditText emailET = ((EditText) findViewById(R.id.emailEditText));
        EditText passET = ((EditText) findViewById(R.id.passEditText));
        EditText passConfET = ((EditText) findViewById(R.id.passConfEditText));
        EditText roleET = ((EditText) findViewById(R.id.prodcategotyEditText));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameET.getText().toString().trim();
                String email = emailET.getText().toString().trim();
                String pass = passET.getText().toString().trim();
                String passConf = passConfET.getText().toString().trim();
                String role = roleET.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                APIService service = RetrofitClient.getRetrofitInstance().create(APIService.class);
                Call<AuthResp> call = service.register(name, email, role, pass, passConf);
                call.enqueue(new Callback<AuthResp>() {
                    @Override
                    public void onResponse(Call<AuthResp> call, Response<AuthResp> response) {

                        if (!response.body().getSuccess()) {
                            if (response.body().getName() != null)
                                nameET.setError(response.body().getName());
                            if (response.body().getEmail() != null)
                                emailET.setError(response.body().getEmail());
                            if (response.body().getRole() != null){
                                roleET.setError(response.body().getRole());
                                Log.d("role",role);
                            }

                            if (response.body().getPassword() != null) {
                                passET.setError(response.body().getPassword());
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

            }

        });
    }
}