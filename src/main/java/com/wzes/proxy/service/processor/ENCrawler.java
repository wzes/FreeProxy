package com.wzes.proxy.service.processor;

import com.wzes.proxy.domain.Proxy;
import com.wzes.proxy.util.FileHelper;
import com.wzes.proxy.util.HttpHelper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ENCrawler {


    private static String api = "http://www.89ip.cn/apijk/";

    /**
     * 根据api获取代理
     * @param num
     * @return
     */
    public static List<Proxy> getProxys(int num){

        List<Proxy> proxies = new ArrayList<>();
        String url = api + "?&tqsl=" + num + "&sxa=&sxb=&tta=&ports=&ktip=&cf=1";
        try {
            Document document = Jsoup.connect(url)
                    .header("User-Agent", HttpHelper.getRandomUserAgent())
                    .header("Content-Type", "text/html")
                    .header("Connection", "keep-alive")
                    .cookie("Cookie","__cfduid=d0b78c9d223500b0ac0b815622d4d8f031508587694; yjs_id=TW96aWxsYS81LjAgKFgxMTsgTGludXggeDg2XzY0OyBydjo1NS4wKSBHZWNrby8yMDEwMDEwMSBGaXJlZm94LzU1LjB8d3d3Ljg5aXAuY258MTUwODU4NzcwNDUyMHw; ctrl_time=1; UM_distinctid=15f3ed5c22c470-0e264f88e68bb78-3f6e4646-1fa400-15f3ed5c22d645; CNZZDATA1254651946=1933271950-1508584518-%7C1508584518")
                    .get();
            Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+:\\d+)");
            Matcher matcher = pattern.matcher(document.text());
            while(matcher.find()) {
                proxies.add(new Proxy(matcher.group().split(":")[0],
                         matcher.group().split(":")[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return proxies;
    }

    private static List<Proxy> mProxyList = new ArrayList<>();

    private static int globle_index = 0;
    public static List<Proxy> getCanUseProxys(int num){
        List<Proxy> proxys = getProxys(num);
        System.out.println("---------------------------" + proxys.size());
        int nThreds = 20;
        ExecutorService executor = Executors.newFixedThreadPool(nThreds);
        for (Proxy proxy : proxys){
            executor.execute(() -> {
                if(HttpHelper.checkGoogleProxy(proxy)){
                    proxy.setType("google");
                    proxy.setLocation(HttpHelper.getIpLocation(proxy.getIp()));
                    FileHelper.writeLine("/d1/lab409/FreeProxy/data/proxy.txt", proxy.toString() + "\n");
                    mProxyList.add(proxy);
                    count++;
                    System.out.println(globle_index + " " +
                            count + " " + "this proxy is ok for google: " + proxy.getIp()
                            + " " + proxy.getPort() + " " + proxy.getLocation());
                }else if(HttpHelper.checkBaiduProxy(proxy)){
                    proxy.setType("baidu");
                    proxy.setLocation(HttpHelper.getIpLocation(proxy.getIp()));
                    FileHelper.writeLine("/d1/lab409/FreeProxy/data/proxy.txt", proxy.toString() + "\n");
                    mProxyList.add(proxy);
                    count++;
                    System.out.println(globle_index + " " +
                            count + " " + "this proxy is ok for baidu: " + proxy.getIp()
                            + " " + proxy.getPort() + " " + proxy.getLocation());
                }
                globle_index++;
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        return proxys;
    }

    private static int count = 0;
    public static void main(String[] args) {
        //　获取初始的url
        List<Proxy> proxys = getProxys(10000);
        //　开启的线程数
        int nThreds = 10;
        //　开启线程池
        ExecutorService executor = Executors.newFixedThreadPool(nThreds);
        for (Proxy proxy : proxys){
            //System.out.println(proxy.toString());
            executor.execute(() -> {
                if(HttpHelper.checkGoogleProxy(proxy)){
                    proxy.setType("google");
                    proxy.setLocation(HttpHelper.getIpLocation(proxy.getIp()));
                    FileHelper.writeLine("/d1/lab409/FreeProxy/data/proxy.txt", proxy.toString() + "\n");
                    mProxyList.add(proxy);
                    count++;
                    System.out.println(globle_index + " " +
                            count + " " + "this proxy is ok for google: " + proxy.getIp()
                            + " " + proxy.getPort() + " " + proxy.getLocation());
                }else if(HttpHelper.checkBaiduProxy(proxy)){
                    proxy.setType("baidu");
                    proxy.setLocation(HttpHelper.getIpLocation(proxy.getIp()));
                    FileHelper.writeLine("/d1/lab409/FreeProxy/data/proxy.txt", proxy.toString() + "\n");
                    mProxyList.add(proxy);
                    count++;
                    System.out.println(globle_index + " " +
                            count + " " + "this proxy is ok for baidu: " + proxy.getIp()
                            + " " + proxy.getPort() + " " + proxy.getLocation());
                }
                globle_index++;
            });
        }
    }

}
