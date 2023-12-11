package cn.linter.light.service;

import cn.linter.light.entity.ApiConfig;
import cn.linter.light.entity.PageableResultList;
import cn.linter.light.entity.ParamMap;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author wangxiaoyang
 */
public interface ApiService {

    /**
     * 查询一条数据
     *
     * @param apiConfig API配置
     * @param param     SQL参数
     * @return 查询结果
     */
    LinkedHashMap<String, Object> getOne(ApiConfig apiConfig, ParamMap param);

    /**
     * 查询数据列表
     *
     * @param apiConfig API配置
     * @param param     SQL参数
     * @return 查询结果
     */
    List<LinkedHashMap<String, Object>> getList(ApiConfig apiConfig, ParamMap param);

    /**
     * 分页查询数据列表
     *
     * @param apiConfig API配置
     * @param param     SQL参数
     * @return 查询结果
     */
    PageableResultList getPageList(ApiConfig apiConfig, ParamMap param);

    /**
     * 新增数据
     *
     * @param apiConfig API配置
     * @param param     SQL参数
     */
    void insert(ApiConfig apiConfig, ParamMap param);

    /**
     * 更新数据
     *
     * @param apiConfig API配置
     * @param param     SQL参数
     */
    void update(ApiConfig apiConfig, ParamMap param);

    /**
     * 删除数据
     *
     * @param apiConfig API配置
     * @param param     SQL参数
     */
    void delete(ApiConfig apiConfig, ParamMap param);

}