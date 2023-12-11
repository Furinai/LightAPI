package cn.linter.light.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * 数据源配置实体
 *
 * @author wangxiaoyang
 */
@Data
@EqualsAndHashCode
@Table("data_source_config")
public class DataSourceConfig implements Serializable {

    private static final long serialVersionUID = 243772078636874566L;

    @Id
    private Integer id;

    /**
     * 类型
     */
    @Column("type")
    private String type;

    /**
     * 名称
     */
    @Column("name")
    private String name;

    /**
     * URL
     */
    @Column("jdbc_url")
    private String jdbcUrl;

    /**
     * 用户名
     */
    @Column("username")
    private String username;

    /**
     * 密码
     */
    @Column("password")
    private String password;

}