package com.example.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RecisterActivity extends AppCompatActivity {

    EditText mail,password;
    Button singUp;
    ProgressBar progressBar;

    FirebaseAuth auth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recister);

        mail=findViewById(R.id.registerEmail);
        password=findViewById(R.id.registerPassword);
        singUp=findViewById(R.id.singupbutton);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singUp.setClickable(false);
                String userEmail=mail.getText().toString();
                String userpassword=password.getText().toString();
                singUpFirebase(userEmail,userpassword);
            }
        });

    }

    public void singUpFirebase(String useremail,String password)
    {
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(useremail,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(RecisterActivity.this,"succesfull",Toast.LENGTH_SHORT).show();
                            finish();
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            Toast.makeText(RecisterActivity.this,"try agin",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}