package com.upc.eden.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.exam.service.PaperService;
import com.upc.eden.exam.mapper.PaperMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper>
    implements PaperService{

    @Resource
    private PaperMapper paperMapper;

    @Override
    public Integer getMaxQuesNoByType(Integer paperId, Integer typeId) {
        return paperMapper.findMaxQuesNo(paperId, typeId);
    }

    @Override
    public Integer reviseQuesNo(Integer paperId, Integer quesNo) {
        return paperMapper.reviseQuesNo(paperId, quesNo);
    }

    @Override
    public Integer reviseQuesNoAdd(Integer paperId, Integer quesNo) {
        return paperMapper.reviseQuesNoAdd(paperId, quesNo);
    }

    @Override
    public List<Integer> getAllPaperId() {
        return paperMapper.getAllPaperId();
    }
}




