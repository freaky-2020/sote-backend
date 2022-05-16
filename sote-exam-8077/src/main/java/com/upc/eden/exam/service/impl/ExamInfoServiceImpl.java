package com.upc.eden.exam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commen.clients.AuthClient;
import com.upc.eden.commen.domain.auth.User;
import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.api.ExamResultsApi;
import com.upc.eden.exam.mapper.ExamDetailMapper;
import com.upc.eden.exam.mapper.PaperMapper;
import com.upc.eden.exam.mapper.StuExamMapper;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.mapper.ExamInfoMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
@Service
public class ExamInfoServiceImpl extends ServiceImpl<ExamInfoMapper, ExamInfo>
    implements ExamInfoService{

    @Resource
    private ExamInfoMapper examInfoMapper;
    @Resource
    private PaperMapper paperMapper;
    @Resource
    private StuExamMapper stuExamMapper;
    @Resource
    private ExamDetailMapper examDetailMapper;
    @Resource
    private AuthClient authClient;

    @Override
    public Integer findMaxPaperId() {
        return examInfoMapper.findMaxPaperId();
    }

    @Override
    public List<ExamResultsApi> getResultsForTeacher(Integer examId) {

        List<ExamResultsApi> res = new ArrayList<>();

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("exam_id", examId);
        ExamInfo examInfo = examInfoMapper.selectOne(examInfoQueryWrapper);;
        Integer paperId = examInfo.getPaperId();
        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq("paper_id", paperId);
        List<Paper> papers = paperMapper.selectList(paperQueryWrapper);
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
        List<StuExam> stuExams = stuExamMapper.selectList(stuExamQueryWrapper);
        for (StuExam stuExam: stuExams) {
            if(stuExam == null) continue;
            String examineeId = stuExam.getExamineeId();
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
            List<ExamDetail> detail = examDetailMapper.selectList(examDetailQueryWrapper);
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

    @Override
    public Integer findMaxExamId() {
        return examInfoMapper.findMaxExamId();
    }
}




