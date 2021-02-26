package info.albertcode.domain.event;

import info.albertcode.utils.enums.ETaskType;
import info.albertcode.utils.pair.OneKeyOneValue;

import java.util.Date;

/**
 * @Description: http 响应事件
 * @Author: Albert Shen
 */

public class HttpResponseEvent extends Event{
    // 包括
    // 响应协议版本（httpVersion），如 HTTP/1.1
    // 响应状态码（statusCode），如 200
    // 响应状态码描述（reasonPhrase），如 OK
    // 三部分
    private OneKeyOneValue keyValueOverview;
    // http 响应的头部，包括任意多个键值对
    private OneKeyOneValue keyValueHeader;
    // body 部分无需重写

    public HttpResponseEvent() {
        this.setTypeEnum(ETaskType.HttpRequest);
        this.setGeneratedTime(new Date());
        keyValueOverview = new OneKeyOneValue();
        keyValueHeader = new OneKeyOneValue();
    }

    // Overview 相关操作

    public String getHttpVersion() {
        return (String) keyValueOverview.getValue("httpVersion");
    }

    public void setHttpVersion(String httpVersion) {
        keyValueOverview.addValue("httpVersion", httpVersion);
        this.overview = keyValueOverview.toJsonString();
    }

    public String getStatusCode() {
        return (String) keyValueOverview.getValue("statusCode");
    }

    public void setStatusCode(String statusCode) {
        keyValueOverview.addValue("statusCode", statusCode);
        this.overview = keyValueOverview.toJsonString();
    }

    public String getReasonPhrase() {
        return (String) keyValueOverview.getValue("reasonPhrase");
    }

    public void setReasonPhrase(String reasonPhrase) {
        keyValueOverview.addValue("reasonPhrase", reasonPhrase);
        this.overview = keyValueOverview.toJsonString();
    }

    public void setOverview(String httpVersion, String statusCode, String reasonPhrase){
        keyValueOverview.addValue("httpVersion", httpVersion);
        keyValueOverview.addValue("statusCode", statusCode);
        keyValueOverview.addValue("reasonPhrase", reasonPhrase);
        this.overview = keyValueOverview.toJsonString();
    }

    @Override
    public void setOverview(String overview){
        keyValueOverview = new OneKeyOneValue(overview);
        this.overview = keyValueOverview.toJsonString();
    }

    public void setOverview(OneKeyOneValue keyValueOverview){
        this.keyValueOverview = keyValueOverview;
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

    public void setHeader(OneKeyOneValue keyValueHeader) {
        this.keyValueHeader = keyValueHeader;
        this.header = keyValueHeader.toJsonString();
    }
}
