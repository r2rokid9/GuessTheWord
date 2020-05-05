package com.example.guesstheword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class GuessLetter extends AppCompatActivity {


    LinearLayout innerLayout;
    int arrLetterGuess[];
    String packageStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_letter);

        innerLayout = findViewById(R.id.innerLayout);
        arrLetterGuess = new int[26];

        Intent intent = getIntent();

        packageStr = intent.getStringExtra("GUESSES");

        for (int i = 0;i < 26;i++) {
            if (packageStr.charAt(i) == '0')
                arrLetterGuess[i] = 0;
            else
                arrLetterGuess[i] = 1;
        }

        layoutView();
    }

    private char intToLetter(int n)
    {
        return (char) ('A' + n);
    }

    private void layoutView()
    {
        int ctr = 0, numButtons = 4;
        LinearLayout newLayout = null;

        for (int i = 0;i < 26;i++)
        {
            if (ctr == 0)
            {
                newLayout = new LinearLayout(this);
                newLayout.setOrientation(LinearLayout.HORIZONTAL);
                newLayout.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
            }
            Button b = new Button(this);
            b.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            if (arrLetterGuess[i] == 0)
                b.setText (""+intToLetter(i));
            else
                b.setText ("-");
            b.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
            final int num = i;
            if (arrLetterGuess[i] == 0) {
                b.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             guessLetter("" + intToLetter(num));
                                         }
                                     }
                );
            }
            newLayout.addView(b);
            ctr++;
            if (ctr == numButtons || i == 25) {
                innerLayout.addView(newLayout);
                ctr = 0;
            }
        }
    }

    private void guessLetter(String strGuess)
    {
        Toast.makeText(getApplicationContext(),"You guessed "+strGuess, Toast.LENGTH_SHORT).show();
        if (strGuess.equals("-") == false) {
            Intent intent = new Intent();
            intent.putExtra("GUESS", strGuess);
            setResult(1, intent);
            finish();
        }
    }
}
