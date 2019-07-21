package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MenuActivity extends AppCompatActivity  implements View.OnClickListener {
    private Button button_getAll,button_add,button_search,button_baidu;
    private EditText editText_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bindView();
        setOcClick();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.btn_menu_getAll:
                //跳转到显示界面
                Intent intent_all=new Intent(MenuActivity.this,ShowActivity.class);
                startActivity(intent_all);
                break;
            case R.id.btn_menu_add:
                Intent intent_add=new Intent(MenuActivity.this,AddActivity.class);
                startActivity(intent_add);
                //跳转到添加订单界面
                break;
            case R.id.btn_menu_search:
                //获取输入的id,然后跳转到显示界面
                String inputInfor=editText_id.getText().toString();
                //装入数据
                Intent intent=new Intent(MenuActivity.this,ShowActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("id",inputInfor);
                intent.putExtras(bundle);
                //启动跳转
                startActivity(intent);
                break;

            case R.id.btn_menu_baidu:
                startActivity(new Intent(MenuActivity.this,Main2Activity.class));
                break;

        }
    }

    /**
     * 绑定布局
     */
    private void bindView(){
        button_getAll=findViewById(R.id.btn_menu_getAll);
        button_add=findViewById(R.id.btn_menu_add);
        button_search=findViewById(R.id.btn_menu_search);
        editText_id=findViewById(R.id.edt_menu_id);
        button_baidu=findViewById(R.id.btn_menu_baidu);
    }
    private void setOcClick(){
        button_search.setOnClickListener(this);
        button_add.setOnClickListener(this);
        button_getAll.setOnClickListener(this);
        button_baidu.setOnClickListener(this);
    }
}
