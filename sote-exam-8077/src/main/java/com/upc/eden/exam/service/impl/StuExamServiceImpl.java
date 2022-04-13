package com.upc.eden.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.api.FindAllExamOfStuApi;
import com.upc.eden.exam.service.StuExamService;
import com.upc.eden.exam.mapper.StuExamMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class StuExamServiceImpl extends ServiceImpl<StuExamMapper, StuExam>
    implements StuExamService{

    @Resource
    private StuExamMapper stuExamMapper;

    @Override
    public Integer findMaxDetail() {
        return stuExamMapper.findMaxDetail();
    }

    @Override
    public Integer findPresentTime(Integer examineeId, Integer examId) {
        return stuExamMapper.findPresentTime(examineeId, examId);
    }

    @Override
    public List<FindAllExamOfStuApi> findAllExamOfStu(Integer examineeId) {
        return stuExamMapper.findAllExamOfStu(examineeId);
    }

    @Override
    public Integer findFinishedTime(Integer examineeId, Integer examId) {
        return stuExamMapper.findFinishedTime(examineeId, examId);
    }

    @Override
    public Integer findExamineeCountByExamId(Integer examId) {
        return stuExamMapper.findExamineeCountByExamId(examId);
    }

    @Override
    public Integer findPaperCountByExamId(Integer examId) {
        return stuExamMapper.findPaperCountByExamId(examId);
    }


}

