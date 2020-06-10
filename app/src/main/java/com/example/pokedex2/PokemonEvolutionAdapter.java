package com.example.pokedex2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedex2.ui.main.Pokemon;

import java.util.ArrayList;

public class PokemonEvolutionAdapter extends RecyclerView.Adapter<PokemonEvolutionAdapter.ViewHolder>{

    private ArrayList<Pokemon> pokemons = new ArrayList<>();
    private Context mcontext;

    public PokemonEvolutionAdapter(ArrayList<Pokemon> pokemons, Context mcontext) {
        this.pokemons = pokemons;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evolution_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.nameTextView.setText(pokemons.get(position).getName());
        String imageUrl = pokemons.get(position).getImgUrl();
        Log.d("Adapter", imageUrl);
        Glide.with(mcontext).clear(holder.pokemonImage);
        Glide.with(mcontext)
                .asBitmap()
                .load(imageUrl)
                .circleCrop()
                .placeholder(R.drawable.pokedexpng)
                .into(holder.pokemonImage);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, pokemons.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mcontext, PokemonDetailActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mcontext, holder.pokemonImage, "pokeimg_transition");
                intent.putExtra("NAME", pokemons.get(position).getName());
                mcontext.startActivity(intent, options.toBundle());

                //((Activity) mcontext).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView pokemonImage;
        LinearLayout parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.evolution_pokemon);
            pokemonImage = itemView.findViewById(R.id.evolution_Image);
            parent = itemView.findViewById(R.id.evolution_list_item);
        }
    }
}
