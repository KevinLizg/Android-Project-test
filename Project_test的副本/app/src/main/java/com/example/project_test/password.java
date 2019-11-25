package com.example.project_test;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class password extends AppCompatActivity {

    Button b;

    EditText des,password;

    TextView d,pa;

    List<Passw> PassList = new ArrayList<Passw>();

    List<Passw> newPassList = new ArrayList<Passw>();

    PasswordAdapter adapter;

    ListView pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);



        Toolbar chooser = (Toolbar) findViewById(R.id.chooser);
        setSupportActionBar(chooser);

        Intent g= getIntent();
        final String username=g.getStringExtra("user");

//        File file=new File(getExternalFilesDir(username).getPath());

        pass= (ListView) findViewById(R.id.list_password);


        PassList=getPassList(username);
        adapter=new PasswordAdapter(password.this,R.layout.pass,PassList);
        pass.setAdapter(adapter);

        pass.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int index=position;
                AlertDialog.Builder delete=new AlertDialog.Builder(password.this);
                delete.setTitle("Delete Password");
                delete.setPositiveButton("SURE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Passw passw=PassList.remove(index);
                        String [] remove={passw.getDis(),username};
                        UserDataManager.DataBaseManagementHelper dbHelper=new UserDataManager.DataBaseManagementHelper(password.this);
                        SQLiteDatabase re=dbHelper.getWritableDatabase();
                        re.execSQL("delete from user_password where pass_dis=? and user_name=?",remove);
                        re.close();
                        File file=new File(getExternalFilesDir(username).getPath()+File.separator+passw.getDis()+".txt");
                        if(file.exists()){
                            file.delete();
                        }
                        adapter.notifyDataSetChanged();
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

        pass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                final Passw p=PassList.get(position);
//                AlertDialog.Builder dia=new AlertDialog.Builder(password.this);
//                final View v=getLayoutInflater().inflate(R.layout.dialog_text,null);
//                d= (TextView) v.findViewById(R.id.text_des);
//                pa=(TextView) v.findViewById(R.id.text_pass);
//                d.setText(p.getDis());
////                getExternalFilesDir("Kevin").getPath() + File.separator + "log.txt"
//                String password= ReadTxtFromSDCard(getExternalFilesDir(username).getPath() + File.separator + p.getDis()+".txt");
////                decrypt(getExternalFilesDir(username).getPath() + File.separator + p.getDis()+".txt");
//                pa.setText(password);
//                dia.setPositiveButton("OK", new DialogInterface.OnClickListener() {
////                    @RequiresApi(api = Build.VERSION_CODES.M)
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        String password= ReadTxtFromSDCard(getExternalFilesDir(username).getPath() + File.separator + p.getDis()+".txt");
////                        decrypt(getExternalFilesDir(username).getPath() + File.separator + p.getDis()+".txt");
////                        pa.setText(password);
//                    }
//                });
//                dia.setView(v);
//                dia.show();

                Passw p=PassList.get(position);
                Intent intent=new Intent(password.this,PassReview.class);
                intent.putExtra("path",p.getDis());
                intent.putExtra("user",username);
                decrypt(getExternalFilesDir(username).getPath() + File.separator + p.getDis()+".txt");
                startActivity(intent);
            }
        });
//        List<Passw> PassList = new ArrayList<Passw>();
//
//        UserDataManager.DataBaseManagementHelper dbHelper=new UserDataManager.DataBaseManagementHelper(password.this);
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery("select * from user_password where user_name=?",new String[]{username});
//
//        if(cursor.moveToFirst()){
//            do{
//                String password=cursor.getString(cursor.getColumnIndex("pass_path"));
//                String passdis=cursor.getString(cursor.getColumnIndex("pass_dis"));
//                Passw passw=new Passw(passdis,password);
//                PassList.add(passw);
//            }while (cursor.moveToNext());
//            cursor.close();
//        }
//        db.close();
//
//        adapter=new PasswordAdapter(password.this,R.layout.pass,PassList);
//
//        pass.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.password_add,menu);
        return true;
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            Intent g=getIntent();
            String username=g.getStringExtra("user");
        switch (item.getItemId()){
            case R.id.add_pass:
                showDialog(username);
                break;
            default:
        }
        return true;
    }

    public void showDialog(final String username){
        AlertDialog.Builder dia=new AlertDialog.Builder(password.this);
        final UserDataManager.DataBaseManagementHelper dbHelper=new UserDataManager.DataBaseManagementHelper(password.this);
        final View v=getLayoutInflater().inflate(R.layout.dialog,null);
        dia.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                des=(EditText) v.findViewById(R.id.description);
                password=(EditText) v.findViewById(R.id.password);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                if(des.getText().toString().isEmpty()||password.getText().toString().isEmpty()){
                    Toast.makeText(password.this,"INVALID",Toast.LENGTH_LONG).show();
                }
                else {
                    values.put("user_name",username);
                    values.put("pass_dis",des.getText().toString());
                    //Write text
                    try {
                        FileWriter fw = new FileWriter(getExternalFilesDir(username).getPath() + File.separator +
                                des.getText().toString()+".txt");
                        fw.write(password.getText().toString());
//                        encrypt(getExternalFilesDir(username).getPath() + File.separator +
//                                des.getText().toString()+".txt");
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    encrypt(getExternalFilesDir(username).getPath() + File.separator+
                            des.getText().toString()+".txt");
                    //End
//                    values.put("pass_path",password.getText().toString());
                    db.insert("user_password",null,values);
                    db.close();
                }
                PassList.clear();
                newPassList=getPassList(username);
                PassList.addAll(newPassList);
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        dia.setView(v);
        dia.show();
    }

    public List<Passw> getPassList(String username){
        List<Passw> PassList = new ArrayList<Passw>();

        UserDataManager.DataBaseManagementHelper dbHelper=new UserDataManager.DataBaseManagementHelper(password.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from user_password where user_name=?",new String[]{username});

        if(cursor.moveToFirst()){
            do{
//                String password=cursor.getString(cursor.getColumnIndex("pass_path"));
                String passdis=cursor.getString(cursor.getColumnIndex("pass_dis"));
//                Passw passw=new Passw(passdis,password);
                Passw passw=new Passw(passdis);
                PassList.add(passw);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();

        return PassList;
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

    public void encrypt(String path) {
            if (!FileEnDecryptManager.getInstance().isEncrypt(path))
            FileEnDecryptManager.getInstance().encryptFile(path);
    }

    public void decrypt(String path) {
        if (FileEnDecryptManager.getInstance().isEncrypt(path))
            FileEnDecryptManager.getInstance().decryptFile(path);
    }
}
