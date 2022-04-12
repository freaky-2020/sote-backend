package com.upc.eden.exam.api;

import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.StuExam;
import lombok.Data;

/**
 * @Author: CS Dong
 * @Date: 2022/04/12/16:30
 * @Description:
 */
@Data
public class ExamAndStuApi {

    private StuExam stuExam;
    private ExamInfo examInfo;
}
