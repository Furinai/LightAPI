package cn.linter.light.controller;

import cn.linter.light.entity.ApiGroup;
import cn.linter.light.service.ApiConfigService;
import cn.linter.light.service.ApiGroupService;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangxiaoyang
 */
@RestController
@RequestMapping("/api/sys/api-groups")
public class ApiGroupController {

    private final ApiGroupService apiGroupService;
    private final ApiConfigService apiConfigService;

    public ApiGroupController(ApiGroupService apiGroupService, ApiConfigService apiConfigService) {
        this.apiGroupService = apiGroupService;
        this.apiConfigService = apiConfigService;
    }

    @GetMapping("{id}")
    public ApiGroup getApiGroupById(@PathVariable("id") Integer id) {
        return apiGroupService.getById(id);
    }

    @GetMapping
    public Iterable<ApiGroup> listApiGroup() {
        return apiGroupService.list();
    }

    @PostMapping
    public ApiGroup createApiGroup(@RequestBody ApiGroup apiGroup) {
        return apiGroupService.create(apiGroup);
    }

    @PutMapping
    public ApiGroup updateApiGroup(@RequestBody ApiGroup apiGroup) {
        return apiGroupService.update(apiGroup);
    }

    @DeleteMapping("{id}")
    public void deleteApiGroupById(@PathVariable("id") Integer id) {
        apiGroupService.deleteById(id);
    }

}