package cn.linter.light.controller;

import cn.linter.light.entity.ApiConfig;
import cn.linter.light.entity.PageableResultList;
import cn.linter.light.service.ApiConfigService;
import org.springframework.data.domain.PageRequest;
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
    public PageableResultList<ApiConfig> listApiConfig(@RequestParam(required = false) Integer groupId,
                                                       @RequestParam(required = false) Integer dataSourceConfigId,
                                                       @RequestParam(required = false, defaultValue = "0") Integer page,
                                                       @RequestParam(required = false, defaultValue = "10") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        if (groupId != null && dataSourceConfigId != null) {
            return apiConfigService.listByGroupIdAndDataSourceConfigId(groupId, dataSourceConfigId, pageRequest);
        }
        if (groupId != null) {
            return apiConfigService.listByGroupId(groupId, pageRequest);
        }
        if (dataSourceConfigId != null) {
            return apiConfigService.listByDataSourceConfigId(dataSourceConfigId, pageRequest);
        }
        return apiConfigService.list(pageRequest);
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
        ApiConfig apiConfig = apiConfigService.getById(id);
        apiConfigService.delete(apiConfig);
    }

}