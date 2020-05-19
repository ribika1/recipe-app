package com.example.project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UsePhoto extends AppCompatActivity {

    ImageView photoDisp;
    Button submit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_lo);

        photoDisp = (ImageView)findViewById(R.id.photoDisp);
        submit = (Button)findViewById(R.id.submitPhoto);

        Intent i1 = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        if(i1.resolveActivity(getPackageManager()) != null){
            File photo = null;
            photo = savePhoto();
            if(photo!=null){
                startActivityForResult(i1, 1);
            }
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i1);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        photoDisp.setImageBitmap(bitmap);

    }

    private File savePhoto(){
        String name = new SimpleDateFormat("yyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try{
            image = File.createTempFile(name, ".jpg", storageDir);
        } catch (IOException e) {
            Log.d("photoLog", "Exception: " + e.toString());

        }

        return image;
    }


}
