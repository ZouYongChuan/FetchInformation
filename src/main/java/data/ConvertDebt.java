package data;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ConvertDebt {
    private final int timeOut;
    private final String url;
    private final int backGroudWaitTime;
    private final int skipNumber;
    private final Integer tdHrefPosition;
    private final String defaultUrl;

    public ConvertDebt(final int timeOut,final String url,final int backGroudWaitTime,final int skipNumber,final Integer tdHrefPosition,final String defaultUrl) {
        this.timeOut = timeOut;
        this.url = url;
        this.backGroudWaitTime = backGroudWaitTime;
        this.skipNumber = skipNumber;
        this.tdHrefPosition = tdHrefPosition;
        this.defaultUrl = defaultUrl;
    }

    private final static String VERTICAL = "|";

    public Optional<Map<Integer, List<String>>> get() throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(timeOut);
        final HtmlPage htmlPage = webClient.getPage(url);
        webClient.waitForBackgroundJavaScript(backGroudWaitTime);
        final String pageAsXml = htmlPage.asXml();
        final Document doc = Jsoup.parse(pageAsXml, url);
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
    }
}