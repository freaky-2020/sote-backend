package com.upc.eden.commen.clients;

import com.upc.eden.commen.config.FeignRequestConfig;
import com.upc.eden.commen.domain.bank.Question;
import io.swagger.models.auth.In;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: CS Dong
 * @Date: 2022/03/31/19:00
 * @Description:
 */
@FeignClient(value = "gateway-service", contextId = "bank", configuration = FeignRequestConfig.class)
public interface BankClient {

    @GetMapping("/bank/client/getQuesByIds")
    List<Question> getQuesByIds(@RequestParam List<Integer> qIds);

    @GetMapping("/bank/client/getQuesById")
    Question getQuesById(@RequestParam Integer qId);

    @GetMapping("/bank/client/getQuesByType")
    List<Question> getQuesByType(@RequestParam Integer typeId, @RequestParam Integer subjectId);
}
