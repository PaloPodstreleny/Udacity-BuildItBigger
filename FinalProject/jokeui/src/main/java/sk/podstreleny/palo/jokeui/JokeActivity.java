package sk.podstreleny.palo.jokeui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokeActivity extends AppCompatActivity {

    public static final String JOKE_EXTRA = "JOKE_EXTRA";
    private TextView mJokeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);


        final Intent intent = getIntent();
        if(intent != null && intent.hasExtra(JOKE_EXTRA)){
            mJokeTextView = findViewById(R.id.joke_tv);
            final String joke = intent.getStringExtra(JOKE_EXTRA);
            mJokeTextView.setText(joke);
        }





    }




}
