 package com.example.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

 public class ForgotPassword extends AppCompatActivity {

    EditText email;
    Button button;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email=findViewById(R.id.forgotpasswordemail);
        button=findViewById(R.id.buttonfrogotpassword);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail=email.getText().toString();
                resetPassword(useremail);

            }
        });
    }

    public void resetPassword(String usermail){
        auth.sendPasswordResetEmail(usermail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ForgotPassword.this,"We sent you email to reset your password",Toast.LENGTH_SHORT).show();
                             button.setClickable(false);
                            finish();
                        }
                        else{
                            Toast.makeText(ForgotPassword.this,"Try Again",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}