package com.example.quizapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;

import java.util.ArrayList;

public class Quiz1 extends AppCompatActivity {
    RadioGroup rg;
    RadioButton rb  , rb2;
    Button bNext;
    public int score=0;
    String repCorrect = "a";

    //DataBase
    DatabaseReference databaseReference;
    public static ArrayList<QuestionReponse> list;
    TextView tvQ1;

    // database 2
    int total = 0;
    int correct = 0;

    // Time
    CountDownTimer countDownTimer;
    int timerValue=20;
    RoundedHorizontalProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz1);

        //chargement();





        //  tvQ1 = (TextView) findViewById(R.id.tvQ1);




        //database2
        updateQuestion();
        chargement();


        rg = (RadioGroup) findViewById(R.id.rgQ1);
        bNext = (Button) findViewById(R.id.bNextQ1);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rg.getCheckedRadioButtonId()==-1){
                    Toast.makeText(getApplicationContext(), "merci d'entrer une reponse", Toast.LENGTH_SHORT).show();
                } else {
                    rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
                  /*  if(rb.getText().toString().equals(repCorrect)){
                        score += 1;
                    }  */
                    if(rb.getText().toString().equals(repCorrecte)){
                        score += 1;
                    }

                    //total++;
                    updateQuestion();
                    chargement();

                   // Intent intent = new Intent(Quiz1.this, Quiz1.class);
                   // intent.putExtra("score", score);
                   // startActivity(intent);
                    overridePendingTransition(R.anim.exit,R.anim.entry);
                    //finish();
                }
            }
        });


        //database3

       // j'ai ignorer ce code









    }
    //database 2
    String repCorrecte;
    private void updateQuestion() {
        total++;
        if(total==5){
            Intent intent = new Intent(Quiz1.this, Score.class);
            intent.putExtra("score", score);
            startActivity(intent);
        }
        else {
            databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("Question").child(String.valueOf(total)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (!task.isSuccessful()) {
                        Log.e("firebase", "Error getting data", task.getException());

                    }
                    else {
                        Log.d("firebase", String.valueOf(task.getResult().getValue()));

                        final TextView tv = (TextView) findViewById(R.id.tvQ1);
                        tv.setText(task.getResult().child("Question").getValue().toString());

                        final RadioButton rbOptionOne = (RadioButton)findViewById(R.id.rb1);
                        rbOptionOne.setText(task.getResult().child("Rep1").getValue().toString());

                        final RadioButton rbOptionTwo = (RadioButton)findViewById(R.id.rb2);
                        rbOptionTwo.setText(task.getResult().child("Rep2").getValue().toString());

                        repCorrecte = task.getResult().child("answer").getValue().toString();
                    }
                }
            });
        }
    }



    private String[] tab = new String[5];
    private ImageView imageView;
    private FirebaseStorage storage;

    int i = 0;


    private void chargement(){
      //  i++;
        tab[0] = "https://firebasestorage.googleapis.com/v0/b/quizapptest-166a5.appspot.com/o/q1.jpg?alt=media&token=b4b6787a-96f4-41f7-8c65-c3fc1a682701";
        tab[1] = "https://firebasestorage.googleapis.com/v0/b/quizapptest-166a5.appspot.com/o/q2.jpg?alt=media&token=cd64d617-5e8e-4368-9a14-81c646bbf997";
        tab[2] = "https://firebasestorage.googleapis.com/v0/b/quizapptest-166a5.appspot.com/o/q3.jpg?alt=media&token=b0c83da9-daca-4f10-bff2-23e1d7a2560b";
        tab[3] = "https://firebasestorage.googleapis.com/v0/b/quizapptest-166a5.appspot.com/o/q4.jpg?alt=media&token=fe65c585-0679-4b20-ae8a-ac8896712f28";
        tab[4] = "https://firebasestorage.googleapis.com/v0/b/quizapptest-166a5.appspot.com/o/q5.jpg?alt=media&token=aa1a06b4-4660-4820-aecb-89cf11c8389b";

        imageView = (ImageView) findViewById(R.id.imQ1);
        try {
            storage= FirebaseStorage.getInstance();
            StorageReference httpsReference = storage.getReferenceFromUrl(tab[i]);
            final long ONE_MEGABYTE = 2024 * 2024;

            httpsReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bm);
                    i++;
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(Quiz1.this, "failed to load the logo",
                            Toast.LENGTH_SHORT).show();
                    System.out.println("Omar "+ exception.getMessage());
                }
            });
        }catch(Exception e)
        {
            Toast.makeText(Quiz1.this, e.getMessage()+" download exception",
                    Toast.LENGTH_SHORT).show();
            System.out.println("download Exception "+e.getMessage());
        }
    }
}