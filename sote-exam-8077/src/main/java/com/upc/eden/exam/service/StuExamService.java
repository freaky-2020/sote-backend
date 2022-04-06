package com.upc.eden.exam.service;

import com.upc.eden.commen.domain.exam.StuExam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface StuExamService extends IService<StuExam> {

    public Integer findMaxDetail();

    public Integer findPresentTime(Integer ExamimeeId, Integer examId);
}
