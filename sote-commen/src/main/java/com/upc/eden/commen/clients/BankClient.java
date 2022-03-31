package com.upc.eden.commen.clients;

import com.upc.eden.commen.domain.bank.Question;
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
@FeignClient(value = "bank-service")
public interface BankClient {

    @GetMapping("/client/getQuesByIds")
    List<Question> getQuesByIds(@RequestParam List<Integer> qIds);
}
