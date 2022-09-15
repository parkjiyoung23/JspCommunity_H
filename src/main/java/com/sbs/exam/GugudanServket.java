package com.sbs.exam;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/gugudan")
public class GugudanServket extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    Rq rq = new Rq(req,resp);

    int dan = rq.getIntParam("dan",9);
    int limit = rq.getIntParam("limit",9);

    req.setAttribute("dan",dan);
    req.setAttribute("limit",limit);

    RequestDispatcher requestDispatcher = req.getRequestDispatcher("/gugudan2.jsp");
    requestDispatcher.forward(req,resp);
  }
}

//http://localhost:8081/gugudan?dan=5&limit=9
//jsp 내장객체 검색
