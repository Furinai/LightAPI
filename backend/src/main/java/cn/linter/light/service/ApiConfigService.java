package cn.linter.light.service;

import cn.linter.light.entity.ApiConfig;
import org.springframework.http.HttpMethod;

import java.util.List;

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
     * @return API配置列表
     */
    Iterable<ApiConfig> list();

    /**
     * 通过分组ID查询API配置列表
     *
     * @param groupId 分组ID
     * @return API配置列表
     */
    List<ApiConfig> listByGroupId(Integer groupId);

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