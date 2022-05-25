package com.example.quizgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizPage extends AppCompatActivity {

    TextView time,correct, wrong;
    TextView question ,a,b,c,d;
    Button next, finish;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Question");

    String userAnswer;
    String quizQ,quiza,quizb,quizc,quizd,quizanswer;
    int questionCount;
    int questionNumber=1;

    int userCorrectCount=0;
    int userWrongCount=0;

    CountDownTimer countDownTimer;
    private static final long TOTAL_TIME=15000;
    Boolean timeContinue;
    long timeLeft=TOTAL_TIME;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseUser user=auth.getCurrentUser();

    DatabaseReference databaseReferencesecond=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);

        time=findViewById(R.id.time);
        correct=findViewById(R.id.correctanswer);
        wrong=findViewById(R.id.wronganswer);
        question=findViewById(R.id.question);
        a=findViewById(R.id.first);
        b=findViewById(R.id.second);
        c=findViewById(R.id.third);
        d=findViewById(R.id.fourth);
        next=findViewById(R.id.nextquestion);
        finish=findViewById(R.id.finish);


        game();
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                userAnswer="a";
                if (quizanswer.equals(userAnswer))
                {
                    a.setBackgroundColor(Color.GREEN);
                    userCorrectCount++;
                    correct.setText(String.valueOf(userCorrectCount));
                }
                else
                {
                    a.setBackgroundColor(Color.RED);
                    userWrongCount++;
                    wrong.setText(String.valueOf(userWrongCount));
                    FindAnswer();

                }

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();

                userAnswer="b";
                if (quizanswer.equals(userAnswer))
                {
                    b.setBackgroundColor(Color.GREEN);
                    userCorrectCount++;
                    correct.setText(String.valueOf(userCorrectCount));
                }
                else
                {
                    b.setBackgroundColor(Color.RED);
                    userWrongCount++;
                    wrong.setText(String.valueOf(userWrongCount));
                    FindAnswer();

                }
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();

                userAnswer="c";
                if (quizanswer.equals(userAnswer))
                {
                    c.setBackgroundColor(Color.GREEN);
                    userCorrectCount++;
                   correct.setText(String.valueOf(userCorrectCount));
                }
                else
                {
                    c.setBackgroundColor(Color.RED);
                    userWrongCount++;
                    wrong.setText(String.valueOf(userWrongCount));
                    FindAnswer();

                }
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();

                userAnswer="d";
                if (quizanswer.equals(userAnswer))
                {
                    d.setBackgroundColor(Color.GREEN);
                    userCorrectCount++;
                    correct.setText(String.valueOf(userCorrectCount));
                }
                else
                {
                    d.setBackgroundColor(Color.RED);
                    userWrongCount++;
                    wrong.setText(String.valueOf(userWrongCount));
                    FindAnswer();

                }
            }
        });



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               resetTimer();
                game();

            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendScore();
                Intent intent=new Intent(QuizPage.this,ScorePage.class);
                startActivity(intent);
                finish();
            }
        });


    }
    public void game(){

        startTimer();
        a.setBackgroundColor(getResources().getColor(R.color.purple_200));
        b.setBackgroundColor(getResources().getColor(R.color.purple_200));
        c.setBackgroundColor(getResources().getColor(R.color.purple_200));
        d   .setBackgroundColor(getResources().getColor(R.color.purple_200));

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questionCount=(int)snapshot.getChildrenCount();
                quizQ=snapshot.child(String.valueOf(questionNumber)).child("question").getValue().toString();
                quiza=snapshot.child(String.valueOf(questionNumber)).child("a").getValue().toString();
                quizb=snapshot.child(String.valueOf(questionNumber)).child("b").getValue().toString();
                quizc=snapshot.child(String.valueOf(questionNumber)).child("c").getValue().toString();
                quizd=snapshot.child(String.valueOf(questionNumber)).child("d").getValue().toString();
                quizanswer=snapshot.child(String.valueOf(questionNumber)).child("answer").getValue().toString();


                question.setText(quizQ);
                a.setText(quiza);
                b.setText(quizb);
                c.setText(quizc);
                d.setText(quizd);
                if (questionNumber<questionCount){
                    questionNumber++;
                }
                else
                {
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
            }
        });




    }
    public void FindAnswer()
    {
        if (quizanswer.equals("a"))
        {
            a.setBackgroundColor(Color.GREEN);
        }
        else if(quizanswer.equals("b"))
        {
            b.setBackgroundColor(Color.GREEN);
        }
        else if (quizanswer.equals("c"))
        {
            c.setBackgroundColor(Color.GREEN);
        }
        else if(quizanswer.equals("d"))
        {
            d.setBackgroundColor(Color.GREEN);
        }

    }

    public void startTimer(){
        countDownTimer=new CountDownTimer(timeLeft,1000) {
            @Override
            public void onTick(long l) {

                timeLeft=l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeContinue=false;
                pauseTimer();
                question.setText("Sorry Time is up");

            }
        }.start();
        timeContinue=true;
    }

    public void updateCountDownText()
    {
        int second=(int)(timeLeft/1000)%60;
        time.setText(String.valueOf(second));
    }

    public void pauseTimer()
    {
        countDownTimer.cancel();
        timeContinue=false;

    }
    public void resetTimer()
    {
        timeLeft=TOTAL_TIME;
        updateCountDownText();
    }
    public void sendScore(){
        String userUID=user.getUid();
        databaseReferencesecond.child("Score").child(userUID).child("correct").setValue(userCorrectCount);
        databaseReferencesecond.child("Score").child(userUID).child("wrong").setValue(userWrongCount);
    }

}