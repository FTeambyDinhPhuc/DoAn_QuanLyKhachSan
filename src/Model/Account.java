package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.ConnectSql;

public class Account {
	private int MaTaiKhoan;
	private String TenHienThiCuaNhanVien;
	private String TenDangNhap;
	private String MatKhau;
	ConnectSql Sql = new ConnectSql();
    private static final String SELECT_QUERY = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? and MatKhau = ?";
 
    public Account() {
		
	}
    
 
	public Account(int maTaiKhoan, String tenHienThiCuaNhanVien) {
		MaTaiKhoan = maTaiKhoan;
		TenHienThiCuaNhanVien = tenHienThiCuaNhanVien;
	}


	public Account(int maTaiKhoan, String tenHienThiCuaNhanVien, String tenDangNhap, String matKhau) {
		super();
		MaTaiKhoan = maTaiKhoan;
		TenHienThiCuaNhanVien = tenHienThiCuaNhanVien;
		TenDangNhap = tenDangNhap;
		MatKhau = matKhau;
	}
	
	

	public int getMaTaiKhoan() {
		return MaTaiKhoan;
	}


	public void setMaTaiKhoan(int maTaiKhoan) {
		MaTaiKhoan = maTaiKhoan;
	}


	public String getTenHienThiCuaNhanVien() {
		return TenHienThiCuaNhanVien;
	}


	public void setTenHienThiCuaNhanVien(String tenHienThiCuaNhanVien) {
		TenHienThiCuaNhanVien = tenHienThiCuaNhanVien;
	}


	public String getTenDangNhap() {
		return TenDangNhap;
	}


	public void setTenDangNhap(String tenDangNhap) {
		TenDangNhap = tenDangNhap;
	}


	public String getMatKhau() {
		return MatKhau;
	}


	public void setMatKhau(String matKhau) {
		MatKhau = matKhau;
	}


	public boolean validate(String tenDangNhap, String matKhau)
    {
    	try {
    		Connection connection =DriverManager.getConnection(Sql.DATABASE_URL,Sql.DATABASE_USERNAME,Sql.DATABASE_PASSWORD);
    	
    		PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
    		preparedStatement.setString(1, tenDangNhap);
    		preparedStatement.setString(2, matKhau);
			//System.out.println(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next())
			{
				return true;
			}   		
		} catch (SQLException e) {
			printSQLException(e);
		}
		return false; 
    			
}
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
