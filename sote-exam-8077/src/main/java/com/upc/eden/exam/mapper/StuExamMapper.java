package com.upc.eden.exam.mapper;

import com.upc.eden.commen.domain.exam.StuExam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Entity com.upc.eden.commen.domain.exam.StuExam
 */
public interface StuExamMapper extends BaseMapper<StuExam> {

    @Select("select max(details) from stu_exam")
    Integer findMaxDetail();

    @Select("select max(present_time) from stu_exam " +
            "where examinee_id = #{examineeId} and exam_id = #{examId}")
    Integer findPresentTime(Integer examineeId, Integer examId);
}
