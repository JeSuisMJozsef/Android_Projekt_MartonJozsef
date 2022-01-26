package com.example.project.adadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VH> {
    private Context context;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public CategoryAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_row,parent,false);
        return new VH(itemView,context);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.VH holder, int position) {
        Category category=categories.get(position);
        holder.rootView.setTag(category);
        holder.catName.setText(category.getCategoryName());
        holder.catDesc.setText(category.getCategoryDesc());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final TextView catName;
        final TextView catDesc;

        public VH(@NonNull View itemView, Context context) {
            super(itemView);
            this.rootView = itemView;
            this.catName =(TextView) itemView.findViewById(R.id.nameTextView);
            this.catDesc = (TextView) itemView.findViewById(R.id.descTextView);
        }
    }
}
