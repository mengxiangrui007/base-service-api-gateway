package com.risen.base.api.gateway.mapper;

import com.risen.base.api.gateway.model.GwAppInfo;

public interface GwAppInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GwAppInfo record);

    int insertSelective(GwAppInfo record);

    GwAppInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GwAppInfo record);

    int updateByPrimaryKey(GwAppInfo record);
}