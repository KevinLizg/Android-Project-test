package com.example.project_test;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_test.util.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Imageview extends AppCompatActivity {

    private List<Bm> arrayList=new ArrayList<>();

//    private List<Bm> nList=new ArrayList<>();

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        Intent get=getIntent();
        String username=get.getStringExtra("user");
        Toolbar add = (Toolbar) findViewById(R.id.add_tool);
        setSupportActionBar(add);

        arrayList=getPic(username);

        BitmapAdapter testAdapter=new BitmapAdapter(Imageview.this,R.layout.bitm,arrayList);
        ListView listView=findViewById(R.id.list_image);
        listView.setAdapter(testAdapter);
        testAdapter.notifyDataSetChanged();

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int index=position;
                AlertDialog.Builder delete=new AlertDialog.Builder(Imageview.this);
                delete.setTitle("Delete Picture");
                delete.setPositiveButton("SURE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bm b=arrayList.remove(index);
                        String remove[]={b.getTag()};

                        UserDataManager.DataBaseManagementHelper dbHelper=new UserDataManager.DataBaseManagementHelper(Imageview.this);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        db.execSQL("delete from user_picture where pic_tag=?",remove);
                        Intent g=getIntent();

                        File f=new File(getExternalFilesDir(g.getStringExtra("user")).getPath()+"/"+b.getTag()+".jpg");

                        System.out.println(getExternalFilesDir(g.getStringExtra("user")).getPath()+"/"+b.getTag()+".jpg");
                        if(f.exists()){
                        f.delete();
                        }
                    }
                });
                delete.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                delete.show();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int index=position;
                    final Bm b=arrayList.get(position);
                    Intent name=getIntent();
                    String user=name.getStringExtra("user");
                    Intent next=new Intent(Imageview.this,ImageReview.class);
                    decrypt(getExternalFilesDir(name.getStringExtra("user")).getPath()+"/"+b.getTag()+".jpg");
                    next.putExtra("user",user);
                    next.putExtra("image",b.getTag());
                    startActivity(next);
            }
        });

    }
// image.setImageBitmap(b.getImage());
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.password_add,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_pass:
                Intent in=new Intent(Imageview.this,ImageChooser.class);
                Intent get=getIntent();
                String username=get.getStringExtra("user");
                in.putExtra("user",username);
                startActivity(in);
                break;
            default:
        }
        return true;
    }

    public List<Bm> getPic(String username){
//        File file=new File(getExternalFilesDir(username).getPath());


//        UserDataManager.DataBaseManagementHelper dbHelper=new UserDataManager.DataBaseManagementHelper(Imageview.this);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery("select * from user_picture where user_name=?",new String[]{username});
//        if(cursor.moveToFirst()){
//            do{
//                String get_tag = cursor.getString(cursor.getColumnIndex("pic_tag"));
//            }
//            while (cursor.moveToNext());
//            cursor.close();
//        }
//        db.close();

//        String [] f=file.list();
//        for(int i=0;i<file.list().length;i++){
//            System.out.println(getExternalFilesDir(username).getPath()+"/"+f[i]);
            try {
                UserDataManager.DataBaseManagementHelper dbHelper=new UserDataManager.DataBaseManagementHelper(Imageview.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                Cursor cursor = db.rawQuery("select * from user_picture where user_name=?",new String[]{username});
                if(cursor.moveToFirst()){
                    do{
                        String get_tag = cursor.getString(cursor.getColumnIndex("pic_tag"));
//                FileInputStream fs = new FileInputStream(getExternalFilesDir(username).getPath()+"/"+get_tag+".jpg");
                FileInputStream fs = new FileInputStream(getExternalFilesDir(username).getPath()+"/"+get_tag+".jpg");

                Bitmap bitmap  = BitmapFactory.decodeStream(fs);
                //Start
//                UserDataManager.DataBaseManagementHelper dbHelper=new UserDataManager.DataBaseManagementHelper(Imageview.this);
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//                Cursor cursor = db.rawQuery("select * from user_picture where user_name=?",new String[]{username});
//                if(cursor.moveToFirst()){
//                    do{
//                        String get_tag = cursor.getString(cursor.getColumnIndex("pic_tag"));
                        Bm image=new Bm(bitmap,get_tag);
                        arrayList.add(image);
                    }
                    while (cursor.moveToNext());
                    cursor.close();
                }
                db.close();
                //End
//                Bm image=new Bm(bitmap,get_tag);
//                arrayList.add(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

//        }
        return arrayList;
    }

    public void encrypt(String path) {
            if (!FileEnDecryptManager.getInstance().isEncrypt(path)) {
                FileEnDecryptManager.getInstance().encryptFile(path);
            }
    }

    public void decrypt(String path) {
            if (FileEnDecryptManager.getInstance().isEncrypt(path)){
                FileEnDecryptManager.getInstance().decryptFile(path);
            }
    }


}
