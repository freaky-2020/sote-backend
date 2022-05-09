package com.upc.eden.exam.service;

import com.upc.eden.commen.domain.exam.ExamInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upc.eden.exam.api.ExamResultsApi;

import java.util.List;

/**
 *
 */
public interface ExamInfoService extends IService<ExamInfo> {

    public Integer findMaxPaperId();

    public List<ExamResultsApi> getResultsForTeacher(Integer examId);

}
