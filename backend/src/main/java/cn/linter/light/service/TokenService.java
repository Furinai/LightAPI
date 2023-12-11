package cn.linter.light.service;

/**
 * @author wangxiaoyang
 */
public interface TokenService {

    /**
     * 获取Token
     *
     * @param password 密码
     * @return token
     */
    String getToken(String password);

}