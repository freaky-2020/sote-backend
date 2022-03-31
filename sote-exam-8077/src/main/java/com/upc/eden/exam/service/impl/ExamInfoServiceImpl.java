package com.upc.eden.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.mapper.ExamInfoMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 *
 */
@Service
public class ExamInfoServiceImpl extends ServiceImpl<ExamInfoMapper, ExamInfo>
    implements ExamInfoService{

    @Resource
    private ExamInfoMapper examInfoMapper;

    @Override
    public Integer findMaxPaperId() {
        return examInfoMapper.findMaxPaperId();
    }
}




