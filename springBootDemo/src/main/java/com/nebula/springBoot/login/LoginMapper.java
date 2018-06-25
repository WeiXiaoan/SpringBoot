package com.nebula.springBoot.login;

import org.apache.ibatis.annotations.Mapper;

/**
 * Created by SmallAnn on 2018/6/25
 */
@Mapper
public interface LoginMapper {
    void createTable();

    void insert();
}
