package com.sbs.exam.servlet;


import com.sbs.exam.Config;
import com.sbs.exam.exception.SQLErrorException;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/article/doWrite")
public class ArticleDoWriteServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset-utf-8");

    HttpSession session = req.getSession();

    if(session.getAttribute("loginedMemberId") == null){
      resp.getWriter().append(String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login'); </script>"));
    }

    String driverName = Config.getDriverClassName();


    try {
      Class.forName(driverName);
    } catch ( SQLErrorException e ) {
      e.getOrigin().printStackTrace();
    } catch (
        ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      resp.getWriter().append("DB 드라이버 클래스 로딩 실패");
      return;
    }

    // DB 연결
    Connection con = null;

    try {
      con = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());

      String title = req.getParameter("title");
      String body = req.getParameter("body");

      int loginedMemberId = (int) session.getAttribute("loginedMemberId");


      SecSql sql = SecSql.from("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?", title);
      sql.append(", body = ?", body);
      sql.append(", memberId = ?", loginedMemberId);

      int id = DBUtil.insert(con, sql);
      resp.getWriter().append(String.format("<script> alert('%d번 글이 등록되었습니다.'); location.replace('list'); </script>", id));


    } catch (
        SQLException e) {
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