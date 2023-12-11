package cn.linter.light.service;

import cn.linter.light.entity.ApiConfig;
import cn.linter.light.entity.PageableResultList;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;

/**
 * @author wangxiaoyang
 */
public interface ApiConfigService {

    /**
     * 通过ID查询API配置
     *
     * @param id ID
     * @return API配置
     */
    ApiConfig getById(Integer id);

    /**
     * 通过请求路径和请求方法查询API配置
     *
     * @param requestPath   请求路径
     * @param requestMethod 请求方法
     * @return API配置
     */
    ApiConfig getByRequestPathAndRequestMethod(String requestPath, HttpMethod requestMethod);

    /**
     * 查询API配置列表
     *
     * @param pageable 分页参数
     * @return API配置列表
     */
    PageableResultList<ApiConfig> list(Pageable pageable);

    /**
     * 通过分组ID查询API配置列表
     *
     * @param groupId  分组ID
     * @param pageable 分页参数
     * @return API配置列表
     */
    PageableResultList<ApiConfig> listByGroupId(Integer groupId, Pageable pageable);

    /**
     * 通过数据源ID查询API配置列表
     *
     * @param dataSourceConfigId 数据源ID
     * @param pageable           分页参数
     * @return API配置列表
     */
    PageableResultList<ApiConfig> listByDataSourceConfigId(Integer dataSourceConfigId, Pageable pageable);

    /**
     * 通过分组ID查询API配置列表
     *
     * @param groupId            分组ID
     * @param dataSourceConfigId 数据源ID
     * @param pageable           分页参数
     * @return API配置列表
     */
    PageableResultList<ApiConfig> listByGroupIdAndDataSourceConfigId(Integer groupId, Integer dataSourceConfigId, Pageable pageable);

    /**
     * 新增API配置
     *
     * @param apiConfig API配置实例
     * @return API配置实例
     */
    ApiConfig create(ApiConfig apiConfig);

    /**
     * 更新API配置
     *
     * @param apiConfig API配置实例
     * @return API配置实例
     */
    ApiConfig update(ApiConfig apiConfig);

    /**
     * 删除API配置
     *
     * @param apiConfig API配置实例
     */
    void delete(ApiConfig apiConfig);

}