package net.javaguides.usermanagement.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.javaguides.usermanagement.dao.UserDAO;
import net.javaguides.usermanagement.model.user;


@WebServlet("/")
public class userServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
    
    public userServlet() {
    	this.userDAO = new UserDAO() ;
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		switch (action) {
		case "/new":
			showNewform(request, response);
		   break;
		case "/insert" :
			try {
				insertUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
	
			}
		   break ;
		case "/delete":
			 try {
				deleteUser(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   break;
		case "/edit" :
			try {
				showEditForm(request, response);
			} catch (SQLException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   break;
		case "/update":
			try {
				updateUser(request, response);
			} catch (SQLException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   break;
		default:
			try {
				listUser(request, response);
			} catch (SQLException | IOException | ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		user existingUser = userDAO.selectUser(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		request.setAttribute("user", existingUser);
		dispatcher.forward(request, response);
	}
	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		userDAO.deleteUser(id);
		response.sendRedirect("list");
	}
    
	private void showNewform(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-fore.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertUser(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException{
		 String name = request.getParameter("name");
		 String email = request.getParameter("email");
		 String country = request.getParameter("country");
		 user newUser  = new user(name, email, country);
		 userDAO.insertUser(newUser);
		 response.sendRedirect("list");
		 
	}
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		user book = new user(id, name, email, country);
		userDAO.updateUser(book);
		response.sendRedirect("list");
	}
	
	private void listUser(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException{
		List<user> listUser = userDAO.selectAllUsers();
		request.setAttribute("listUser", listUser);
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
