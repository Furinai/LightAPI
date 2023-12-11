package cn.linter.light.controller;

import cn.linter.light.constant.ParamType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaoyang
 */
@RestController
@RequestMapping("/api/sys/api-param-configs")
public class ApiParamConfigController {

    @GetMapping("/types")
    public ParamType[] listApiConfig() {
        return ParamType.values();
    }

}