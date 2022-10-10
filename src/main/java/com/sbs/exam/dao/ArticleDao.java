package com.sbs.exam.dao;

import com.sbs.exam.dto.Article;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {
  private Connection con;
  public ArticleDao(Connection con){
    this.con=con;
  }
  public int getTotalCount(){
    SecSql sql = SecSql.from("SELECT COUNT(*) AS cot");
    sql.append("FROM article");

    int totalCount = DBUtil.selectRowIntValue(con, sql);

    return totalCount;
  }
  public List<Article> getArticles(int itemInApage, int limitFrom){
    SecSql sql = SecSql.from("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?",limitFrom, itemInApage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(con,sql);

   List<Article> articles = new ArrayList<>();

   for(Map<String, Object> articleRow : articleRows){
     articles.add(new Article(articleRow));
   }
   return articles;
  }
  public int write(String title, String body, int loginedMemberId) {
    SecSql sql = SecSql.from("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", body = ?", body);
    sql.append(", memberId = ?", loginedMemberId);

    int id = DBUtil.insert(con, sql);

    return id;
  }

}
