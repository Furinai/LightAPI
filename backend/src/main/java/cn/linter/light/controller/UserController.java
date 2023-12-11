package cn.linter.light.controller;

import cn.linter.light.service.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaoyang
 */
@RestController
@RequestMapping("/api/sys/user")
public class UserController {

    private final TokenService tokenService;

    public UserController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @GetMapping("/token")
    public String getToken(String password) {
        return tokenService.getToken(password);
    }

}