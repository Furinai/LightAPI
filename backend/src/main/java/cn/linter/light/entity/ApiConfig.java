package cn.linter.light.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.util.List;

/**
 * API配置实体
 *
 * @author wangxiaoyang
 */
@Data
@EqualsAndHashCode
@Table("api_config")
public class ApiConfig implements Serializable {

    private static final long serialVersionUID = 364628743502973566L;

    @Id
    private Integer id;

    /**
     * 名称
     */
    @Column("name")
    private String name;

    /**
     * 请求路径
     */
    @Column("request_path")
    private String requestPath;

    /**
     * 请求方法
     */
    @Column("request_method")
    private HttpMethod requestMethod;

    /**
     * SQL语句
     */
    @Column("sql_statement")
    private String sqlStatement;

    /**
     * 数据源配置ID
     */
    @Column("data_source_config_id")
    private Integer dataSourceConfigId;

    /**
     * 分组ID
     */
    @Column("group_id")
    private Integer groupId;

    /**
     * 是否缓存
     */
    @Column("is_cacheable")
    private Boolean cacheable;

    /**
     * 是否列表查询
     */
    @Column("is_listable")
    private Boolean listable;

    /**
     * 是否分页查询
     */
    @Column("is_pageable")
    private Boolean pageable;

    /**
     * 是否启用
     */
    @Column("is_enable")
    private Boolean enable;

    /**
     * 参数配置列表
     */
    @MappedCollection(idColumn = "api_config_id", keyColumn = "list_order")
    private List<ApiParamConfig> paramConfigList;

}