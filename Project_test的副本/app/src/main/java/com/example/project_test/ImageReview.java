//package com.example.project_test;
//
//import android.content.ContentValues;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//
//public class ImageReview extends AppCompatActivity {
//
//    private ImageView imageView;
//
//    private Button b;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image_review);
//
//        imageView = findViewById(R.id.check_image);
//
//        Intent get=getIntent();
//
//        final String name=get.getStringExtra("user");
//
//        final String image=get.getStringExtra("image");
//
//        b=findViewById(R.id.made);
//
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(ImageReview.this,getExternalFilesDir(name).getPath()+"/"+image+".jpg",Toast.LENGTH_LONG).show();
//            }
//        });
//
//        try {
//            FileInputStream fs = new FileInputStream(getExternalFilesDir(name).getPath()+"/"+image+".jpg");
//            Bitmap bitmap  = BitmapFactory.decodeStream(fs);
//            imageView.setImageBitmap(bitmap);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
////        Bitmap bitmap  = BitmapFactory.decodeStream(fs);
////        imageView.setImageBitmap(bitmap);
//    }
//
//
//    public void encrypt(String path) {
//            if (!FileEnDecryptManager.getInstance().isEncrypt(path))
//
////            FileEnDecryptManager.getInstance().encryptFile(photoPath.getText().toString());
//            FileEnDecryptManager.getInstance().encryptFile(path);
////            Toast.makeText(ImageChooser.this,"Encrypt Success", Toast.LENGTH_LONG).show();
//    }
//
////    public void decrypt() {
////        if(photoPath.getText().toString().isEmpty()){
////            Toast.makeText(ImageChooser.this,"Please Chooose your Photo", Toast.LENGTH_LONG).show();
////        }
////        else {
////            if (FileEnDecryptManager.getInstance().isEncrypt(photoPath.getText().toString()))
////                FileEnDecryptManager.getInstance().decryptFile(photoPath.getText().toString());
////            Toast.makeText(ImageChooser.this,"Decrypt Success", Toast.LENGTH_LONG).show();
////        }
////    }
//}
