package entity;

import java.io.Serializable;

public class Order implements Serializable {
    private String id;
    private String orderId;
    private String content;

    public Order(String id, String orderId, String content) {
        this.id = id;
        this.orderId = orderId;
        this.content = content;
    }

    public Order(String orderId, String content) {
        this.orderId = orderId;
        this.content = content;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("{\"id\":\"")
                .append(id)
                .append("\",")
                .append("\"orderId\":\"")
                .append(orderId)
                .append("\",")
                .append("\"content\":\"")
                .append(content)
                .append("\"")
                .append("}");
        //{}

        return builder.toString();
    }


}
