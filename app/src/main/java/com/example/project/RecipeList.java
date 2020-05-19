package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Map;

public class RecipeList  extends AppCompatActivity implements RecipeListAdapter.onButtonListener {

    Button explore;
    Button list;
    Button recipes;
    TextView userName;
    RecyclerView recipeDisp;
    RecipeListAdapter recipeAdapter;
    ArrayList<Recipe> recipeList;

    DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list_lo);

        FirebaseAuth userID = FirebaseAuth.getInstance();


        userName = (TextView)findViewById(R.id.PgTitle);
        userName.setText("My Recipes");

        explore = (Button)findViewById(R.id.Explore);
        list = (Button)findViewById(R.id.List);
        recipes = (Button)findViewById(R.id.Recipes);


        recipeDisp = (RecyclerView) findViewById(R.id.recipeDisp);
        recipeDisp.setHasFixedSize(true);
        recipeDisp.setLayoutManager(new LinearLayoutManager(this));
        recipeList = new ArrayList<Recipe>();
        recipeAdapter = new RecipeListAdapter(this, recipeList, this);
        recipeDisp.setAdapter(recipeAdapter);

        mRootRef = FirebaseDatabase.getInstance().getReference("recipes");
        Query query = mRootRef.orderByChild("userID").equalTo(userID.getUid());


        query.addValueEventListener(new ValueEventListener() {
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


        /* for(int i=0; i <10; i++){
            View recipeTile = inflater.inflate(R.layout.new_recipe_tile, null, false);
            TextView title = (TextView) recipeTile.findViewById((R.id.newTitlDisp));
            TextView author = (TextView) recipeTile.findViewById((R.id.newAuthDisp));
            TextView cookTime = (TextView) recipeTile.findViewById((R.id.newCTDisp));
            TextView prepTime = (TextView) recipeTile.findViewById((R.id.newPTDisp));

            title.setText("Mac");
            author.setText("Cindy");
            cookTime.setText("1hr");
            prepTime.setText("20min");

            recipeDisp.addView(recipeTile, i);


        }
        */