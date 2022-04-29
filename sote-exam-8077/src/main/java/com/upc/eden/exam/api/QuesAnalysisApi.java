package com.upc.eden.exam.api;

import com.upc.eden.commen.domain.exam.Paper;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: CS Dong
 * @Date: 2022/04/29/11:31
 * @Description:
 */
@Data
@NoArgsConstructor
public class QuesAnalysisApi {

    private Integer quesNo;
    private String stem;
    private String answer;
    private Integer typeId;
    private String aRate;
    private String bRate;
    private String cRate;
    private String dRate;
    private String getRate;
    private String average;
    private Integer totalScore;
    private Integer difficultyId;

    public QuesAnalysisApi(Paper paper) {

        this.quesNo = paper.getQuesNo();
        this.stem = paper.getStem();
        this.answer = paper.getAnswer();
        this.typeId = paper.getTypeId();
        this.totalScore = paper.getScore();
        this.difficultyId = paper.getDifficultyId();
    }
}
