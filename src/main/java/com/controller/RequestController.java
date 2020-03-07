package com.controller;

import com.data.ConvertDebt;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author zouyongchuan
 */
@RestController
@PropertySource("classpath:data.properties")
@EnableAutoConfiguration
public class RequestController {
    private static Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    @Value("${url}")
    private String url;
    @Value("${defaultUrl}")
    private String defaultUrl;
    @Value("${tdHrefPosition}")
    private Integer tdHrefPosition;
    @Value("${skipNumber}")
    private Integer skipNumber;
    @Value("${timeOut}")
    private Integer timeOut;
    @Value("${backGroudWaitTime}")
    private Integer backGroudWaitTime;

    @RequestMapping(value = "/data",method = RequestMethod.GET)
    @ResponseBody
    public Optional<Map<Integer, List<String>>> data(){
        try {
            final ConvertDebt convertDebt = new ConvertDebt(timeOut, url, backGroudWaitTime, skipNumber, tdHrefPosition, defaultUrl);
            return convertDebt.get();
        }catch (Exception e){
            LOGGER.error(String.valueOf(e));
            return Optional.empty();
        }
    }
}
