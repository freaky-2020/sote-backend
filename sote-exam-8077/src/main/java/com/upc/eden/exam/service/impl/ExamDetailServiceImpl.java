package com.upc.eden.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.exam.service.ExamDetailService;
import com.upc.eden.exam.mapper.ExamDetailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class ExamDetailServiceImpl extends ServiceImpl<ExamDetailMapper, ExamDetail>
    implements ExamDetailService{

    @Resource
    private ExamDetailMapper examDetailMapper;

    @Override
    public void autoMark1(Integer details) {
        examDetailMapper.autoMark1(details);
    }
}




