package com.upc.eden.exam.controller;

import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.exam.service.ExamDetailService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: CS Dong
 * @Date: 2022/04/11/12:02
 * @Description:
 */

@RestController
@RequestMapping("detail")
public class ExamDetailsController {

    @Resource
    private ExamDetailService examDetailService;

    
}
