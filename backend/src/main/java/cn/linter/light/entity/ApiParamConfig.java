package cn.linter.light.entity;

import cn.linter.light.constant.ParamType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * API参数配置实体
 *
 * @author wangxiaoyang
 */
@Data
@EqualsAndHashCode
@Table("api_param_config")
public class ApiParamConfig implements Serializable {

    private static final long serialVersionUID = 137722407902613267L;

    @Id
    private Integer id;

    /**
     * 名称
     */
    @Column("name")
    private String name;

    /**
     * 键
     */
    @Column("key")
    private String key;

    /**
     * 类型
     */
    @Column("type")
    private ParamType type;

    /**
     * 是否数组
     */
    @Column("is_listable")
    private Boolean listable;

    /**
     * 是否必需
     */
    @Column("is_required")
    private Boolean required;

    /**
     * API配置ID
     */
    @Column("api_config_id")
    private Integer apiConfigId;

}