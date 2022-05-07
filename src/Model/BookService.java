package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.ConnectSql;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BookService {
	ConnectSql Sql = new ConnectSql();
	private static final String QUERY_INSERT_BOOKSERVICE= "INSERT INTO PhieuSuDungDichVu(MaPhong,MaDichVu,SoLuong) VALUES(?,?,?)";
	private static final String QUERY_SELECT_SERVICE ="SELECT PhieuSuDungDichVu.MaDichVu, DichVu.TenDichVu, SoLuong FROM PhieuSuDungDichVu JOIN DichVu ON PhieuSuDungDichVu.MaDichVu=DichVu.MaDichVu WHERE PhieuSuDungDichVu.MaPhong=?";
	private static final String QUERY_SELECT_SLGT="SELECT SoLuong, DichVu.GiaTien FROM PhieuSuDungDichVu JOIN DichVu ON PhieuSuDungDichVu.MaDichVu=DichVu.MaDichVu WHERE PhieuSuDungDichVu.MaPhong=?";
	private static final String QUERY_SELECT_DELETEBOOKSERVICE ="DELETE FROM PhieuSuDungDichVu WHERE PhieuSuDungDichVu.MaPhong=?";
	private static final String QUERY_SET_DEFAULTBOOKSERVICE ="DBCC CHECKIDENT ('[PhieuSuDungDichVu]', RESEED, 0)";
	private int MaPhieuSuDungDichVu;
	private int MaPhong;
	private int MaDichVu;
	private int SoLuong;
	private String TenDichVu;
	private float GiaTien;
	public BookService() {
		
	}
	

	public BookService(int soLuong, float giaTien) {
		SoLuong = soLuong;
		GiaTien = giaTien;
	}

	
	public BookService(int maDichVu, int soLuong) {
		MaDichVu = maDichVu;
		SoLuong = soLuong;
	}


	public BookService(int maPhieuSuDungDichVu, int maPhong, int maDichVu, int soLuong) {
		MaPhieuSuDungDichVu = maPhieuSuDungDichVu;
		MaPhong = maPhong;
		MaDichVu = maDichVu;
		SoLuong = soLuong;
	}
	
	public BookService(int maDichVu, String tenDichVu, int soLuong) {
		MaDichVu = maDichVu;
		TenDichVu = tenDichVu;
		SoLuong = soLuong;
	}
	
	public float getGiaTien() {
		return GiaTien;
	}


	public void setGiaTien(float giaTien) {
		GiaTien = giaTien;
	}


	public String getTenDichVu() {
		return TenDichVu;
	}

	public void setTenDichVu(String tenDichVu) {
		TenDichVu = tenDichVu;
	}

	public int getMaPhieuSuDungDichVu() {
		return MaPhieuSuDungDichVu;
	}

	public void setMaPhieuSuDungDichVu(int maPhieuSuDungDichVu) {
		MaPhieuSuDungDichVu = maPhieuSuDungDichVu;
	}

	public int getMaPhong() {
		return MaPhong;
	}

	public void setMaPhong(int maPhong) {
		MaPhong = maPhong;
	}

	public int getMaDichVu() {
		return MaDichVu;
	}

	public void setMaDichVu(int maDichVu) {
		MaDichVu = maDichVu;
	}

	public int getSoLuong() {
		return SoLuong;
	}

	public void setSoLuong(int soLuong) {
		SoLuong = soLuong;
	}

	public boolean ThemPhieuSuDungDichVu(int maPhong, int maDichVu, int soLuong) 
	{
			try {
	    		Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);	
	    		PreparedStatement preparedStatement = connection.prepareStatement(QUERY_INSERT_BOOKSERVICE);
	    		preparedStatement.setInt(1,maPhong);
	    		preparedStatement.setInt(2,maDichVu);
	    		preparedStatement.setInt(3,soLuong);	
	    		preparedStatement.execute();		
			} catch (SQLException e) {
				System.out.println(e);
			}
			return false; 			
	}
	public ObservableList<BookService> GetDataService(int maPhong)
	{
		ObservableList<BookService> DSSList = FXCollections.observableArrayList();
		try {
			Connection connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_SERVICE);
			preparedStatement.setInt(1,maPhong);
			ResultSet rs = preparedStatement.executeQuery();			
			while (rs.next())
			{
				DSSList.add(new BookService(rs.getInt("MaDichVu"),rs.getString("TenDichVu"),rs.getInt("SoLuong")));
			}	
			return DSSList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	public ObservableList<BookService> LaySoLuongVaGiaTien(int maPhong)
	{
		ObservableList<BookService> SLGTList = FXCollections.observableArrayList();
		try {
			Connection connection = DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_SLGT);
			preparedStatement.setInt(1,maPhong);
			ResultSet rs = preparedStatement.executeQuery();			
			while (rs.next())
			{
				SLGTList.add(new BookService(rs.getInt("SoLuong"),rs.getFloat("GiaTien")));
			}	
			return SLGTList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	public boolean XoaPhieuSuDungDichVu(int maPhong)
	{
		try {
			Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SELECT_DELETEBOOKSERVICE);
    		preparedStatement.setInt(1,maPhong);	
    		preparedStatement.execute();	
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false; 
	}
	public boolean SetDefaultService()
	{
		try {
			Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
			PreparedStatement preparedStatement = connection.prepareStatement(QUERY_SET_DEFAULTBOOKSERVICE);	
    		preparedStatement.execute();	
		} catch (SQLException e) {
			System.out.println(e);
		}
		return false; 
	}
}
