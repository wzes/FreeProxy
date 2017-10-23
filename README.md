# 免费代理ip爬取(仅供参考！别干坏事哦)
## 使用Crawler4j开源工具爬取整个网站
- [快代理](http://www.kuaidaili.com/free)
- [西刺代理](http://www.89ip.cn/apijk/)
- [89代理](http://www.xicidaili.com/)
- [全网代理](http://www.goubanjia.com/free/index1.shtml)
### HttpHelper工具类，自动切换user-agent
```
    /**
	 * 获取ｉｐ归属地
	 * @param ip
	 * @return
	 */
	public static String getIpLocation(String ip){
		String api = "http://www.ip138.com/ips138.asp?ip=";
		String url = api + ip + "&datatype=text";
		String location = null;
		try {
			Document document = Jsoup.connect(url).get();
			location = document.select("li").get(0).text().substring(5);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return location;
	}
```
### 检测代理是否可用 分别检测百度和谷歌
```$xslt
/**
	 * 快速检测一个代理是否能用
	 * @param proxy
	 * @return
	 */
	public static boolean checkGoogleProxy(Proxy proxy){
		URL url = null;
		try {
			url = new URL("https://www.google.com/");
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		// 创建代理服务器
		InetSocketAddress addr = new InetSocketAddress(proxy.getIp(), Integer.parseInt(proxy.getPort()));
		java.net.Proxy mProxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, addr);
		HttpURLConnection conn = null;
		try {
			if (url != null) {
				conn = (HttpURLConnection)url.openConnection(mProxy);
				conn.setConnectTimeout(3000);
				conn.connect();
				int code = conn.getResponseCode();
				if(code == 200){
					return true;
				}else {
					System.out.println(code);
					return false;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public static boolean checkBaiduProxy(Proxy proxy){
		URL url = null;
		try {
			url = new URL("http://www.baidu.com/");
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		// 创建代理服务器
		InetSocketAddress addr = new InetSocketAddress(proxy.getIp(), Integer.parseInt(proxy.getPort()));
		java.net.Proxy mProxy = new java.net.Proxy(java.net.Proxy.Type.HTTP, addr);
		HttpURLConnection conn = null;
		try {
			if (url != null) {
				conn = (HttpURLConnection)url.openConnection(mProxy);
				conn.setConnectTimeout(3000);
				conn.connect();
				int code = conn.getResponseCode();
				if(code == 200){
					return true;
				}else {
					System.out.println(code);
					return false;
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
```
### FileHelper 文件写入类 追加到最后一行
```$xslt
/**
     * 追加文件：使用FileWriter
     *
     * @param fileName
     * @param content
     */
    public static void writeLine(String fileName, String content) {
        FileWriter writer = null;
        try {
            File file = new File(fileName);
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileName, true);
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```
### Crawler4j controller 以爬取xici代理为例
```$xslt
public class XiciController {

    private static CloseableHttpClient httpClient = null;

    public static void main(String[] args) throws Exception {
        String crawlStorageFolder = "/home/xuantang/IdeaProjects/FreeProxy/data";
        int numberOfCrawlers = 8;

        CrawlConfig config = new CrawlConfig();

        config.setFollowRedirects(false);
        config.setCrawlStorageFolder(crawlStorageFolder);

        HashSet<BasicHeader> collections = new HashSet<BasicHeader>();
        collections.add(new BasicHeader("User-Agent","Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3192.0 Safari/537.36"));
        collections.add(new BasicHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"));
        collections.add(new BasicHeader("Accept-Encoding", "gzip,deflate,sdch"));
        collections.add(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6"));
        collections.add(new BasicHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8"));
        collections.add(new BasicHeader("Connection", "keep-alive"));
        config.setDefaultHeaders(collections);
        /*
         * Instantiate the controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */
        controller.addSeed("http://www.xicidaili.com/nt");
        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        controller.start(XiciCrawler.class, numberOfCrawlers);
    }
}
```
#### 页面处理
```$xslt
public class XiciCrawler extends WebCrawler {

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
        return href.startsWith("http://www.xicidaili.com/nt");
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
            Element content = document.getElementById("ip_list");
            Elements dds = content.getElementsByTag("tr");
            for (Element element : dds) {
                if (element.getElementsByTag("td").text().length() != 0) {
                    try{
                        String text = element.getElementsByTag("td").text();
                        String[] result = text.split(" ");
                        String ip = result[0];
                        String port = result[1];
                        String location = result[2];
                        Proxy proxy = new Proxy(ip, port, location);
                        count++;
                        if(HttpHelper.checkGoogleProxy(proxy)){
                            proxy.setType("google");
                            FileHelper.writeLine("/d1/lab409/FreeProxy/data/proxy.txt", proxy.toString() + "\n");
                            System.out.println(count + " " + "this proxy is ok for google: " + proxy.getIp()
                                    + " " + proxy.getPort() + " " + proxy.getLocation() + "------------------" +
                                    "--------------------------------------------------------------");
                        }else if(HttpHelper.checkBaiduProxy(proxy)){
                            proxy.setType("baidu");
                            FileHelper.writeLine("/d1/lab409/FreeProxy/data/proxy.txt", proxy.toString() + "\n");
                            System.out.println(count + " " + "this proxy is ok for baidu: " + proxy.getIp()
                                    + " " + proxy.getPort() + " " + proxy.getLocation());
                        }
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }

                }

            }
        }
    }
}
```
## 使用Springboot可以对外提供api获取可用代理
- 数据持久化，使用mybatis连接数据库操作
```$xslt
@Mapper
public interface ProxyMapper {
    /**
     * @param
     * @return
     */
    @Insert("INSERT INTO proxy (ip, port, location) VALUES (#{ip},#{port},#{location});")
    int insert(Proxy proxy);

    @Select("select ip, port, location from proxy")
    List<Proxy> get();
}
```
### Service 接口服务, 插入数据库，查询数据库
```$xslt
@RestController
public class ProxyController {

    @Autowired
    ProxyMapper proxyMapper;

    @RequestMapping(value = "/proxys", method = RequestMethod.GET,
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public List<Proxy> getProxy(){
        List<Proxy> canUseProxys = ENCrawler.getCanUseProxys(10000);
        for (Proxy proxy : canUseProxys){
            if(proxy.getLocation() != null)
                try {
                    proxyMapper.insert(proxy);
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
        }
        return canUseProxys;
    }
}
```