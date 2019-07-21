package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;

import java.util.List;

import entity.Order;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Order> orderList;
    private int resouceId;
    public RecyclerViewAdapter(List<Order> orderList,int id){
        this.orderList=orderList;
        this.resouceId=id;

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textView_id,textView_content,textView_orderid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView_content=itemView.findViewById(R.id.tx_order_item_content);
            this.textView_id=itemView.findViewById(R.id.tx_order_item_id);
            this.textView_orderid=itemView.findViewById(R.id.tx_order_item_id);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(resouceId,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Order order=orderList.get(position);
        ViewHolder viewHolder=(ViewHolder)holder;
        viewHolder.textView_orderid.setText(order.getOrderId());
        viewHolder.textView_id.setText(order.getId());
        viewHolder.textView_content.setText(order.getContent());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
