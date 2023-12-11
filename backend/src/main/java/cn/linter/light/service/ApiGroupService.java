package cn.linter.light.service;

import cn.linter.light.entity.ApiGroup;

/**
 * @author wangxiaoyang
 */
public interface ApiGroupService {

    /**
     * 通过ID查询分组
     *
     * @param id ID
     * @return 分组
     */
    ApiGroup getById(Integer id);

    /**
     * 查询分组列表
     *
     * @return 分组列表
     */
    Iterable<ApiGroup> list();

    /**
     * 新增分组
     *
     * @param apiGroup 分组实例
     * @return 分组实例
     */
    ApiGroup create(ApiGroup apiGroup);

    /**
     * 更新分组
     *
     * @param apiGroup 分组实例
     * @return 分组实例
     */
    ApiGroup update(ApiGroup apiGroup);

    /**
     * 通过ID删除分组
     *
     * @param id ID
     */
    void deleteById(Integer id);

}