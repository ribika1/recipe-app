package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RecipeInput  extends AppCompatActivity {

    DatabaseReference mRootRef;
    Button submit;
    EditText titleEnter;
    EditText authEnter;
    EditText CTEnter;
    EditText PTEnter;
    EditText IngEnter;
    EditText StepsEnter;
    boolean successfulRemove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_input_lo);

        mRootRef = FirebaseDatabase.getInstance().getReference("recipes");

        submit = (Button)findViewById(R.id.SaveRecipe);
        titleEnter = (EditText)findViewById(R.id.TitleEnter);
        authEnter = (EditText)findViewById(R.id.AuthEnter);
        CTEnter = (EditText)findViewById(R.id.CTEnter);
        PTEnter = (EditText)findViewById(R.id.PTEnter);
        IngEnter = (EditText)findViewById(R.id.IngEnter);
        StepsEnter = (EditText)findViewById(R.id.StepsEnter);

        // ensures the user is good to attempt a submission
        String test = mRootRef.push().getKey();
        DatabaseReference tempRef =  FirebaseDatabase.getInstance().getReference("recipes/"+test);


        tempRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        successfulRemove = true;
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String recipeID = mRootRef.push().getKey();

                final String userID = FirebaseAuth.getInstance().getUid();
                final String title = titleEnter.getText().toString();
                final String author = authEnter.getText().toString();
                final String cookTime = CTEnter.getText().toString();
                final String prepTime = PTEnter.getText().toString();
                final String ingredients = IngEnter.getText().toString();
                final String instructions = StepsEnter.getText().toString();
                if(title.equals("") || author.equals("") || cookTime.equals("") || prepTime.equals("")
                        || ingredients.equals("") || instructions.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "One or more fields is missing information.", Toast.LENGTH_SHORT).show();


                } else {



                    final Recipe recSub = new Recipe(userID, title, author, cookTime, prepTime, ingredients, instructions);
                    mRootRef.child(recipeID).child("userID").setValue(userID);
                    mRootRef.child(recipeID).child("title").setValue(title);
                    mRootRef.child(recipeID).child("author").setValue(author);
                    mRootRef.child(recipeID).child("cookTime").setValue(cookTime);
                    mRootRef.child(recipeID).child("prepTime").setValue(prepTime);
                    mRootRef.child(recipeID).child("ingredients").setValue(recSub.getIngredients()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            mRootRef.child(recipeID).child("instructions").setValue(recSub.getInstructions()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent i1 = new Intent(getApplicationContext(), RecipeView.class);
                                        i1.putExtra("Recipe ID", recipeID);
                                        startActivity(i1);

                                    } else {
                                        Toast.makeText(RecipeInput.this, "There was an error submitting your recipe.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    });

                }
            }
        });


    }
}
