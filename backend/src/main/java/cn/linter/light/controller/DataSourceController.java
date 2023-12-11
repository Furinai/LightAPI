package cn.linter.light.controller;

import cn.linter.light.entity.DataSourceConfig;
import cn.linter.light.service.DataSourceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author wangxiaoyang
 */
@RestController
@RequestMapping("/api/sys/data-sources")
public class DataSourceController {

    private final DataSourceService dataSourceService;

    public DataSourceController(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @PostMapping
    public void getConnectDataSource(@RequestBody DataSourceConfig dataSourceConfig) throws SQLException {
        Connection connection = dataSourceService.getConnection(dataSourceConfig);
        if (connection != null) {
            connection.close();
        }
    }

}