package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.commen.clients.BankClient;
import com.upc.eden.commen.domain.bank.Question;
import com.upc.eden.commen.domain.exam.Papers;
import com.upc.eden.exam.service.PapersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/03/31/16:39
 * @Description:
 */

@Api(tags = { "试卷业务接口文档"} )
@RestController
@RequestMapping("/paper")
public class PaperController {
    
    @Resource
    private PapersService papersService;
    @Resource
    private BankClient bankClient;

    @ApiOperation("依据paperId拉取该试卷目前所有的题目：paperId拼接在路径中")
    @ApiImplicitParams({@ApiImplicitParam(name = "paperId", value = "创建考试时返回的试卷Id", paramType = "path")})
    @GetMapping("/{paperId}/get")
    public List<Question> getPaper(@PathVariable Integer paperId) {

        List<Integer> qIds = papersService.getQIdByPId(paperId);
        List<Question> questions = bankClient.getQuesByIds(qIds);
        return questions;
    }

//    @PostMapping("/{paperId}/add")
//    public boolean add(@PathVariable Integer paperId, List<Integer> qIds) {
//
//        QueryWrapper<Papers> wrapper = new QueryWrapper<>();
//        wrapper.eq("paper_id", paperId);
//        for (int qId: qIds) {
//            papersService.save
//        }
//    }
}
