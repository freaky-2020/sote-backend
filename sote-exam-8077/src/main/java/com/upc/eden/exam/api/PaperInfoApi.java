package com.upc.eden.exam.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: CS Dong
 * @Date: 2022/04/25/10:37
 * @Description:
 */
@Data
@ApiModel(description = "试卷库展示返回体")
public class PaperInfoApi {

    @ApiModelProperty(value = "试卷id", example = "1")
    private Integer paperId;
    @ApiModelProperty(value = "试卷名", example = "高等数学2-2期中检测")
    private String paperName;
    @ApiModelProperty(value = "试卷隶属科目id", example = "1")
    private Integer subjectId;
    @ApiModelProperty(value = "试卷发布时间", example = "2022-04-25 19:00:00")
    private String publicTime;
    @ApiModelProperty(value = "被套用次数", example = "20")
    private Integer copiedTime;
    @ApiModelProperty(value = "整体难度系数，1~10", example = "5")
    private Integer difficulty;
}
