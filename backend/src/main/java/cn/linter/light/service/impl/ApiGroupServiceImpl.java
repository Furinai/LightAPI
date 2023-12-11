package cn.linter.light.service.impl;

import cn.linter.light.entity.ApiGroup;
import cn.linter.light.exception.BusinessException;
import cn.linter.light.repository.ApiConfigRepository;
import cn.linter.light.repository.ApiGroupRepository;
import cn.linter.light.service.ApiGroupService;
import org.springframework.stereotype.Service;

/**
 * @author wangxiaoyang
 */
@Service
public class ApiGroupServiceImpl implements ApiGroupService {

    private final ApiGroupRepository apiGroupRepository;
    private final ApiConfigRepository apiConfigRepository;

    public ApiGroupServiceImpl(ApiGroupRepository apiGroupRepository,
                               ApiConfigRepository apiConfigRepository) {
        this.apiGroupRepository = apiGroupRepository;
        this.apiConfigRepository = apiConfigRepository;
    }

    @Override
    public ApiGroup getById(Integer id) {
        return apiGroupRepository.findById(id).orElse(null);
    }

    @Override
    public Iterable<ApiGroup> list() {
        return apiGroupRepository.findAll();
    }

    @Override
    public ApiGroup create(ApiGroup apiGroup) {
        return apiGroupRepository.save(apiGroup);
    }

    @Override
    public ApiGroup update(ApiGroup apiGroup) {
        return apiGroupRepository.save(apiGroup);
    }

    @Override
    public void deleteById(Integer id) {
        if (apiConfigRepository.existsByGroupId(id)) {
            throw new BusinessException("分组正在被使用，无法删除！");
        }
        apiGroupRepository.deleteById(id);
    }

}