import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Fetch {
    private final static String URL = "https://www.jisilu.cn/data/cbnew/#cb";
    private final static String DEFAULTURL = "https://www.jisilu.cn";
    private final static String VERTICAL = "|";
    private final static Integer TDHREFPOSITION = 4;
    private final static Integer SKIPNUMBER = 2;
    private final static Integer TIMEOUT = 10000;
    private final static Integer BACKGOUPRDWAITTIME = 25000;

    public static void main(String[] args) throws Exception {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setTimeout(TIMEOUT);
        final HtmlPage htmlPage = webClient.getPage(URL);
        webClient.waitForBackgroundJavaScript(BACKGOUPRDWAITTIME);
        final String pageAsXml = htmlPage.asXml();
        final Document doc = Jsoup.parse(pageAsXml, URL);
        final Element elementTable = doc.getElementById("flex_cb");
        final Elements elemenTrs = elementTable.select("tr");
        final Map<Integer, List<String>> integerListMap = elemenTrs.stream().skip(SKIPNUMBER)
                .collect(Collectors.toMap(elemenTrs::indexOf, tr -> tr.select("td")
                        .stream().map(td -> {
                            if (TDHREFPOSITION.equals(tr.select("td").indexOf(td))) {
                                return DEFAULTURL + td.select("a").attr("href") + VERTICAL + td.text();
                            } else {
                                return td.text();
                            }
                        }).collect(Collectors.toList())));
        for (Map.Entry<Integer, List<String>> entry : integerListMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}