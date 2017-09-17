package com.udacity.androidlib;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {


    public static final String EXTRA_JOKE = "extra_joke";

    public static Intent getJokeDisplayActivityIntent(Context context, String joke) {
        Intent intent = new Intent(context, JokeDisplayActivity.class);
        intent.putExtra(EXTRA_JOKE, joke);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        String joke = getIntent().getExtras().getString(EXTRA_JOKE);

        TextView jokeTextView = (TextView) findViewById(R.id.joke_text_view);
        jokeTextView.setText(joke);
    }
}
