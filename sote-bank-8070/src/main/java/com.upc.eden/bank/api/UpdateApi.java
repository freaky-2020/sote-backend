package com.upc.eden.bank.api;

import com.upc.eden.commen.domain.bank.BankRequire;
import com.upc.eden.commen.domain.bank.Question;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: CS Dong
 * @Date: 2022/04/15/19:41
 * @Description:
 */
@Data
@ApiModel(description = "题目变动申请实体类")
public class UpdateApi {

    @ApiModelProperty(value = "修改前题目")
    private Question before;
    @ApiModelProperty(value = "拟修改后题目")
    private BankRequire after;
}
