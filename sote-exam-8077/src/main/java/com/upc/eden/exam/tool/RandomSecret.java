package com.upc.eden.exam.tool;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * @Author: CS Dong
 * @Date: 2022/03/30/10:37
 * @Description:
 */
@Component
public class RandomSecret {

    public String getRandomSecret(int length) {

        String all = "@£$∑@£$ABCDEFGHIJKLMNOPQRSTUVWXYZ@£$∑@£$";
        StringBuffer secret = new StringBuffer();

        Random rd = new Random();
        while (secret.length()<length) {
            secret.append(all.charAt(rd.nextInt(all.length())));
        }
        return secret.toString();
    }
}
