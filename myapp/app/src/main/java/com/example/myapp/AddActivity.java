package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import entity.Order;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.Config;
import utils.Tips;


public class AddActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_orderId, editText_content;
    private Button button_submit;
    private Order order;

    //更新界面的操作全部在handler的handleMessage来处理
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case Config.MESSAGE_INSERT_OK:
                    Tips.toast(AddActivity.this,"添加成功");
                    startActivity(new Intent(AddActivity.this,ShowActivity.class));
                    break;
                case Config.MESSAGE_INSERT_FAIL:
                    Tips.toast(AddActivity.this,"添加失败");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        bindView();
        button_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //拿到添加的数据
        String oid = editText_orderId.getText().toString();
        String content = editText_content.getText().toString();
        order = new Order(oid, content);
        order.setId("0");

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    OkHttpClient client = new OkHttpClient();

                    RequestBody body = new FormBody.Builder()
                            .add(Config.ACTION, Config.ACTION_INSERT)
                            .add("order", new String(order.toString().getBytes("utf-8")))
                            .build();

                    Request request = new Request.Builder()
                            .post(body)
                            .url(Config.URL)
                            .build();
                    Response response = client.newCall(request).execute();
                    Message message = new Message();
                    message.what = response.code() == 200 ? Config.MESSAGE_INSERT_OK : Config.MESSAGE_INSERT_FAIL;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    void bindView() {
        editText_content = findViewById(R.id.edt_add_content);
        editText_orderId = findViewById(R.id.edt_add_orderId);
        button_submit = findViewById(R.id.btn_add_submit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }
}
