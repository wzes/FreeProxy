package com.wzes.proxy.service.processor;

import com.wzes.proxy.domain.Proxy;
import com.wzes.proxy.util.FileHelper;
import com.wzes.proxy.util.HttpHelper;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Pattern;

public class QuanwangCrawler extends WebCrawler {

    private static int count = 0;
    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
            + "|png|mp3|mp4|zip|gz))$");

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "http://www.ics.uci.edu/". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return href.startsWith("http://www.goubanjia.com/free");
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {

        System.out.println(page.getContentCharset());
        if (page.getParseData() instanceof HtmlParseData) {
            HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
            String html = htmlParseData.getHtml();
            Document document = Jsoup.parse(html);
            Element content = document.getElementById("list");
            Elements dds = content.getElementsByTag("tr");
            for (Element element : dds) {
                if (element.getElementsByTag("td").text().length() != 0) {
                    try{
                        String text = element.getElementsByTag("td").text();
                        System.out.println(text.trim() + page.getWebURL());
//                        String[] result = text.split(" ");
//                        String ip = result[0];
//                        String port = result[1];
//                        String location = result[2];
//                        Proxy proxy = new Proxy(ip, port, location);
//                        count++;
//                        if(HttpHelper.checkGoogleProxy(proxy)){
//                            proxy.setType("google");
//                            FileHelper.writeLine("/d1/lab409/FreeProxy/data/proxy.txt", proxy.toString() + "\n");
//                            System.out.println(count + " " + "this proxy is ok for google: " + proxy.getIp()
//                                    + " " + proxy.getPort() + " " + proxy.getLocation() + "------------------" +
//                                    "--------------------------------------------------------------");
//                        }else if(HttpHelper.checkBaiduProxy(proxy)){
//                            proxy.setType("baidu");
//                            FileHelper.writeLine("/d1/lab409/FreeProxy/data/proxy.txt", proxy.toString() + "\n");
//                            System.out.println(count + " " + "this proxy is ok for baidu: " + proxy.getIp()
//                                    + " " + proxy.getPort() + " " + proxy.getLocation());
//                        }
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                }

            }
        }
    }
}