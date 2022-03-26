package com.upc.eden.bank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commen.domain.bank.Question;
import com.upc.eden.bank.service.QuestionService;
import com.upc.eden.bank.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




