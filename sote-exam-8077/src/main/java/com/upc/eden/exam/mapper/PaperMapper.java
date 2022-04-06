package com.upc.eden.exam.mapper;

import com.upc.eden.commen.domain.exam.Paper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @Entity com.upc.eden.commen.domain.exam.Paper
 */
public interface PaperMapper extends BaseMapper<Paper> {

    @Select("select max(ques_no) from paper " +
            "where paper_id = #{paperId} and type_id = #{typeId}")
    Integer findMaxQuesNo(Integer paperId, Integer typeId);

    @Update("update paper set ques_no = ques_no-1 " +
            "where paper_id = #{paperId} and ques_no > #{quesNo}")
    Integer reviseQuesNo(Integer paperId, Integer quesNo);

    @Update("update paper set ques_no = ques_no+1 " +
            "where paper_id = #{paperId} and ques_no >= #{quesNo}")
    Integer reviseQuesNoAdd(Integer paperId, Integer quesNo);
}




