package com.upc.eden.exam.mapper;

import com.upc.eden.commen.domain.exam.ExamInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Entity com.upc.eden.commen.domain.exam.ExamInfo
 */
public interface ExamInfoMapper extends BaseMapper<ExamInfo> {

    @Select("select max(paper_id) from exam_info")
    public Integer findMaxPaperId();
}




