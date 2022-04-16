package com.upc.eden.bank.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.bank.api.UpdateApi;
import com.upc.eden.bank.service.BankRequireService;
import com.upc.eden.bank.service.QuestionService;
import com.upc.eden.commen.domain.bank.BankRequire;
import com.upc.eden.commen.domain.bank.Question;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @Author: CS Dong
 * @Date: 2022/04/14/23:10
 * @Description:
 */
@Api(tags = { "题目变动申请与审批接口文档"} )
@RestController
@RequestMapping("/required")
public class RequiredController {

    @Resource
    private BankRequireService bankRequireService;
    @Resource
    private QuestionService questionService;

    @ApiOperation("向试题库中添加题目的申请，返回message")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "账号", paramType = "path")})
    @GetMapping("/{userName}/add")
    public String add(@PathVariable Integer userName, Question question) {

        BankRequire bankRequire = new BankRequire(question);
        bankRequire.setDoWay(1);
        bankRequire.setRequestUserName(userName);
        Date requireTime = new Date(new Date().getTime() + 8 * 60 * 60 * 1000);
        bankRequire.setRequireTime(requireTime);
        bankRequireService.save(bankRequire);
        return "添加题目申请成功，请留意个人中心的消息通知！";
    }

    @ApiOperation("修改试题库中题目的申请，返回message")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "账号", paramType = "path")})
    @GetMapping("/{userName}/update")
    public String update(@PathVariable Integer userName, Question question) {

        BankRequire bankRequire = new BankRequire(question);
        bankRequire.setDoWay(2);
        bankRequire.setRequestUserName(userName);
        Date requireTime = new Date(new Date().getTime() + 8 * 60 * 60 * 1000);
        bankRequire.setRequireTime(requireTime);
        bankRequireService.save(bankRequire);
        return "修改题目申请成功，请留意个人中心的消息通知！";
    }

    @ApiOperation("删除试题库中题目的申请（可批量），返回message")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "账号", paramType = "path"),
            @ApiImplicitParam(name = "ids", allowMultiple = true, dataTypeClass = List.class)})
    @GetMapping("/{userName}/delete")
    public String delete(@PathVariable Integer userName, Integer[] ids) {

        for (Integer id: ids) {
            if (id != null) {
                QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
                questionQueryWrapper.eq("id", id);
                Question ques = questionService.getOne(questionQueryWrapper);
                BankRequire bankRequire = new BankRequire(ques);
                bankRequire.setDoWay(3);
                bankRequire.setRequestUserName(userName);
                Date requireTime = new Date(new Date().getTime() + 8 * 60 * 60 * 1000);
                bankRequire.setRequireTime(requireTime);
                bankRequireService.save(bankRequire);
            }
        }

        return "删除题目申请成功，请留意个人中心的消息通知！";
    }

    @ApiOperation("获取添加题目的所有申请实体")
    @GetMapping("/getRequire/add")
    public List<BankRequire> getAdd() {

        QueryWrapper<BankRequire> bankRequireQueryWrapper = new QueryWrapper<>();
        bankRequireQueryWrapper.eq("do_way", 1);
        List<BankRequire> res = bankRequireService.list(bankRequireQueryWrapper);
        return res;
    }

    @ApiOperation("获取修改题目的所有申请实体：{ before:原题目 after:拟修改题目 }")
    @GetMapping("/getRequire/update")
    public List<UpdateApi> getUpdate() {

        List<UpdateApi> res = new ArrayList<>();

        QueryWrapper<BankRequire> bankRequireQueryWrapper = new QueryWrapper<>();
        bankRequireQueryWrapper.eq("do_way", 2);
        List<BankRequire> list = bankRequireService.list(bankRequireQueryWrapper);
        for(BankRequire b: list) {
            if (b==null) continue;
            Integer quesId = b.getQuesId();
            QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
            questionQueryWrapper.eq("id", quesId);
            Question ques = questionService.getOne(questionQueryWrapper);
            if (ques == null) continue;
            UpdateApi updateApi = new UpdateApi();
            updateApi.setBefore(ques);
            updateApi.setAfter(b);
            res.add(updateApi);
        }
        return res;
    }

    @ApiOperation("获取删除题目的所有申请实体")
    @GetMapping("/getRequire/delete")
    public List<BankRequire> getDelete() {

        QueryWrapper<BankRequire> bankRequireQueryWrapper = new QueryWrapper<>();
        bankRequireQueryWrapper.eq("do_way", 3);
        List<BankRequire> res = bankRequireService.list(bankRequireQueryWrapper);
        return res;
    }

    @ApiOperation("裁决增加题目的申请，返回message")
    @ApiImplicitParams({@ApiImplicitParam(name = "decision", value = "{ 1:同意 0:驳回 }")})
    @GetMapping("/judge/add")
    public String judgeAdd(BankRequire bankRequire, Integer decision) {

        // 判断是否已经被处理
        QueryWrapper<BankRequire> bankRequireQueryWrapper = new QueryWrapper<>();
        bankRequireQueryWrapper.eq("id", bankRequire.getId());
        BankRequire one = bankRequireService.getOne(bankRequireQueryWrapper);
        if (one.getDoWay() == -1) return "该申请已被其他管理员处理！";

        Integer id = bankRequire.getId();
        UpdateWrapper<BankRequire> bankRequireUpdateWrapper = new UpdateWrapper<>();
        bankRequireUpdateWrapper.eq("id", id);
        // 置-1留档
        bankRequireUpdateWrapper.set("do_way", -1);
        bankRequireService.update(bankRequireUpdateWrapper);

        if (decision == 0) return "已驳回！";

        Question question = new Question(bankRequire);
        Date now = new Date(new Date().getTime() + 8 * 60 * 60 * 1000);
        question.setCreateTime(now);
        question.setUpdateTime(now);
        boolean res = questionService.save(question);
        if (res) return "审批成功！题目已添加至题库！";
        else return "审批异常，请稍后再试！";
    }

    @ApiOperation("裁决修改题目的申请，返回message")
    @ApiImplicitParams({@ApiImplicitParam(name = "decision", value = "{ 1:同意 0:驳回 }")})
    @PostMapping("/judge/update/{decision}")
    public String judgeUpdate(@RequestBody List<BankRequire> bankRequires, @PathVariable Integer decision) {

        BankRequire bankRequire = bankRequires.get(0);

        // 判断是否已经被处理
        QueryWrapper<BankRequire> bankRequireQueryWrapper = new QueryWrapper<>();
        bankRequireQueryWrapper.eq("id", bankRequire.getId());
        BankRequire one = bankRequireService.getOne(bankRequireQueryWrapper);
        if (one.getDoWay() == -1) return "该申请已被其他管理员处理！";

        Integer id = bankRequire.getId();
        UpdateWrapper<BankRequire> bankRequireUpdateWrapper = new UpdateWrapper<>();
        bankRequireUpdateWrapper.eq("id", id);
        // 置-1留档
        bankRequireUpdateWrapper.set("do_way", -1);
        bankRequireService.update(bankRequireUpdateWrapper);

        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("id", bankRequire.getQuesId());
        Question ques = questionService.getOne(questionQueryWrapper);
        if (ques == null) return "该题目已在试题库中删除！";

        if (decision == 0) return "已驳回！";

        Question question = new Question(bankRequire);
        UpdateWrapper<Question> questionUpdateWrapper = new UpdateWrapper<>();
        SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ndf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        questionUpdateWrapper.set("update_time", ndf.format(new Date()));
        questionUpdateWrapper.eq("id", bankRequire.getQuesId());
        boolean res = questionService.update(question, questionUpdateWrapper);
        if (res) return "审批成功！题目已更新！";
        else return "审批异常，请稍后再试！";
    }

    @ApiOperation("裁决删除题目的申请，返回message")
    @ApiImplicitParams({@ApiImplicitParam(name = "decision", value = "{ 1:同意 0:驳回 }")})
    @GetMapping("/judge/delete")
    public String judgeDelete(BankRequire bankRequire, Integer decision) {

        // 判断是否已经被处理
        QueryWrapper<BankRequire> bankRequireQueryWrapper = new QueryWrapper<>();
        bankRequireQueryWrapper.eq("id", bankRequire.getId());
        BankRequire one = bankRequireService.getOne(bankRequireQueryWrapper);
        if (one.getDoWay() == -1) return "该申请已被其他管理员处理！";

        Integer id = bankRequire.getId();
        UpdateWrapper<BankRequire> bankRequireUpdateWrapper = new UpdateWrapper<>();
        bankRequireUpdateWrapper.eq("id", id);
        // 置-1留档
        bankRequireUpdateWrapper.set("do_way", -1);
        bankRequireService.update(bankRequireUpdateWrapper);

        if (decision == 0) return "已驳回！";

        Integer quesId = bankRequire.getQuesId();
        QueryWrapper<Question> questionQueryWrapper = new QueryWrapper<>();
        questionQueryWrapper.eq("id", quesId);
        boolean res = questionService.remove(questionQueryWrapper);
        if (res) return "审批成功！题目已删除！";
        else return "审批异常，请稍后再试！";
    }
}
