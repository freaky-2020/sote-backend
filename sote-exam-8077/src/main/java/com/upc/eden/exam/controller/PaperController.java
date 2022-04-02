package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.clients.BankClient;
import com.upc.eden.commen.domain.bank.Question;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.commen.domain.exam.Papers;
import com.upc.eden.exam.service.PaperService;
import com.upc.eden.exam.service.PapersService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private PaperService paperService;

    @ApiOperation("依据paperId拉取该试卷目前所有的题目：paperId拼接在路径中")
    @ApiImplicitParams({@ApiImplicitParam(name = "paperId", value = "创建考试时返回的试卷Id", paramType = "path")})
    @GetMapping("/{paperId}/get")
    public Map<Integer, List<Paper>> getPaper(@PathVariable Integer paperId) {

        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", paperId);
        List<Paper> records = paperService.list(wrapper);

        Map<Integer, List<Paper>> res = new HashMap<>();
        for (int i=1; i<=5; i++) res.put(i, new ArrayList<>());

        for (Paper record: records) {
            if (record != null) {
                Integer typeId = record.getTypeId();
                res.get(typeId).add(record);
            }
        }
        return res;
    }

    @ApiOperation("从题库中选择题目（可多个）加入试卷：paperId拼接在路径中，返回添加成功的条数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperId", value = "创建考试时返回的试卷Id", paramType = "path"),
            @ApiImplicitParam(name = "questions", value = "题目集合", allowMultiple = true, dataTypeClass = List.class)})
    @PostMapping("/{paperId}/addFromBank")
    public int add(@PathVariable Integer paperId, List<Question> questions) {

        int res = 0;
        for (Question question : questions) {
            if (question != null) {
                Paper record = new Paper(question);
                record.setPaperId(paperId);
                record.setScore(10);
                if (paperService.save(record)) ++res;
            }
        }
        return res;
    }

    @ApiOperation("自定义题目加入试卷：paperId拼接在路径中，返回是否成功（true or false）")
    @PostMapping("/{paperId}/addBySelf")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperId", value = "创建考试时返回的试卷Id", paramType = "path"),
            @ApiImplicitParam(name = "question", value = "题目")})
    public boolean add(@PathVariable Integer paperId, @ModelAttribute Question question) {

        Paper record = new Paper(question);
        record.setPaperId(paperId);
        record.setScore(10);
        return paperService.save(record);
    }

    @ApiOperation("提交试卷/修改试卷，需要将试卷中所有的题目提交过来")
    @ApiImplicitParams({@ApiImplicitParam(name = "papers", value = "所有题目", allowMultiple = true, dataTypeClass = List.class)})
    @PutMapping("/update")
    public int update(List<Paper> papers) {

        int res = 0;
        for (Paper paper: papers) {
            if(paper != null) {
                UpdateWrapper<Paper> wrapper = new UpdateWrapper<>();
                wrapper.eq("id", paper.getId());
                if(paperService.update(paper, wrapper)) ++res;
            }
        }
        return res;
    }

}
