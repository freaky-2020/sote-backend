package com.upc.eden.exam.api;

import lombok.Data;

/**
 * @Author: CS Dong
 * @Date: 2022/04/28/19:12
 * @Description:
 */
@Data
public class ExamResultDataApi {

    private Integer postNum;
    private Integer totalNum;
    private Integer passNum;
    private Integer greatNum;
    private Integer passScore;
    private Integer greatScore;
    private Integer totalScore;
    private String passRate;
    private String greatRate;
    private String getRate;
    private Integer max;
    private Integer min;
    private String average;
    private Integer diff;
}
