package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import enity.Order;
import util.MyDataBase;

public class OrderDao {
	private MyDataBase myDataBase=new MyDataBase();;
	private Connection connection=null;
	 private PreparedStatement preparedStatement=null;
	private ResultSet resultSet=null;
	public List<Order> findall(){
		
		List<Order> orders=new ArrayList<>();
		
		connection=myDataBase.getConnetion();
		String SQL="select *  from order1 ";
		try {
			preparedStatement=connection.prepareStatement(SQL);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				orders.add(new Order(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return orders;
	}
	public Order finOne(int id) {
		Order order=null;
		connection=myDataBase.getConnetion();
		String SQL="select *  from order1 where id=?";
		try {
			preparedStatement=connection.prepareStatement(SQL);
			preparedStatement.setInt(1, id);
			resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				order=new Order(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return order;
	}
	
	public int delete(int id) {
		int rs=0;
		connection=myDataBase.getConnetion();
		String sql="delete from order1 where id=?";
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rs=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
				
		
	}
	
	public int update(Order order) {
		int rs=0;
		
		connection=myDataBase.getConnetion();
		String sql="update  order1 set orderid=?,content=? where id=?";
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, order.getOrderId());
			preparedStatement.setString(2, order.getConetent());
			preparedStatement.setInt(3, order.getId());
			rs=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
	public int insert(Order order) {
		int rs =0;
		connection=myDataBase.getConnetion();
		String sql="insert into order1 (orderid,content) values(?,?)";
		try {
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, order.getOrderId());
			preparedStatement.setString(2, order.getConetent());
			rs=preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}
	
}
