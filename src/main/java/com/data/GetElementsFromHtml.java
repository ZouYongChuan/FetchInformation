package com.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GetElementsFromHtml {
    private static Logger LOGGER = LoggerFactory.getLogger(GetElementsFromHtml.class);
    private final int skipNumber;
    private final Integer tdHrefPosition;
    private final String defaultUrl;
    private final GetHtmlPage getHtmlPage;

    private final static String VERTICAL = "|";

    public GetElementsFromHtml(final GetHtmlPage getHtmlPage, final Integer skipNumber, final Integer tdHrefPosition, final String defaultUrl) {
        this.skipNumber = skipNumber;
        this.tdHrefPosition = tdHrefPosition;
        this.defaultUrl = defaultUrl;
        this.getHtmlPage = getHtmlPage;
    }

    public Optional<Map<Integer, List<String>>> process() {
        try {
            final String pageAsXml=getHtmlPage.process();
            final Document doc = Jsoup.parse(pageAsXml, getHtmlPage.getUrl());
            final Element elementTable = doc.getElementById("flex_cb");
            final Elements elemenTrs = elementTable.select("tr");
            return Optional.of(elemenTrs.stream().skip(skipNumber)
                    .collect(Collectors.toMap(elemenTrs::indexOf, tr -> tr.select("td")
                            .stream().map(td -> {
                                if (tdHrefPosition.equals(tr.select("td").indexOf(td))) {
                                    return defaultUrl + td.select("a").attr("href") + VERTICAL + td.text();
                                } else {
                                    return td.text();
                                }
                            }).collect(Collectors.toList()))));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }
}