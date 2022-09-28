package com.sbs.exam.servlet;

import com.sbs.exam.Config;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
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

@WebServlet("/article/list")
public class ArticleListServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String driverName = Config.getDriverClassName();

    try {
      Class.forName(driverName);
    } catch (ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      resp.getWriter().append("DB 드라이버 클래스 로딩 실패");
      return;
    }

    // DB 연결
    Connection con = null;

    try {
      con = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());


      int page = 1;

      if(req.getParameter("page") != null && req.getParameter("page").length() != 0) {
        page = Integer.parseInt(req.getParameter("page"));
      }

      int itemInAPage = 10;
      int limitFrom = (page - 1) * itemInAPage;

      SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
      sql.append("FROM article");

      int totalCount = DBUtil.selectRowIntValue(con, sql);
      int totalPage = (int) Math.ceil((double)totalCount / itemInAPage);

      sql = SecSql.from("SELECT *");
      sql.append("FROM article");
      sql.append("ORDER BY id DESC");
      sql.append("LIMIT ?, ?", limitFrom, itemInAPage);
      System.out.println(sql);
      List<Map<String, Object>> articleRows = DBUtil.selectRows(con, sql);

     req.setAttribute("articleRows", articleRows);
      req.setAttribute("page", page);
      req.setAttribute("totalPage", totalPage);
     req.getRequestDispatcher("../article/list.jsp").forward(req,resp);
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
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req,resp);
  }
}



