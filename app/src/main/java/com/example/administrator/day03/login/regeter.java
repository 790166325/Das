package com.example.administrator.day03.login;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.day03.R;
import com.example.administrator.day03.bean.MyUser;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class regeter extends Activity {

    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.autoCompleteTextView2)
    AutoCompleteTextView autoCompleteTextView2;
    @Bind(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.activity_regeter)
    RelativeLayout activityRegeter;
    @Bind(R.id.regeter11)
    Button regeter11;
    String path;
    private ImageButton image;
    BmobFile bmobFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regeter);
        ButterKnife.bind(this);
        Button bt= (Button) findViewById(R.id.regeter11);
        image= (ImageButton) findViewById(R.id.Touimage);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                MyUser myUser = new MyUser();
                myUser.setUsername(autoCompleteTextView2.getText().toString().trim());
                myUser.setPassword(autoCompleteTextView.getText().toString().trim());
                final BmobFile file=new BmobFile(autoCompleteTextView2.getText().toString().trim(),null,new File(path).toString());

                myUser.setImage(file);
                myUser.signUp(new SaveListener<MyUser>() {
                    @Override
                    public void done(MyUser myUser, BmobException e) {
                        myUser.getImage();
                        Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            path = getImagePath(uri, null);
            bmobFile = new BmobFile(new File(path));

            ContentResolver cr = this.getContentResolver();
            try {
                final Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                /* 将Bitmap设定到ImageView */
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        image.setImageBitmap(bitmap);
                    }
                });
            } catch (FileNotFoundException e) {
                Log.e("qwe", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getImagePath(Uri uri, String seletion) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, seletion, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            }
            cursor.close();

        }
        return path;

    }

}
