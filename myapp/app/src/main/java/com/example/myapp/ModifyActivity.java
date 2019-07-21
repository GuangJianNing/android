package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_id, editText_orderId, editText_content;
    private Button button_submit;
    private Order order;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case Config.MESSAGE_UPDATE_OK:
                    Tips.toast(ModifyActivity.this,"修改成功");
                    startActivity(new Intent(ModifyActivity.this,ShowActivity.class));
                    break;
                case Config.MESSAGE_UPDATE_FAIL:
                    Tips.toast(ModifyActivity.this,"修改失败");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        bindView();
        //显示需要修改的数据
        showModifyData();
        button_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_modify_submit:
                final String orderId = editText_orderId.getText().toString();
                String content = editText_content.getText().toString();
                order.setContent(content);
                order.setOrderId(orderId);
                //
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        try {

                        RequestBody body = new FormBody.Builder()
                                .add(Config.ACTION, Config.ACTION_UPDATE)
                                .add("order", new String (order.toString().getBytes("utf-8")))
                                .build();
                            Log.d("orderstring", "run: "+new String(order.toString().getBytes("utf-8")));
                        Request request = new Request.Builder()
                                .url(Config.URL)
                                .post(body)
                                .build();


                            Response response = client.newCall(request).execute();
                            Message message = new Message();
                            message.what = response.code() == 200 ? Config.MESSAGE_UPDATE_OK : Config.MESSAGE_UPDATE_FAIL;
                            handler.sendMessage(message);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }

    private void bindView() {
        editText_content = findViewById(R.id.edt_modify_content);
        editText_id = findViewById(R.id.edt_modify_id);
        editText_orderId = findViewById(R.id.edt_modify_orderId);
        button_submit = findViewById(R.id.btn_modify_submit);
    }

    /**
     * 展示需要修改的数据
     */
    private void showModifyData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            order = (Order) bundle.getSerializable("order");
            editText_id.setText(order.getId());
            editText_orderId.setText(order.getOrderId());
            editText_content.setText(order.getContent());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }
}
