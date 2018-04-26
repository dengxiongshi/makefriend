package myservlet.control;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.rowset.CachedRowSetImpl;

import mybean.data.*;

public class HandleDatabase extends HttpServlet {
	CachedRowSetImpl rowSet = null;

	public Connection con = null; // 创建连接
	public ResultSet rs = null; // 查询预编译的sql语句

	private static String url = "jdbc:mysql://localhost:3306/makefriend?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";
	private static String name = "root";
	private static String mima = "3333";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {}
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

	public void continueDoPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);

		StringBuffer presentPageResult = new StringBuffer();
		ShowByPage showBean = null;
		try {
			showBean = (ShowByPage) session.getAttribute("show");
			if (showBean == null) {
				showBean = new ShowByPage();// 创建Javabean对象
				session.setAttribute("show", showBean);
			}
		} catch (Exception e) {
			showBean = new ShowByPage();
			session.setAttribute("show", showBean);
		}
		showBean.setPageSize(3);// 每页显示3条记录

		int showPage = Integer.parseInt(request.getParameter("showPage"));
		if (showPage > showBean.getPageAllCount()) {
			showPage = 1;
		}
		if (showPage <= 0) {
			showPage = showBean.getPageAllCount();
		}
		showBean.setShowPage(showPage);
		int pageSize = showBean.getPageSize();

		try {
			// 连接数据库
			con = DriverManager.getConnection(url, name, mima);
			Statement sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = sql.executeQuery("SELECT *FROM member");
			rowSet = new CachedRowSetImpl();// 创建行集对象
			rowSet.populate(rs);
			con.close();// 关闭连接
			showBean.setRowSet(rowSet);// 数据存储在showBean中
			rowSet.last();

			int m = rowSet.getRow();// 总行数
			int n = pageSize;
			int pageAllCount = ((m % n) == 0) ? (m / n) : (m / n + 1);
			showBean.setPageAllCount(pageAllCount);// 数据存储在showBean中
			presentPageResult = show(showPage, pageSize, rowSet);
			showBean.setPresentPageResult(presentPageResult);
		} catch (SQLException e) {
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("showAllMember.jsp");// 跳转全部用户页面
		dispatcher.forward(request, response);
	}

	public StringBuffer show(int showPage, int pageSize, CachedRowSetImpl rowSet) {
		StringBuffer str = new StringBuffer();
		try {
			rowSet.absolute((showPage - 1) * (pageSize + 1));
			for (int i = 1; i <= pageSize; i++) {
				str.append("<tr>");
				str.append("<td>" + rowSet.getString(1) + "</td>");
				str.append("<td>" + rowSet.getString(3) + "</td>");
				str.append("<td>" + rowSet.getString(4) + "</td>");
				str.append("<td>" + rowSet.getString(5) + "</td>");
				String s = "<img src=makeFrient/image/" + rowSet.getString(6) + "width=100 height=100/>";
				str.append("<td>" + s + "</td>");
				str.append("</tr>");
				rowSet.next();
			}
		} catch (SQLException e) {
		}
		return str;
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Login login = (Login) session.getAttribute("login");
		boolean ok = true;
		if (login == null) {
			ok = false;
			response.sendRedirect("login.jsp");
		}
		if (ok == true) {
			continueDoGet(request, response);
		}
	}

	public void continueDoGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberInform inform = new MemberInform();
		request.setAttribute("inform", inform);
		String logname = request.getParameter("logname");
		try {
			con = DriverManager.getConnection(url, name, mima);
			Statement sql = con.createStatement();
			rs = sql.executeQuery("SELECT * FROM member where logname = '" + logname + "'");
			if (rs.next()) {
				inform.setLogname(rs.getString(1));
				inform.setPhone(rs.getString(3));
				inform.setEmail(rs.getString(4));
				inform.setMessage(rs.getString(5));
				inform.setPic(rs.getString(6));
				inform.setBackNews("查询到的会员信息: ");
			}
			con.close();
			RequestDispatcher dispatcher = request.getRequestDispatcher("showLookedMember.jsp");
			dispatcher.forward(request, response);
		} catch (SQLException e) {
			inform.setBackNews("" + e);
		}
	}
}
