package com.example.pokedex2.ui.Location;

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
import com.example.pokedex2.MainActivity;
import com.example.pokedex2.R;
import com.example.pokedex2.ui.main.MainFragment;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private ArrayList<String> locations;
    private Context mcontext;

    public LocationAdapter(ArrayList<String> locations, Context mcontext) {
        this.locations = locations;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.nameTextView.setText(locations.get(position));

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, locations.get(position), Toast.LENGTH_SHORT).show();
                if (MainFragment.publicPokeURL != null) {
                    MainFragment.publicPokeURL = MainFragment.publicPokeURL + "/" + locations.get(position);
                    MainActivity.fragmentManager.beginTransaction()
                            .replace(R.id.container, MainFragment.newInstance())
                            .commitNow();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.location_name);
            parent = itemView.findViewById(R.id.location_list_item);
        }
    }
}
