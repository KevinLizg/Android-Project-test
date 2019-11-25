package com.example.project_test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

//import com.example.foolishfan.Project.view.Gesture;
//import com.example.foolishfan.user_v10.R;
import com.example.project_test.view.Gesture;

public class GestureLock extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_lock);
        final UserDataManager dataManager=new UserDataManager(GestureLock.this);
        Gesture gestureLockView = (Gesture) findViewById(R.id.gestureView);
        final String[] shit = new String[1];
        gestureLockView.setGestureListener(new Gesture.GestureListener() {
            @Override
            public boolean getGesture(String gestureCode) {
                if (0 == gestureCode.length()) return false;
                if (gestureCode.length()!=0) {
                    shit[0] =gestureCode;
                    return true;
                }

                return false;
        }
        });
        Button b=(Button) findViewById(R.id.shit1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDataManager check=new UserDataManager(GestureLock.this);
                check.openDataBase();
                Intent get=getIntent();
                int c=check.findUserByNameAndGesture(get.getStringExtra("user"),shit[0]);
                if(c==1){
                    Intent g = getIntent();
                    String username=g.getStringExtra("user");
                    Intent intent = new Intent(GestureLock.this,User.class) ;//切换Login Activity至User Activity
                    intent.putExtra("user",username);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(GestureLock.this, getString(R.string.login_fail),Toast.LENGTH_SHORT).show();//登录失败提示
                }
            }
        });
//        Toast.makeText(this, shit[0],Toast.LENGTH_SHORT).show();//登录成功提示
//        UserDataManager check=new UserDataManager(GestureLock.this);
//        check.openDataBase();
//        Intent get=getIntent();
//        int c=check.findUserByNameAndGesture(get.getStringExtra("user"),shit[0]);
//        if(c==1){
//            Intent intent = new Intent(GestureLock.this,User.class) ;//切换Login Activity至User Activity
//            startActivity(intent);
//            Toast.makeText(this, shit[0],Toast.LENGTH_SHORT).show();//登录成功提示
//        }
//        else{
//            Toast.makeText(this, getString(R.string.login_fail),Toast.LENGTH_SHORT).show();//登录失败提示
//        }
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(GestureLock.this, shit[0], Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
