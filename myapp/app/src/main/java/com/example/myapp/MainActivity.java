package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import dataBase.MyOpenHelper;
import utils.Tips;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private CheckBox checkBox_remenberPass;
    private EditText editText_username,editText_password;
    private Button button_login,button_regist;
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase database;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        setOnClick();
        showRegistInfor();
        //实例化sharedPreferences
        sharedPreferences=getSharedPreferences("remenber",MODE_PRIVATE);

        //如果勾选了chekbox把数据填充到编辑框
        Boolean isChecked=sharedPreferences.getBoolean("isChecked",false);
        if (isChecked){
            //填充数据
            editText_username.setText(sharedPreferences.getString("username",null));
            editText_password.setText(sharedPreferences.getString("password",null));

            //保持勾选状态
            checkBox_remenberPass.setChecked(isChecked);
        }
    }

    @Override
    public void onClick(View view) {

        //点击逻辑

        int id=view.getId();
        switch (id){
            case R.id.btn_main_login:
                //登录逻辑
                String name=editText_username.getText().toString();
                String pass=editText_password.getText().toString();

                //去数据库查询对应信息是否存在
                database=new MyOpenHelper(MainActivity.this,"my.db",null,1).getWritableDatabase();
                //String sql=select *  from login where username=name and password=pass
                //database.executeSQL(sql);
                Cursor cursor =database.
                        query("login",
                                null,
                                "username=? and password=?",
                                new String[]{name,pass},
                                null,
                                null,
                                null);
                if (cursor!=null&&cursor.moveToFirst()){
                    //如果存在……
                    //实例化editor
                    editor = sharedPreferences.edit();
                    //检查checkBox是否被选中
                    if (checkBox_remenberPass.isChecked()) {

                        //把数据存入
                        editor.putString("username", name);
                        editor.putString("password", pass);
                        editor.putBoolean("isChecked",true);
                    }else {
                        editor.clear();
                    }
                    //把数据写入到文件---insert
                    editor.apply();
                    //跳转界面
                    Tips.toast(MainActivity.this,"登录成功");
                    startActivity(new Intent(MainActivity.this,MenuActivity.class));
                }else {
                    //如果不存在……
                    //Toast.makeText(MainActivity.this,"用户名或者密码不正确",Toast.LENGTH_LONG).show();
                    Tips.toast(MainActivity.this,"用户名或者密码不正确");
                }

                //select *  from login  where username= and password=
                break;
            case R.id.btn_main_regist:
                //注册逻辑
              //跳转到注册界面
                Intent intent=new Intent(MainActivity.this,RegistActivity.class);
                startActivity(intent);
                break;
        }
    }


    /**
     * 绑定控件
     * */
    private void bindView(){
        editText_password=findViewById(R.id.edt_main_password);
        editText_username=findViewById(R.id.edt_main_username);
        button_login=findViewById(R.id.btn_main_login);
        button_regist=findViewById(R.id.btn_main_regist);
        checkBox_remenberPass=findViewById(R.id.checkbox_main_remenberPass);
    }

    /**
     * 批量注册点击事件
     */
    private void setOnClick(){
        button_regist.setOnClickListener(this);
        button_login.setOnClickListener(this);
    }
    /**
     * 显示注册成功过后的用户信息
     *
     * */
    private  void showRegistInfor(){
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            //显示注册信息
            editText_username.setText(bundle.getString("uname"));
            editText_password.setText(bundle.getString("upass"));
        }
    }
    /**
     * renmenberPass
     * */
    private void renmenberPass(){

    }
}
