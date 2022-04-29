package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.api.ExamResultDataApi;
import com.upc.eden.exam.api.QuesAnalysisApi;
import com.upc.eden.exam.api.ScoreSegmentApi;
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
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Author: CS Dong
 * @Date: 2022/04/28/17:12
 * @Description:
 */
@Api(tags = { "成绩分析接口文档"} )
@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private PaperService paperService;
    @Resource
    private StuExamService stuExamService;
    @Resource
    private ExamDetailService examDetailService;

    @ApiOperation("键入examId，获取各分数段人数及比例详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试Id", paramType = "path")})
    @GetMapping("/segment/{examId}")
    public List<ScoreSegmentApi> getSegment(@PathVariable Integer examId) {

        DecimalFormat df = new DecimalFormat("0.0000");

        List<ScoreSegmentApi> res = new ArrayList<>();

        int[] sCounts = new int[10];
        Arrays.fill(sCounts, 0);

        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("exam_id", examId);
        stuExamQueryWrapper.eq("status", 2);
        stuExamQueryWrapper.eq("present_time", 1);
        List<StuExam> stuExams = stuExamService.list(stuExamQueryWrapper);
        List<Integer> getScores = new ArrayList<>();
        Integer totalScore = 0;
        for (StuExam stu: stuExams) {
            Integer details = stu.getDetails();
            QueryWrapper<ExamDetail> examDetailQueryWrapper = new QueryWrapper<>();
            examDetailQueryWrapper.eq("details", details);
            List<ExamDetail> scores = examDetailService.list(examDetailQueryWrapper);
            Integer getScore = 0;
            for (ExamDetail score: scores) {
                getScore += score.getScore();
                if (getScores.size() == 0) totalScore += score.getMaxScore();
            }
            getScores.add(getScore);
        }
        Collections.sort(getScores, (s1, s2) -> s2 - s1);

        Double fraction = totalScore * 0.1;
        Double compareLevel = totalScore - fraction;
        Integer count = 0;
        for (Integer score: getScores) {
            while (score < compareLevel) {
                compareLevel -= fraction;
                ++count;
            }
            ++sCounts[count];
        }
        for (int i=0; i<10; i++) {
            res.add(new ScoreSegmentApi(sCounts[i],
                    df.format((double)sCounts[i] / stuExams.size())));
        }
        return res;
    }

    @ApiOperation("键入examId，获取该考试得分的详细统计")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试Id", paramType = "path")})
    @GetMapping("/data/{examId}")
    public ExamResultDataApi getData(@PathVariable Integer examId) {

        DecimalFormat df = new DecimalFormat("0.0000");
        DecimalFormat adf = new DecimalFormat("0.00");

        ExamResultDataApi res = new ExamResultDataApi();

        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("exam_id", examId);
        stuExamQueryWrapper.eq("status", -1);
        stuExamQueryWrapper.eq("status", 0);
        stuExamQueryWrapper.eq("present_time", 1);
        List<StuExam> t = stuExamService.list(stuExamQueryWrapper);
        Integer absentNum = t.size();

        stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("exam_id", examId);
        stuExamQueryWrapper.eq("status", 2);
        stuExamQueryWrapper.eq("present_time", 1);
        List<StuExam> stuExams = stuExamService.list(stuExamQueryWrapper);
        List<Integer> getScores = new ArrayList<>();
        Integer totalScore = 0;
        for (StuExam stu: stuExams) {
            Integer details = stu.getDetails();
            QueryWrapper<ExamDetail> examDetailQueryWrapper = new QueryWrapper<>();
            examDetailQueryWrapper.eq("details", details);
            List<ExamDetail> scores = examDetailService.list(examDetailQueryWrapper);
            Integer getScore = 0;
            for (ExamDetail score: scores) {
                getScore += score.getScore();
                if (getScores.size() == 0) totalScore += score.getMaxScore();
            }
            getScores.add(getScore);
        }
        Collections.sort(getScores, (s1, s2) -> s2 - s1);

        Integer passScore = (int)Math.ceil(totalScore * 0.6);
        Integer greatScore = (int)Math.ceil(totalScore * 0.8);

        Integer passCount = 0;
        Integer greatCount = 0;
        Integer allScore = 0;
        Integer max = 0;
        Integer min = totalScore;
        for (Integer score: getScores) {
            if (score >= greatScore) ++greatCount;
            if (score >= passScore) ++passCount;
            if (score > max) max = score;
            if (score < min) min = score;
            allScore += score;
        }
        Double average = (double)allScore / stuExams.size();

        res.setPostNum(stuExams.size());
        res.setTotalNum(absentNum + stuExams.size());
        res.setTotalScore(totalScore);
        res.setPassScore(passScore);
        res.setPassNum(passCount);
        res.setPassRate(df.format((double)passCount / stuExams.size()));
        res.setGreatScore(greatScore);
        res.setGreatNum(greatCount);
        res.setGreatRate(df.format((double)greatCount / stuExams.size()));
        res.setAverage(adf.format(average));
        res.setGetRate(df.format(average / totalScore));
        res.setMax(max);
        res.setMin(min);
        res.setDiff(1);

        return res;
    }

    @ApiOperation("键入examId，获取该次考试的试卷分析")
    @ApiImplicitParams({@ApiImplicitParam(name = "examId", value = "考试Id", paramType = "path")})
    @GetMapping("/paperAnalysis/{examId}")
    public List<QuesAnalysisApi> paperAnalysis(@PathVariable Integer examId) {

        DecimalFormat df = new DecimalFormat("0.0000");
        DecimalFormat adf = new DecimalFormat("0.00");

        List<QuesAnalysisApi> res = new ArrayList<>();

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);
        Integer paperId = examInfo.getPaperId();

        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq("paper_id", paperId)
                .orderBy(true, true, "ques_no");
        List<Paper> questions = paperService.list(paperQueryWrapper);

        QueryWrapper<StuExam> stuExamQueryWrapper = new QueryWrapper<>();
        stuExamQueryWrapper.eq("exam_id", examId);
        stuExamQueryWrapper.eq("status", 2);
        stuExamQueryWrapper.eq("present_time", 1);
        List<StuExam> stus = stuExamService.list(stuExamQueryWrapper);
        List<Integer> checkDetails = new ArrayList<>();
        for (StuExam stu: stus) checkDetails.add(stu.getDetails());

        for (Paper quesion: questions) {

            Integer typeId = quesion.getTypeId();
            Integer quesNo = quesion.getQuesNo();
            Integer totalScore = quesion.getScore();

            QueryWrapper<ExamDetail> examDetailQueryWrapper = new QueryWrapper<>();
            examDetailQueryWrapper.eq("paper_id", paperId);
            examDetailQueryWrapper.eq("ques_no", quesNo);
            List<ExamDetail> details = examDetailService.list(examDetailQueryWrapper);

            Integer total = 0;
            Integer aCount = 0;
            Integer bCount = 0;
            Integer cCount = 0;
            Integer dCount = 0;
            Integer computeCount = details.size();

            QuesAnalysisApi api = new QuesAnalysisApi(quesion);

            for (ExamDetail detail: details) {
                if (!checkDetails.contains(detail.getDetails())) {
                    --computeCount;
                    continue;
                }
                total += detail.getScore();
                if (typeId == 1 || typeId == 2) {
                    if (detail.getAnswer() == null) continue;
                    String[] answers = detail.getAnswer().split(",");
                    for (String answer : answers) {
                        if (answer.equals("1")) ++aCount;
                        if (answer.equals("2")) ++bCount;
                        if (answer.equals("3")) ++cCount;
                        if (answer.equals("4")) ++dCount;
                    }
                }
            }
            Double average = (double)total / computeCount;
            api.setAverage(adf.format(average));
            api.setGetRate(df.format(average / totalScore));
            if (typeId == 1 || typeId == 2) {
                api.setARate(df.format((double)aCount / computeCount));
                api.setBRate(df.format((double)bCount / computeCount));
                api.setCRate(df.format((double)cCount / computeCount));
                api.setDRate(df.format((double)dCount / computeCount));
            } else {
                api.setARate("-");
                api.setBRate("-");
                api.setCRate("-");
                api.setDRate("-");
            }
            res.add(api);
        }
        return res;
    }
}
