package com.risen.base.api.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author mengxr
 * @since 1.0
 */

@Controller
public class IndexController {
    @RequestMapping({"/", "", "/index"})
    public String index() {
        return "index";
    }
}
