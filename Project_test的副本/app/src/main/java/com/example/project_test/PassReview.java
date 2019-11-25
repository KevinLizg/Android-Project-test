package com.example.project_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

public class PassReview extends AppCompatActivity {

    private TextView password;

//    private Button re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_review);

        password = findViewById(R.id.check_pass);

        Toolbar tool = (Toolbar) findViewById(R.id.pass_review);
        setSupportActionBar(tool);

//        re = findViewById(R.id.reset);

        Intent get=getIntent();

        String username=get.getStringExtra("user");

        String path=get.getStringExtra("path");

        String pass=ReadTxtFromSDCard(getExternalFilesDir(username).getPath() + File.separator + path +".txt");

        PassReview.this.setTitle(path);

        password.setText(pass);

    }

    @Override
    public void onBackPressed() {
        Intent get=getIntent();

        final String username=get.getStringExtra("user");

        final String path=get.getStringExtra("path");

        encrypt(getExternalFilesDir(username).getPath()+ File.separator + path +".txt");

        super.onBackPressed();
    }

    public void encrypt(String path) {
//        if (!FileEnDecryptManager.getInstance().isEncrypt(path))
            FileEnDecryptManager.getInstance().encryptFile(path);
    }

    private String ReadTxtFromSDCard(String filename){

        StringBuilder sb = new StringBuilder("");
        //判断是否有读取权限
        if(Environment.getExternalStorageState().
                equals(Environment.MEDIA_MOUNTED)){

            //打开文件输入流
            try {
                FileInputStream input = new FileInputStream(filename);
                byte[] temp = new byte[1024];

                int len = 0;
                //读取文件内容:
                while ((len = input.read(temp)) > 0) {
                    sb.append(new String(temp, 0, len));
                }
                //关闭输入流
                input.close();
            } catch (java.io.IOException e) {
                Log.e("ReadTxtFromSDCard","ReadTxtFromSDCard");
                e.printStackTrace();
            }

        }
        return sb.toString();
    }

}
