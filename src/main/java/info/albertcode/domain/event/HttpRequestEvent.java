package info.albertcode.domain.event;

import info.albertcode.utils.enums.EEventType;

/**
 * @Description: http请求事件，overview = method + " " + url
 * @Author: Albert Shen
 */

public class HttpRequestEvent extends Event{
    private String method; // 请求方法
    private String url; // 请求链接

    public HttpRequestEvent() {
        this.type = EEventType.HttpRequest;
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
    public void setOverview(String overview){
        int blank = overview.indexOf(" ");
        this.setOverview(overview.substring(0, blank), overview.substring(blank + 1));
    }

    public void setHeader(String header){
        this.header = header;
    }

    public void setBody(String body){
        this.body = body;
    }
}
