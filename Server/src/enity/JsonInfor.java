package enity;
import java.util.List;

public class JsonInfor {
	private List<Order> data;
	private String msg;
	private String action;
	public List<Order> getData() {
		return data;
	}
	public void setData(List<Order> data) {
		this.data = data;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public JsonInfor(List<Order> data, String msg, String action) {
		super();
		this.data = data;
		this.msg = msg;
		this.action = action;
	}
	@Override
	public String toString() {
		return "{data:" + data + ", msg:" + msg + ", action:" + action + "}";
	}
	public JsonInfor() {
		super();
	}
	
	
	
	
	
	
	
	
}
