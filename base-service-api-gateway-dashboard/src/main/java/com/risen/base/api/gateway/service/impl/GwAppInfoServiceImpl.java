package com.risen.base.api.gateway.service.impl;

import com.risen.base.api.gateway.base.Page;
import com.risen.base.api.gateway.service.IGwAppInfoService;
import com.risen.base.api.gateway.service.dto.GwAppInfnDto;
import com.risen.base.api.gateway.service.entity.GwAppInfoEt;
import com.risen.base.api.gateway.storage.db.mapper.GwAppInfoMapper;
import com.risen.base.api.gateway.storage.db.model.GwAppInfo;
import com.risen.base.api.gateway.util.BeanCopierUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author mengxr
 * @since 1.0
 */
@Service
@Transactional(readOnly = true)
public class GwAppInfoServiceImpl implements IGwAppInfoService {
    @Resource
    private GwAppInfoMapper gwAppInfoMapper;

    @Override
    public Page<GwAppInfoEt> queryGwAppInfos(GwAppInfnDto gwAppInfnDto, int currentPage, int pageSize) {
        Page<GwAppInfoEt> pager = null;
        GwAppInfo gwAppInfo = BeanCopierUtils.copierTargetBean(gwAppInfnDto, GwAppInfnDto.class, GwAppInfo.class);
        int sum = gwAppInfoMapper.selectCountWithBySearch(gwAppInfo);
        if (sum > 0) {
            pager = new Page(sum, currentPage, pageSize);
            List<GwAppInfo> gwAppInfos = gwAppInfoMapper.selectListWithBySearch(gwAppInfo, pager.getStart(), pager.getPageSize());
            List<GwAppInfoEt> gwAppInfoEts = BeanCopierUtils.copierTargetBeanList(gwAppInfos, GwAppInfo.class, GwAppInfoEt.class);
            pager.setList(gwAppInfoEts);
        } else {
            pager = new Page();
        }
        return pager;
    }
}
