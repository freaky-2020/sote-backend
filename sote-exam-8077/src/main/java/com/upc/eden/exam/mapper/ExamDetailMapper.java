package com.upc.eden.exam.mapper;

import com.upc.eden.commen.domain.exam.ExamDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.upc.eden.commen.domain.exam.ExamDetail
 */
public interface ExamDetailMapper extends BaseMapper<ExamDetail> {

    @Select("update exam_detail set score = if(answer = real_answer, max_score, 0), is_mark = 1 " +
            "where details = #{details} and (type_id = 1 or type_id = 3)")
    void autoMark1(Integer details);

    @Select("select details from exam_detail where paper_id = #{paperId} " +
            "and is_mark = 0 group by details")
    public List<Integer> findNonMarkPersonNum(Integer paperId);
}




