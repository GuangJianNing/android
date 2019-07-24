package service;

import java.util.ArrayList;
import java.util.List;

import dao.OrderDao;
import enity.JsonInfor;
import enity.Order;

public class OrderService {
	//data access object
	private OrderDao orderDao=new OrderDao();
	private JsonInfor jsonInfor=new JsonInfor();
	
	public JsonInfor findAll(int id){
		List<Order> lists=null;
		if(id==0) {
			lists=orderDao.findall();
		}else {
			lists=new ArrayList<>();
			lists.add(orderDao.finOne(id));
		}
		
		jsonInfor.setData(lists);
		jsonInfor.setMsg("查询成功");
		return jsonInfor;
	}
	
	public JsonInfor insert (Order order) {
		int rs=0;
		rs=orderDao.insert(order);
		if(rs!=0) {
			jsonInfor.setMsg("ok");
		}
		return jsonInfor;
	}
	public JsonInfor update(Order order) {
		int rs=0;
		rs=orderDao.update(order);
		if(rs!=0) {
			jsonInfor.setMsg("ok");
		}
		return jsonInfor;
	}
	public JsonInfor delete (int id) {
		int rs=0;
		rs=orderDao.delete(id);
		if(rs!=0) {
			jsonInfor.setMsg("ok");
		}
		return jsonInfor;
	}
}
