package info.albertcode.domain.request;

/**
 * @Description: 发起 HTTPRequest 的请求，overview = method + " " + url
 *      其中，get请求的 url 是不包括 query 部分的，是被写在 body 内，
 *      目的是为了和 post 方法保持一致，从而使用同一个执行方法。
 *      body 和 header 部分采用键值对形式的字符串，同一键值对的键与值使用 = 连接，
 *      不同键值对之间使用 & 连接。
 *      如对于 get https://www.baidu.com/s?ie=UTF-8&wd=albert，储存形式为
 *      overview：get https://www.baidu.com/s
 *      body：ie=UTF-8&wd=albert
 * @Author: Albert Shen
 */

public class HttpRequestRequest extends Request{
    private String method; // 请求方法
    private String url; // 请求链接

    public HttpRequestRequest() {
        this.type = "HttpRequest";
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
        this.overview = this.method + " " + this.url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        this.overview = this.method + " " + this.url;
    }

    public void setOverview(String method, String url){
        setMethod(method);
        setUrl(url);
    }

    /**
     * 仅提供一个字符串时，将其拆分为 method 与 url
     * @param overview 需要为 "get url" 或 “post url”的形式，method与url中间有一个空格
     */
    @Override
    public void setOverview(String overview){
        int blank = overview.indexOf(" ");
        this.setOverview(overview.substring(0, blank), overview.substring(blank + 1));
    }
}
