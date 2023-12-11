package cn.linter.light.repository;

import cn.linter.light.entity.ApiGroup;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wangxiaoyang
 */
@Repository
public interface ApiGroupRepository extends CrudRepository<ApiGroup, Integer> {
}