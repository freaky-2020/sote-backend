package com.upc.eden.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commen.domain.exam.StuExam;
import com.upc.eden.exam.service.StuExamService;
import com.upc.eden.exam.mapper.StuExamMapper;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}




