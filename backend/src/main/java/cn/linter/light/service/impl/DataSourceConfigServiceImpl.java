package cn.linter.light.service.impl;

import cn.linter.light.entity.DataSourceConfig;
import cn.linter.light.exception.BusinessException;
import cn.linter.light.repository.ApiConfigRepository;
import cn.linter.light.repository.DataSourceConfigRepository;
import cn.linter.light.service.DataSourceConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * @author wangxiaoyang
 */
@Slf4j
@Service
public class DataSourceConfigServiceImpl implements DataSourceConfigService {

    private final DataSourceConfigRepository dataSourceConfigRepository;
    private final ApiConfigRepository apiConfigRepository;

    public DataSourceConfigServiceImpl(DataSourceConfigRepository dataSourceConfigRepository, ApiConfigRepository apiConfigRepository) {
        this.dataSourceConfigRepository = dataSourceConfigRepository;
        this.apiConfigRepository = apiConfigRepository;
    }

    @Override
    public DataSourceConfig getById(Integer id) {
        return dataSourceConfigRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<DataSourceConfig> list() {
        return dataSourceConfigRepository.findAll();
    }

    @Override
    public DataSourceConfig create(DataSourceConfig dataSourceConfig) {
        return dataSourceConfigRepository.save(dataSourceConfig);
    }

    @CacheEvict(cacheNames = "api_mapper", key = "#dataSourceConfig.id")
    @Override
    public DataSourceConfig update(DataSourceConfig dataSourceConfig) {
        return dataSourceConfigRepository.save(dataSourceConfig);
    }

    @CacheEvict(cacheNames = "api_mapper", key = "#id")
    @Override
    public void deleteById(Integer id) {
        if (apiConfigRepository.existsByDataSourceConfigId(id)) {
            throw new BusinessException("数据源正在被使用，无法删除！");
        }
        dataSourceConfigRepository.deleteById(id);
    }

}