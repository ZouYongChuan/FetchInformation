package com.data;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class GetHtmlPage {
    private static Logger LOGGER= LoggerFactory.getLogger(GetHtmlPage.class);
    private final int timeOut;
    private final String url;
    private final int backGroudWaitTime;

    public GetHtmlPage(final int timeOut, final String url, final int backGroudWaitTime) {
        this.timeOut = timeOut;
        this.url = url;
        this.backGroudWaitTime = backGroudWaitTime;
    }

    public Optional<Document> process(){
        try {
            final WebClient webClient = new WebClient(BrowserVersion.CHROME);
            webClient.getOptions().setJavaScriptEnabled(true);
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setThrowExceptionOnScriptError(false);
            webClient.getOptions().setTimeout(timeOut);
            final HtmlPage htmlPage = webClient.getPage(url);
            webClient.waitForBackgroundJavaScript(backGroudWaitTime);
            final String pageAsXml =  htmlPage.asXml();
            return Optional.of(Jsoup.parse(pageAsXml, url));
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }
}
