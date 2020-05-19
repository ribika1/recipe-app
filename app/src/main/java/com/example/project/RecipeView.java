package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeView  extends AppCompatActivity {



    DatabaseReference mRootRef;

    TextView titleDisp;
    TextView authDisp;
    TextView cookTDisp;
    TextView prepTDisp;
    TextView ingrDisp;
    TextView instDisp;


    Recipe thisRecipe;

    Button explore;
    Button list;
    Button recipes;
    FloatingActionButton readRecipe;
    TextToSpeech readAloud;


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.share_button:
                Intent sharingIntent= new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareSubject="";

                sharingIntent.putExtra(Intent.EXTRA_TEXT,shareSubject);
                startActivity(Intent.createChooser(sharingIntent, "Share Using"));

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view_lo);

        thisRecipe = new Recipe();
        final Intent here = getIntent();
        readRecipe = (FloatingActionButton)findViewById(R.id.readRecipe);

        mRootRef = FirebaseDatabase.getInstance().getReference("recipes/"+here.getStringExtra("Recipe ID"));

        explore = (Button)findViewById(R.id.Explore);
        list = (Button)findViewById(R.id.List);
        recipes = (Button)findViewById(R.id.Recipes);


        titleDisp = (TextView) findViewById(R.id.RecipeTitle);
        authDisp = (TextView)findViewById(R.id.Author);
        cookTDisp = (TextView)findViewById(R.id.CookTime);
        prepTDisp = (TextView)findViewById(R.id.PrepTime);
        ingrDisp = (TextView)findViewById(R.id.Ingredients);
        instDisp = (TextView)findViewById(R.id.Instructions);



            mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userID = dataSnapshot.child("userID").getValue().toString();
                String title = dataSnapshot.child("title").getValue().toString();
                titleDisp.setText(title);
                String author = dataSnapshot.child("author").getValue().toString();
                authDisp.setText(author);
                String cookTime = dataSnapshot.child("cookTime").getValue().toString();
                cookTDisp.setText(cookTime);
                String prepTime = dataSnapshot.child("prepTime").getValue().toString();
                prepTDisp.setText(prepTime);
                ArrayList ingredients = (ArrayList) dataSnapshot.child("ingredients").getValue();
                ingrDisp.setText(thisRecipe.ingredientsToString(ingredients));
                ArrayList instructions = (ArrayList) dataSnapshot.child("instructions").getValue();
                instDisp.setText(thisRecipe.instructionsToString(instructions));
                thisRecipe = new Recipe(userID, title, author, cookTime, prepTime, ingredients, instructions);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        readAloud = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status== TextToSpeech.SUCCESS){
                    int result = readAloud.setLanguage(Locale.ENGLISH);
                    if(result == TextToSpeech.LANG_MISSING_DATA
                        || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language Not Supported");
                    } else {
                        readRecipe.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed.");
                }
            }
        });

        readRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
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

        mRootRef.push();


    }

    private void speak(){
        String recipe = "";
        recipe += "I will now read aloud the ingredients and instructions for the recipe " + titleDisp.getText().toString();
        recipe += "You will need the following ingredients";
        for(int a=0; a<thisRecipe.getIngredients().size(); a++){

            if(a == thisRecipe.getIngredients().size()-1){
                recipe += "\n and " + thisRecipe.getIngredients().get(a);
            } else {
                recipe += "\n" + thisRecipe.getIngredients().get(a);
            }
        }
        for(int b=0; b<thisRecipe.getInstructions().size(); b++){
            recipe += "\n Step " + (b+1);
            recipe += "\n" + thisRecipe.getInstructions().get(b);
        }

        System.out.println(recipe);
        readAloud.setSpeechRate(0.65f);
        readAloud.speak(recipe, TextToSpeech.QUEUE_FLUSH, null);

    }
}