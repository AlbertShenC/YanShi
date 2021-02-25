package info.albertcode.domain.request;

import info.albertcode.utils.pair.impl.OneKeyOneValue;

/**
 * @Description: 发起 HTTPRequest 的请求，
 *      overview 包括请求方法（method）与请求链接（url）两部分
 *      其中，get请求的 url 是不包括 query 部分的，是被写在 body 内，
 *      目的是为了和 post 方法保持一致，从而使用同一个执行方法。
 *      如对于 get https://www.baidu.com/s?ie=UTF-8&wd=albert，储存形式为
 *      overview：get https://www.baidu.com/s
 * @Author: Albert Shen
 */

public class HttpRequestRequest extends Request{
    // 包括请求方法（method），请求链接（url），代理ip（proxyDomain），代理端口（proxyPort）
    private OneKeyOneValue keyValueOverview;
    // http 请求的头部，包括任意多个键值对
    private OneKeyOneValue keyValueHeader;
    // http 请求的主体，包括任意多个键值对
    private OneKeyOneValue keyValueBody;

    public HttpRequestRequest(Request request){
        this.setId(request.getId());
        this.setOverview(request.getOverview());
        this.setHeader(request.getHeader());
        this.setBody(request.getBody());
    }

    // Overview 相关操作

    public String getMethod() {
        return (String) keyValueOverview.getValue("method");
    }

    public void setMethod(String method) {
        keyValueOverview.addValue("method", method);
        this.overview = keyValueOverview.toJsonString();
    }

    public String getUrl() {
        return (String) keyValueOverview.getValue("url");
    }

    public void setUrl(String url) {
        keyValueOverview.addValue("url", url);
        this.overview = keyValueOverview.toJsonString();
    }

    public String getProxyDomain() {
        return (String) keyValueOverview.getValue("proxyDomain");
    }

    public void setProxyDomain(String proxyDomain) {
        keyValueOverview.addValue("proxyDomain", proxyDomain);
        this.overview = keyValueOverview.toJsonString();
    }

    public String getProxyPort() {
        return (String) keyValueOverview.getValue("proxyPort");
    }

    public void setProxyPort(String proxyPort) {
        keyValueOverview.addValue("proxyPort", proxyPort);
        this.overview = keyValueOverview.toJsonString();
    }

    public void setOverview(String method, String url){
        keyValueOverview.addValue("method", method);
        keyValueOverview.addValue("url", url);
        this.overview = keyValueOverview.toJsonString();
    }

    public void setOverview(String method, String url, String proxyDomain, String proxyPort){
        keyValueOverview.addValue("method", method);
        keyValueOverview.addValue("url", url);
        keyValueOverview.addValue("proxyDomain", proxyDomain);
        keyValueOverview.addValue("proxyPort", proxyPort);
        this.overview = keyValueOverview.toJsonString();
    }

    /**
     * 仅提供一个字符串时，将其拆分为 method 与 url
     */
    @Override
    public void setOverview(String overview){
        keyValueOverview = new OneKeyOneValue(overview);
        this.overview = keyValueOverview.toJsonString();
    }

    // Header 相关操作

    public void addHeader(String key, String value) {
        keyValueHeader.addValue(key, value);
        this.header = keyValueHeader.toJsonString();
    }

    public void deleteHeader(String key) {
        keyValueHeader.deleteKey(key);
        this.header = keyValueHeader.toJsonString();
    }

    @Override
    public void setHeader(String header) {
        keyValueHeader = new OneKeyOneValue(header);
        this.header = keyValueHeader.toJsonString();
    }

    // Body 相关操作

    public void addBody(String key, String value) {
        keyValueBody.addValue(key, value);
        this.body = keyValueBody.toJsonString();
    }

    public void deleteBody(String key) {
        keyValueBody.deleteKey(key);
        this.body = keyValueBody.toJsonString();
    }

    @Override
    public void setBody(String body) {
        keyValueBody = new OneKeyOneValue(body);
        this.body = keyValueBody.toJsonString();
    }
}
