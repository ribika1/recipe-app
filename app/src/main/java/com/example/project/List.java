package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class List extends AppCompatActivity implements ListAdapter.onButtonListener {

    Button explore;
    Button list;
    Button recipes;
    RecyclerView groceryDisp;
    ArrayList<String> groceryList;
    FloatingActionButton addToList;
    ListAdapter adapter;
    DatabaseReference mRootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_lo);

        explore = (Button)findViewById(R.id.Explore);
        list = (Button)findViewById(R.id.List);
        recipes = (Button)findViewById(R.id.Recipes);
        groceryDisp = findViewById(R.id.groceryList);
        addToList = (FloatingActionButton)findViewById(R.id.addToList);


        groceryDisp.setHasFixedSize(true);
        groceryDisp.setLayoutManager(new LinearLayoutManager(this));
        groceryList = new ArrayList<String>();
        adapter = new ListAdapter(this, groceryList, this);
        groceryDisp.setAdapter(adapter);

        mRootRef = FirebaseDatabase.getInstance().getReference("list");

        addToList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groceryList.add("");
                adapter.notifyDataSetChanged();

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
        groceryList.remove(position);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClickS(View v, int position) {

    }


}