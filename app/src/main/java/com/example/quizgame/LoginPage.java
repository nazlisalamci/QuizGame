package com.example.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {

    EditText mail,password;
    Button singin;

    TextView singUp,forgotPassword;

    FirebaseAuth auth=FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mail=findViewById(R.id.loginmail);
        password=findViewById(R.id.loginpassword);
        singin=findViewById(R.id.singin);
        singUp=findViewById(R.id.sinup);
        forgotPassword=findViewById(R.id.forgotpassword);


        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usermail=mail.getText().toString();
                String userpassword=password.getText().toString();

                singinwithFirebase(usermail,userpassword);
            }
        });


        singUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(LoginPage.this,RecisterActivity.class);
                startActivity(intent);
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginPage.this,ForgotPassword.class);
                startActivity(intent);

            }
        });

    }
    public void singinwithFirebase(String usermail, String userpassword){
        singin.setClickable(false);
        auth.signInWithEmailAndPassword(usermail,userpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent=new Intent(LoginPage.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginPage.this,"Succesful",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(LoginPage.this,"Try Again",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user=auth.getCurrentUser();
        if (user!=null)
        {
            Intent intent=new Intent(LoginPage.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}