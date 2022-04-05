package com.upc.eden.exam.service;

import com.upc.eden.commen.domain.exam.Paper;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface PaperService extends IService<Paper> {

    public Integer getMaxQuesNo(Integer paperId);

    public Integer reviseQuesNo(Integer paperId, Integer quesNo);
}
