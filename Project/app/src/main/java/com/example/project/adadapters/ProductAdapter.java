package com.example.project.adadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.VH> {
    private Context context;
    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ProductAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_row,parent,false);
        return new VH(itemView,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.VH holder, int position) {
        Product product=products.get(position);
        holder.rootView.setTag(product);
        holder.prodName.setText(product.getName());
        holder.catId.setText("CatId: "+String.valueOf(product.getCategoryId()));
        holder.sku.setText("Sku: "+product.getSku());
        holder.packaging.setText("Package: "+product.getPackaging());
        holder.stock.setText("Instock: "+String.valueOf(product.getStock())+ " " + product.getPackaging());
        holder.price.setText("Price: "+String.valueOf(product.getPrice()));

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final TextView prodName;
        final TextView catId;
        final TextView sku;
        final TextView packaging;
        final TextView stock;
        final TextView price;
        public VH(@NonNull View itemView, final Context context) {

            super(itemView);
            rootView=itemView;
            prodName=(TextView) itemView.findViewById(R.id.prodName);
            catId=(TextView) itemView.findViewById(R.id.catId);
            sku=(TextView) itemView.findViewById(R.id.prodSku);
            packaging=(TextView) itemView.findViewById(R.id.prodPackaging);
            stock=(TextView) itemView.findViewById(R.id.prodStock);
            price=(TextView) itemView.findViewById(R.id.prodPrice);
        }
    }
}
