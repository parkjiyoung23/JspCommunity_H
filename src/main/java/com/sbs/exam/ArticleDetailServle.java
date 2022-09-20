package com.sbs.exam;

import com.sbs.exam.util.DBUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


@WebServlet("/article/detail")
public class ArticleDetailServle extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String url = "jdbc:mysql://127.0.0.1:3306/Jsp_Community?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    String user = "root";
    String password = "";

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      resp.getWriter().append("DB 드라이버 클래스 로딩 실패");
      return;
    }

    // DB 연결
    Connection con = null;

    try {
      con = DriverManager.getConnection(url, user, password);
      DBUtil dbUtil = new DBUtil(req, resp);

      String sql = "SELECT * FROM article WHERE id=%d";
      List<Map<String, Object>> articleRows = dbUtil.selectRows(con, sql);

      req.setAttribute("articleRows", articleRows);
      req.getRequestDispatcher("../article/detail.jsp").forward(req,resp);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }
}


