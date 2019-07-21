package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.List;

import entity.Order;

public class OrderAdapter extends ArrayAdapter  {
    private int resouceId;
    private  List<Order> orderList;
    public OrderAdapter(Context context, int resourceId, List<Order> orderList){
        super(context,resourceId,orderList);
        this.resouceId=resourceId;
        this.orderList=orderList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;
        ViewHolder holder=null;
        Order order=orderList.get(position);
        //加载子布局及其控件的绑定
        if (convertView==null){
            view=LayoutInflater.from(getContext()).inflate(resouceId,parent,false);
            holder=new ViewHolder();
            holder.textView_content=view.findViewById(R.id.tx_order_item_content);
            holder.textView_orderId=view.findViewById(R.id.tx_order_item_orderId);
            holder.textView_id=view.findViewById(R.id.tx_order_item_id);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder) view.getTag();
        }
        //给子布局控件赋值

        holder.textView_id.setText(order.getId());
        holder.textView_orderId.setText(order.getOrderId());
        holder.textView_content.setText(order.getContent());
        return view;
    }

    class ViewHolder{
        TextView textView_id,textView_orderId,textView_content;
    }


}
