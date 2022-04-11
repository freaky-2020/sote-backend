package com.upc.eden.exam.service;

import com.upc.eden.commen.domain.exam.StuExam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upc.eden.exam.api.findAllExamOfStuApi;

import java.util.List;

/**
 *
 */
public interface StuExamService extends IService<StuExam> {

    public Integer findMaxDetail();

    public Integer findPresentTime(Integer ExamimeeId, Integer examId);

    public List<findAllExamOfStuApi> findAllExamOfStu(Integer examineeId);
}
