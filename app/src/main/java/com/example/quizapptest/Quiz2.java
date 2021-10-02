package com.example.quizapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Quiz2 extends AppCompatActivity {
    RadioGroup rg;
    RadioButton rb;
    Button bNext;


    ImageView imageView;
    FirebaseStorage storage;

   // FirebaseDatabase mDatabase;

    int score;
    String RepCorrect = "A droite";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        rg = (RadioGroup) findViewById(R.id.rgQ2);
        bNext = (Button) findViewById(R.id.bNextQ2);
        Intent intent = getIntent();
        score = intent.getIntExtra("score",0); // valeur par defaut

       // mDatabase.child("users").child().setValue("user");


        imageView = (ImageView) findViewById(R.id.imQ2);
       // DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
       // mDatabase.setValue("Hello, World");

        try {
            storage= FirebaseStorage.getInstance();
            StorageReference httpsReference = storage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/quizapptest-166a5.appspot.com/o/quiz2.jpeg?alt=media&token=dd216101-295c-4f5a-8c57-afde19466f0b");
            final long ONE_MEGABYTE = 2024 * 2024;

            httpsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bm);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(Quiz2.this, "failed to load the logo",
                            Toast.LENGTH_SHORT).show();
                    System.out.println("Omar "+ exception.getMessage());
                }
            });
        }catch(Exception e)
        {
            Toast.makeText(Quiz2.this, e.getMessage()+" download exception",
                    Toast.LENGTH_SHORT).show();
            System.out.println("download Exception "+e.getMessage());
        }


        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                if(rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(), "Enter une reponse", Toast.LENGTH_SHORT).show();
                } else {
                    if(rb.getText().toString().equals(RepCorrect)){
                        score += 1;
                    }
                    Intent intent1 = new Intent(Quiz2.this, Score.class);
                    intent1.putExtra("score", score);
                    startActivity(intent1);
                    //
                    finish();
                }
            }
        });


    }
}