package com.example.pokedex2.ui.items;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedex2.R;
import com.example.pokedex2.ui.main.Pokemon;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private ArrayList<Item> items = new ArrayList<>();
    private Context mcontext;

    public ItemAdapter(ArrayList<Item> items, Context mcontext) {
        this.items = items;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nameTextView.setText(items.get(position).getName());
        String imageUrl = items.get(position).getImgUrl();
        Glide.with(mcontext).clear(holder.itemImage);
        Glide.with(mcontext)
                .asBitmap()
                .load(imageUrl)
                .circleCrop()
                .placeholder(R.drawable.circle_bg)
                .into(holder.itemImage);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, items.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView itemImage;
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            itemImage = itemView.findViewById(R.id.item_image);
            parent = itemView.findViewById(R.id.item_list_item);
        }
    }
}
