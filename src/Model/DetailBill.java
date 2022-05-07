package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.ConnectSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DetailBill {
	ConnectSql Sql = new ConnectSql();
	private static final String QUERY_INSERT_DETAILBILL ="INSERT INTO ChiTietHoaDon(MaHoaDon,MaDichVu,SoLuong) VALUES (?,?,?)";
	private static final String QUERY_SELECT_DETAILBILL ="SELECT MaHoaDon,DichVu.TenDichVu,DichVu.GiaTien,SoLuong FROM ChiTietHoaDon JOIN DichVu ON ChiTietHoaDon.MaDichVu=DichVu.MaDichVu WHERE MaHoaDon=?";
	private int MaChiTietHoaDon;
	private int MaHoaDon;
	private String TenDichVu;
	private int MaDichVu;
	private int MaPhong;
	private int SoLuong;
	private float GiaTien;
	ObservableList<DetailBill> detailBill = FXCollections.observableArrayList();
	
	public DetailBill(int maHoaDon, String tenDichVu, float giaTien, int soLuong) {

		MaHoaDon = maHoaDon;
		TenDichVu = tenDichVu;
		GiaTien = giaTien;
		SoLuong = soLuong;
	}

	public DetailBill() {
	
	}
	
	public DetailBill(int maHoaDon, int maDichVu, int soLuong) {
		MaHoaDon = maHoaDon;
		MaDichVu = maDichVu;
		SoLuong = soLuong;
	}

	public DetailBill(int maChiTietHoaDon, int maHoaDon, int maDichVu, int maPhong, int soLuong, int giaTien) {
		MaChiTietHoaDon = maChiTietHoaDon;
		MaHoaDon = maHoaDon;
		MaDichVu = maDichVu;
		MaPhong = maPhong;
		SoLuong = soLuong;
		GiaTien = giaTien;
	}
	
	public String getTenDichVu() {
		return TenDichVu;
	}

	public void setTenDichVu(String tenDichVu) {
		TenDichVu = tenDichVu;
	}

	public int getMaChiTietHoaDon() {
		return MaChiTietHoaDon;
	}
	public void setMaChiTietHoaDon(int maChiTietHoaDon) {
		MaChiTietHoaDon = maChiTietHoaDon;
	}
	public int getMaHoaDon() {
		return MaHoaDon;
	}
	public void setMaHoaDon(int maHoaDon) {
		MaHoaDon = maHoaDon;
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
	public int getSoLuong() {
		return SoLuong;
	}
	public void setSoLuong(int soLuong) {
		SoLuong = soLuong;
	}
	public float getGiaTien() {
		return GiaTien;
	}
	public void setGiaTien(float giaTien) {
		GiaTien = giaTien;
	}
	public boolean ThemChiTietHoaDon(int maHoaDon, int maDichVu, int soLuong) 
	{
			try {
	    		Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);	
	    		PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT_DETAILBILL);
	    		preparedStatement.setInt(1,maHoaDon);
	    		preparedStatement.setInt(2,maDichVu);
	    		preparedStatement.setInt(3,soLuong);	
	    		preparedStatement.execute();		
			} catch (SQLException e) {
				System.out.println(e);
			}
			return false; 			
	}
	public ObservableList<DetailBill> GetDataDetailBill(int maPhong)
	{
		ObservableList<DetailBill> detailBillList = FXCollections.observableArrayList();
		try {
			Connection connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_DETAILBILL);
			preparedStatement.setInt(1,maPhong);
			ResultSet rs = preparedStatement.executeQuery();			
			while (rs.next())
			{
				detailBillList.add(new DetailBill(rs.getInt("MaHoaDon"),rs.getString("TenDichVu"),rs.getFloat("GiaTien"),rs.getInt("SoLuong")));
			}	
			return detailBillList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	
	

}
