package net.javaguides.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {

	private String url = "jdbc:mysql://localhost:3325/demo";
	private String user = "root";
	private String pwd = "";
    
	private static final String INSERT_USERS_SQL = " insert into users" +
	" (name, email, country) values " + " (?, ?, ?,);";
	
	private static final String SELECT_USER_BY_ID = "select id,namenemail,country from users where id =?";
	private static final String SELECT_ALL_USERS = "select*from users" ;
	private static final String DELETE_USERS_SQL = "delete from users where id = ? ;" ;
	private static final String UPDATE_USERS_SQL = "update users set name = ? , email=?, where id=?;" ;

	protected Connection  getConnection() {
		Connection connection = null ;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, pwd);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block 
			e.printStackTrace();
		}
		
		return connection;
	}
	
	// create or insert user 
	public void insertUser(User user) throws SQLException {
		try (Connection connection = getConnection();
		          PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
		      preparedStatement.setString(1, user.getName())  ;
		      preparedStatement.setString(2, user.getEmail());
		      preparedStatement.setString(3, user.getCountry());
		      preparedStatement.executeUpdate();
		      }catch (Exception e) {
		    	  e.printStackTrace();
		      }
		}
	
	



}
