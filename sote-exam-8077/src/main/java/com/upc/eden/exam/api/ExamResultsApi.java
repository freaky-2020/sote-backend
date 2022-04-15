package com.upc.eden.exam.api;

import com.upc.eden.commen.domain.auth.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/04/15/10:45
 * @Description:
 */
@Data
@ApiModel(description = "教师获取某考试全部考生得分详情返回体")
public class ExamResultsApi {

    @ApiModelProperty(value = "考生作答状态：{ 1:作答 0:缺考 }", example = "1")
    private Integer status;
    @ApiModelProperty(value = "考生信息")
    private User user;
    @ApiModelProperty(value = "非简答题得分", example = "25")
    private Integer nonSynScore;
    @ApiModelProperty(value = "非简答题总分", example = "30")
    private Integer maxNonSynScore;
    @ApiModelProperty(value = "简答题得分", example = "45")
    private Integer synScore;
    @ApiModelProperty(value = "简答题总分", example = "50")
    private Integer maxSynScore;
    @ApiModelProperty(value = "考生总得分", example = "70")
    private Integer totalScore;
    @ApiModelProperty(value = "试卷满分", example = "80")
    private Integer maxScore;
}
