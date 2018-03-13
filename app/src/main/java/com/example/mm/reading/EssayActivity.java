package com.example.mm.reading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class EssayActivity extends AppCompatActivity {


    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_essay);
        Intent intent = getIntent();
        String title = intent.getStringExtra(TITLE);
        String author = intent.getStringExtra(AUTHOR);
        String content = intent.getStringExtra(CONTENT);
        TextView title_txt = (TextView) findViewById(R.id.Essaytitle);
        TextView author_txt = (TextView) findViewById(R.id.Essayauthor);
        TextView content_txt = (TextView) findViewById(R.id.Essaycontent);
        title_txt.setText(title);
        author_txt.setText("作者"+author);
        content_txt.setText("\u3000\u3000"+content);
    }
}
