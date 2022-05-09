package com.upc.eden.exam.api;

import com.upc.eden.commen.domain.exam.ExamInfo;
import lombok.Data;

/**
 * @Author: CS Dong
 * @Date: 2022/04/11/11:03
 * @Description:
 */
@Data
public class FindAllExamOfStuApi {

    private Integer examId;
    private Integer time;
    private Integer score;
    private Integer totalScore;
    private String average;
    private String rank;

    private ExamInfo examInfo;
}
