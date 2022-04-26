package Model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import application.ConnectSql;


public class Customers{

	ConnectSql Sql = new ConnectSql();
	private static final String SELECT_QUERY_ADD ="INSERT INTO KhachHang(TenKhachHang,CCCD,SoDienThoai) VALUES(?,?,?)";
	private static final String SELECT_QUERY_DELETE ="DELETE FROM KhachHang WHERE MaKhachHang=?";
	private static final String SELECT_QUERY_EDIT = "UPDATE KhachHang SET TenKhachHang=?, SoDienThoai=?, CCCD=? where MaKhachHang=?";
	private int MaKhachHang;
	private String TenKhachHang;
	private String SoDienThoai;
	private String CCCD;
	

	Customers customers;
	public Customers() {
		
	}

	public Customers(int maKhachHang, String tenKhachHang, String soDienThoai, String cCCD) {
		MaKhachHang = maKhachHang;
		TenKhachHang = tenKhachHang;
		SoDienThoai = soDienThoai;
		CCCD = cCCD;
	}

	public int getMaKhachHang() {
		return MaKhachHang;
	}

	public void setMaKhachHang(int maKhachHang) {
		MaKhachHang = maKhachHang;
	}

	public String getTenKhachHang() {
		return TenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		TenKhachHang = tenKhachHang;
	}

	public String getSoDienThoai() {
		return SoDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		SoDienThoai = soDienThoai;
	}

	public String getCCCD() {
		return CCCD;
	}

	public void setCCCD(String cCCD) {
		CCCD = cCCD;
	}
	
	
	//Handle
	//Add Customer
	public boolean ThemKhachHang(String tenKhachHang, String soDienThoai, String cCCD) 
	{
			try {
	    		Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);	
	    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_ADD);
	    		preparedStatement.setString(1, tenKhachHang);
	    		preparedStatement.setString(2, soDienThoai);
	    		preparedStatement.setString(3, cCCD);				
	    		preparedStatement.execute();		
			} catch (SQLException e) {
				printSQLException(e);
			}
			return false; 
			
	}
	//Delete Customer
	public boolean XoaKhachHang(int maKhachHang)
	{
		try {
			Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_DELETE);
    		preparedStatement.setInt(1,maKhachHang);	
    		preparedStatement.execute();	
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false; 
	}
	//Edit Customer
	public boolean Edit(String tenKhachHang, String soDienThoai, String cCCD, int maKhachHang)
	{
		Connection connection;
		try {
			connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_EDIT);
    		preparedStatement.setString(1, tenKhachHang);
    		preparedStatement.setString(2, soDienThoai);	
    		preparedStatement.setString(3, cCCD);	
    		preparedStatement.setInt(4, maKhachHang);	
    		preparedStatement.execute();		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
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
