package com.sbs.exam.dao;

import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
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
  public List<Map<String, Object>> getARticleRows(int itemInApage, int limitFrom){
    SecSql sql = SecSql.from("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?",limitFrom, itemInApage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(con,sql);

    return articleRows;
  }

}
