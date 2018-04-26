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

import mybean.data.Register;

//给前端url访问路径
public class HandleRegister extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {}
	}

	public String handleString(String s) {
		try {
			byte bb[] = s.getBytes("iso-8859-1");// 设置编码集
			s = new String(bb);
		} catch (Exception e) {}
		return s;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con;
		Statement sql;
		Register reg = new Register();
		request.setAttribute("register", reg);
		String logname = request.getParameter("logname").trim(), password = request.getParameter("password").trim(),
				email = request.getParameter("email").trim(), phone = request.getParameter("phone").trim(),
				message = request.getParameter("message");
		if (logname == null){
			logname = "";
		}
		if (password == null){
			password = "";
		}
		boolean isLD = true;
		for (int i = 0; i < logname.length(); i++) {
			char c = logname.charAt(i);
			if (!((c <= 'z' && c >= 'a') || (c <= 'Z' && c >= 'A') || (c <= '9' && c >= '0'))) {
				isLD = false;
			}
			boolean boo = logname.length() > 0 && password.length() > 0 && isLD;
			String backNews = "";
			try {
				logname = handleString(logname);
				password = handleString(password);
				phone = handleString(phone);
				email = handleString(email);
				message = handleString(message);
				// 公共图片
				String pic = "2.jpg";//用户注册默认的头像

				String insertRecord = "('" + logname + "','" + password + "','" + phone + "','" + email + "','"
						+ message + "','" + pic + "')";
				String url = "jdbc:mysql://localhost:3306/makefriend?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
				String name = "root";
				String mima = "3333";
				con = DriverManager.getConnection(url, name, mima);
				String insertCondition = "INSERT INTO member VALUES" + insertRecord;
				sql = con.createStatement();
				if (boo) {
					int m = sql.executeUpdate(insertCondition);
					if (m != 0) {
						backNews = "注册成功";
						reg.setBackNews(backNews);
						reg.setLogname(logname);
						reg.setPassword(password);
						reg.setPhone(phone);
						reg.setEmail(email);
						reg.setMessage(message);
					}
				} else {
					backNews = "信息填写不完整或名字中有非法字符";
					reg.setBackNews(backNews);
				}
				con.close();
			} catch (SQLException exp) {
				backNews = "该会员名已被使用，请您更换名字" + exp;
				reg.setBackNews(backNews);
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("showRegisterMess.jsp");// 转发
			dispatcher.forward(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
