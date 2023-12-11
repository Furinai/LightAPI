package cn.linter.light.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author wangxiaoyang
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class PageableResultList<T> {
    private Long total;
    private List<T> list;
}