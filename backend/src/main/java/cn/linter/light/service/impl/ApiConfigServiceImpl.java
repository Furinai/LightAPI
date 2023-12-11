package cn.linter.light.service.impl;

import cn.linter.light.entity.ApiConfig;
import cn.linter.light.exception.BusinessException;
import cn.linter.light.repository.ApiConfigRepository;
import cn.linter.light.service.ApiConfigService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<ApiConfig> listByGroupId(Integer groupId) {
        return apiConfigRepository.findByGroupId(groupId);
    }

    @Override
    public Iterable<ApiConfig> list() {
        return apiConfigRepository.findAll();
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