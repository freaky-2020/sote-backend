package com.upc.eden.auth.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @Author: CS Dong
 * @Date: 2022/03/13/17:18
 * @Description: 获取RSA公钥接口
 */
@RestController
@RequestMapping("/rsa")
public class KeyPairController {

    @Resource
    private KeyPair keyPair;

    /**
     * 获取公钥
     * /publicKey
     * @return json格式公钥
     */
    @GetMapping("/publicKey")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}
