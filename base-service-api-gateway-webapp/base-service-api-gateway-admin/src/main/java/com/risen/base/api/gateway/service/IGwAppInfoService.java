package com.risen.base.api.gateway.service;

import com.risen.base.api.gateway.base.Page;
import com.risen.base.api.gateway.service.dto.GwAppInfnDto;
import com.risen.base.api.gateway.service.entity.GwAppInfoEt;

/**
 * 应用信息接口
 *
 * @author mengxr
 * @since 1.0
 */
public interface IGwAppInfoService {
    /**
     * 查询应用列表信息
     *
     * @param gwAppInfnDto
     * @param currentPage
     * @param pageSize
     * @return
     */
    Page<GwAppInfoEt> queryGwAppInfos(GwAppInfnDto gwAppInfnDto, int currentPage, int pageSize);

}
