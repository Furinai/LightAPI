package cn.linter.light.repository;

import cn.linter.light.entity.DataSourceConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangxiaoyang
 */
@Repository
public interface DataSourceConfigRepository extends CrudRepository<DataSourceConfig, Integer> {
}