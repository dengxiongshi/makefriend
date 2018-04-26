package myservlet.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.data.*;

public class HandleLogin extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {

		}
	}
	
	public String handleString(String s){
		try {
			byte bb[]=s.getBytes("iso-8859-1");//设置编码集
			s=new String(bb);
		} catch (Exception e) {}
		return s;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		Statement sql;
		Login login = null;
		String backNews = "";
		HttpSession session = request.getSession(true);
		try {
			login = (Login) session.getAttribute("login");
			if (login == null) {
				login = new Login();
				session.setAttribute("login", login);
			}
		} catch (Exception e) {
			login = new Login();
			session.setAttribute("login", login);
		}
		String logname = request.getParameter("logname").trim(), password = request.getParameter("password").trim();
		boolean ok = login.getSuccess();
		logname = handleString(logname);
		password = handleString(password);
		if (ok == true && logname.equalsIgnoreCase(login.getLogname())) {
			backNews = logname + "已经登录了";
			login.setBackNews(backNews);
		} else {
			//数据库路径
			String url = "jdbc:mysql://localhost:3306/makefriend";
			String name = "root";
			String mima = "3333";
			boolean boo = (logname.length() > 0) && (password.length() > 0);
			try {
				con = DriverManager.getConnection(url, name, mima);// 连接数据库
				String condition = "select * from member where logname = '" + logname + "'and password ='" + password
						+ "'";
				sql = con.createStatement();
				if (boo) {
					ResultSet rs = sql.executeQuery(condition);
					boolean m = rs.next();
					if (m == true) {
						backNews = "登录成功";
						login.setBackNews(backNews);
						login.setSuccess(true);
						login.setLogname(logname);
					} else {
						backNews = "您输入的用户名不存在，或密码不匹配";
						login.setBackNews(backNews);
						login.setSuccess(false);
						login.setLogname(logname);
						login.setPassword(password);
					}
				} else {
					backNews = "您输入的用户名不存在，或密码不匹配";
					login.setBackNews(backNews);
					login.setSuccess(false);
					login.setLogname(logname);
					login.setPassword(password);
				}
				con.close();
			} catch (SQLException exp) {
				backNews = "" + exp;
				login.setBackNews(backNews);
				login.setSuccess(false);
			}
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("showLoginMess.jsp");// 转发
		dispatcher.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}
}
