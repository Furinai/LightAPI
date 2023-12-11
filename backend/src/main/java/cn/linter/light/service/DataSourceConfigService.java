package cn.linter.light.service;

import cn.linter.light.entity.DataSourceConfig;

import java.util.List;

/**
 * @author wangxiaoyang
 */
public interface DataSourceConfigService {

    /**
     * 通过ID查询数据源
     *
     * @param id ID
     * @return 数据源
     */
    DataSourceConfig getById(Integer id);

    /**
     * 查询数据源列表
     *
     * @return 数据源列表
     */
    Iterable<DataSourceConfig> list();

    /**
     * 新增数据源
     *
     * @param dataSourceConfig 数据源实例
     * @return 数据源实例
     */
    DataSourceConfig create(DataSourceConfig dataSourceConfig);

    /**
     * 更新数据源
     *
     * @param dataSourceConfig 数据源实例
     * @return 数据源实例
     */
    DataSourceConfig update(DataSourceConfig dataSourceConfig);

    /**
     * 通过ID删除数据源
     *
     * @param id ID
     */
    void deleteById(Integer id);

}