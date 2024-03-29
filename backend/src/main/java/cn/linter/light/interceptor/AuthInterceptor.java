package cn.linter.light.interceptor;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;

/**
 * @author wangxiaoyang
 */
@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Value("${light.user.token.secret}")
    private String secret;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object object) {
        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token) || !verifyToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private boolean verifyToken(String token) {
        try {
            SignedJWT signedJwt = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(secret);
            Date expirationTime = signedJwt.getJWTClaimsSet().getExpirationTime();
            return signedJwt.verify(verifier) && new Date().before(expirationTime);
        } catch (ParseException e) {
            log.error("Token解析失败，Token：{}", token, e);
            return false;
        } catch (JOSEException e) {
            log.warn("Token验证失败，Token：{}", token, e);
            return false;
        }
    }

}