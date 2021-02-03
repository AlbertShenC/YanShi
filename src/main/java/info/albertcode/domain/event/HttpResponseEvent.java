package info.albertcode.domain.event;

/**
 * @Description: http 响应事件，overview = httpVersion + " " + statusCode + " " + reasonPhrase
 * @Author: Albert Shen
 */

public class HttpResponseEvent extends Event{
    private String httpVersion; // 服务器返回的 http 协议版本，如 HTTP/1.1
    private String statusCode; // 服务器返回的响应状态码 200
    private String reasonPhrase; // 服务器返回的状态代码的描述 OK

    public HttpResponseEvent() {
        this.type = "HttpResponse";
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
        this.overview = this.httpVersion + " " + this.statusCode
                + " " + this.reasonPhrase;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
        this.overview = this.httpVersion + " " + this.statusCode
                + " " + this.reasonPhrase;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
        this.overview = this.httpVersion + " " + this.statusCode
                + " " + this.reasonPhrase;
    }

    public void setOverview(String httpVersion, String statusCode, String reasonPhrase){
        this.setHttpVersion(httpVersion);
        this.setStatusCode(statusCode);
        this.setReasonPhrase(reasonPhrase);
    }

    public void setOverview(String overview){
        int firstBlank = overview.indexOf(" ");
        int secondBlank = overview.indexOf(" ", firstBlank + 1);
        this.setOverview(overview.substring(0, firstBlank),
                overview.substring(firstBlank + 1, secondBlank),
                overview.substring(secondBlank + 1));
    }
}
