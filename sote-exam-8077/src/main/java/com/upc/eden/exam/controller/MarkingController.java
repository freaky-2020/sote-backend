package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.clients.AuthClient;
import com.upc.eden.commen.domain.auth.User;
import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.api.UserAndDetailApi;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: CS Dong
 * @Date: 2022/04/12/15:08
 * @Description:
 */

@Api(tags = { "批阅业务接口文档" } )
@RestController
@RequestMapping("/mark")
public class MarkingController {

    @Resource
    private ExamDetailService examDetailService;
    @Resource
    private StuExamService stuExamService;
    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private PaperService paperService;
    @Resource
    private AuthClient authClient;

    @ApiOperation("选择题、判断题与填空题的自动判分，返回alert消息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试Id", paramType = "path"),
            @ApiImplicitParam(name = "rule", value = "多选题赋分规则:{1:选不全得0分 2:选不全得半分}"),
            })
    @GetMapping("/auto/{examId}")
    public String autoMark(@PathVariable Integer examId, Integer rule) {

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);

        // 1、界定考试是否截止，未结束无法批卷
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ndf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String deadline = df.format(examInfo.getDeadline());
        if (ndf.format(new Date()).compareTo(deadline) < 0) {
            return "考试未结束，无法批卷，请在 " + deadline + " 后提交请求！";
        }

        // 2、界定考试是否公布，公布无法批卷
        if(examInfo.getIsPublic()==1) return "考试结果已公布，无法批阅！";

        // 3、若考生考试状态滞留为1，则将其考试状态强制置2
        UpdateWrapper<StuExam> stuExamUpdateWrapper = new UpdateWrapper<>();
        stuExamUpdateWrapper.eq("exam_id",examId);
        stuExamUpdateWrapper.eq("status", 1);
        stuExamUpdateWrapper.set("status", 2);
        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("exam_id", examId);
        stuExamQueryWrapper.eq("status", 1);
        List<StuExam> list = stuExamService.list(stuExamQueryWrapper);
        Integer durationTime = examInfo.getDurationTime();
        if(list.size() > 0) {
            for (StuExam each: list) {
                if(each != null) {
                    String tTime = ndf.format(new Date(each.getStartTime().getTime() + durationTime*60*1000));
                    String submitTime = tTime.compareTo(deadline) < 0 ? tTime : deadline;
                    stuExamUpdateWrapper.set("submit_time", submitTime);
                    stuExamService.update(stuExamUpdateWrapper);
                }
            }
        }

        // 4、若考生考试状态为0，则将其考试状态置-1
        stuExamUpdateWrapper = new UpdateWrapper<>();
        stuExamUpdateWrapper.eq("exam_id", examId);
        stuExamUpdateWrapper.eq("status", 0);
        stuExamUpdateWrapper.set("status", -1);
        stuExamService.update(stuExamUpdateWrapper);

        // 4、确认无误，执行自动批卷操作
        stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("exam_id", examId);
        stuExamQueryWrapper.eq("status", 2);
        List<StuExam> stuExams = stuExamService.list(stuExamQueryWrapper);
        for (StuExam stuExam: stuExams) {
            Integer details = stuExam.getDetails();
            // Ⅰ 单选与判断
            examDetailService.autoMark1(details);
            // Ⅱ 多选
            QueryWrapper<ExamDetail> examDetailQueryWrapper = new QueryWrapper<>();
            examDetailQueryWrapper.eq("details", details);
            examDetailQueryWrapper.eq("type_id", 2);
            List<ExamDetail> l1 = examDetailService.list(examDetailQueryWrapper);
            if(l1.size() > 0) {
                for (ExamDetail examDetail: l1) {
                    if(examDetail != null) {
                        UpdateWrapper<ExamDetail> examDetailUpdateWrapper = new UpdateWrapper<>();
                        examDetailUpdateWrapper.eq("id", examDetail.getId());
                        String answer = examDetail.getAnswer();
                        // 答案为空情况
                        if(answer == null || "".equals(answer)) {
                            examDetailUpdateWrapper.set("score", 0);
                            examDetailService.update(examDetailUpdateWrapper);
                            continue;
                        }
                        String realAnswer = examDetail.getRealAnswer();
                        List<String> ra = Arrays.asList(realAnswer.split(","));
                        List<String> a = Arrays.asList(answer.split(","));
                        // 答案有误情况
                        if (!ra.containsAll(a)) {
                            examDetailUpdateWrapper.set("score", 0);
                            examDetailService.update(examDetailUpdateWrapper);
                            continue;
                        }
                        // 答案不全情况
                        if(!a.containsAll(ra)) {
                            if(rule == 1) examDetailUpdateWrapper.set("score", 0);
                            else examDetailUpdateWrapper.set("score", examDetail.getMaxScore()/2);
                            examDetailService.update(examDetailUpdateWrapper);
                            continue;
                        }
                        // 答案全对情况
                        examDetailUpdateWrapper.set("score", examDetail.getMaxScore());
                        examDetailService.update(examDetailUpdateWrapper);
                    }
                }
            }
            // Ⅲ 填空
            examDetailQueryWrapper = new QueryWrapper<>();
            examDetailQueryWrapper.eq("details", details);
            examDetailQueryWrapper.eq("type_id", 4);
            List<ExamDetail> l2 = examDetailService.list(examDetailQueryWrapper);
            if(l2.size() > 0) {
                for (ExamDetail examDetail : l2) {
                    if (examDetail != null) {
                        UpdateWrapper<ExamDetail> examDetailUpdateWrapper = new UpdateWrapper<>();
                        examDetailUpdateWrapper.eq("id", examDetail.getId());
                        String answer = examDetail.getAnswer();
                        // 答案为空情况
                        if(answer==null || "".equals(answer)) {
                            examDetailUpdateWrapper.set("score", 0);
                            examDetailService.update(examDetailUpdateWrapper);
                            continue;
                        }
                        String realAnswer = examDetail.getRealAnswer();
                        List<String> ra = Arrays.asList(realAnswer.split(","));
                        List<String> a = Arrays.asList(answer.split(","));
                        // 答案全对情况
                        if(ra.equals(a)) {
                            examDetailUpdateWrapper.set("score", examDetail.getMaxScore());
                            examDetailService.update(examDetailUpdateWrapper);
                            continue;
                        }
                        Integer score = 0;
                        Integer maxScore = examDetail.getMaxScore();
                        // 逐空匹配
                        for (int i=0; i<a.size(); i++) {
                            if(a.get(i).equals(ra.get(i))) score += maxScore / ra.size();
                        }
                        examDetailUpdateWrapper.set("score", score);
                        examDetailService.update(examDetailUpdateWrapper);
                    }
                }
            }
        }
        // 多少人、多少份
        Integer examineeCount = stuExamService.findExamineeCountByExamId(examId);
        Integer paperCount = stuExamService.findPaperCountByExamId(examId);
        return "选择题、判断题与填空题已自动批阅完成，" +
                "共计" + examineeCount + "人"+paperCount + "份答卷！";
    }

    @ApiOperation("传入paperId，获取该试卷的所有简答题")
    @ApiImplicitParams({@ApiImplicitParam(name = "paperId", value = "试卷Id", paramType = "path")})
    @GetMapping("/allQues/{paperId}")
    public List<Paper> handMark(@PathVariable Integer paperId) {

        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq("paper_id", paperId);
        paperQueryWrapper.eq("type_id", 5);
        List<Paper> questions = paperService.list(paperQueryWrapper);
        return questions;
    }

    @ApiOperation("传入examId，检测该考试能否批卷")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试Id", paramType = "path")})
    @GetMapping("isDead/{examId}")
    public boolean isDead(@PathVariable Integer examId) {

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);

        // 1、界定考试是否截止，未结束无法批卷
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ndf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String deadline = df.format(examInfo.getDeadline());
        if (ndf.format(new Date()).compareTo(deadline) < 0) {
            return false;
        }

        // 2、若考生考试状态滞留为1，则将其考试状态强制置2
        UpdateWrapper<StuExam> stuExamUpdateWrapper = new UpdateWrapper<>();
        stuExamUpdateWrapper.eq("exam_id",examId);
        stuExamUpdateWrapper.eq("status", 1);
        stuExamUpdateWrapper.set("status", 2);
        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("exam_id", examId);
        stuExamQueryWrapper.eq("status", 1);
        List<StuExam> list = stuExamService.list(stuExamQueryWrapper);
        Integer durationTime = examInfo.getDurationTime();
        if(list.size() > 0) {
            for (StuExam each: list) {
                if(each != null) {
                    String tTime = ndf.format(new Date(each.getStartTime().getTime() + durationTime*60*1000));
                    String submitTime = tTime.compareTo(deadline) < 0 ? tTime : deadline;
                    stuExamUpdateWrapper.set("submit_time", submitTime);
                    stuExamService.update(stuExamUpdateWrapper);
                }
            }
        }

        // 3、若考生考试状态为0，则将其考试状态置-1
        stuExamUpdateWrapper = new UpdateWrapper<>();
        stuExamUpdateWrapper.eq("exam_id", examId);
        stuExamUpdateWrapper.eq("status", 0);
        stuExamUpdateWrapper.set("status", -1);
        stuExamService.update(stuExamUpdateWrapper);

        return true;
    }

    // -----paperId复用的话会不匹配，记录！！！！！------
    @ApiOperation("获取某考试的某简答题答题情况")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "考试Id", paramType = "path"),
            @ApiImplicitParam(name = "quesNo", value = "题号", paramType = "path")})
    @GetMapping("/hand/{examId}/{quesNo}")
    public List<UserAndDetailApi> handMark(@PathVariable Integer examId,
                                           @PathVariable Integer quesNo) {

        List<UserAndDetailApi> res = new ArrayList<>();

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo exam = examInfoService.getOne(examInfoQueryWrapper);
        Integer paperId = exam.getPaperId();

        QueryWrapper<ExamDetail> examDetailQueryWrapper = new QueryWrapper<>();
        examDetailQueryWrapper.eq("paper_id", paperId);
        examDetailQueryWrapper.eq("ques_no", quesNo);
        List<ExamDetail> list = examDetailService.list(examDetailQueryWrapper);
        if(list.size()>0) {
            for (ExamDetail examDetail: list) {
                if(examDetail != null) {
                    QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
                    stuExamQueryWrapper.eq("details", examDetail.getDetails());
                    StuExam one = stuExamService.getOne(stuExamQueryWrapper);
                    Integer userName = one.getExamineeId();
                    User user = authClient.getInfoByUserName(userName.toString());
                    res.add(new UserAndDetailApi(user, examDetail));
                }
            }
        }
        return res;
    }

    @ApiOperation("简答题打分，返回message")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "某次作答记录Id", paramType = "path"),
            @ApiImplicitParam(name = "score", value = "打分", paramType = "path")})
    @GetMapping("/markScore/{id}")
    public String markScore(@PathVariable Integer id, Integer score) {

        QueryWrapper<ExamDetail> examDetailQueryWrapper = new QueryWrapper<>();
        examDetailQueryWrapper.eq("id", id);
        ExamDetail one = examDetailService.getOne(examDetailQueryWrapper);
        if(score<0 || score>one.getMaxScore()) {
        return "您的打分应在0~"+one.getMaxScore()+"之间！";
        }

        UpdateWrapper<ExamDetail> examDetailUpdateWrapper = new UpdateWrapper<>();
        examDetailUpdateWrapper.eq("id", id);
        examDetailUpdateWrapper.set("score", score);
        boolean res = examDetailService.update(examDetailUpdateWrapper);
        if (res) return "批阅成功";
        else return "保存失败，请稍后再试";
    }

}
