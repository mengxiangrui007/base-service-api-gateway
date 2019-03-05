package com.risen.base.api.gateway.mapper;

import com.risen.base.api.gateway.model.GwAppServer;

public interface GwAppServerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GwAppServer record);

    int insertSelective(GwAppServer record);

    GwAppServer selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GwAppServer record);

    int updateByPrimaryKey(GwAppServer record);
}