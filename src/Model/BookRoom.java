package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.ConnectSql;

public class BookRoom {
	ConnectSql Sql = new ConnectSql();
	private static final String SELECT_QUERY_INSERTBOOKROOM ="INSERT INTO PhieuDatPhong(ThoiGianNhanPhong,ThoiGianTraPhong,MaKhachHang,MaPhong) VALUES(?,?,?,?)";
	private static final String SELECT_QUERY_TG = "SELECT ThoiGianNhanPhong, ThoiGianTraPhong FROM PhieuDatPhong WHERE MaPhong=?";
	private static final String SELECT_QUERY_DELETEBOOKROOM = "DELETE FROM PhieuDatPhong WHERE MaPhong=?";
	private static final String QUERY_SET_DEFAULTBOOKROOM ="DBCC CHECKIDENT ('[PhieuDatPhong]', RESEED, 0)";
	Date ThoiGianNhanPhong;
	private Date ThoiGianTraPhong;
	private int MaKhachHang;
	private int MaDichVu;
	private int MaPhong;
	public BookRoom() {
		
	}
	
	public BookRoom(Date thoiGianNhanPhong, Date thoiGianTraPhong) {
		ThoiGianNhanPhong = thoiGianNhanPhong;
		ThoiGianTraPhong = thoiGianTraPhong;
	}
	

	public BookRoom(Date thoiGianNhanPhong, Date thoiGianTraPhong, int maKhachHang, int maDichVu, int maPhong) {
		ThoiGianNhanPhong = thoiGianNhanPhong;
		ThoiGianTraPhong = thoiGianTraPhong;
		MaKhachHang = maKhachHang;
		MaDichVu = maDichVu;
		MaPhong = maPhong;
	}
	public Date getThoiGianNhanPhong() {
		return ThoiGianNhanPhong;
	}
	public void setThoiGianNhanPhong(Date thoiGianNhanPhong) {
		ThoiGianNhanPhong = thoiGianNhanPhong;
	}
	public Date getThoiGianTraPhong() {
		return ThoiGianTraPhong;
	}
	public void setThoiGianTraPhong(Date thoiGianTraPhong) {
		ThoiGianTraPhong = thoiGianTraPhong;
	}
	public int getMaKhachHang() {
		return MaKhachHang;
	}
	public void setMaKhachHang(int maKhachHang) {
		MaKhachHang = maKhachHang;
	}
	public int getMaDichVu() {
		return MaDichVu;
	}
	public void setMaDichVu(int maDichVu) {
		MaDichVu = maDichVu;
	}
	public int getMaPhong() {
		return MaPhong;
	}
	public void setMaPhong(int maPhong) {
		MaPhong = maPhong;
	}
	

	public boolean ThemPhieuDatPhong(Date thoiGianDatPhong, Date thoiGianTraPhong, int maKhachHang, int maPhong) 
	{
			try {
	    		Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);	
	    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_INSERTBOOKROOM);
	    		preparedStatement.setDate(1,thoiGianDatPhong);
	    		preparedStatement.setDate(2,thoiGianTraPhong);
	    		preparedStatement.setInt(3,maKhachHang);	
	    		preparedStatement.setInt(4,maPhong);
	    		preparedStatement.execute();		
			} catch (SQLException e) {
				printSQLException(e);
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
	public List<BookRoom> GetTime(int maPhong)
	{
		List<BookRoom> TGList = new ArrayList<>();
		try {
			Connection connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_TG);
			preparedStatement.setInt(1,maPhong);
			ResultSet rs = preparedStatement.executeQuery();			
			while (rs.next())
			{
				TGList.add(new BookRoom(rs.getDate("ThoiGianNhanPhong"),rs.getDate("ThoiGianTraPhong")));
			}	
			return TGList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	//Delete BookRoom
	public boolean XoaDatPhong(int maPhong)
	{
		try {
			Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_DELETEBOOKROOM);
    		preparedStatement.setInt(1,maPhong);	
    		preparedStatement.execute();	
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false; 
	}
	public boolean SetDefaultBookRoom()
	{
		try {
			Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SET_DEFAULTBOOKROOM);
    		preparedStatement.execute();	
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false; 
	}
}
