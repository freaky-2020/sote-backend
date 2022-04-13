package com.upc.eden.exam.api;

import com.upc.eden.commen.domain.auth.User;
import com.upc.eden.commen.domain.exam.ExamDetail;
import com.upc.eden.commen.domain.exam.Paper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: CS Dong
 * @Date: 2022/04/13/12:03
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAndDetailApi {

    private User user;
    private ExamDetail examDetail;
}
