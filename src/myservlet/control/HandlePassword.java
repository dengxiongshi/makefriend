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

import mybean.data.Login;
import mybean.data.Password;

/**
 * @author 小狮 
 * 用来连接数据库，并转发修改信息到showModifyMess.jsp页面
 */
public class HandlePassword extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Login login = (Login) session.getAttribute("login");
		boolean ok = true;
		if (login == null) {
			ok = false;
			response.sendRedirect("login.jsp");// 如果登录失败，返回登录页面
		}
		if (ok == true) {
			continueWork(request, response);
		}
	}

	public void continueWork(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Login login = (Login) session.getAttribute("login");
		Connection con = null;
		String logname = login.getLogname();
		Password passwordBean = new Password();
		request.setAttribute("password", passwordBean);
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		// 连接数据库
		String url = "jdbc:mysql://localhost:3306/makefriend?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
		try {
			con = DriverManager.getConnection(url, "root", "3333");
			Statement sql = con.createStatement();
			// 编写查询语句
			ResultSet rs = sql.executeQuery("SELECT * FROM member where logname='" + logname + "'And password='" + oldPassword + "'");
			if (rs.next()) {
				// 更新密码
				String updateString = "UPDATE member SET password='" + newPassword + "'where logname='" + logname + "'";
				int m = sql.executeUpdate(updateString);
				if (m == 1) {
					passwordBean.setBackNews("密码更新成功");
					passwordBean.setOldPassword(oldPassword);
					passwordBean.setNewPassword(newPassword);
				} else {
					passwordBean.setBackNews("密码更新失败");
				}
			} else {
				passwordBean.setBackNews("密码更新失败");
			}
		} catch (SQLException e) {
			passwordBean.setBackNews("密码更新失败" + e);
		}
		//密码更新成功，跳转到新页面
		RequestDispatcher dispatcher = request.getRequestDispatcher("showNewPassword.jsp");
		dispatcher.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
