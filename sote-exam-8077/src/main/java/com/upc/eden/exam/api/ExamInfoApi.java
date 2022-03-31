package com.upc.eden.exam.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: CS Dong
 * @Date: 2022/03/30/10:28
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "考试信息请求返回体")
public class ExamInfoApi {

    @ApiModelProperty(value = "本场考试的唯一试卷Id", example = "1")
    private Integer paperId;
    @ApiModelProperty(value = "本场考试的唯一密钥口令", example = "Y@%@FMF$")
    private String word;
}
