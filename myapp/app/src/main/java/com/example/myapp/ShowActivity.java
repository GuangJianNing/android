package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapter.OrderAdapter;
import entity.Order;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.Config;
import utils.Tips;


public class ShowActivity extends AppCompatActivity {
    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;
    private ListView listView_show;
    private List<Order> orderList;
    private OrderAdapter adapter ;
    private int position;//被长按的listView 的位置
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(@NonNull Message msg) {

            switch (msg.what) {
                case Config.MESSAGE_INIT:
                    adapter = new OrderAdapter(ShowActivity.this, R.layout.order_item, orderList);
                    listView_show.setAdapter(adapter);
                    //让加载圆圈消失
                    progressBar.setVisibility(View.GONE);
                    refreshLayout.setRefreshing(false);
                    addMenu();
                    break;
                case Config.MESSAGE_DELETE_OK:
                    progressBar.setVisibility(View.GONE);
                    orderList.remove(position);
                    adapter.notifyDataSetChanged();
                    Tips.toast(ShowActivity.this,"删除成功");
                    break;
                case Config.MESSAGE_DELETE_FAIL:

                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        bindView();
        init();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });

    }

    private void bindView() {
        listView_show = findViewById(R.id.listview_show);
        progressBar=findViewById(R.id.progress_bar_show);
        refreshLayout=findViewById(R.id.refresh_show);
    }

    /**
     * 初始化showActivity
     */
    private void init() {
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //
                OkHttpClient client = new OkHttpClient();
                RequestBody body = new FormBody
                        .Builder()
                        .add(Config.ACTION, Config.ACTION_FIND_ALL)
                        .build();
                Request request = new Request.Builder().post(body).url(Config.URL).build();

                try {
                    Response response = client.newCall(request).execute();
                    String respoanseData = response.body().string();
                    Log.d("respoanseData", "run: " + respoanseData);
                    //解析json字符串为List<Order>
                    orderList = parseJson(respoanseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Message message = new Message();
                message.what = Config.MESSAGE_INIT;
                handler.sendMessage(message);

            }
        }).start();
    }

    /**
     * 解析json字符串为List<Order>
     *
     * @param jsonString
     * @return
     */
    private List<Order> parseJson(String jsonString) {
        List<Order> orders = new ArrayList<>();
        //
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                //得到数组里面的每一个json对象
                JSONObject object = jsonArray.getJSONObject(i);

                //将json对象封装成Order对象
                //将order对象装入list
                orders.add(new Order(object.getString("id"), object.getString("orderId"), object.getString("content")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * 长按listView弹出选择菜单
     */
    private void addMenu() {
        listView_show.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(Menu.NONE, Config.MENU_DELETE, 0, "删除");
                contextMenu.add(Menu.NONE, Config.MENU_MODIFY, 1, "修改");
            }
        });
    }

    /**
     * 设置弹出小菜单的点击事件
     *
     * @param item 被点击的菜单中的子项
     * @return
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //得到position
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        position = info.position;
        //被点击的菜单子项的id
        int id = item.getItemId();
        switch (id) {
            case Config.MENU_DELETE:
                //显示确认删除对话框
                AlertDialog.Builder dialog=new AlertDialog.Builder(ShowActivity.this);
                //设置消息体
                dialog.setMessage("确认删除");
                //设置确认按钮内容
                dialog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //删除逻辑
                        delete(orderList.get(position).getId());
                    }
                });
                //设置取消按钮内容
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Tips.toast(ShowActivity.this,"取消");
                    }
                });
                //显示对话框
                dialog.show();
                break;
            case Config.MENU_MODIFY:
                //跳转到修改界面
                //获取被点击的数据
                //存入intent，然后跳转
                Order order=orderList.get(position);
                Intent intent=new Intent(ShowActivity.this,ModifyActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("order",order);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }


    private void delete(final String id){
        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //真正的删除逻辑
                OkHttpClient client=new OkHttpClient();
                RequestBody body=new FormBody.Builder()
                        .add(Config.ACTION,Config.ACTION_DELETE)
                        .add("id",id)
                        .build();
                Request request=new Request
                        .Builder()
                        .post(body)
                        .url(Config.URL)
                        .build();
                try {
                    Response response=client.newCall(request).execute();
                    Message message=new Message();
                    message.what=(response.code()==200)?Config.MESSAGE_DELETE_OK:Config.MESSAGE_DELETE_FAIL;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
    }
}
