package enity;

public class Order {
	
	private String orderId;
	private String content;
	private int id;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getConetent() {
		return content;
	}
	public void setConetent(String conetent) {
		this.content = conetent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Order(int id,String orderId, String conetent) {
		super();
		this.orderId = orderId;
		this.content = conetent;
		this.id = id;
	}
	public Order(String orderId, String conetent) {
		super();
		this.orderId = orderId;
		this.content = conetent;
	}
	@Override
	public String toString() {
		return "{\"orderId\":" +"\""+orderId+"\""+ ", \"content\":" +"\""+ content +"\""+ ", \"id\":\"" + id+"\"}";
	}
	
	
	
	
	
	
	
}
