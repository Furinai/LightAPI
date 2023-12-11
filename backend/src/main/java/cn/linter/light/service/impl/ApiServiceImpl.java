package cn.linter.light.service.impl;

import cn.linter.light.entity.ApiConfig;
import cn.linter.light.entity.PageableResultList;
import cn.linter.light.entity.ParamMap;
import cn.linter.light.mapper.ApiMapper;
import cn.linter.light.service.ApiService;
import cn.linter.light.service.DataSourceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author wangxiaoyang
 */
@Service
public class ApiServiceImpl implements ApiService {

    private final DataSourceService dataSourceService;

    public ApiServiceImpl(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    @Override
    public LinkedHashMap<String, Object> getOne(ApiConfig apiConfig, ParamMap param) {
        ApiMapper apiMapper = dataSourceService.getApiMapper(apiConfig.getDataSourceConfigId());
        return apiMapper.selectOne(param);
    }

    @Override
    public List<LinkedHashMap<String, Object>> getList(ApiConfig apiConfig, ParamMap param) {
        ApiMapper apiMapper = dataSourceService.getApiMapper(apiConfig.getDataSourceConfigId());
        return apiMapper.selectList(param);
    }

    @Override
    public PageableResultList getPageList(ApiConfig apiConfig, ParamMap param) {
        ApiMapper apiMapper = dataSourceService.getApiMapper(apiConfig.getDataSourceConfigId());
        PageHelper.startPage((int) param.get("pageNumber"), (int) param.get("pageSize"));
        List<LinkedHashMap<String, Object>> result = apiMapper.selectList(param);
        long total = PageInfo.of(result).getTotal();
        return PageableResultList.builder().list(result).total(total).build();
    }

    @Override
    public void insert(ApiConfig apiConfig, ParamMap param) {
        ApiMapper apiMapper = dataSourceService.getApiMapper(apiConfig.getDataSourceConfigId());
        apiMapper.insert(param);
    }

    @Override
    public void update(ApiConfig apiConfig, ParamMap param) {
        ApiMapper apiMapper = dataSourceService.getApiMapper(apiConfig.getDataSourceConfigId());
        apiMapper.update(param);
    }

    @Override
    public void delete(ApiConfig apiConfig, ParamMap param) {
        ApiMapper apiMapper = dataSourceService.getApiMapper(apiConfig.getDataSourceConfigId());
        apiMapper.delete(param);
    }

}