package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.ConnectSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Room {
	ConnectSql Sql = new ConnectSql();
	private static final String SELECT_QUERY_ROOM = "SELECT MaPhong,SoPhong,SoGiuong,TenLoaiPhong,GiaTienPhong,TrangThaiPhong FROM Phong JOIN LoaiPhong ON LoaiPhong.MaLoaiPhong=Phong.MaLoaiPhong 																									";
	private static final String SELECT_QUERY_ADDROOM ="INSERT INTO Phong(SoPhong,SoGiuong,MaLoaiPhong,GiaTienPhong) VALUES(?,?,?,?);";
	private static final String SELECT_QUERY_DELETEROOM ="DELETE FROM Phong WHERE MaPhong=?";
	private static final String SELECT_QUERY_EDITROOM = "UPDATE Phong SET SoPhong=?,SoGiuong=?,MaLoaiPhong=?,GiaTienPhong=? WHERE MaPhong=?";
	private static final String SELECT_QUERY_GETMLPHONG = "SELECT MaLoaiPhong FROM LoaiPhong WHERE TenLoaiPhong=?";
	private static final String SELECT_QUERY_GETMAPHONG = "SELECT MaPhong FROM Phong WHERE SoPhong=?";
	private static final String SELECT_QUERY_GETMONEY ="SELECT GiaTienPhong FROM Phong WHERE MaPhong=?";
	private static final String QUERY_UPDATE_STATUS ="UPDATE Phong SET TrangThaiPhong=? WHERE MaPhong=?";
	private static final String QUERY_SELECT_STATUS ="SELECT TrangThaiPhong FROM Phong WHERE MaPhong=?";
	private String name;
	private double price;
	private String color;
	private String status;
	
	private int MaPhong;
	private String SoPhong;
	private int SoGiuong;
	private float GiaTienPhong;
	private String TenLoaiPhong;
	private int LoaiPhong;
	private int MaTrangThaiPhong;
	private String TenTrangThaiPhong;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Room()
	{
		
	}
	
	public Room(int maTrangThaiPhong, int maPhong) {
		MaTrangThaiPhong = maTrangThaiPhong;
		MaPhong= maPhong;
	}
	public Room(int maPhong, String soPhong, int soGiuong, String tenLoaiPhong, float giaTienPhong) {

		MaPhong = maPhong;
		SoPhong = soPhong;
		SoGiuong = soGiuong;
		TenLoaiPhong = tenLoaiPhong;
		GiaTienPhong = giaTienPhong;
	}
	
	public int getMaTrangThaiPhong() {
		return MaTrangThaiPhong;
	}
	public void setMaTrangThaiPhong(int maTrangThaiPhong) {
		MaTrangThaiPhong = maTrangThaiPhong;
	}
	public String getTenTrangThaiPhong() {
		return TenTrangThaiPhong;
	}
	public void setTenTrangThaiPhong(String tenTrangThaiPhong) {
		TenTrangThaiPhong = tenTrangThaiPhong;
	}
	public int getMaPhong() {
		return MaPhong;
	}
	public void setMaPhong(int maPhong) {
		MaPhong = maPhong;
	}
	public String getSoPhong() {
		return SoPhong;
	}
	public void setSoPhong(String soPhong) {
		SoPhong = soPhong;
	}
	public int getSoGiuong() {
		return SoGiuong;
	}
	public void setSoGiuong(int soGiuong) {
		SoGiuong = soGiuong;
	}
	public float getGiaTienPhong() {
		return GiaTienPhong;
	}
	public void setGiaTienPhong(float giaTienPhong) {
		GiaTienPhong = giaTienPhong;
	}
	public String getTenLoaiPhong() {
		return TenLoaiPhong;
	}
	public void setTenLoaiPhong(String tenLoaiPhong) {
		TenLoaiPhong = tenLoaiPhong;
	}
	public int getLoaiPhong() {
		return LoaiPhong;
	}
	public void setLoaiPhong(int loaiPhong) {
		LoaiPhong = loaiPhong;
	}
	//Handle
	//Add Customer
	public boolean ThemPhong(String soPhong, int soGiuong, int maLoaiPhong, Float giaTienPhong) 
	{
			try {
	    		Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);	
	    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_ADDROOM);
	    		preparedStatement.setString(1,soPhong);
	    		preparedStatement.setInt(2,soGiuong);
	    		preparedStatement.setInt(3,maLoaiPhong);	
	    		preparedStatement.setFloat(4,giaTienPhong);
	    		preparedStatement.execute();		
			} catch (SQLException e) {
				printSQLException(e);
			}
			return false; 			
	}
	//Delete Customer
	public boolean XoaPhong(int maPhong)
	{
		try {
			Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_DELETEROOM);
    		preparedStatement.setInt(1,maPhong);	
    		preparedStatement.execute();	
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false; 
	}
	//Edit Room
	public boolean Edit(String soPhong, int soGiuong, int maLoaiPhong, Float giaTienPhong,int maPhong)
	{
		Connection connection;
		try {
			connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_EDITROOM);
    		preparedStatement.setString(1, soPhong);
    		preparedStatement.setInt(2, soGiuong);	
    		preparedStatement.setInt(3, maLoaiPhong);	
    		preparedStatement.setFloat(4, giaTienPhong);
    		preparedStatement.setInt(5, maPhong);
    		preparedStatement.execute();					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;		
	}
	
	public ObservableList<Room> getDataRoom(){
		try {
			Connection conn =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			ResultSet rs = conn.createStatement().executeQuery(SELECT_QUERY_ROOM);
			ObservableList<Room> roomList = FXCollections.observableArrayList();
			//int i = 0;
			while (rs.next())
			{
//				System.out.println(rs.getString("TenLoaiPhong"));
				roomList.add(new Room(rs.getInt("MaPhong"),rs.getString("SoPhong"),rs.getInt("SoGiuong"),rs.getString("TenLoaiPhong"),rs.getFloat("GiaTienPhong")));
//				System.out.println(roomList.get(i).getTenLoaiPhong());
				//i++;
			}
			return roomList;
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
	//Lấy mã loại phòng từ tên loại phòng
	public int LayMaLoaiPhong(String tenLoaiPhong)
	{
		try {
			Connection connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_GETMLPHONG);
			preparedStatement.setString(1, tenLoaiPhong);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
			{
				int mlp=rs.getInt("MaLoaiPhong");
				return mlp;	
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	//Lấy mã loại phòng từ tên loại phòng
	public int LayMaPhong(int soPhong1)
	{
		try {
			Connection connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_GETMAPHONG);
			preparedStatement.setInt(1, soPhong1);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
			{
				int mp=rs.getInt("MaPhong");
				return mp;	
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	//Lấy mã loại phòng từ tên loại phòng
	public int LayGiaTienPhong(int maPhong)
	{
		try {
			Connection connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_GETMONEY);
			preparedStatement.setInt(1, maPhong);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
			{
				int giaTienphong=rs.getInt("GiaTienPhong");
				return giaTienphong;	
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public boolean UpdateStatus(int trangThaiPhong,int maPhong)
	{
		Connection connection;
		try {
			connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_UPDATE_STATUS);
			preparedStatement.setInt(1, trangThaiPhong);
    		preparedStatement.setInt(2, maPhong);
    		preparedStatement.execute();					
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;		
	}
	public int LayTrangThaiPhong(int maPhong)
	{
		try {
			Connection connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_STATUS);
			preparedStatement.setInt(1, maPhong);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next())
			{
				int ttp=rs.getInt("TrangThaiPhong");
				return ttp;	
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
