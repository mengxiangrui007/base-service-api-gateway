package com.risen.base.api.gateway.storage.db.mapper;


import com.risen.base.api.gateway.storage.db.model.GwAppInfo;

import java.util.List;
import java.util.Set;

@Mybatis
public interface GwAppInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GwAppInfo record);

    int insertSelective(GwAppInfo record);

    GwAppInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GwAppInfo record);

    int updateByPrimaryKey(GwAppInfo record);

    Set<String> selectAllAppkeys();

    /**
     * 通过Key获取服务详情
     *
     * @param appKey
     * @return
     */
    GwAppInfo selectByAppkey(String appKey);

    int selectCountWithBySearch(GwAppInfo gwAppInfo);

    List<GwAppInfo> selectListWithBySearch(GwAppInfo gwAppInfo, int start, int pageSize);
}