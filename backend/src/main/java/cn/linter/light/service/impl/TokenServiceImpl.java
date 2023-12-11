package cn.linter.light.service.impl;

import cn.linter.light.exception.BusinessException;
import cn.linter.light.service.TokenService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author wangxiaoyang
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Value("${light.user.password}")
    private String password;

    @Value("${light.user.token.secret}")
    private String secret;

    @Value("${light.user.token.expiration-time}")
    private Long expirationTime;

    @Override
    public String getToken(String password) {
        if (Objects.equals(this.password, password)) {
            return generateToken();
        } else {
            log.debug("用户密码错误");
            throw new BusinessException("密码错误");
        }
    }

    private String generateToken() {
        JWTClaimsSet.Builder claimsSetBuilder = new JWTClaimsSet.Builder();
        long expirationMinute = System.currentTimeMillis() + expirationTime * 60000;
        claimsSetBuilder.expirationTime(new Date(expirationMinute));
        JWTClaimsSet jwtClaimsSet = claimsSetBuilder.build();
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        SignedJWT signedJwt = new SignedJWT(jwsHeader, jwtClaimsSet);
        try {
            signedJwt.sign(new MACSigner(secret));
        } catch (JOSEException e) {
            log.error("Token生成失败", e);
        }
        return signedJwt.serialize();
    }

}