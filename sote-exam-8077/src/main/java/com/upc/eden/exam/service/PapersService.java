package com.upc.eden.exam.service;

import com.upc.eden.commen.domain.exam.Papers;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface PapersService extends IService<Papers> {

    public List<Integer> getQIdByPId(Integer paperId);
}
