package info.albertcode.domain.event;

import info.albertcode.utils.enums.ETaskType;
import info.albertcode.utils.pair.impl.OneKeyOneValue;

import java.util.Date;

/**
 * @Description: http 响应事件，overview = httpVersion + " " + statusCode + " " + reasonPhrase
 * @Author: Albert Shen
 */

public class HttpResponseEvent extends Event{
    // 包括
    // 响应协议版本（httpVersion），如 HTTP/1.1
    // 响应状态码（statusCode），如 200
    // 响应状态码描述（reasonPhrase），如 OK
    // 三部分
    private OneKeyOneValue keyValueOverview;

    public HttpResponseEvent() {
        this.setGeneratedTime(new Date());
        this.setTypeEnum(ETaskType.HttpRequest);
        keyValueOverview = new OneKeyOneValue();
    }

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

    public void setOverview(String overview){
        keyValueOverview = new OneKeyOneValue(overview);
        this.overview = keyValueOverview.toJsonString();
    }
}
