package com.example.androiddevelopmentproject2mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class Game extends AppCompatActivity {

    TextView score;
    TextView time;
    TextView life;

    TextView question;
    TextView answer;

    Button ok;
    Button next;

    Random random = new Random();
    int num1;
    int num2;
    int trueAnswer;
    int userAnswer;

    int userScore = 0;
    int userLife = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score= findViewById(R.id.textViewScore);
        time= findViewById(R.id.textViewTime);
        life = findViewById(R.id.textViewLife);

        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);

        ok = findViewById(R.id.buttonOK);
        next = findViewById(R.id.buttonNext);

        gameContinue();


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userAnswer = Integer.valueOf(answer.getText().toString());
                if(userAnswer == trueAnswer){

                    userScore = userScore + 10;
                    question.setText("Congratulation, your answer isa true!");

                } else {
                    userLife--;
                    question.setText("Sorry , wrong answer!");

                    if (userLife  == 0){

                    }

                }


            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public void gameContinue(){

        num1 = random.nextInt(100);
        num2 = random.nextInt(100);

        question.setText(num1 + " + " + num2);







    }


}