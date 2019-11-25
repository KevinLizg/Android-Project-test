package com.example.project_test;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_test.util.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;


public class ImageChooser extends AppCompatActivity {

    private static final int REQUEST_CHOOSE_IMAGE = 0x01;

    private static final int REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT = 0xff;

    private TextView photoPath;
    private ImageView photo;

    private EditText tag;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.locker,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_chooser);

        findViewById(R.id.choose_image_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareToOpenAlbum();
            }
        });
        photoPath = (TextView) findViewById(R.id.photo_path);
        photo = (ImageView) findViewById(R.id.photo);
        tag = (EditText) findViewById(R.id.tag);

        Toolbar tool = (Toolbar) findViewById(R.id.chooser);
        setSupportActionBar(tool);
//        en=findViewById(R.id.en);
//        de=findViewById(R.id.de);

//        en.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                encrypt();
//            }
//        });
//
//        de.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                decrypt();
//            }
//        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.enc:
                if(tag.getText().toString().isEmpty()){
                    Toast.makeText(ImageChooser.this,"Please Set A Tag for your photo",Toast.LENGTH_LONG).show();
                }
                else{
                Intent get=getIntent();
                encrypt(get.getStringExtra("user"),tag.getText().toString());
                UserDataManager.DataBaseManagementHelper dbHelper=new UserDataManager.DataBaseManagementHelper(ImageChooser.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("user_name",get.getStringExtra("user"));
//                values.put("pic_path",photoPath.getText().toString());
                values.put("pic_tag",tag.getText().toString());
                db.insert("user_picture",null,values);
                db.close();
                }
//                Toast.makeText(ImageChooser.this,get.getStringExtra("user"),Toast.LENGTH_LONG).show();
                break;
//            case R.id.dec:
//                decrypt();
//                break;
        }
        return true;
    }

    private void prepareToOpenAlbum() {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT);
//        } else {
            openAlbum();
//        }
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_WRITE_EXTERNAL_PERMISSION_GRANT) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(ImageChooser.this, "You denied the write_external_storage permission", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CHOOSE_IMAGE && resultCode == RESULT_OK) {
            Uri uri =  data.getData();
            Log.d("Tianma", "Uri = " + uri);
            String path = ImageUtils.getRealPathFromUri(this, uri);
            Log.d("Tianma", "realPath = " + path);

            photoPath.setVisibility(View.INVISIBLE);
            photoPath.setText(path);
            int requiredHeight = photo.getHeight();
            int requiredWidth = photo.getWidth();
            Bitmap bm = ImageUtils.decodeSampledBitmapFromDisk(path, requiredWidth, requiredHeight);
            photo.setImageBitmap(bm);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void encrypt(String username,String tag) {
        if(photoPath.getText().toString().isEmpty()){
            Toast.makeText(ImageChooser.this,"Please Chooose your Photo", Toast.LENGTH_LONG).show();
        }
        else{
        if (!FileEnDecryptManager.getInstance().isEncrypt(photoPath.getText().toString()))
            copyFile(photoPath.getText().toString(),getExternalFilesDir(username).getPath()+"/"+tag+".jpg");
            FileEnDecryptManager.getInstance().encryptFile(photoPath.getText().toString());
            File f=new File(photoPath.getText().toString());
            if(f.exists()){
                f.delete();
            }
//            FileEnDecryptManager.getInstance().encryptFile(photoPath.getText().toString());
//            FileEnDecryptManager.getInstance().encryptFile(getExternalFilesDir(username).getPath()+"/"+tag+".jpg");
            Toast.makeText(ImageChooser.this,"Encrypt Success", Toast.LENGTH_LONG).show();
        }
    }

//    public void decrypt() {
//        if(photoPath.getText().toString().isEmpty()){
//            Toast.makeText(ImageChooser.this,"Please Chooose your Photo", Toast.LENGTH_LONG).show();
//        }
//        else {
//        if (FileEnDecryptManager.getInstance().isEncrypt(photoPath.getText().toString()))
//            FileEnDecryptManager.getInstance().decryptFile(photoPath.getText().toString());
//            Toast.makeText(ImageChooser.this,"Decrypt Success", Toast.LENGTH_LONG).show();
//        }
//    }

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
            Toast.makeText(ImageChooser.this,"Success",Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(ImageChooser.this,"Failed",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }


}

