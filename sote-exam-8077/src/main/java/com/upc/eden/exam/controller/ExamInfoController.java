package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.clients.AuthClient;
import com.upc.eden.commen.domain.auth.User;
import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.api.ExamInfoApi;
import com.upc.eden.exam.api.ExamResultsApi;
import com.upc.eden.exam.api.UserApi;
import com.upc.eden.exam.service.ExamDetailService;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.service.PaperService;
import com.upc.eden.exam.service.StuExamService;
import com.upc.eden.exam.tool.RandomSecret;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: CS Dong
 * @Date: 2022/03/28/11:16
 * @Description:
 */

@Api(tags = { "考试信息业务接口文档"} )
@RestController
@RequestMapping("/info")
public class ExamInfoController {

    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private ExamDetailService examDetailService;
    @Resource
    private StuExamService stuExamService;
    @Resource
    private PaperService paperService;
    @Resource
    private AuthClient authClient;
    @Resource
    private RandomSecret randomSecret;

    @PostMapping("/add")
    @ApiOperation("添加考试相关信息：返回本场考试唯一试卷Id与唯一密钥口令，详见Schemas")
    public ExamInfoApi add(ExamInfo examInfo) {

        Integer maxPaperId = examInfoService.findMaxPaperId();
        Integer paperId;
        if (maxPaperId == null) paperId = 1;
        else paperId = maxPaperId + 1;

        String secret;
        if (examInfo.getNoticeWay()==1) {
            QueryWrapper<ExamInfo> wrapper = new QueryWrapper<>();
            do{
                secret = randomSecret.getRandomSecret(16);
                wrapper.eq("word", secret);
            } while (examInfoService.getOne(wrapper) != null);
        }
        else secret = null;

        examInfo.setPaperId(paperId);
        examInfo.setWord(secret);
//        examInfo.setInvigilatorId(authClient.getCurrentUser().getId());

        examInfoService.save(examInfo);
        return new ExamInfoApi(paperId, secret);
    }

    @ApiOperation("修改考试相关信息：考试Id与参加考试方式不可修改")
    @PutMapping("/update")
    public boolean update(ExamInfo examInfo) {

        UpdateWrapper<ExamInfo> wrapper = new UpdateWrapper<>();
        examInfo.setNoticeWay(null);
        wrapper.eq("exam_id", examInfo.getExamId());
        boolean res = examInfoService.update(examInfo, wrapper);
        return res;
    }

    @ApiOperation("删除考试信息：examId拼接在路径尾")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试信息Id", paramType = "path")})
    @DeleteMapping("/delete/{examId}")
    public boolean delete(@PathVariable Integer examId) {

        QueryWrapper<ExamInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("exam_id", examId);
        boolean res = examInfoService.remove(wrapper);
        return res;
    }

    @ApiOperation("刷新某考试的密钥口令：examId拼接在路径尾")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试信息Id", paramType = "path")})
    @GetMapping("/refreshWord/{examId}")
    public boolean changeWord(@PathVariable Integer examId) {

        QueryWrapper<ExamInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(wrapper);
        if (examInfo != null && examInfo.getNoticeWay()==1) {
            String secret;
            QueryWrapper<ExamInfo> wrapper2 = new QueryWrapper<>();
            do{
                secret = randomSecret.getRandomSecret(16);
                wrapper2.eq("word", secret);
            } while (examInfoService.getOne(wrapper2) != null);
            examInfo.setWord(secret);
            return examInfoService.update(examInfo, wrapper);
        } else return false;
    }

    @ApiOperation("按照传入的若干条件检索对应的考试信息，返回一个list：" +
            "{ 0:还未开放的考试 1:进行中的考试 2:已截止的考试 3:已公布的考试 }，均按科目、开始时间排序")
    @GetMapping("/query")
    public List<List<ExamInfo>> query(ExamInfo examInfo) {

        QueryWrapper<ExamInfo> wrapper = new QueryWrapper<>(examInfo);
        wrapper.orderBy(true, true, "subject_id");
        wrapper.orderBy(true, true, "start_time");
        List<ExamInfo> examInfos = examInfoService.list(wrapper);

        List<List<ExamInfo>> res = new ArrayList<>();
        for(int i = 0; i < 4; i++) res.add(new ArrayList<>());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ndf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String date = ndf.format(new Date());
        for(ExamInfo record: examInfos) {
            if(record != null) {
                System.out.println(record.getStartTime());
                if (record.getIsPublic()==1) res.get(3).add(record);
                else if (date.compareTo(df.format(record.getStartTime()))<0) res.get(0).add(record);
                else if(date.compareTo(df.format(record.getDeadline()))>0) res.get(2).add(record);
                else res.get(1).add(record);
            }
        }
        return res;
    }

    @ApiOperation("获取某场考试全部参考学生的信息，alreadyTime是已考次数")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试信息Id", paramType = "path")})
    @GetMapping("/stu/{examId}")
    public List<UserApi> gerAllStuInfo(@PathVariable Integer examId) {

        List<UserApi> res = new ArrayList<>();

        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("exam_id", examId);
        List<StuExam> list = stuExamService.list(stuExamQueryWrapper);
        for (StuExam stuExam: list) {
            if (stuExam != null) {
                Integer examineeId = stuExam.getExamineeId();
                User user = authClient.getInfoByUserName(examineeId.toString());
                Integer finishedTime = stuExamService.findFinishedTime(examineeId, examId);
                UserApi userApi = new UserApi(user, finishedTime);
                res.add(userApi);
            }
        }
        Collections.sort(res, (r1, r2) -> (r2.getAlreadyTime()-r1.getAlreadyTime()));
        return res;
    }

    @ApiOperation("教师尝试公布考试结果，返回message")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试信息Id", paramType = "path")})
    @GetMapping("/try/publish/{examId}")
    public String tryPublish(@PathVariable Integer examId) {

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);
        Integer paperId = examInfo.getPaperId();
        Integer isPublic = examInfo.getIsPublic();

        // 1、校验自动批阅是否完成
        if(isPublic == 0) return "请您至少在自动批阅后再尝试公布考试结果！";

        // 2、校验是否全部批阅完成
        List<Integer> nonMarkPersonNum = examDetailService.findNonMarkPersonNum(paperId);
        if (nonMarkPersonNum.size() > 0) {
            return "仍有"+nonMarkPersonNum.size()+
                    "名考生的简答题还未全部批阅，您确定要公布结果吗？公布后无法再次批阅与修改成绩！";
        }

        // 3、无误，执行批阅
        UpdateWrapper<ExamInfo> examInfoUpdateWrapper = new UpdateWrapper<>();
        examInfoUpdateWrapper.eq("exam_id", examId);
        examInfoUpdateWrapper.set("is_public", 1);
        examInfoService.update(examInfoUpdateWrapper);

        return "成绩与答案已公开，请通知考生及时查看！";
    }

    @ApiOperation("教师公布考试结果，返回message提示")
    @GetMapping("/publish/{examId}")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试信息Id", paramType = "path")})
    public String publish(@PathVariable Integer examId) {

        UpdateWrapper<ExamInfo> examInfoUpdateWrapper = new UpdateWrapper<>();
        examInfoUpdateWrapper.eq("exam_id", examId);
        examInfoUpdateWrapper.set("is_public", 1);
        examInfoService.update(examInfoUpdateWrapper);

        return "成绩与答案已公开，请通知考生及时查看！";
    }

    @ApiOperation("教师获取某考试各考生得分情况，返回message")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试信息Id", paramType = "path")})
    @GetMapping("/result/{examId}")
    public List<ExamResultsApi> getResults(@PathVariable Integer examId) {

        List<ExamResultsApi> res = new ArrayList<>();

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);
        Integer paperId = examInfo.getPaperId();
        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq("paper_id", paperId);
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
        stuExamQueryWrapper.eq("exam_id", examId);
        // 只返第一次，记录
        stuExamQueryWrapper.eq("present_time", 1);
        List<StuExam> stuExams = stuExamService.list(stuExamQueryWrapper);
        for (StuExam stuExam: stuExams) {
            if(stuExam == null) continue;
            Integer examineeId = stuExam.getExamineeId();
            User user = authClient.getInfoByUserName(examineeId.toString());
            ExamResultsApi now = new ExamResultsApi();
            now.setUser(user);
            now.setMaxNonSynScore(maxNonSynScore);
            now.setMaxSynScore(maxSynScore);
            now.setMaxScore(maxScore);
            // 缺考
            if (stuExam.getStatus() != 2) {
                now.setStatus(0);
                now.setNonSynScore(-1);
                now.setSynScore(-1);
                now.setTotalScore(-1);
                now.setCuttingTime(-1);
                now.setTotalCuttingTime(-1);
                now.setUndetectedTime(-1);
                now.setLeaveTimes(-1);
                res.add(now);
                continue;
            }
            // 已考
            Integer details = stuExam.getDetails();
            QueryWrapper<ExamDetail> examDetailQueryWrapper = new QueryWrapper<>();
            examDetailQueryWrapper.eq("details", details);
            List<ExamDetail> detail = examDetailService.list(examDetailQueryWrapper);
            Integer nonSynScore = 0;
            Integer synScore = 0;
            Integer totalScore = 0;
            for (ExamDetail examDetail: detail) {
                if (examDetail == null) continue;
                Integer typeId = examDetail.getTypeId();
                if(typeId==5) synScore += examDetail.getScore();
                else nonSynScore += examDetail.getScore();
            }
            totalScore = nonSynScore + synScore;
            now.setStatus(stuExam.getCheat() == 1 ? -1 : 1);
            now.setSynScore(synScore);
            now.setNonSynScore(nonSynScore);
            now.setTotalScore(totalScore);
            now.setCuttingTime(stuExam.getCuttingTime());
            now.setTotalCuttingTime(stuExam.getTotalCuttingTime());
            now.setUndetectedTime(stuExam.getUndetectedTime());
            now.setLeaveTimes(stuExam.getLeaveTimes());
            res.add(now);
        }
        Collections.sort(res, (r1, r2) -> (r2.getTotalScore()-r1.getTotalScore()));
        for (int i=0; i<res.size(); i++) res.get(i).setRank(i+1);
        return res;
    }

    @ApiOperation("修改考试信息，返回boolean值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examId", value = "试卷Id", paramType = "path"),
            @ApiImplicitParam(name = "examName", value = "考试名", defaultValue = "高等数学"),
            @ApiImplicitParam(name = "startTime", value = "考试开始时间，注意格式", defaultValue = "1970-01-01"),
            @ApiImplicitParam(name = "deadline", value = "考试截止时间，注意格式", defaultValue = "2999-12-31"),
            @ApiImplicitParam(name = "durationTime", value = "考试持续时间", defaultValue = "120"),
            @ApiImplicitParam(name = "allowableTime", value = "允许参考次数", defaultValue = "3")})
    @GetMapping("/update/{examId}")
    public boolean updateExamInfo(@PathVariable Integer examId,
                                 @RequestParam(required = false) String examName,
                                 @RequestParam(required = false) String examNote,
                                 @RequestParam(required = false) String startTime,
                                 @RequestParam(required = false) String deadline,
                                 @RequestParam(required = false) Integer durationTime,
                                 @RequestParam(required = false) Integer allowableTime) {

        boolean res = true;

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String oldStartTime = df.format(examInfo.getStartTime());
        String oldDeadLine = df.format(examInfo.getDeadline());
        ndf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String now = ndf.format(new Date());

        UpdateWrapper<ExamInfo> examInfoUpdateWrapper = new UpdateWrapper<>();
        examInfoUpdateWrapper.eq("exam_id", examId);
        // 1、开考前可以修改的
        if (now.compareTo(oldStartTime) < 0) {
            if (examName != null) examInfoUpdateWrapper.set("exam_name", examName);
            if (examNote != null) examInfoUpdateWrapper.set("exam_note", examNote);
            if (startTime != null) examInfoUpdateWrapper.set("start_time", startTime);
            if (deadline != null) examInfoUpdateWrapper.set("deadline", deadline);
            if (durationTime != null) examInfoUpdateWrapper.set("duration_time", durationTime);
            if (allowableTime != null) {
                examInfoUpdateWrapper.set("allowable_time", allowableTime);
                QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
                stuExamQueryWrapper.eq("exam_id", examId).groupBy("examinee_id");
                List<StuExam> stuList = stuExamService.list(stuExamQueryWrapper);
                Integer oldAllowableTime = examInfo.getAllowableTime();
                for (StuExam stu: stuList) {
                    if (allowableTime > oldAllowableTime) {
                        for (int presentTime = oldAllowableTime+1; presentTime <= allowableTime; presentTime++) {
                            StuExam stuExam = new StuExam(stu.getExamineeId(), examId, presentTime);
                            stuExam.setStatus(0);
                            stuExamService.save(stuExam);
                        }
                    } else if (allowableTime < oldAllowableTime) {
                        for (int presentTime = oldAllowableTime; presentTime >= allowableTime; presentTime--) {
                            QueryWrapper<StuExam> wrapper = new QueryWrapper<>();
                            wrapper.eq("exam_id", examId);
                            wrapper.eq("examinee_id", stu.getExamineeId());
                            wrapper.eq("present_time", presentTime);
                            stuExamService.remove(wrapper);
                        }
                    }
                }
            }
            res = res && examInfoService.update(null, examInfoUpdateWrapper);
        }
        // 2、开考后、截止前可以修改的
        if (now.compareTo(oldStartTime) >= 0 && now.compareTo(oldDeadLine) <= 0) {
            if (deadline != null) examInfoUpdateWrapper.set("deadline", deadline);
            if (allowableTime != null) {
                examInfoUpdateWrapper.set("allowable_time", allowableTime);
                QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
                stuExamQueryWrapper.eq("exam_id", examId).groupBy("examinee_id");
                List<StuExam> stuList = stuExamService.list(stuExamQueryWrapper);
                Integer oldAllowableTime = examInfo.getAllowableTime();
                for (StuExam stu : stuList) {
                    if (allowableTime > oldAllowableTime) {
                        for (int presentTime = oldAllowableTime + 1; presentTime <= allowableTime; presentTime++) {
                            StuExam stuExam = new StuExam(stu.getExamineeId(), examId, presentTime);
                            stuExam.setStatus(0);
                            stuExamService.save(stuExam);
                        }
                    }
                }
            }
            res = res && examInfoService.update(null, examInfoUpdateWrapper);
        }
        // 3、截止后可以修改的
        if (now.compareTo(oldDeadLine) > 0) {
            if (deadline != null) examInfoUpdateWrapper.set("deadline", deadline);
            res = res && examInfoService.update(null, examInfoUpdateWrapper);
            UpdateWrapper<StuExam> wrapper = new UpdateWrapper<>();
            wrapper.eq("exam_id", examId).eq("status", -1).eq("details", null);
            wrapper.set("status", 0);
            stuExamService.update(null, wrapper);
        }
        return true;
    }
}
