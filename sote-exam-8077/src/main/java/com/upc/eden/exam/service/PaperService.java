package com.upc.eden.exam.service;

import com.upc.eden.commen.domain.exam.Paper;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 *
 */
public interface PaperService extends IService<Paper> {

    public Integer getMaxQuesNoByType(Integer paperId, Integer typeId);

    public Integer reviseQuesNo(Integer paperId, Integer quesNo);

    public Integer reviseQuesNoAdd(Integer paperId, Integer quesNo);

    public List<Integer> getAllPaperId();
}
