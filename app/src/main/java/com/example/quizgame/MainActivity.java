package com.example.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button startquiz;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.singout);
        startquiz=findViewById(R.id.startquiz);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 auth.signOut();
                 Intent intent=new Intent(MainActivity.this,LoginPage.class);
                 startActivity(intent);
                 finish();
            }
        });


        startquiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,QuizPage.class);
                startActivity(intent);
            }
        });
    }
}