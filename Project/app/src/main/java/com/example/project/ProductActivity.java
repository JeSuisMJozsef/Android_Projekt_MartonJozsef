package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project.API.APIService;
import com.example.project.API.RetrofitClient;
import com.example.project.adadapters.ProductAdapter;
import com.example.project.models.Product;
import com.example.project.models.ProductResp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity implements View.OnClickListener {
    Button searchButt, clearButt;
    Boolean first;
    List<Product> products;
    String token;
    APIService service;
    EditText searchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        registerForContextMenu(findViewById(R.id.rvProducts));
        searchInput = ((EditText) findViewById(R.id.searchText));
        products = new ArrayList<>();
        service = RetrofitClient.getRetrofitInstance().create(APIService.class);
        token = getSharedPreferences("bearer", 0).getString("token", "");
        first = true;
        searchButt = (Button) findViewById(R.id.searchButton);
        clearButt = (Button) findViewById(R.id.clearButton);
        searchButt.setOnClickListener(this);
        clearButt.setOnClickListener(this);
        if (first) {
            Call<ProductResp> call = service.getProducts("Bearer " + token);
            call.enqueue(new Callback<ProductResp>() {
                @Override
                public void onResponse(Call<ProductResp> call, Response<ProductResp> response) {
                    if (response.body().isSuccess() && !response.body().getProducts().isEmpty()) {
                        products.addAll(response.body().getProducts());
                        RecyclerView rvProducts = findViewById(R.id.rvProducts);
                        rvProducts.setHasFixedSize(true);
                        rvProducts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        ProductAdapter adapter = new ProductAdapter(getApplicationContext(), products);
                        rvProducts.setAdapter(adapter);
                    } else if (response.body().getProducts().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "No products", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong" + call.request().url(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProductResp> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(this, AddProductActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.delete) {
            startActivity(new Intent(this, DeleteProductActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view) {
        if (view == searchButt) {
            String search = searchInput.getText().toString().trim();
            Call<ProductResp> call = service.getSearchResults(search, "Bearer " + token);
            if (TextUtils.isEmpty(search)) {
                Toast.makeText(getApplicationContext(), "Search box is empty", Toast.LENGTH_LONG).show();
            } else {
                call.enqueue(new Callback<ProductResp>() {

                    @Override
                    public void onResponse(Call<ProductResp> call, Response<ProductResp> response) {
                        if (response.body().isSuccess() && !response.body().getProducts().isEmpty()) {
                            products.clear();
                            products.addAll(response.body().getProducts());
                            RecyclerView rvProducts = findViewById(R.id.rvProducts);
                            rvProducts.setHasFixedSize(true);
                            rvProducts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            ProductAdapter adapter = new ProductAdapter(getApplicationContext(), products);
                            rvProducts.setAdapter(adapter);
                            Toast.makeText(getApplicationContext(), products.size() + " results found", Toast.LENGTH_LONG).show();
                        } else if (response.body().getProducts().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "No results", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong" + call.request().url(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductResp> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }


        }
        if (view == clearButt) {
            Call<ProductResp> call = service.getProducts("Bearer " + token);
            call.enqueue(new Callback<ProductResp>() {
                @Override
                public void onResponse(Call<ProductResp> call, Response<ProductResp> response) {
                    searchInput.setText(null);
                    if (response.body().isSuccess() && !response.body().getProducts().isEmpty()) {
                        products.clear();
                        products.addAll(response.body().getProducts());
                        RecyclerView rvProducts = findViewById(R.id.rvProducts);
                        rvProducts.setHasFixedSize(true);
                        rvProducts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        ProductAdapter adapter = new ProductAdapter(getApplicationContext(), products);
                        rvProducts.setAdapter(adapter);
                    } else if (response.body().getProducts().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "No products", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong" + call.request().url(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProductResp> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        }

    }
}