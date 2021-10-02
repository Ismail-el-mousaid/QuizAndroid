package com.example.quizapptest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText etName, etMail, etPass1, etPass2;
    Button bRegister;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etMail = (EditText) findViewById(R.id.etMail);
        etPass1 = (EditText) findViewById(R.id.etPass1);
        etPass2 = (EditText) findViewById(R.id.etPass2);
        bRegister = (Button) findViewById(R.id.bRegister);
        firebaseAuth = FirebaseAuth.getInstance();

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = etMail.getText().toString();
                String password1 = etPass1.getText().toString();
                String password2 = etPass2.getText().toString();
                if(TextUtils.isEmpty(mail)){
                    Toast.makeText(getApplicationContext(), "Svp entrer votre email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password1)){
                    Toast.makeText(getApplicationContext(), "Svp entrer votre password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password2)){
                    Toast.makeText(getApplicationContext(), "Svp confirmer votre password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password1.length()<6){
                    Toast.makeText(getApplicationContext(), "Password doit contient au moins 6 caracteres ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password1.equals(password2)){
                    Toast.makeText(getApplicationContext(),"Svp entrer votre un password correcte",Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(mail, password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            Toast.makeText(getApplicationContext(), "inscription réussi! ", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "E-mail ou password incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });




    }
}