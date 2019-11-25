package com.example.project_test;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class ImageReview extends AppCompatActivity {

    private ImageView imageView;

    private Button decry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_review);

        android.support.v7.widget.Toolbar tool = (Toolbar) findViewById(R.id.image_review);
        setSupportActionBar(tool);

        imageView = findViewById(R.id.check_image);

        Intent get=getIntent();

        final String name=get.getStringExtra("user");

        final String image=get.getStringExtra("image");

        ImageReview.this.setTitle(image);

        try {
            FileInputStream fs = new FileInputStream(getExternalFilesDir(name).getPath()+"/"+image+".jpg");
            Bitmap bitmap  = BitmapFactory.decodeStream(fs);
            imageView.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        Bitmap bitmap  = BitmapFactory.decodeStream(fs);
//        imageView.setImageBitmap(bitmap);

        decry = findViewById(R.id.decry);

        decry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrypt(getExternalFilesDir(name).getPath()+"/"+image+".jpg");
                copyFile(getExternalFilesDir(name).getPath()+"/"+image+".jpg","/storage/emulated/0/DCIM/Camera/"+image+".jpg");
                File pic= new File(getExternalFilesDir(name).getPath()+"/"+image+".jpg");
                if(pic.exists()){
                    pic.delete();
                }
                Toast.makeText(ImageReview.this,"This photo has been put into your System album",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                File file=new File("/storage/emulated/0/DCIM/Camera/"+image+".jpg");
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                ImageReview.this.sendBroadcast(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent get=getIntent();

        final String name=get.getStringExtra("user");

        final String image=get.getStringExtra("image");

        encrypt(getExternalFilesDir(name).getPath()+"/"+image+".jpg");

        super.onBackPressed();
    }

    public void encrypt(String path) {
            if (!FileEnDecryptManager.getInstance().isEncrypt(path))

//            FileEnDecryptManager.getInstance().encryptFile(photoPath.getText().toString());
            FileEnDecryptManager.getInstance().encryptFile(path);
//            Toast.makeText(ImageChooser.this,"Encrypt Success", Toast.LENGTH_LONG).show();
    }

    public void decrypt(String path) {

            if (FileEnDecryptManager.getInstance().isEncrypt(path))
                FileEnDecryptManager.getInstance().decryptFile(path);

    }

    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ( (byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
//            Toast.makeText(I.this,"Success",Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
//            Toast.makeText(ImageChooser.this,"Failed",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
