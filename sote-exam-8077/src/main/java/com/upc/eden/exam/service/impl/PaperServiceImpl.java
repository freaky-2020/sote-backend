package com.upc.eden.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.exam.service.PaperService;
import com.upc.eden.exam.mapper.PaperMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper>
    implements PaperService{

    @Resource
    private PaperMapper paperMapper;

    @Override
    public Integer getMaxQuesNo(Integer paperId) {
        return paperMapper.findMaxQuesNo(paperId);
    }
}




