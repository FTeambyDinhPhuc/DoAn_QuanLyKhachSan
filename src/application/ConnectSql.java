package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSql {
	Connection conn =null;
	public void Connect()
	{
		String connectURL="jdbc:sqlserver://DESKTOP-IOCHANQ\\SQLEXPRESS:1433;databaseName=QuanLyKhachSan;encrypt=false;integratedSecurity=true;";
		try {
			conn = DriverManager.getConnection(connectURL,"sa","123456a@");
			System.out.println("Kết nối SQL thành công!");
		} catch (SQLException e) {
			System.out.println("Kết nối SQL thất bại!");
			System.err.println(e.getMessage()+"/n"+ e.getClass() + "/n" + e.getCause());
		}
	}
	
}
