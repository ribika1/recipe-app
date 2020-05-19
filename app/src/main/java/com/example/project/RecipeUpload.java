package com.example.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeUpload  extends AppCompatActivity {

     Button url;
     Button photo;
     Button manual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_upload_lo);

        Button url = (Button)findViewById(R.id.urlUP);
        Button photo = (Button)findViewById(R.id.photoUp);
        Button manual = (Button)findViewById(R.id.manUp);

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 = new Intent(getApplicationContext(), URLHandler.class);
                startActivity(i1);
            }
        });

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), UsePhoto.class);
                startActivity(i1);

            }
        });

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), RecipeInput.class);
                startActivity(i1);
            }
        });


    }


}