package com.sbs.exam.servlet;

import com.sbs.exam.Config;
import com.sbs.exam.Rq;
import com.sbs.exam.controller.ArticleController;
import com.sbs.exam.controller.Controller;
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
import java.util.List;
import java.util.Map;

@WebServlet("/usr/*")
public class DispatcherServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
  Rq rq = new Rq(req,resp);

    if(rq.getIsInvalid()){
      rq.appendBody("올바른 요청이 아닙니다.");
    }

    String driverName = Config.getDriverClassName();

    try {
      Class.forName(driverName);
    } catch (
        ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      rq.appendBody("DB 드라이버 클래스 로딩 실패");
      return;
    }

    // DB 연결
    Connection con = null;

    try {
      con = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());
      // 모든 요청을 들어가기 전에 무조건 해야 하는 일 시작
      HttpSession session = req.getSession();

      boolean isLogined = false;
      int loginedMemberId = -1;
      Map<String, Object> loginedMemberRow = null;

      if (session.getAttribute("loginedMemberId") != null) {
        loginedMemberId = (int) session.getAttribute("loginedMemberId");
        isLogined = true;

        SecSql sql = SecSql.from("SELECT * FROM member");
        sql.append("WHERE id = ?", loginedMemberId);
        loginedMemberRow = DBUtil.selectRow(con, sql);
      }

      req.setAttribute("isLogined", isLogined);
      req.setAttribute("loginedMemberId", loginedMemberId);
      req.setAttribute("loginedMemberRow", loginedMemberRow);
      // 모든 요청을 들어가기 전에 무조건 해야 하는 일 끝


      switch (rq.getControllerTypeName()) {
        case "usr":
          ArticleController articleController = new ArticleController(req, resp, con);
          switch (rq.getControllerName()) {
            case "article":
              articleController.performAction(rq);
              break;
        }
      }


    } catch (
        SQLException e) {
      e.printStackTrace();
    } catch ( SQLErrorException e ) {
      e.getOrigin().printStackTrace();
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



