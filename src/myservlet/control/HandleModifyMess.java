package myservlet.control;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.data.Login;
import mybean.data.ModifyMessage;

/**
 * @author 小狮
 * @describe:1.负责连接数据库，将用户提交的新信息写入到member表 2.将用户转到showModifyMess.jsp页面查看修改反馈信息
 */
public class HandleModifyMess extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
		}
	}

	// 设定字符编码集
	public String handleString(String s) {
		try {
			byte bb[] = s.getBytes("iso-8859-1");
			s = new String(bb);
		} catch (Exception e) {
		}
		return s;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Login login = (Login) session.getAttribute("login");
		boolean ok = true;
		if (login == null) {
			ok = false;
			response.sendRedirect("login.jsp");
		}
		if (ok == true) {
			continueDoPost(request, response);
		}
	}

	public void continueDoPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Login login = (Login) session.getAttribute("login");
		String logname = login.getLogname();
		Connection con;
		Statement sql;
		ModifyMessage modify = new ModifyMessage();
		request.setAttribute("modify", modify);
		String email = request.getParameter("newEmail").trim(), 
				phone = request.getParameter("newPhone").trim(),
				message = request.getParameter("newMessage");
		email = handleString(email);
		message = handleString(message);
		String backNews = "";
		try {
			// 连接数据库
			String url = "jdbc:mysql://localhost:3306/makefriend?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
			String name = "root";
			String mima = "3333";
			con = DriverManager.getConnection(url, name, mima);

			// 修改信息
			String updateCondition = "UPDATE member SET phone='" + phone + "',email='" + email + "',message='" + message
					+ "'WHERE logname='" + logname+"'";
			sql = con.createStatement();
			int m = sql.executeUpdate(updateCondition);
			if (m == 1) {
				backNews = "修改信息成功";
				modify.setBackNews(backNews);
				modify.setLogname(logname);
				modify.setNewEmail(email);
				modify.setNewPhone(phone);
				modify.setNewMessage(message);
			} else {
				backNews = "信息填写不完整或信息中能有非法字符";
				modify.setBackNews(backNews);
			}
			con.close();//关闭数据库
		} catch (SQLException e) {
			modify.setBackNews("" + e);
		}
		
		//跳转到反馈页面
		RequestDispatcher dispatcher = request.getRequestDispatcher("showModifyMess.jsp");
		dispatcher.forward(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
