package com.example.guesstheword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    String wordToGuess;
    int guesses[], len;
    TextView textWord, livesCount;
    int guessLetters[];

    boolean hasWon;

    int LETTER_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent i = getIntent();

        wordToGuess = i.getStringExtra("WORD");
        wordToGuess = wordToGuess.toUpperCase();
        textWord = findViewById(R.id.textWord);
        livesCount = findViewById(R.id.livesCount);


        len = wordToGuess.length();
        initGame();

    }

    private void initGame()
    {
        guesses = new int[len];
        guessLetters = new int[26];

        for (int i = 0;i < len;i++)
            guesses[i] = 0;

        updateView();

        for (int i = 0;i < 26;i++)
            guessLetters[i] = 0;

        hasWon = false;
    }

    private String packageValues()
    {
        String st = "";

        for (int i = 0;i < 26;i++)
            st += ""+guessLetters[i];

        return st;
    }

    private void updateView()
    {
        String str = "";

        for (int i = 0;i < len;i++) {
            if (guesses[i] == 1)
                str += "" + wordToGuess.charAt(i);
            else
                str += "_";

            if (i+1 < len)
                str += " ";
        }
        textWord.setText(str);
    }

    public void guessLetter(View v)
    {
        if (!hasWon) {
            Intent intent = new Intent(this, GuessLetter.class);
            intent.putExtra("GUESSES", packageValues());
            startActivityForResult(intent, LETTER_REQUEST_CODE);
        }
        else
            finish();
    }

    private boolean gameWon()
    {
        int sum = 0;

        for (int i = 0;i < len;i++)
            sum += guesses[i];

        if (sum == len)
            return true;
        else
            return false;
    }

    private void block(String str)
    {
        char st = str.charAt(0);
        int index = (int) (st - 'A');

        guessLetters[index] = 1;
    }

    private void findLetter(String str)
    {
        boolean bFound = false;
        char st = str.charAt(0);
        for (int i = 0;i < len;i++)
        {
            if (st == wordToGuess.charAt(i)) {
                guesses[i] = 1;
                bFound = true;
            }
        }
        updateView();
        hasWon = gameWon();
        if (hasWon)
            winGame();
    }

    private void winGame()
    {
        livesCount.setText("CONGRATULATIONS!");
        Button b = findViewById(R.id.guessWord);

        ViewGroup v = (ViewGroup) b.getParent();
        if (v != null)
            v.removeView(b);

        Button guessLetter = findViewById(R.id.guessLetter);
        guessLetter.setText ("Return");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // check if the request code is same as what is passed  here it is 2
        if(requestCode == LETTER_REQUEST_CODE)
        {
            if (resultCode == 1) {
                String message = data.getStringExtra("GUESS");
                block(message);
                findLetter(message);
            //    textView1.setText(message);
             //   textView2.setText("");
            }
            else if (resultCode == 2) {
           //     textView2.setText("CANCELLED");
            }
        }
    }
}
