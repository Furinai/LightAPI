package cn.linter.light.controller;

import cn.linter.light.entity.DataSourceConfig;
import cn.linter.light.service.DataSourceConfigService;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangxiaoyang
 */
@RestController
@RequestMapping("/api/sys/data-source-configs")
public class DataSourceConfigController {

    private final DataSourceConfigService dataSourceConfigService;

    public DataSourceConfigController(DataSourceConfigService dataSourceConfigService) {
        this.dataSourceConfigService = dataSourceConfigService;
    }

    @GetMapping("{id}")
    public DataSourceConfig getDataSourceConfigById(@PathVariable("id") Integer id) {
        return dataSourceConfigService.getById(id);
    }

    @GetMapping
    public Iterable<DataSourceConfig> listDataSourceConfig() {
        return dataSourceConfigService.list();
    }

    @PostMapping
    public DataSourceConfig createDataSourceConfig(@RequestBody DataSourceConfig dataSourceConfig) {
        return dataSourceConfigService.create(dataSourceConfig);
    }

    @PutMapping
    public DataSourceConfig updateDataSourceConfig(@RequestBody DataSourceConfig dataSourceConfig) {
        return dataSourceConfigService.update(dataSourceConfig);
    }

    @DeleteMapping("{id}")
    public void deleteDataSourceConfigById(@PathVariable("id") Integer id) {
        dataSourceConfigService.deleteById(id);
    }

}