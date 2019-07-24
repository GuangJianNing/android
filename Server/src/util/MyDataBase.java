package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.OrderDao;

public class MyDataBase {
	
	String name="root";
	String pass="root";
	String url="jdbc:mysql://localhost:3306/server";
	String driverName="com.mysql.jdbc.Driver";
	
	public Connection getConnetion()  {
		Connection connection=null;
		try {
			Class.forName(driverName);
			connection=DriverManager.getConnection(url,name, pass);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	
	
}
