package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.clients.AuthClient;
import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.api.*;
import com.upc.eden.exam.service.ExamDetailService;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.service.PaperService;
import com.upc.eden.exam.service.StuExamService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: CS Dong
 * @Date: 2022/04/05/19:57
 * @Description:
 */
@Api(tags = { "考生-考试业务接口文档"} )
@RestController
@RequestMapping("/stu")
public class StuExamController {

    @Resource
    private StuExamService stuExamService;
    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private PaperService paperService;
    @Resource
    private ExamDetailService examDetailService;
    @Resource
    private AnalysisController analysisController;
    @Resource
    private AuthClient authClient;

    @ApiOperation("依据考试密钥加入对应考试")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "word", value = "密钥", paramType = "path"),
            @ApiImplicitParam(name = "examineeId", value = "考生userName")})
    @GetMapping("/join/{word}")
    public String joinByWord(@PathVariable String word, String examineeId) {

        // 鉴别密钥是否有效
        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("word", word);
        ExamInfo exam = examInfoService.getOne(examInfoQueryWrapper);
        if (exam == null) return "密钥错误！";

        Integer examId = exam.getExamId();

        // 判断是否重复加入
        QueryWrapper isInWrapper = new QueryWrapper<StuExam>();
        isInWrapper.eq("examinee_id", examineeId);
        isInWrapper.eq("exam_id", examId);
        if (stuExamService.list(isInWrapper).size() != 0) return "您已加入过该考试，请在您的考试中心查看！";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ndf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String now = ndf.format(new Date());
        String startTime = df.format(exam.getStartTime());
        String deadLine = df.format(exam.getDeadline());

        // 加入考试
        boolean res = true;
        for (int presentTime = 1; presentTime <= exam.getAllowableTime(); presentTime++) {

            StuExam stuExam = new StuExam(examineeId, examId, presentTime);
            stuExam.setStatus(now.compareTo(deadLine) > 0 ? -1 : null);
            res = res && stuExamService.save(stuExam);
        }

        if (res) {
            if (now.compareTo(startTime) < 0) return "加入考试成功！请留意考试开放时间，切勿错过考试!";
            else if (now.compareTo(deadLine) > 0) return "加入考试成功！该考试已结束，您只能在试题开放后浏览试题内容！";
            else return "加入考试成功！考试已开放，请留意考试截止时间，切勿错过考试！";
        }
        return "加入考试失败，请稍后再试！";
    }

    @ApiOperation("考生查看自己所有考试：{0:未开放的 1:已开放的 2:已截止的 3:已发布的}，返回所有考试的相关信息与已考次数")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName", value = "考生账号", paramType = "path")})
    @GetMapping("/getExam/stu/{userName}")
    public List<List<FindAllExamOfStuApi>> getExamForStu(@PathVariable String userName) {

        // 拉取信息之前，修正该考生所有考试的提交时间，亦即实现离线态的强制交卷
        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("examinee_id", userName);
        stuExamQueryWrapper.eq("status", 1);
        List<StuExam> all = stuExamService.list(stuExamQueryWrapper);
        if(all.size() > 0) {
            for (StuExam each: all) {
                if(each != null) {
                    Integer examId = each.getExamId();
                    QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
                    examInfoQueryWrapper.eq("exam_id", examId);
                    ExamInfo info = examInfoService.getOne(examInfoQueryWrapper);
                    Integer durationTime = info.getDurationTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    ndf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                    String startTime = df.format(each.getStartTime());
                    String compareTime = ndf.format(new Date(new Date().getTime() - durationTime*60*1000));
                    if(compareTime.compareTo(startTime)>0) {
                        UpdateWrapper<StuExam> stuExamUpdateWrapper = new UpdateWrapper<>();
                        stuExamUpdateWrapper.eq("examinee_id", each.getExamineeId());
                        stuExamUpdateWrapper.eq("exam_id", each.getExamId());
                        stuExamUpdateWrapper.eq("present_time", each.getPresentTime());
                        stuExamUpdateWrapper.set("status", 2);
                        String tTime = df.format(new Date(each.getStartTime().getTime() + durationTime*60*1000));
                        String dead = df.format(info.getDeadline());
                        String submitTime = tTime.compareTo(dead) < 0 ? tTime : dead;
                        stuExamUpdateWrapper.set("submit_time", submitTime);
                        stuExamService.update(stuExamUpdateWrapper);
                    }
                }
            }
        }

        // 初始化返回体
        List<List<FindAllExamOfStuApi>> res = new ArrayList<>();
        for (int i = 0; i < 4; i++) res.add(new ArrayList<>());
        List<FindAllExamOfStuApi> allExamOfStu = stuExamService.findAllExamOfStu(userName);

        // 赋值返回体
        for (FindAllExamOfStuApi each : allExamOfStu) {
            if (each != null) {
                Integer examId = each.getExamId();
                QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
                examInfoQueryWrapper.eq("exam_id", examId);
                ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);
                each.setExamInfo(examInfo);
                each.setTime(stuExamService.findFinishedTime(userName, examId));

                // 按时间分类
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                ndf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
                String now = ndf.format(new Date());
                String startTime = df.format(examInfo.getStartTime());
                String deadLine = df.format(examInfo.getDeadline());

                if(examInfo.getIsPublic()==1) res.get(3).add(each);
                else if (now.compareTo(startTime) < 0) res.get(0).add(each);
                else if (now.compareTo(deadLine) > 0) res.get(2).add(each);
                else res.get(1).add(each);
            }
        }

        String rank = null;
        Integer score = -1;
        for (FindAllExamOfStuApi exam: res.get(3)) {
            Integer examId = exam.getExamInfo().getExamId();
            ExamResultDataApi data = analysisController.getData(examId);

            ExamResultsApi er = null;
            List<ExamResultsApi> results = examInfoService.getResultsForTeacher(examId);
            for (ExamResultsApi era: results) {
                if (era.getUser().getUserName().equals(userName)) {
                    er = era;
                }
            }
            if (er != null) {
                rank = er.getRank() + "/" + results.size();
                score = er.getTotalScore();
            }
            String average = data.getAverage();
            Integer totalScore = data.getTotalScore();
            exam.setAverage(average);
            exam.setTotalScore(totalScore);
            exam.setRank(rank);
            exam.setScore(score);
        }

        for (List<FindAllExamOfStuApi> e: res) Collections.sort
                (e, (r1, r2) -> r1.getExamInfo().getStartTime().
                        compareTo(r1.getExamInfo().getStartTime()));
        return res;
    }

    @ApiOperation("考生开始考试：考试次数传入已考次数+1，返回一个Map<String, ExamAndStuApi>，String是提示信息，ExamAndStuApi是成功回传的对象")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "考生账号", paramType = "path"),
            @ApiImplicitParam(name = "examId", value = "考试号", paramType = "path"),
            @ApiImplicitParam(name = "time", value = "考试次数", paramType = "path"),
    })
    @GetMapping("/start/{userName}/{examId}/{time}")
    public Map<String, ExamAndStuApi> start(@PathVariable String userName, @PathVariable Integer examId,
                                            @PathVariable Integer time) {

        HashMap<String, ExamAndStuApi> resMap = new HashMap<>();
        ExamAndStuApi api = new ExamAndStuApi();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ndf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        // 1、判断是否在考试时间内
        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);
        api.setExamInfo(examInfo);
        String start = df.format(examInfo.getStartTime());
        String dead = df.format(examInfo.getDeadline());
        String now = ndf.format(new Date());
        if (now.compareTo(start)<0) {
            resMap.put("考试还未开放，请留意考试开放时间！", null);
            return resMap;
        } else if(now.compareTo(dead)>0) {
            resMap.put("考试已经截止，无法进入考试！", null);
            return resMap;
        }

        // 2、判断是否超过可考次数
        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("examinee_id", userName);
        stuExamQueryWrapper.eq("exam_id", examId);
        stuExamQueryWrapper.eq("present_time", time);
        StuExam item = stuExamService.getOne(stuExamQueryWrapper);
        if (item == null) {
            resMap.put("您的作答次数已达上限！", null);
            return resMap;
        }

        // 3、判断是否是已开启而待完成的轮次
        if (item.getStatus() == 1) {
            Integer durationTime = examInfo.getDurationTime();
            String startTime = df.format(item.getStartTime());
            String compareTime = ndf.format(new Date(new Date().getTime() - durationTime*60*1000));
            if (compareTime.compareTo(startTime)>0) {
                UpdateWrapper<StuExam> stuExamUpdateWrapper = new UpdateWrapper<>();
                stuExamUpdateWrapper.eq("examinee_id", item.getExamineeId());
                stuExamUpdateWrapper.eq("exam_id", item.getExamId());
                stuExamUpdateWrapper.eq("present_time", item.getPresentTime());
                stuExamUpdateWrapper.set("status", 2);
                String tTime = df.format(new Date(item.getStartTime().getTime() + durationTime * 60 * 1000));
                String submitTime = tTime.compareTo(dead) < 0 ? tTime : dead;
                stuExamUpdateWrapper.set("submit_time", submitTime);
                stuExamService.update(stuExamUpdateWrapper);
                resMap.put("检测到您上次考试异常退出且已超时，已为您强制交卷，请再次提交新一次考试请求！", null);
                return resMap;
            } else {
                item = stuExamService.getOne(stuExamQueryWrapper);
                api.setStuExam(item);
                resMap.put("success", api);
                return resMap;
            }
        }

        // 4、在details中注入答题卡并将status置1
        Integer maxDetail = stuExamService.findMaxDetail();
        Integer detailsId = maxDetail == null ? 1 : maxDetail + 1;
        UpdateWrapper<StuExam> stuExamUpdateWrapper = new UpdateWrapper<>();
        stuExamUpdateWrapper.eq("examinee_id", item.getExamineeId());
        stuExamUpdateWrapper.eq("exam_id", item.getExamId());
        stuExamUpdateWrapper.eq("present_time", item.getPresentTime());
        stuExamUpdateWrapper.set("details", detailsId);
        stuExamUpdateWrapper.set("status", 1);
        Integer paperId = examInfo.getPaperId();
        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq("paper_id", paperId);
        paperQueryWrapper.orderBy(true, true, "ques_no");
        List<Paper> papers = paperService.list(paperQueryWrapper);
        for (Paper paper : papers) {
            if (paper != null) {
                ExamDetail each = new ExamDetail(detailsId, paperId,
                        paper.getQuesNo(), paper.getTypeId(), paper.getAnswer(), paper.getScore());
                examDetailService.save(each);
            }
        }

        // 5、赋值startTime，开始计时
        String theNow = ndf.format(new Date());
        stuExamUpdateWrapper.set("start_time", theNow);
        stuExamService.update(stuExamUpdateWrapper);

        item = stuExamService.getOne(stuExamQueryWrapper);
        api.setStuExam(item);
        resMap.put("success", api);
        return resMap;
    }

    @ApiOperation("考生提交考试：返回alert消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "考生账号", paramType = "path"),
            @ApiImplicitParam(name = "examId", value = "考试号", paramType = "path"),
            @ApiImplicitParam(name = "time", value = "考试次数", paramType = "path"),
    })
    @GetMapping("/submit/{userName}/{examId}/{time}")
    public String submit(@PathVariable String userName, @PathVariable Integer examId,
                         @PathVariable Integer time) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ndf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);

        UpdateWrapper<StuExam> stuExamUpdateWrapper = new UpdateWrapper<>();
        stuExamUpdateWrapper.eq("examinee_id", userName);
        stuExamUpdateWrapper.eq("exam_id", examId);
        stuExamUpdateWrapper.eq("present_time", time);
        stuExamUpdateWrapper.set("status", 2);

        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("exam_id", examId);
        stuExamQueryWrapper.eq("examinee_id", userName);
        stuExamQueryWrapper.eq("present_time", time);
        StuExam stuExam = stuExamService.getOne(stuExamQueryWrapper);

        String now = ndf.format(new Date());
        Integer durationTime = examInfo.getDurationTime();
        String deadLine = df.format(examInfo.getDeadline());
        String compareTime = df.format(new Date(stuExam.getStartTime().getTime() + durationTime * 60 * 1000));
        String submitTime = now.compareTo(compareTime) < 0 ? now : compareTime;
        submitTime = submitTime.compareTo(deadLine) < 0 ? submitTime : deadLine;
        stuExamUpdateWrapper.set("submit_time", submitTime);
        stuExamService.update(stuExamUpdateWrapper);

        return "提交成功！您可在教师公布考试结果后查看您的作答情况！";
    }

    @ApiOperation("考生查看自己的考试成绩：返回他作答情况的一个数组")
    @GetMapping("/getRes/{userName}/{examId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "考生账号", paramType = "path"),
            @ApiImplicitParam(name = "examId", value = "考试号", paramType = "path"),})
    public List<ExamResultApi> getMyExamRes(@PathVariable String userName,
                                            @PathVariable Integer examId) {

        List<ExamResultApi> res = new ArrayList<>();

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);
        Integer paperId = examInfo.getPaperId();
        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq("paper_id", paperId);
        paperQueryWrapper.orderBy(true, true, "ques_no");
        List<Paper> papers = paperService.list(paperQueryWrapper);
        Integer maxScore = 0;
        Integer maxNonSynScore = 0;
        Integer maxSynScore = 0;
        for (Paper paper: papers) {
            if(paper.getTypeId()!=5) maxNonSynScore += paper.getScore();
            else maxSynScore += paper.getScore();
        }
        maxScore = maxNonSynScore + maxSynScore;
        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("examinee_id", userName);
        stuExamQueryWrapper.eq("exam_id", examId);
        stuExamQueryWrapper.orderBy(true, true, "present_time");

        List<StuExam> times = stuExamService.list(stuExamQueryWrapper);
        if(times.size() != 0) {
            for (StuExam time: times) {
                if(time != null) {
                    if (time.getStatus() != 2) {
                        ExamResultApi examResultApi = new ExamResultApi();
                        examResultApi.setPapers(papers);
                        examResultApi.setStatus(0);
                        examResultApi.setExamDetails(null);
                        examResultApi.setMaxNonSynScore(maxNonSynScore);
                        examResultApi.setMaxSynScore(maxSynScore);
                        examResultApi.setMaxScore(maxScore);
                        examResultApi.setNonSynScore(0);
                        examResultApi.setSynScore(0);
                        examResultApi.setTotalScore(0);
                        continue;
                    }
                    Integer details = time.getDetails();
                    QueryWrapper<ExamDetail> examDetailQueryWrapper = new QueryWrapper<>();
                    examDetailQueryWrapper.eq("details", details);
                    List<ExamDetail> examDetails = examDetailService.list(examDetailQueryWrapper);
                    Integer nonSynScore = 0;
                    Integer synScore = 0;
                    for (ExamDetail examDetail: examDetails) {
                        if (examDetail.getTypeId() != 5) nonSynScore += examDetail.getScore();
                        else synScore += examDetail.getScore();
                    }
                    ExamResultApi examResultApi = new ExamResultApi();
                    examResultApi.setPapers(papers);
                    examResultApi.setExamDetails(examDetails);
                    examResultApi.setMaxNonSynScore(maxNonSynScore);
                    examResultApi.setMaxSynScore(maxSynScore);
                    examResultApi.setMaxScore(maxScore);
                    examResultApi.setNonSynScore(nonSynScore);
                    examResultApi.setSynScore(synScore);
                    examResultApi.setTotalScore(nonSynScore+synScore);

                    res.add(examResultApi);
                }
            }
        }
        ExamResultsApi er = null;
        List<ExamResultsApi> results = examInfoService.getResultsForTeacher(examId);
        for (ExamResultsApi era: results) {
            if (era.getUser().getUserName().equals(userName)) {
                er = era;
            }
        }
        if (er != null) res.get(0).setRank(er.getRank() + "/" + results.size());
        return res;
    }
}