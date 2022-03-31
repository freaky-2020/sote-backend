package com.upc.eden.exam.mapper;

import com.upc.eden.commen.domain.exam.Papers;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.upc.eden.commen.domain.exam.Papers
 */
public interface PapersMapper extends BaseMapper<Papers> {

    @Select("select question_id from papers where paper_id = #{paperId}")
    List<Integer> getAllQIdByPId(Integer paperId);

}




