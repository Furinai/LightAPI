package cn.linter.light.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author wangxiaoyang
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class PageableResultList {
    private Long total;
    private List<LinkedHashMap<String, Object>> list;
}