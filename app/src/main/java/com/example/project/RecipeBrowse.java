package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecipeBrowse  extends AppCompatActivity implements RecipeListAdapter.onButtonListener  {

    Button explore;
    Button list;
    Button recipes;
    RecyclerView recipeDisp;
    RecipeListAdapter recipeAdapter;
    ArrayList<Recipe> recipeList;

    DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list_lo);


        FirebaseAuth userID = FirebaseAuth.getInstance();

        explore = (Button) findViewById(R.id.Explore);
        list = (Button) findViewById(R.id.List);
        recipes = (Button) findViewById(R.id.Recipes);


        recipeDisp = (RecyclerView) findViewById(R.id.recipeDisp);
        recipeDisp.setHasFixedSize(true);
        recipeDisp.setLayoutManager(new LinearLayoutManager(this));
        recipeList = new ArrayList<Recipe>();
        recipeAdapter = new RecipeListAdapter(this, recipeList, this);
        recipeDisp.setAdapter(recipeAdapter);

        mRootRef = FirebaseDatabase.getInstance().getReference("recipes");



        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recipeDisp.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String id = snapshot.child("").getKey();
                    String title = snapshot.child("title").getValue().toString();
                    String author = snapshot.child("author").getValue().toString();
                    String cookTime = snapshot.child("cookTime").getValue().toString();
                    String prepTime = snapshot.child("prepTime").getValue().toString();
                    Recipe recipe = new Recipe(id, title, author, cookTime, prepTime);
                    recipeList.add(recipe);
                }
                recipeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i1);
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), List.class);
                startActivity(i1);
            }
        });

        recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), RecipeList.class);
                startActivity(i1);
            }
        });


    }

    @Override
    public void onClick(View v, int position) {
        Intent intent = new Intent(this, RecipeView.class);
        intent.putExtra("Recipe ID", recipeList.get(position).key);
        startActivity(intent);
    }
}