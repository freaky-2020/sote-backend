package com.upc.eden.auth.service.impl;
import cn.hutool.core.collection.CollUtil;
import com.upc.eden.auth.constant.RedisConstant;
import com.upc.eden.auth.mapper.AuthMapper;
import com.upc.eden.commen.domain.auth.Auth;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @Author: CS Dong
 * @Date: 2022/03/13/16:37
 * @Description: 资源与角色匹配关系管理业务类
 */
@Service
public class ResourceServiceImpl {

    private Map<String, List<String>> resourceRolesMap;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Resource
    private AuthMapper authMapper;

    @PostConstruct
    public void initData() {

        redisTemplate.delete("AUTH:RESOURCE_ROLES_MAP");

        resourceRolesMap = new TreeMap<>();
        List<Auth> allUrl = authMapper.selectList(null);
        allUrl.forEach(url -> {
            List<String> roles = CollUtil.toList(url.getAuthRole().split(","));
            resourceRolesMap.put(url.getUrl(), roles);
        });
        redisTemplate.opsForHash().putAll(RedisConstant.RESOURCE_ROLES_MAP, resourceRolesMap);
    }
}
