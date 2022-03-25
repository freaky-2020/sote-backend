package com.upc.eden.bank.controller;

import com.upc.eden.bank.service.DifficultyService;
import com.upc.eden.commen.domain.bank.Difficulty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/03/18/17:24
 * @Description:
 */

@RestController
@RequestMapping("/difficulty")
public class DifficultyController {

    @Resource
    DifficultyService difficultyService;

    @GetMapping
    public List<Difficulty> list() {

        List<Difficulty> difficultyList = difficultyService.list();
        return difficultyList;
    }
}
