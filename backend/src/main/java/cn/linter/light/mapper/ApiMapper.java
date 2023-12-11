package cn.linter.light.mapper;

import cn.linter.light.entity.ParamMap;
import cn.linter.light.provider.SqlProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author wangxiaoyang
 **/
public interface ApiMapper {

    /**
     * 查询单条数据
     *
     * @param param 参数
     * @return 单个结果
     */
    @SelectProvider(type = SqlProvider.class)
    LinkedHashMap<String, Object> selectOne(ParamMap param);

    /**
     * 查询多条数据
     *
     * @param param 参数
     * @return 多个结果
     */
    @SelectProvider(type = SqlProvider.class)
    List<LinkedHashMap<String, Object>> selectList(ParamMap param);

    /**
     * 插入数据
     *
     * @param params 参数
     * @return 影响行数
     */
    @InsertProvider(type = SqlProvider.class)
    int insert(ParamMap params);

    /**
     * 更新数据
     *
     * @param params 参数
     * @return 影响行数
     */
    @UpdateProvider(type = SqlProvider.class)
    int update(ParamMap params);

    /**
     * 删除数据
     *
     * @param params 参数
     * @return 影响行数
     */
    @DeleteProvider(type = SqlProvider.class)
    int delete(ParamMap params);

}