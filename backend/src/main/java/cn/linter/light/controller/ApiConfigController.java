package cn.linter.light.controller;

import cn.linter.light.entity.ApiConfig;
import cn.linter.light.service.ApiConfigService;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangxiaoyang
 */
@RestController
@RequestMapping("/api/sys/api-configs")
public class ApiConfigController {

    private final ApiConfigService apiConfigService;

    public ApiConfigController(ApiConfigService apiConfigService) {
        this.apiConfigService = apiConfigService;
    }

    @GetMapping("/{id}")
    public ApiConfig getApiConfigById(@PathVariable("id") Integer id) {
        return apiConfigService.getById(id);
    }

    @GetMapping
    public Iterable<ApiConfig> listApiConfig() {
        return apiConfigService.list();
    }

    @PostMapping
    public ApiConfig createApiConfig(@RequestBody ApiConfig apiConfig) {
        return apiConfigService.create(apiConfig);
    }

    @PutMapping
    public ApiConfig updateApiConfig(@RequestBody ApiConfig apiConfig) {
        return apiConfigService.update(apiConfig);
    }

    @DeleteMapping("{id}")
    public void deleteApiConfigById(@PathVariable("id") Integer id) {
        ApiConfig apiConfig= apiConfigService.getById(id);
        apiConfigService.delete(apiConfig);
    }

}