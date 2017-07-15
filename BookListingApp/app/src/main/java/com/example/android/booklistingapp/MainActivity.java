package com.example.android.booklistingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button_search);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = (EditText) findViewById(R.id.edit_text);
                String answer = editText.getText().toString().replaceAll("\\s+", "").toLowerCase();
                Intent bookActivityIntent = new Intent(MainActivity.this, BookActivity.class);
                bookActivityIntent.putExtra("answer", answer);
                // Start the new activity
                startActivity(bookActivityIntent);
            }
        });

    }
}
