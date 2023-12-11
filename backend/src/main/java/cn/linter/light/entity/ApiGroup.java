package cn.linter.light.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * API分组实体
 *
 * @author wangxiaoyang
 */
@Data
@EqualsAndHashCode
@Table("api_group")
public class ApiGroup implements Serializable {

    private static final long serialVersionUID = 753640269071631569L;

    @Id
    private Integer id;

    /**
     * 名称
     */
    @Column("name")
    private String name;

}