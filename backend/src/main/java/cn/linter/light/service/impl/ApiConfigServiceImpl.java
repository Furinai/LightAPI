package cn.linter.light.service.impl;

import cn.linter.light.entity.ApiConfig;
import cn.linter.light.entity.PageableResultList;
import cn.linter.light.exception.BusinessException;
import cn.linter.light.repository.ApiConfigRepository;
import cn.linter.light.service.ApiConfigService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * @author wangxiaoyang
 */
@Service
public class ApiConfigServiceImpl implements ApiConfigService {

    private static final char SEPARATOR = '/';

    private final ApiConfigRepository apiConfigRepository;

    public ApiConfigServiceImpl(ApiConfigRepository apiConfigRepository) {
        this.apiConfigRepository = apiConfigRepository;
    }

    @Override
    public ApiConfig getById(Integer id) {
        return apiConfigRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "api_config", key = "#requestPath + '.' + #requestMethod.name()")
    @Override
    public ApiConfig getByRequestPathAndRequestMethod(String requestPath, HttpMethod requestMethod) {
        return apiConfigRepository.findByRequestPathAndRequestMethod(requestPath, requestMethod);
    }

    @Override
    public PageableResultList<ApiConfig> listByGroupId(Integer groupId, Pageable pageable) {
        Page<ApiConfig> result = apiConfigRepository.findByGroupId(groupId, pageable);
        return PageableResultList.<ApiConfig>builder().list(result.toList()).total(result.getTotalElements()).build();
    }

    @Override
    public PageableResultList<ApiConfig> listByDataSourceConfigId(Integer dataSourceConfigId, Pageable pageable) {
        Page<ApiConfig> result = apiConfigRepository.findByDataSourceConfigId(dataSourceConfigId, pageable);
        return PageableResultList.<ApiConfig>builder().list(result.toList()).total(result.getTotalElements()).build();
    }

    @Override
    public PageableResultList<ApiConfig> listByGroupIdAndDataSourceConfigId(Integer groupId, Integer dataSourceConfigId, Pageable pageable) {
        Page<ApiConfig> result = apiConfigRepository.findByGroupIdAndDataSourceConfigId(groupId, dataSourceConfigId, pageable);
        return PageableResultList.<ApiConfig>builder().list(result.toList()).total(result.getTotalElements()).build();
    }

    @Override
    public PageableResultList<ApiConfig> list(Pageable pageable) {
        Page<ApiConfig> result = apiConfigRepository.findAll(pageable);
        return PageableResultList.<ApiConfig>builder().list(result.toList()).total(result.getTotalElements()).build();
    }

    @Override
    public ApiConfig create(ApiConfig apiConfig) {
        String requestPath = apiConfig.getRequestPath();
        if (requestPath.charAt(0) != SEPARATOR) {
            requestPath = "/" + requestPath;
            apiConfig.setRequestPath(requestPath);
        }
        HttpMethod requestMethod = apiConfig.getRequestMethod();
        ApiConfig existApiConfig = apiConfigRepository.findByRequestPathAndRequestMethod(requestPath, requestMethod);
        if (existApiConfig != null) {
            throw new BusinessException("请求路径已被使用");
        }
        return apiConfigRepository.save(apiConfig);
    }

    @CacheEvict(cacheNames = "api_config", key = "#apiConfig.requestPath + '.' + #apiConfig.requestMethod.name()")
    @Override
    public ApiConfig update(ApiConfig apiConfig) {
        String requestPath = apiConfig.getRequestPath();
        HttpMethod requestMethod = apiConfig.getRequestMethod();
        ApiConfig existApiConfig = apiConfigRepository.findByRequestPathAndRequestMethod(requestPath, requestMethod);
        if (existApiConfig != null && !existApiConfig.getId().equals(apiConfig.getId())) {
            throw new BusinessException("请求路径已被使用");
        }
        return apiConfigRepository.save(apiConfig);
    }

    @CacheEvict(cacheNames = "api_config", key = "#apiConfig.requestPath + '.' + #apiConfig.requestMethod.name()")
    @Override
    public void delete(ApiConfig apiConfig) {
        apiConfigRepository.deleteById(apiConfig.getId());
    }

}