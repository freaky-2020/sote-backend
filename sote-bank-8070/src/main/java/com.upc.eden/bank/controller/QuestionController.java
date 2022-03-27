package com.upc.eden.bank.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.upc.eden.bank.service.QuestionService;
import com.upc.eden.commen.domain.auth.User;
import com.upc.eden.commen.domain.bank.Question;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/03/17/21:16
 * @Description:
 */

@Api(tags = { "题目增删改查接口文档"} )
@RestController
@RequestMapping("/question")
public class QuestionController {

    @Resource
    QuestionService questionService;

    /**
     * 按题型获取题目
     * /bank/quesiton/type/{typeId}
     * @param typeId 题型Id: {1:单选 2:多选 3:判断 4:填空 5:综合}
     * @param cp 当前页码，默认为1
     * @param size 每页条数，默认为10
     * @return 对应题型的题目页集，按科目、难度依次降序
     */
    @GetMapping("/type/{typeId}")
    @ApiOperation("按题型Id索引题目，题型Id拼接在路径中：返回对应科目的题目页，按科目、难度依次降序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "typeId", value = "题型Id"),
            @ApiImplicitParam(name = "cp", value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10")})
    public Page<Question> typeList(@PathVariable Integer typeId,
                                   @RequestParam(defaultValue = "1") long cp,
                                   @RequestParam(defaultValue = "10") long size) {

        Page<Question> page = new Page<>(cp, size);
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("type_id", typeId)
                .orderBy(true, true, "subject_id")
                .orderBy(true, true, "difficulty_id");
        Page<Question> typePage = questionService.page(page, questionQueryWrapper);
        return typePage;
    }

    /**
     * 按科目获取题目
     * /bank/question/subject/{subjectId}
     * @param subjectId 科目Id
     * @param cp 当前页码，默认为1
     * @param size 每页条数，默认为10
     * @return 对应科目的题目页，按题型、难度依次降序
     */
    @GetMapping("/subject/{subjectId}")
    @ApiOperation("按科目Id索引题目，科目Id拼接在路径中：返回对应科目的题目页，按题型、难度依次降序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "subjectId", value = "科目Id"),
            @ApiImplicitParam(name = "cp", value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10")})
    public Page<Question> subjectList(@PathVariable Integer subjectId,
                                      @RequestParam(defaultValue = "1") long cp,
                                      @RequestParam(defaultValue = "10") long size) {

        Page<Question> page = new Page<>(cp, size);
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("subject_id", subjectId)
                .orderBy(true, true, "type_id")
                .orderBy(true, true, "difficulty_id");
        Page<Question> subjectPage = questionService.page(page, questionQueryWrapper);
        return subjectPage;
    }

    /**
     * 按照传入的请求信息获取题目
     * /bank/question/query
     * @param question 传入的题目信息(如科目Id、题型Id)会被自动封装
     * @param startTime 题目创建时间的范围-起始时间: 格式为 yyyy-MM-dd
     * @param endTime 题目创建时间的范围-截止时间: 格式为 yyyy-MM-dd
     * @param cp cp 当前页码，默认为1
     * @param size size 每页条数，默认为10
     * @return 对应的题目页，按科目、题型、难度依次降序
     */
    @GetMapping("/query")
    @ApiOperation("按传入的检索条件检索题目（支持题干模糊查询）：返回对应的题目页，按科目、题型、难度依次降序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startTime", value = "创建日期起始检索值", defaultValue = "1970-01-01"),
            @ApiImplicitParam(name = "endTime", value = "创建日期截止检索值", defaultValue = "2999-12-31"),
            @ApiImplicitParam(name = "cp", value = "当前页码", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页条数", defaultValue = "10")})
    public Page<Question> query(Question question,
                                @RequestParam(required = false) String startTime,
                                @RequestParam(required = false) String endTime,
                                @RequestParam(defaultValue = "1") long cp,
                                @RequestParam(defaultValue = "10") long size) {

        String stem = question.getStem();
        question.setStem(null);
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>(question);
        questionQueryWrapper.like("stem", stem);
        if(startTime != null) {
            questionQueryWrapper.ge("create_time", startTime);
        }
        if(endTime != null) {
            questionQueryWrapper.le("create_time", endTime);
        }
        questionQueryWrapper
                .orderBy(true, true, "subject_id")
                .orderBy(true, true, "type_id")
                .orderBy(true, true, "difficulty_id");

        Page<Question> page = new Page<>(cp, size);
        Page<Question> questionPage = questionService.page(page, questionQueryWrapper);
        return questionPage;
    }

    /**
     * 向试题库中添加题目
     * /bank/question/add
     * @param question 需要添加的试题信息
     * @return 添加是否很成功，true or false
     * @throws ParseException 时间转换异常
     */

    @PostMapping("/add")
    @ApiOperation("向试题库中添加题目")
    public boolean add(Question question) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        question.setCreateTime(df.parse(df.format(new Date())));
        question.setUpdateTime(df.parse(df.format(new Date())));
        boolean res = questionService.save(question);
        return res;
    }

    /**
     * 更新用户
     * /bank/question/update
     * @param question 需要更新的题目信息
     * @return 更新是否成功，true or false
     */
    @PutMapping("/update")
    @ApiOperation("更新试题库题目：以id为索引标准，即id不可修改")
    public boolean update(Question question) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        question.setUpdateTime(df.parse(df.format(new Date())));
        UpdateWrapper<Question> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", question.getId());
        boolean res = questionService.update(question, wrapper);
        return res;
    }

    /**
     * 删除试题库题目
     * /bank/question/delete/{userName}
     * @param id 待删除题目的 userName, 拼接在路径中
     * @return 删除是否成功，true or false
     */
    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除试题库题目，待删除题目的id拼接在路径尾")
    public boolean delete(@PathVariable Integer id) {

        QueryWrapper<Question> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        boolean res = questionService.remove(wrapper);
        return res;
    }

    /**
     * 批量删除题目，以整型数组封装所有待删除题目的id作为参数
     * @param ids 待删除题目id的整型数组
     * @return 删除成功的条数
     */
    @DeleteMapping("/delete")
    @ApiOperation("批量删除题目，以整型数组封装所有待删除题目的id作为参数：返回值为成功条数")
    @ApiImplicitParam(name = "ids", allowMultiple = true, dataTypeClass = List.class)
    public int delete(Integer[] ids) {

        int res = 0;
        for (int id: ids) {
            QueryWrapper<Question> wrapper = new QueryWrapper<>();
            wrapper.eq("id", id);
            if (questionService.remove(wrapper)) ++res;
        }
        return res;
    }
}
