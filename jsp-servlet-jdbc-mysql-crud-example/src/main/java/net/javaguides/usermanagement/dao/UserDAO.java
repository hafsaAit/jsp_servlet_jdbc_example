package net.javaguides.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.usermanagement.model.user; 
public class UserDAO {

	private String url = "jdbc:mysql://localhost:3325/demo";
	private String Username = "root";
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
			connection = DriverManager.getConnection(url, Username, pwd);
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
	public void insertUser(user user) throws SQLException {
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
	
	// update user
	
	public boolean updateUser(user user) throws SQLException {
		boolean rowUpdated ;
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);){
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			statement.setInt(2,  user.getId());
			
			rowUpdated = statement.executeUpdate() > 0 ;
		}
		return rowUpdated;
	}

// select user by id 
	public user selectUser(int id) {
		user user = null;
		// step1 establishing a connection 
		try(Connection connection= getConnection();
				// step2 Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);){
			preparedStatement.setInt(1,  id);
			System.out.println(preparedStatement);
			//step3 Execute the query or update query 
			ResultSet rs = preparedStatement.executeQuery();
			
			// step4 process the resultSetpbject 
			 while(rs.next()) {
				 String name = rs.getString("name");
				 String email = rs.getString("email");
				 String country = rs.getString("country");
				 user = new user(id, name, email, country);
			 }
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return user ;
	}
	
	// select users 
	public List<user> selectAllUsers() {
		List<user> users = new ArrayList<>();
		// step1 establishing a connection 
		try(Connection connection= getConnection();
				// step2 Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);){
			
			System.out.println(preparedStatement);
			//step3 Execute the query or update query 
			ResultSet rs = preparedStatement.executeQuery();
			
			// step4 process the resultSetpbject 
			 while(rs.next()) {
				 int id = rs.getInt("id");
				 String name = rs.getString("name");
				 String email = rs.getString("email");
				 String country = rs.getString("country");
				 users.add(new user(id, name, email, country));
			 }
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return users ;
	}
	
	//delete user 
	
	public boolean deleteUser(int id) throws SQLException{
		boolean rowDeleted;
		try(Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);){
			statement.setInt(1, id);
			rowDeleted= statement.executeUpdate()> 0 ;
		}
		return rowDeleted;
	}
	
	
	
	
	
	
	
	
	
}
