package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import dataBase.MyOpenHelper;
import utils.Tips;

public class RegistActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_username,editText_password;
    private Button button_submit;

    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        bindView();
        button_submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        //注册逻辑
        //获得注册信息
        String name=editText_username.getText().toString();
        String pass=editText_password.getText().toString();
        //实例化数据库
        sqLiteDatabase=new MyOpenHelper(RegistActivity.this,"my.db",null,1)
                .getWritableDatabase();
        //存放插入数据库的信息
        ContentValues values=new ContentValues();
        values.put("username",name);
        values.put("password",pass);
        long reasult=0;

        //insert into login (username,password) values(name,pass)
        reasult=sqLiteDatabase.insert("login",null,values);
        if (reasult!=0){
            //
            Intent intent=new Intent(RegistActivity.this,MainActivity.class);
            Bundle bundle=new Bundle();
            bundle.putString("uname",name);
            bundle.putString("upass",pass);
            intent.putExtras(bundle);
            startActivity(intent);
            //Toast.makeText(RegistActivity.this,"注册成功",Toast.LENGTH_LONG).show();
            Tips.toast(RegistActivity.this,"注册成功");
        }else {
            //Toast.makeText(RegistActivity.this,"注册失败",Toast.LENGTH_LONG).show();
            Tips.toast(RegistActivity.this,"注册失败");
        }
    }

    private void bindView(){
        editText_username=findViewById(R.id.edt_regist_username);
        editText_password=findViewById(R.id.edt_regist_password);
        button_submit=findViewById(R.id.btn_regist_submit);
    }
}
