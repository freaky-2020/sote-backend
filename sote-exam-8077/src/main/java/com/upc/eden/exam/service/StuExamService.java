package com.upc.eden.exam.service;

import com.upc.eden.commen.domain.exam.StuExam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.upc.eden.exam.api.FindAllExamOfStuApi;
import io.swagger.models.auth.In;

import java.util.List;

/**
 *
 */
public interface StuExamService extends IService<StuExam> {

    public Integer findMaxDetail();

    public Integer findPresentTime(String ExamimeeId, Integer examId);

    public List<FindAllExamOfStuApi> findAllExamOfStu(String examineeId);

    public Integer findFinishedTime(String examineeId, Integer examId);

    public Integer findExamineeCountByExamId(Integer examId);

    public Integer findPaperCountByExamId(Integer examId);
}
