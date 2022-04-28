package com.upc.eden.exam.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: CS Dong
 * @Date: 2022/04/28/17:22
 * @Description:
 */
@Data
@NoArgsConstructor
public class ScoreSegmentApi {

    @ApiModelProperty(value = "该段人数", example = "1")
    private Integer sCount;
    @ApiModelProperty(value = "该段比例", example = "0.2500")
    private String proportion;

    public ScoreSegmentApi(Integer sCount, String proportion) {
        this.sCount = sCount;
        this.proportion = proportion;
    }
}
