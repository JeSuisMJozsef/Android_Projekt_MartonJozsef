package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project.API.APIService;
import com.example.project.API.RetrofitClient;
import com.example.project.adadapters.ProductAdapter;
import com.example.project.models.Product;
import com.example.project.models.ProductResp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteProductActivity extends AppCompatActivity {

    ListView list;

    ArrayList<Integer> productsSiplified = new ArrayList<>();

    ArrayAdapter<Integer> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);
        APIService service = RetrofitClient.getRetrofitInstance().create(APIService.class);
        String token = getSharedPreferences("bearer", 0).getString("token", "");
        Call<ProductResp> call = service.getProducts("Bearer " + token);
        call.enqueue(new Callback<ProductResp>() {
            @Override
            public void onResponse(Call<ProductResp> call, Response<ProductResp> response) {
                if (response.body().isSuccess() && !response.body().getProducts().isEmpty()) {
                    for (Product product:response.body().getProducts()
                         ) {

                        productsSiplified.add(product.getId());
                    }
                    list = findViewById(R.id.list);
                    adapter =new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, productsSiplified);
                    registerForContextMenu(list);
                    list.setAdapter(adapter);
                } else if (response.body().getProducts().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "No products", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProductResp> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Something went wrong" + call.request().url(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        APIService service = RetrofitClient.getRetrofitInstance().create(APIService.class);
        String token = getSharedPreferences("bearer", 0).getString("token", "");
        if (item.getItemId() == R.id.prodDelete) {
            int pos = info.position;
            int product=productsSiplified.get(pos);
            productsSiplified.remove(info.position);
            adapter.notifyDataSetChanged();
            Call<ProductResp> call = service.deleteProduct("Bearer " + token, product);
            call.enqueue(new Callback<ProductResp>() {
                @Override
                public void onResponse(Call<ProductResp> call, Response<ProductResp> response) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(DeleteProductActivity.this, "Deleted", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(getApplicationContext(), "Something went wrong" + call.request().url(), Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ProductResp> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}