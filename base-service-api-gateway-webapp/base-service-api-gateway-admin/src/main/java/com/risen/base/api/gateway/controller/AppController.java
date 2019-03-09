package com.risen.base.api.gateway.controller;

import com.risen.base.api.gateway.base.ErrorCode;
import com.risen.base.api.gateway.base.Page;
import com.risen.base.api.gateway.base.Result;
import com.risen.base.api.gateway.controller.param.GwAppInfoParam;
import com.risen.base.api.gateway.service.IGwAppInfoService;
import com.risen.base.api.gateway.service.dto.GwAppInfnDto;
import com.risen.base.api.gateway.service.entity.GwAppInfoEt;
import com.risen.base.api.gateway.util.BeanCopierUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 网关应用接口
 *
 * @author mengxr
 * @since 1.0
 */
@Controller
@RequestMapping("/app")
public class AppController {
    @Autowired
    private IGwAppInfoService iGwAppInfoService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "app/list";
    }

    /**
     * 获取网关列表
     *
     * @param gwAppInfoParam
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryGwAppInfos", method = RequestMethod.GET)
    public Result<Page<GwAppInfoEt>> queryGwAppInfos(@Validated GwAppInfoParam gwAppInfoParam) {
        GwAppInfnDto gwAppInfnDto = BeanCopierUtils.copierTargetBean(gwAppInfoParam, GwAppInfoParam.class, GwAppInfnDto.class);
        Page<GwAppInfoEt> gwAppInfoEtPage = iGwAppInfoService.queryGwAppInfos(gwAppInfnDto,
                gwAppInfoParam.getCurrentPage(), gwAppInfoParam.getPageSize());
        return new Result<>(ErrorCode.Ok.getValue(), "Success", gwAppInfoEtPage);
    }
}
