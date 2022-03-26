package com.upc.eden.bank.controller;

import com.upc.eden.bank.service.TypeService;
import com.upc.eden.commen.domain.bank.Type;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/03/18/17:20
 * @Description:
 */

@Api(tags = { "题型增删改查接口文档"} )
@RestController
@RequestMapping("/type")
public class TypeController {

    @Resource
    TypeService typeService;

    @GetMapping
    @ApiOperation("获取全部题型的Map")
    public List<Type> list() {

        List<Type> typeList = typeService.list();
        return typeList;
    }
}
