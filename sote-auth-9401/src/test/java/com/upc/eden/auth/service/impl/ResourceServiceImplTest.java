package com.upc.eden.auth.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.upc.eden.auth.mapper.AuthMapper;
import com.upc.eden.commen.domain.auth.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author: CS Dong
 * @Date: 2022/03/16/10:22
 * @Description:
 */
@SpringBootTest
class ResourceServiceImplTest {

    private Map<String, List<String>> resourceRolesMap;

    @Resource
    private AuthMapper authMapper;

    @Test
    void test1() {

        resourceRolesMap = new TreeMap<>();
        List<Auth> allUrl = authMapper.selectList(null);
        allUrl.forEach(url -> {
//            Arrays.asList(url.getAuthRole().split(","));
            List<String> roles = CollUtil.toList("ADMIN");
            resourceRolesMap.put(url.getUrl(), roles);
        });
        resourceRolesMap.forEach((key, value) -> {
            System.out.println(key+": "+value);
        });
    }

}