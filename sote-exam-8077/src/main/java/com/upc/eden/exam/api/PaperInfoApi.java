package com.upc.eden.exam.api;

import lombok.Data;

/**
 * @Author: CS Dong
 * @Date: 2022/04/25/10:37
 * @Description:
 */
@Data
public class PaperInfoApi {

    private Integer paperId;
    private String paperName;
    private String createTime;
}
