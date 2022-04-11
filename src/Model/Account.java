package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.ConnectSql;

public class Account {
	
	ConnectSql Sql = new ConnectSql();
    private static final String SELECT_QUERY = "SELECT * FROM TaiKhoan WHERE TenDangNhap = ? and MatKhau = ?";
	
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

