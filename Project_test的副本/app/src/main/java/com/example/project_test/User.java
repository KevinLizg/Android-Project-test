package com.example.project_test;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class User extends AppCompatActivity {
//    private Button mReturnButton;

    private GridView gridView;

    ImageView iv;

    TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        gridView=(GridView) findViewById(R.id.grid);

        gridView.setAdapter(new ItemAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    Intent g=getIntent();
                    String username=g.getStringExtra("user");
                    Intent in=new Intent(User.this, Imageview.class);
                    in.putExtra("user",username);
                    startActivity(in);
//                    Toast.makeText(User.this,"1",Toast.LENGTH_LONG).show();
                }
                if(position==1){
                    Intent g=getIntent();
                    String username=g.getStringExtra("user");
                    Intent in1=new Intent(User.this,password.class);
                    in1.putExtra("user",username);
                    startActivity(in1);
//                    Toast.makeText(User.this,"2",Toast.LENGTH_LONG).show();
                }
                if(position==2){

                }
            }
        });
    }

    class ItemAdapter extends BaseAdapter{
        Context context;

        int[] images = { R.mipmap.photo,R.mipmap.password,R.mipmap.file};

        String[] names = {"Pictures","Passwords","File"};

        public ItemAdapter(Context context){
            this.context=context;
        }

        @Override
        public int getCount() {
            return names.length;
        }

        @Override
        public Object getItem(int position) {
            return names[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //通过布局填充器LayoutInflater填充网格单元格内的布局
            View v = LayoutInflater.from(context).inflate(R.layout.user_view, null);
            //使用findViewById分别找到单元格内布局的图片以及文字
            iv = (ImageView) v.findViewById(R.id.image);
            tv = (TextView) v.findViewById(R.id.text);
            //引用数组内元素设置布局内图片以及文字的内容
            iv.setImageResource(images[position]);
            tv.setText(names[position]);
            //返回值一定为单元格整体布局v
            return v;
        }
    }
    public void back_to_login(View view) {
        setContentView(R.layout.login);
        Intent intent3 = new Intent(User.this,Login.class) ;
        startActivity(intent3);
        finish();

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main,menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
////            case android.R.id.home:
////                mDrawerLayout.openDrawer(GravityCompat.START);
////                break;
//            case R.id.add_picture:
//                Intent image = new Intent(User.this,ImageChooser.class);
//                startActivity(image);
//                break;
//            case R.id.add_password:
//                showDialog();
//                break;
//            default:
//        }
//        return true;
//    }



//    public void showDialog(){
//        AlertDialog.Builder dia=new AlertDialog.Builder(User.this);
////        dia.setTitle("Course Information");
//        final View v=getLayoutInflater().inflate(R.layout.dialog,null);
//        dia.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        dia.setView(v);
//        dia.show();
//    }
}
