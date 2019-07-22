package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapp.R;

import java.util.List;

import entity.Order;

public class CardOrderAdapter extends ArrayAdapter {
    private int subItemId;
    private  List<Order> orderList;
    public CardOrderAdapter(Context context, int subItemId, List<Order> orderList){
        super(context,subItemId,orderList);
        this.orderList=orderList;
        this.subItemId=subItemId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Order order=orderList.get(position);
        View view =null;
        ViewHolder holder=null;
        if (convertView!=null){
            view= LayoutInflater.from(getContext()).inflate(subItemId,parent,false);
            holder=new ViewHolder();
            holder.textView_content=view.findViewById(R.id.tx_card_order_item);
            holder.imageView=view.findViewById(R.id.img_card_order_item);
            view.setTag(holder);
        }else {
            view=convertView;
            holder=(ViewHolder) view.getTag();
        }

        holder.textView_content.setText(order.getContent());

        return view;
    }

    class ViewHolder{
        TextView textView_content;
        ImageView imageView;
    }
}
