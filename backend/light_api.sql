create table api_config
(
    id                    int auto_increment
        primary key,
    name                  varchar(20)   not null comment '名称',
    request_path          varchar(50)   not null comment '请求路径',
    request_method        varchar(6)    not null comment '请求方法',
    sql_statement         varchar(5000) not null comment 'SQL语句',
    group_id              int           not null comment '分组ID',
    data_source_config_id int           not null comment '数据源配置ID',
    is_cacheable          int           not null comment '是否缓存查询结果',
    is_listable           int           not null comment '是否列表查询',
    is_pageable           int           not null comment '是否分页查询',
    is_enable             int           not null comment '是否启用',
    constraint unique_path_method
        unique (request_path, request_method)
)
    comment 'API配置';

create table api_group
(
    id   int auto_increment
        primary key,
    name varchar(20) not null comment '名称'
)
    comment 'API分组';

create table api_param_config
(
    id            int auto_increment
        primary key,
    name          varchar(20) not null comment '名称',
    `key`         varchar(20) not null comment '键',
    type          varchar(20) not null comment '类型',
    is_listable   int         not null comment '是否数组',
    is_required   int         not null comment '是否必需',
    api_config_id int         not null comment 'API配置ID',
    list_order    int         not null comment '关联顺序'
)
    comment 'API参数配置';

create table data_source_config
(
    id       int auto_increment
        primary key,
    type     varchar(20)  not null comment '类型',
    name     varchar(20)  not null comment '名称',
    jdbc_url varchar(200) not null comment 'Url',
    username varchar(20)  not null comment '用户名',
    password varchar(20)  not null comment '密码'
)
    comment '数据源配置';