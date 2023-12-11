package cn.linter.light.controller;

import cn.linter.light.entity.ApiConfig;
import cn.linter.light.entity.ApiParamConfig;
import cn.linter.light.entity.ParamMap;
import cn.linter.light.exception.ApiNotEnableException;
import cn.linter.light.exception.ApiNotFoundException;
import cn.linter.light.exception.RequestMethodNotAllowedException;
import cn.linter.light.service.ApiConfigService;
import cn.linter.light.service.ApiService;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author wangxiaoyang
 */
@RestController
@RequestMapping("/api/**")
public class ApiController {

    private final HttpServletRequest request;
    private final ApiService apiService;
    private final ApiConfigService apiConfigService;

    public ApiController(HttpServletRequest request, ApiService apiService,
                         ApiConfigService apiConfigService) {
        this.request = request;
        this.apiService = apiService;
        this.apiConfigService = apiConfigService;
    }

    @GetMapping
    public Object handGetRequest(@RequestParam(required = false) Map<String, String> requestParam) {
        ApiConfig apiConfig = getApiConfig();
        ParamMap param = parseParam(requestParam, apiConfig);
        if (apiConfig.getListable()) {
            if (apiConfig.getPageable()) {
                return apiService.getPageList(apiConfig, param);
            }
            return apiService.getList(apiConfig, param);
        }
        return apiService.getOne(apiConfig, param);
    }

    @PostMapping
    public void handPostRequest(@RequestBody(required = false) ParamMap param) {
        ApiConfig apiConfig = getApiConfig();
        if (param == null) {
            param = new ParamMap();
        }
        param.setSqlStatement(apiConfig.getSqlStatement());
        apiService.insert(apiConfig, param);
    }

    @PutMapping
    public void handPutRequest(@RequestBody(required = false) ParamMap param) {
        ApiConfig apiConfig = getApiConfig();
        if (param == null) {
            param = new ParamMap();
        }
        param.setSqlStatement(apiConfig.getSqlStatement());
        apiService.update(apiConfig, param);
    }

    @DeleteMapping
    public void handDeleteRequest(@RequestParam(required = false) Map<String, String> requestParam) {
        ApiConfig apiConfig = getApiConfig();
        ParamMap param = parseParam(requestParam, apiConfig);
        apiService.delete(apiConfig, param);
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
            if (apiParamConfig.getListable()) {
                String[] arrayParamValue = StringUtils.commaDelimitedListToStringArray(paramValue);
                List<String> listParamValue = new ArrayList<>(Arrays.asList(arrayParamValue));
                param.put(paramKey, listParamValue);
            } else {
                param.put(paramKey, paramValue);
            }
        }
        param.setSqlStatement(apiConfig.getSqlStatement());
        return param;
    }

}