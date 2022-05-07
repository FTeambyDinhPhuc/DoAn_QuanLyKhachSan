package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.ConnectSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class Service {
	ConnectSql Sql = new ConnectSql();
	private static final String SELECT_QUERY_SERVICE = "SELECT * FROM DichVu";
	private static final String SELECT_QUERY_ADDSERVICE ="INSERT INTO DichVu(TenDichVu,GiaTien) VALUES(?,?)";
	private static final String SELECT_QUERY_DELETESERVICE ="DELETE FROM DichVu WHERE MaDichVu=?";
	private static final String SELECT_QUERY_EDITSERVICE = "UPDATE DichVu SET TenDichVu=?,GiaTien=? where MaDichVu=?";

	private int MaDichVu;
	private String TenDichVu;
	private Float GiaTien;
	private TextField nhapSoLuong;
	public Service() {
		
	}
	public Service(int maDichVu, String tenDichVu, Float giaTien) {
		MaDichVu = maDichVu;
		TenDichVu = tenDichVu;
		GiaTien = giaTien;
	}
	
	public Service(int maDichVu, String tenDichVu, Float giaTien, String nhapSoLuong) {
		
		MaDichVu = maDichVu;
		TenDichVu = tenDichVu;
		GiaTien = giaTien;
		this.nhapSoLuong =  new TextField(nhapSoLuong);
	}
	
	public TextField getNhapSoLuong() {
		return nhapSoLuong;
	}
	public void setNhapSoLuong(TextField nhapSoLuong) {
		this.nhapSoLuong = nhapSoLuong;
	}
	public int getMaDichVu() {
		return MaDichVu;
	}
	public void setMaDichVu(int maDichVu) {
		MaDichVu = maDichVu;
	}
	public String getTenDichVu() {
		return TenDichVu;
	}
	public void setTenDichVu(String tenDichVu) {
		TenDichVu = tenDichVu;
	}
	public float getGiaTien() {
		return GiaTien;
	}
	public void setGiaTien(Float giaTien) {
		GiaTien = giaTien;
	}
	//Handle
	//Add Service
	public boolean ThemDichVu(String tenDichVu, Float giaTien) 
	{
		try {
	    		Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);	
	    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_ADDSERVICE);
	    		preparedStatement.setString(1, tenDichVu);
	    		preparedStatement.setFloat(2, giaTien);
	    		preparedStatement.execute();		
		} catch (SQLException e) {
				printSQLException(e);
		}
			return false; 	
		}
	//Delete Service
		public boolean XoaDichVu(int maDichVu)
		{
			try {
				Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_DELETESERVICE);
	    		preparedStatement.setInt(1,maDichVu);	
	    		preparedStatement.execute();	
			} catch (SQLException e) {
				printSQLException(e);
			}
			return false; 
		}
		//Edit Customer
		public boolean EditDichVu(String tenDichVu, Float giaTien, int maDichVu)
		{
			Connection connection;
			try {
				connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_EDITSERVICE);
	    		preparedStatement.setString(1, tenDichVu);
	    		preparedStatement.setFloat(2, giaTien);	
	    		preparedStatement.setInt(3, maDichVu);	
	    		preparedStatement.execute();					
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
			
		}
		
		public ObservableList<Service> getDataService(){
			try {
				Connection conn =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
				ResultSet rs = conn.createStatement().executeQuery(SELECT_QUERY_SERVICE);
				ObservableList<Service> servicesList = FXCollections.observableArrayList();
				while (rs.next())
				{
					servicesList.add(new Service(rs.getInt("MaDichVu"),rs.getString("TenDichVu"),rs.getFloat("GiaTien")));
				}
				return servicesList;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		public ObservableList<Service> getDataService1(){
			try {
				Connection conn =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
				ResultSet rs = conn.createStatement().executeQuery(SELECT_QUERY_SERVICE);
				ObservableList<Service> servicesList = FXCollections.observableArrayList();
				while (rs.next())
				{
					servicesList.add(new Service(rs.getInt("MaDichVu"),rs.getString("TenDichVu"),rs.getFloat("GiaTien"),""));
				}
				return servicesList;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		//Print Exception
		private void printSQLException(SQLException ex) {
		    for (Throwable e: ex) {
		    	if (e instanceof SQLException) {
		                e.printStackTrace(System.err);
		                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
		                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
		                System.err.println("Message: " + e.getMessage());
		                Throwable t = ex.getCause();
		                while (t != null) {
		                    System.out.println("Cause: " + t);
		                    t = t.getCause();
		                }
		            }
				}
				
			}

	
}
