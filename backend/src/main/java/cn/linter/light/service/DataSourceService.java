package cn.linter.light.service;

import cn.linter.light.entity.DataSourceConfig;
import cn.linter.light.mapper.ApiMapper;

import java.sql.Connection;

/**
 * @author wangxiaoyang
 */
public interface DataSourceService {

    /**
     * 获取ApiMapper
     *
     * @param dataSourceConfigId 数据源配置ID
     * @return ApiMapper
     */
    ApiMapper getApiMapper(Integer dataSourceConfigId);

    /**
     * 获取Connection
     *
     * @param dataSourceConfig 数据源配置
     * @return Connection
     */
    Connection getConnection(DataSourceConfig dataSourceConfig);

}