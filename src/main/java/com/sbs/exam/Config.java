package com.sbs.exam;


  public class Config {
    public static String getDBUrl() {
      return "jdbc:mysql://127.0.0.1:3306/Jsp_Community?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    }

    public static String getDBId() {
      return "root";
    }

    public static String getDBPw() {
      return "";
    }

    public static String getDriverClassName() {
      return "com.mysql.jdbc.Driver";
    }
  }





