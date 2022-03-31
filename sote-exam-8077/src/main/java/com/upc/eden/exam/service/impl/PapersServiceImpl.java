package com.upc.eden.exam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commen.domain.exam.Papers;
import com.upc.eden.exam.service.PapersService;
import com.upc.eden.exam.mapper.PapersMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 */
@Service
public class PapersServiceImpl extends ServiceImpl<PapersMapper, Papers>
    implements PapersService{

    @Resource
    private PapersMapper papersMapper;

    @Override
    public List<Integer> getQIdByPId(Integer paperId) {
        return papersMapper.getAllQIdByPId(paperId);
    }
}




