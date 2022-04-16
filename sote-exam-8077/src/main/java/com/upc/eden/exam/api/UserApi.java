package com.upc.eden.exam.api;

import com.upc.eden.commen.domain.auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: CS Dong
 * @Date: 2022/04/16/10:30
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApi {

    private User user;
    private Integer alreadyTime;
}
