package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/gugudan")
public class GugudanServket extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {

    Rq rq = new Rq(req,resp);

    int dan = rq.getIntParam("dan",9);
    int limit = rq.getIntParam("limit",9);

    rq.appendBody("<h1>%d단<h1>\n".formatted(dan));
    for(int i=1; i<=limit; i++){

      rq.appendBody("<div>%d * %d = %d</div>".formatted(dan, i, dan * i));
    }
  }
}

//http://localhost:8081/gugudan?dan=5&limit=9
//jsp 내장객체 검색
