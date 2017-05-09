package com.example.administrator.day03.login;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.administrator.day03.R;

public class GoodActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good);
        Bundle b = getIntent().getExtras();
        String username = b.getString("name");
        TextView textView = (TextView) findViewById(R.id.nameeee);
        textView.setText(username);
    }
}
