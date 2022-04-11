package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectSql {
	public static final String DATABASE_URL = "jdbc:sqlserver://DESKTOP-IOCHANQ\\SQLEXPRESS:1433;databaseName=QuanLyKhachSan;encrypt=false;integratedSecurity=true;";
	public static final String DATABASE_USERNAME = "sa";
	public static final String DATABASE_PASSWORD = "123456a@";	
}

