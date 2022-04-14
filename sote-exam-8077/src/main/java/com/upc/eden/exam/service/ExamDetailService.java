package com.upc.eden.exam.service;

import com.upc.eden.commen.domain.exam.ExamDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface ExamDetailService extends IService<ExamDetail> {

    public void autoMark1(Integer datails);

    List<Integer> findNonMarkPersonNum(Integer paperId);
}
