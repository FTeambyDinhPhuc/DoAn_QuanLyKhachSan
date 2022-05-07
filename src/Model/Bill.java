package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.chrono.ChronoLocalDate;

import application.ConnectSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Bill {
	ConnectSql Sql = new ConnectSql();
	private static final String QUERY_INSERT_BILL="INSERT INTO HoaDon(MaPhong,MaKhachHang,ThoiGianNhanPhong,ThoiGianTraPhong,TongTien) VALUES (?,?,?,?,?)";
	private static final String QUERY_SELET_LAYMABILL ="SELECT HoaDon.MaHoaDon FROM HoaDon WHERE MaPhong=? AND MaKhachHang =? AND ThoiGianNhanPhong =? AND ThoiGianTraPhong =? AND TongTien= ?";
	private static final String QUERY_SELECT_BILL ="SELECT MaHoaDon, Phong.SoPhong,KhachHang.TenKhachHang,ThoiGianNhanPhong,ThoiGianTraPhong,TongTien  FROM HoaDon JOIN Phong ON HoaDon.MaPhong=Phong.MaPhong JOIN KhachHang ON HoaDon.MaKhachHang=KhachHang.MaKhachHang";
	private int MaHoaDon;
	private int MaPhong;
	private int MaKhachHang;
	private Date ThoiGianNhanPhong;
	private Date ThoiGianTraPhong;
	private float TongTien;
	private String TenKhachHang;
	private String SoPhong;
	public Bill() {
		
	}
	
	public Bill(int maHoaDon, String soPhong, String tenKhachHang, Date thoiGianNhanPhong, Date thoiGianTraPhong,
			float tongTien) {
		MaHoaDon = maHoaDon;
		SoPhong = soPhong;
		TenKhachHang = tenKhachHang;
		ThoiGianNhanPhong = thoiGianNhanPhong;
		ThoiGianTraPhong = thoiGianTraPhong;
		TongTien = tongTien;
	}

	public Bill(int maHoaDon) {

		MaHoaDon = maHoaDon;
	}

	public Bill(int maHoaDon, int maPhong, int maKhachHang, Date thoiGianNhanPhong, Date thoiGianTraPhong,
			float tongTien) {
		MaHoaDon = maHoaDon;
		MaPhong = maPhong;
		MaKhachHang = maKhachHang;
		ThoiGianNhanPhong = thoiGianNhanPhong;
		ThoiGianTraPhong = thoiGianTraPhong;
		TongTien = tongTien;
	}
	
	public String getTenKhachHang() {
		return TenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		TenKhachHang = tenKhachHang;
	}

	public String getSoPhong() {
		return SoPhong;
	}

	public void setSoPhong(String soPhong) {
		SoPhong = soPhong;
	}

	public int getMaHoaDon() {
		return MaHoaDon;
	}
	
	public void setMaHoaDon(int maHoaDon) {
		MaHoaDon = maHoaDon;
	}

	public int getMaPhong() {
		return MaPhong;
	}

	public void setMaPhong(int maPhong) {
		MaPhong = maPhong;
	}

	public int getMaKhachHang() {
		return MaKhachHang;
	}

	public void setMaKhachHang(int maKhachHang) {
		MaKhachHang = maKhachHang;
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

	public float getTongTien() {
		return TongTien;
	}

	public void setTongTien(float tongTien) {
		TongTien = tongTien;
	}
	public boolean ThemHoaDon(int maPhong, int maKhachHang, Date thoiGianNhanPhong, Date thoiGianTraPhong, Float tongTien) 
	{
			try {
	    		Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);	
	    		PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT_BILL);
	    		preparedStatement.setInt(1,maPhong);
	    		preparedStatement.setInt(2,maKhachHang);
	    		preparedStatement.setDate(3,thoiGianNhanPhong);	
	    		preparedStatement.setDate(4,thoiGianTraPhong);
	    		preparedStatement.setFloat(5,tongTien);
	    		preparedStatement.execute();	
	    		return true;
			} catch (SQLException e) {
				System.out.println(e);
			}
			return false; 			
	}
	public int LayMaHoaDon(int maPhong, int maKhachHang, Date thoiGianNhanPhong, Date thoiGianTraPhong, Float tongTien )
	{
		try {
			Connection connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELET_LAYMABILL);
    		preparedStatement.setInt(1,maPhong);
    		preparedStatement.setInt(2,maKhachHang);
    		preparedStatement.setDate(3,thoiGianNhanPhong);	
    		preparedStatement.setDate(4,thoiGianTraPhong);
    		preparedStatement.setFloat(5,tongTien);
			ResultSet rs = preparedStatement.executeQuery();			
			while (rs.next())
			{
				int mhd=rs.getInt("MaHoaDon");
				return mhd;
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;	
	}
	public ObservableList<Bill> getDataBill(){
		try {
			Connection conn =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			ResultSet rs = conn.createStatement().executeQuery(QUERY_SELECT_BILL);
			ObservableList<Bill> billList = FXCollections.observableArrayList();
			while (rs.next())
			{
				billList.add(new Bill(rs.getInt("MaHoaDon"),rs.getString("SoPhong"),rs.getString("TenKhachHang"),rs.getDate("ThoiGianNhanPhong"),rs.getDate("ThoiGianTraPhong"),rs.getFloat("TongTien")));
			}
			return billList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	

}
