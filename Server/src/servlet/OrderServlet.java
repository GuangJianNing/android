package servlet;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import enity.JsonInfor;
import enity.Order;
import service.OrderService;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/OrderServlet")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService;

    /**
     * Default constructor. 
     */
    public OrderServlet() {
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		/** 设置响应头允许ajax跨域访问 **/
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		int selectFlag=0;
		System.currentTimeMillis();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date=format.format(System.currentTimeMillis());
		String ip=request.getRemoteHost();
		
		
		Writer out=response.getWriter();
		orderService=new OrderService();
		
		String action=request.getParameter("action");
		
		System.out.println(action+"||时间："+date+"||ip:"+ip);
		switch (action) {
		case "selectall":
			if(request.getParameter("id")!=null) {
				selectFlag=Integer.parseInt(request.getParameter("id"));
				System.out.println("selectall->"+String.valueOf(selectFlag));
			}
			out.write(select(selectFlag).toString());
			
			break;
		case"delete":
			int id=Integer.parseInt(request.getParameter("id"));
			out.write(delete(id).toString());
			break;
		
		case "update":
			String order=request.getParameter("order");
			System.out.println("servlet update->"+order);
			Order order_update=parseOrderFromJson(order);
			out.write(update(order_update).toString());
			
			break;
		case "insert" :
			String order_insert_str=request.getParameter("order");
			Order order_insert=parseOrderFromJson(order_insert_str);
			out.write(insert(order_insert).toString());
			break;

		default:
			break;
		}
		
		out.flush();
		
		
		
		
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	private StringBuilder select(int id) {
		List<Order> orders=orderService.findAll(id).getData();
		StringBuilder data=new StringBuilder();
		data.append("{\"data\":[");
		int count=0;
		for (Order order : orders) {
			data.append(order.toString());
			count++;
			if(count<orders.size()) {
				data.append(",");
			}
			
		}
		data.append("]}");
		System.out.println(data.toString());
		return data;
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	private StringBuilder delete(int id) {
		
		String msg=orderService.delete(id).getMsg();
		StringBuilder builder=new StringBuilder();
		builder.append("{\"msg\":\"").append(msg).append("\"").append("}");
		System.out.println("servlet delete->"+builder.toString());
		return builder ;
	}
	/**
	 * 
	 * @param order
	 * @return
	 */
	private StringBuilder update(Order order) {
		String msg=orderService.update(order).getMsg();
		StringBuilder builder=new StringBuilder();
		builder.append("{\"msg\":\"").append(msg).append("\"}");
		return builder;
	}
	/**
	 * 
	 * @param order
	 * @return
	 */
	private StringBuilder insert(Order order) {
		StringBuilder builder=new StringBuilder();
		String msg=orderService.insert(order).getMsg();
		builder.append("{\"msg\":\"").append(msg).append("\"}");
		return builder;
	}
	/**
	 * 
	 * @param jsonData
	 * @return
	 */
	private Order parseOrderFromJson(String jsonData)  {
		Order order=null;
		try {
			JSONObject jsonObject=new JSONObject(jsonData);
			System.out.println(jsonObject.toString());
			int id_1=Integer.parseInt(jsonObject.getString("id"));
			String orderId=jsonObject.getString("orderId");
			String content=jsonObject.getString("content");
			System.out.println("servlet parseOrderFromJson->"+id_1+orderId+content);
			order=new Order(id_1,orderId,content);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return order;
	}

}
