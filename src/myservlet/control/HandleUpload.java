package myservlet.control;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mybean.data.Login;
import mybean.data.UploadFile;

/**
 * @author 小狮
 *
 */
public class HandleUpload extends HttpServlet {
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName("cpm.mysql.jdbc.Driver");
		} catch (Exception e) {
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		Login login = (Login) session.getAttribute("login");
		boolean ok = true;
		// 如果用户没有登录，upload控制器将把用户定向到登录页面，login.jsp
		if (login == null) {
			ok = false;
			response.sendRedirect("login.jsp");
		}
		if (ok == true) {
			String logname = login.getLogname();
			uploadFileMethod(request, response, logname);
		}
	}

	// 上传文件
	public void uploadFileMethod(HttpServletRequest request, HttpServletResponse response, String logname)
			throws ServletException, IOException {
		UploadFile upFile = new UploadFile();
		String backNews = "";
		upFile.setBackNews(backNews);
		upFile.setSavedFileName("暂时无名字");

		try {
			HttpSession session = request.getSession(true);
			session.setAttribute("upFile", upFile);
			String tempFileName = (String) session.getId();
			File f1 = new File(tempFileName);
			FileOutputStream o = new FileOutputStream(f1);
			InputStream in = request.getInputStream();

			byte b[] = new byte[10000];
			int n;
			while ((n = in.read(b)) != -1)
				o.write(b, 0, n);
			o.close();
			in.close();
			RandomAccessFile randomRead = new RandomAccessFile(f1, "r");
			// 上传 图片文件名字是用户的会员名
			String savedFileName = logname + ".jpg";
			randomRead.seek(0);
			long forthEndPosition = 0;
			int forth = 1;
			while ((n = randomRead.readByte()) != -1 && (forth <= 4)) {
				if (n == '\n') {
					forthEndPosition = randomRead.getFilePointer();
					forth++;
				}
			}

			String parentDir = f1.getAbsolutePath();
			parentDir = parentDir.substring(0, parentDir.lastIndexOf("bin") - 1);
			String saveDir = parentDir + "/webapps/makefriend/image";
			File dir = new File(saveDir);
			dir.mkdir();
			File savingFile = new File(dir, savedFileName);
			if (savingFile.exists())
				savingFile.delete();
			RandomAccessFile randomSave = new RandomAccessFile(savingFile, "rw");
			randomRead.seek(randomRead.length());

			long endPosition = randomRead.getFilePointer();
			long mark = endPosition;
			int j = 1;
			while ((mark >= 0) && (j <= 6)) {
				mark--;
				randomRead.seek(mark);
				n = randomRead.readByte();
				if (n == '\n') {
					endPosition = randomRead.getFilePointer();
					j++;
				}
			}
			randomRead.seek(forthEndPosition);
			long startPoint = randomRead.getFilePointer();
			while (startPoint < endPosition - 1) {
				n = randomRead.readByte();
				randomSave.write(n);
				startPoint = randomRead.getFilePointer();
			}
			randomSave.close();
			randomRead.close();

			String url = "jdbc:mysql://localhost:3306/makefriend?useUnicode=true&characterEncoding=utf8&autoReconnect=true&rewriteBatchedStatements=TRUE";// 数据库路径
			String name = "root";
			String mima = "3333";
			Connection con = DriverManager.getConnection(url, name, mima);
			Statement sql = con.createStatement();
			ResultSet rs = sql.executeQuery("SELECT *FROM member where logname='" + logname + "'");
			if (rs.next()) {
				int mm = sql.executeUpdate(
						"UPDATE member SET pic= '" + savedFileName + "'where logname = '" + logname + "'");
				if (mm != 0) {
					backNews = "成功上传";
					upFile.setSavedFileName(savedFileName);
					upFile.setBackNews(backNews + ":" + saveDir);
				}
			}
			// 关闭数据库
			con.close();
			f1.delete();
		} catch (Exception e) {
			backNews = "" + e;
			upFile.setBackNews(backNews);
		}
		// 跳转到showUploadMess.jsp页面查看上传操作的反馈信息
		RequestDispatcher dispatcher = request.getRequestDispatcher("showUploadMess.jsp");
		dispatcher.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}
