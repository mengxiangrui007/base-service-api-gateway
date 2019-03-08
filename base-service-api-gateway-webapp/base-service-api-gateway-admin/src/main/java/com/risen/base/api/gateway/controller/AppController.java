package com.risen.base.api.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author mengxr
 * @since 1.0
 */
@Controller
@RequestMapping("/app")
public class AppController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "app/list";
    }

}
