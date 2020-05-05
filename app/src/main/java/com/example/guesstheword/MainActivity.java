package com.example.guesstheword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void newGame(View V)
    {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra ("WORD","Remember");
        startActivity (intent);
    }

    public void quitGame(View V)
    {
        finish();
    }
}
