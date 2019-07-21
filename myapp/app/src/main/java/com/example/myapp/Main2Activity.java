package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.OrderAdapter;
import entity.Order;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import utils.Config;

public class Main2Activity extends AppCompatActivity {
    private Button button;
    private TextView textView;
    private String responseData;
    private List<Order> orderList;
    private ListView listView;
    private String URL="http://192.168.125.115:8000/Server/OrderServlet";//https://www.baidu.com/

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    OrderAdapter orderAdapter=new OrderAdapter(Main2Activity.this,R.layout.order_item,orderList);
                    listView.setAdapter(orderAdapter);
//                    addMenu();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView=new WebView(this);
        setContentView(webView);
        webView.getSettings().setJavaScriptEnabled(true);  //设置WebView属性，能够执行Javascript脚本
        webView.loadUrl("https://www.baidu.com/");

    }







}
