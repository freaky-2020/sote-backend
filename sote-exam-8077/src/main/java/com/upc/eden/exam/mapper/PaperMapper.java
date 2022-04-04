package com.upc.eden.exam.mapper;

import com.upc.eden.commen.domain.exam.Paper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Entity com.upc.eden.commen.domain.exam.Paper
 */
public interface PaperMapper extends BaseMapper<Paper> {

    @Select("select max(ques_no) from paper where paper_id=#{paperId}")
    public Integer findMaxQuesNo(Integer paperId);
}




