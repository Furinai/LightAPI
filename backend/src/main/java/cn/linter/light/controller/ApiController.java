package cn.linter.light.controller;

import cn.linter.light.entity.ApiConfig;
import cn.linter.light.entity.ApiParamConfig;
import cn.linter.light.entity.ParamMap;
import cn.linter.light.exception.ApiNotEnableException;
import cn.linter.light.exception.ApiNotFoundException;
import cn.linter.light.exception.RequestMethodNotAllowedException;
import cn.linter.light.service.ApiConfigService;
import cn.linter.light.service.ApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author wangxiaoyang
 */
@Slf4j
@RestController
@RequestMapping("/api/**")
public class ApiController {

    private final HttpServletRequest request;
    private final ApiService apiService;
    private final ApiConfigService apiConfigService;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper jsonMapper;

    public ApiController(HttpServletRequest request, ApiService apiService, ApiConfigService apiConfigService,
                         StringRedisTemplate redisTemplate, ObjectMapper jsonMapper) {
        this.request = request;
        this.apiService = apiService;
        this.apiConfigService = apiConfigService;
        this.redisTemplate = redisTemplate;
        this.jsonMapper = jsonMapper;
    }

    @GetMapping
    public Object handGetRequest(@RequestParam(required = false) Map<String, String> requestParam) {
        ApiConfig apiConfig = getApiConfig();
        ParamMap param = parseParam(requestParam, apiConfig);
        boolean cacheable = apiConfig.getCacheable() != null && apiConfig.getCacheable();
        String cacheKey = null;
        if (cacheable) {
            StringBuilder keyBuilder = new StringBuilder();
            keyBuilder.append(apiConfig.getDataSourceConfigId()).append("::");
            keyBuilder.append(apiConfig.getId()).append("::");
            if (!param.isEmpty()) {
                param.forEach((k, v) -> keyBuilder.append(k).append('=').append(v).append('&'));
                keyBuilder.deleteCharAt(keyBuilder.length() - 1);
            }
            cacheKey = keyBuilder.toString();
            String cacheString = redisTemplate.opsForValue().get(cacheKey);
            if (cacheString != null) {
                try {
                    return jsonMapper.readValue(cacheString, Object.class);
                } catch (JsonProcessingException e) {
                    log.error("结果缓存反序列化失败", e);
                }
            }
        }
        Object result;
        if (apiConfig.getListable()) {
            if (apiConfig.getPageable()) {
                result = apiService.getPageList(apiConfig, param);
            } else {
                result = apiService.getList(apiConfig, param);
            }
        } else {
            result = apiService.getOne(apiConfig, param);
        }
        if (cacheable) {
            try {
                String valueString = jsonMapper.writeValueAsString(result);
                redisTemplate.opsForValue().set(cacheKey, valueString);
            } catch (JsonProcessingException e) {
                log.error("结果缓存序列化失败", e);
            }
        }
        return result;
    }

    @PostMapping
    public void handPostRequest(@RequestBody(required = false) ParamMap param) {
        ApiConfig apiConfig = getApiConfig();
        if (param == null) {
            param = new ParamMap();
        }
        param.setSqlStatement(apiConfig.getStatement());
        apiService.insert(apiConfig, param);
        clearCache(apiConfig);
    }

    @PutMapping
    public void handPutRequest(@RequestBody(required = false) ParamMap param) {
        ApiConfig apiConfig = getApiConfig();
        if (param == null) {
            param = new ParamMap();
        }
        param.setSqlStatement(apiConfig.getStatement());
        apiService.update(apiConfig, param);
        clearCache(apiConfig);
    }

    @DeleteMapping
    public void handDeleteRequest(@RequestParam(required = false) Map<String, String> requestParam) {
        ApiConfig apiConfig = getApiConfig();
        ParamMap param = parseParam(requestParam, apiConfig);
        apiService.delete(apiConfig, param);
        clearCache(apiConfig);
    }

    private ApiConfig getApiConfig() {
        String requestPath = request.getServletPath().substring(4);
        HttpMethod requestMethod = HttpMethod.resolve(request.getMethod());
        ApiConfig apiConfig = apiConfigService.getByRequestPathAndRequestMethod(requestPath, requestMethod);
        if (apiConfig == null) {
            throw new ApiNotFoundException();
        }
        if (requestMethod != apiConfig.getRequestMethod()) {
            throw new RequestMethodNotAllowedException();
        }
        if (!apiConfig.getEnable()) {
            throw new ApiNotEnableException();
        }
        return apiConfig;
    }

    private ParamMap parseParam(Map<String, String> requestParam, ApiConfig apiConfig) {
        ParamMap param = new ParamMap();
        List<ApiParamConfig> paramConfigList = apiConfig.getParamConfigList();
        for (ApiParamConfig apiParamConfig : paramConfigList) {
            String paramKey = apiParamConfig.getKey();
            String paramValue = requestParam.get(paramKey);
            if (paramValue == null || paramValue.trim().length() == 0) {
                continue;
            }
            if (apiParamConfig.getListable()) {
                String[] arrayParamValue = StringUtils.commaDelimitedListToStringArray(paramValue);
                List<String> listParamValue = new ArrayList<>(Arrays.asList(arrayParamValue));
                param.put(paramKey, listParamValue);
            } else {
                param.put(paramKey, paramValue);
            }
        }
        if (apiConfig.getPageable()) {
            String pageNumber = requestParam.get("pageNumber");
            param.put("pageNumber", StringUtils.hasLength(pageNumber) ? Integer.parseInt(pageNumber) : 1);
            String pageSize = requestParam.get("pageSize");
            param.put("pageSize", StringUtils.hasLength(pageSize) ? Integer.parseInt(pageSize) : 10);
        }
        param.setSqlStatement(apiConfig.getStatement());
        return param;
    }

    private void clearCache(ApiConfig apiConfig) {
        String keyPrefix = apiConfig.getDataSourceConfigId() + "::*";
        Set<String> keys = redisTemplate.keys(keyPrefix);
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }

}