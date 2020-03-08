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

    private final static String VERTICAL = "|";
    private final static String ELEMEMT_TABLE = "flex_cb";
    private final static String TR="tr";
    private final static String TD="td";
    private final static String CSS_A="a";
    private final static String KEY_HREF="href";

    private final int skipNumber;
    private final Integer tdHrefPosition;
    private final String defaultUrl;
    private final GetHtmlPage getHtmlPage;

    public GetElementsFromHtml(final GetHtmlPage getHtmlPage, final Integer skipNumber, final Integer tdHrefPosition, final String defaultUrl) {
        this.skipNumber = skipNumber;
        this.tdHrefPosition = tdHrefPosition;
        this.defaultUrl = defaultUrl;
        this.getHtmlPage = getHtmlPage;
    }

    public Optional<Map<Integer, List<String>>> process() {
        try {
            final Optional<Document> doc = getHtmlPage.process();
            if(doc.isPresent()) {
                final Element elementTable = doc.get().getElementById(ELEMEMT_TABLE);
                final Elements elemenTrs = elementTable.select(TR);
                return Optional.of(elemenTrs.stream().skip(skipNumber)
                        .collect(Collectors.toMap(elemenTrs::indexOf, tr -> tr.select(TD)
                                .stream().map(td -> {
                                    if (tdHrefPosition.equals(tr.select(TD).indexOf(td))) {
                                        return defaultUrl + td.select(CSS_A).attr(KEY_HREF) + VERTICAL + td.text();
                                    } else {
                                        return td.text();
                                    }
                                }).collect(Collectors.toList()))));
            }else{
                LOGGER.error("Got empty document from html.");
                return Optional.empty();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }
}