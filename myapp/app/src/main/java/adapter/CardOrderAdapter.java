package adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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
        return super.getView(position, convertView, parent);
    }

    class ViewHolder{

    }
}
