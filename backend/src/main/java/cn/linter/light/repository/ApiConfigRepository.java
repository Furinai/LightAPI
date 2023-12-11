package cn.linter.light.repository;

import cn.linter.light.entity.ApiConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangxiaoyang
 */
@Repository
public interface ApiConfigRepository extends CrudRepository<ApiConfig, Integer> {

    /**
     * 通过请求路径查询API配置
     *
     * @param requestPath 请求路径
     * @param requestMethod 请求方法
     * @return API配置
     */
    ApiConfig findByRequestPathAndRequestMethod(String requestPath, HttpMethod requestMethod);

    /**
     * 通过分组ID查询API列表
     *
     * @param groupId 分组ID
     * @return API列表
     */
    List<ApiConfig> findByGroupId(Integer groupId);

    /**
     * 通过分组ID判断API是否存在
     *
     * @param groupId 分组ID
     * @return 是否存在
     */
    boolean existsByGroupId(Integer groupId);

    /**
     * 通过数据源配置ID判断API是否存在
     *
     * @param dataSourceConfigId 数据源配置ID
     * @return 是否存在
     */
    boolean existsByDataSourceConfigId(Integer dataSourceConfigId);

}