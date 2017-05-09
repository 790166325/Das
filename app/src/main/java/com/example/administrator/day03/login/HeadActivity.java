package com.example.administrator.day03.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;

import com.example.administrator.day03.MainActivity;
import com.example.administrator.day03.R;


public class HeadActivity extends Activity {
    static  final int up=1;
    private SharedPreferences sp;
    private boolean isFirst = false;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case up:


                    Intent no = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivityForResult(no,10086);

                    finish();
                    break;
            }
        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_head);
        sp = getSharedPreferences("oo",MODE_PRIVATE);
        ProgressBar progressBar= (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);
        //主要以键值对的形式取出
        isFirst = sp.getBoolean("check",false);

        if (isFirst) {

            Intent yes = new Intent(this, MainActivity.class);
            startActivity(yes);
            finish();
        } else {
            Message ms=new Message();
            ms.what=up;
           handler.sendMessageDelayed(ms,1355);



        }
    }
}
