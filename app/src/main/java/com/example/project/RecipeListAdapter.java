package com.example.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private Context mCtx;
    private List<Recipe> recipes;
    onButtonListener mbuttonListener;

    public RecipeListAdapter(Context mCtx, List<Recipe> recipeList, onButtonListener buttonListener) {
        this.mCtx = mCtx;
        this.recipes = recipeList;
        this.mbuttonListener = buttonListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_recipe_tile, parent, false);
        return new RecipeViewHolder(view, mbuttonListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.title.setText(recipe.title);
        holder.author.setText(recipe.author);
        holder.cookTime.setText(recipe.cookTime);
        holder.prepTime.setText(recipe.prepTime);




    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements onButtonListener {

        TextView title, author, cookTime, prepTime;
        onButtonListener buttonListener;

        public RecipeViewHolder(@NonNull View itemView, onButtonListener buttonListener) {
            super(itemView);

            title = itemView.findViewById(R.id.newTitlDisp);
            author = itemView.findViewById(R.id.newAuthDisp);
            cookTime = itemView.findViewById(R.id.newCTDisp);
            prepTime = itemView.findViewById(R.id.newPTDisp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mbuttonListener.onClick(v, getAdapterPosition());
                }
            });
        }


        @Override
        public void onClick(View v, int position) {

        }
    }

    public interface onButtonListener{
        void onClick(View v, int position);
    }
}

