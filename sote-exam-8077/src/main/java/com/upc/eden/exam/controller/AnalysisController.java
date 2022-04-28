package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.commen.domain.exam.StuExam;
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

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoService.getOne(examInfoQueryWrapper);

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
}
