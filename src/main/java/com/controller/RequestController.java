package com.controller;

import com.data.GetElementsFromHtml;
import com.data.GetHtmlPage;
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
public class RequestController {
    @Value("${jisilu.url}")
    private String url;
    @Value("${jisilu.defaultUrl}")
    private String defaultUrl;
    @Value("${tdHrefPosition}")
    private Integer tdHrefPosition;
    @Value("${skipNumber}")
    private Integer skipNumber;
    @Value("${timeOut}")
    private Integer timeOut;
    @Value("${backGroudWaitTime}")
    private Integer backGroudWaitTime;

    @RequestMapping(value = "/jisilu", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Map<Integer, List<String>>> data() {
        final GetHtmlPage getHtmlPage = new GetHtmlPage(timeOut, url, backGroudWaitTime);
        final GetElementsFromHtml getElementsFromHtml = new GetElementsFromHtml(getHtmlPage, skipNumber, tdHrefPosition, defaultUrl);
        return getElementsFromHtml.process();
    }
}
