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
import com.example.project.models.ProductResp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        String token= getSharedPreferences("bearer",0).getString("token","");

        EditText prodNameET = (EditText) findViewById(R.id.prodnameEditText);
        EditText prodCatET = (EditText) findViewById(R.id.prodcategory1EditText);
        EditText prodSkuET = (EditText) findViewById(R.id.prodskuEditText);
        EditText prodPriceET = (EditText) findViewById(R.id.prodpriceEditText);
        EditText prodPackagingET = (EditText) findViewById(R.id.prodpackagingEditText);
        EditText prodStockET = (EditText) findViewById(R.id.prodstockEditText);

        Button addProduct = findViewById(R.id.prodaddButt);
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String prodName = prodNameET.getText().toString();
                Integer prodCat = prodCatET.getText().toString().isEmpty()?0: Integer.valueOf(prodCatET.getText().toString());
                String prodSku = prodSkuET.getText().toString();
                Double prodPrice = prodPriceET.getText().toString().isEmpty()?-1:Double.valueOf(prodPriceET.getText().toString());
                String prodPackaging = prodPackagingET.getText().toString();
                Double prodStock = prodStockET.getText().toString().isEmpty()?-1:Double.valueOf(prodStockET.getText().toString());
                APIService service = RetrofitClient.getRetrofitInstance().create(APIService.class);
                Call<ProductResp> call = service.addProduct("Bearer "+token,prodName, prodCat, prodSku, prodPrice, prodPackaging, prodStock);
                call.enqueue(new Callback<ProductResp>() {
                    @Override
                    public void onResponse(Call<ProductResp> call, Response<ProductResp> response) {
                        if (!response.body().isSuccess()) {
                            if (response.body().getName() != null)
                                prodNameET.setError(response.body().getName());
                            if (response.body().getCategoryId() != null) {
                                prodCatET.setError(response.body().getCategoryId());
                            }
                            if (response.body().getSku() != null) {
                                prodSkuET.setError(response.body().getSku());
                            }
                            if (response.body().getPackaging() != null) {
                                prodPackagingET.setError(response.body().getPackaging());
                            }
                            if (response.body().getPrice() != null) {
                                prodPriceET.setError(response.body().getPrice());
                            }
                            if (response.body().getStock() != null) {
                                prodStockET.setError(response.body().getStock());
                            }

                        } else if (response.body().isSuccess()) {
                            startActivity(new Intent(getApplicationContext(), ProductActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductResp> call, Throwable t) {

                    }
                });


            }
        });

    }


}