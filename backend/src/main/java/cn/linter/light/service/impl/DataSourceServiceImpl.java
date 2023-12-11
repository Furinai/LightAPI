package cn.linter.light.service.impl;

import cn.linter.light.entity.DataSourceConfig;
import cn.linter.light.exception.BusinessException;
import cn.linter.light.mapper.ApiMapper;
import cn.linter.light.service.DataSourceConfigService;
import cn.linter.light.service.DataSourceService;
import cn.linter.light.wrapper.ResultMapWrapperFactory;
import com.github.pagehelper.PageInterceptor;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author wangxiaoyang
 */
@Slf4j
@Service
public class DataSourceServiceImpl implements DataSourceService {

    private final DataSourceConfigService dataSourceConfigService;

    public DataSourceServiceImpl(DataSourceConfigService dataSourceConfigService) {
        this.dataSourceConfigService = dataSourceConfigService;
    }

    @Override
    public Connection getConnection(DataSourceConfig dataSourceConfig) {
        String url = dataSourceConfig.getUrl();
        String username = dataSourceConfig.getUsername();
        String password = dataSourceConfig.getPassword();
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new BusinessException(e.getMessage());
        }
        return connection;
    }

    @Cacheable(cacheNames = "api_mapper", key = "#dataSourceConfigId")
    @Override
    public ApiMapper getApiMapper(Integer dataSourceConfigId) {
        DataSourceConfig dataSourceConfig = dataSourceConfigService.getById(dataSourceConfigId);
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(dataSourceConfig.getUrl());
        hikariDataSource.setUsername(dataSourceConfig.getUsername());
        hikariDataSource.setPassword(dataSourceConfig.getPassword());
        Configuration configuration = new Configuration();
        configuration.addMapper(ApiMapper.class);
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.addInterceptor(new PageInterceptor());
        configuration.setObjectWrapperFactory(new ResultMapWrapperFactory());
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(hikariDataSource);
        sqlSessionFactoryBean.setConfiguration(configuration);
        SqlSessionFactory sqlSessionFactory;
        try {
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            log.error("获取SqlSessionFactory失败", e);
            throw new BusinessException("获取SqlSessionFactory失败", e);
        }
        MapperFactoryBean<ApiMapper> mapperFactoryBean = new MapperFactoryBean<>();
        mapperFactoryBean.setMapperInterface(ApiMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactory);
        ApiMapper apiMapper;
        try {
            apiMapper = mapperFactoryBean.getObject();
        } catch (Exception e) {
            log.error("获取MapperFactoryBean失败", e);
            throw new BusinessException("获取MapperFactoryBean失败", e);
        }
        return apiMapper;
    }

}