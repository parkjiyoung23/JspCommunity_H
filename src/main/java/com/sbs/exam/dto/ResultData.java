package com.sbs.exam.dto;

import com.sbs.exam.util.Util;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

public class ResultData {

  @Getter
  private String msg;
  @Getter
  private String resultCode;
  @Getter
  private Map<String, Object> body;

  private ResultData() {

  }

  public static ResultData from(String resultCode, String msg, Object... bodyArgs) {
    ResultData rd = new ResultData();

    rd.resultCode = resultCode;
    rd.msg = msg;
    rd.body = Util.mapOf(bodyArgs);

    return rd;
  }

  public boolean isSuccess() {
    return resultCode.startsWith("S-1");
  }

  public boolean isFail() {
    return !isSuccess();
  }
}