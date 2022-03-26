package com.upc.eden.bank.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.upc.eden.commen.domain.bank.Type;
import com.upc.eden.bank.service.TypeService;
import com.upc.eden.bank.mapper.TypeMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type>
    implements TypeService{

}




