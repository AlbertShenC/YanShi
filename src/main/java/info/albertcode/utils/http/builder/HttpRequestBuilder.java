package info.albertcode.utils.http.builder;

import info.albertcode.utils.http.domain.HttpRequestAndResponse;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Description: 建造Http请求的抽象父类
 * @Author: Albert Shen
 */

public abstract class HttpRequestBuilder {
    protected HttpRequestAndResponse requestAndResponse;

    public HttpRequestAndResponse getRequestAndResponse() {
        return requestAndResponse;
    }

    // todo: 异常处理
    public void buildUri(String uri) throws URISyntaxException {
        this.buildUri(new URI(uri));
    }

    public void buildUri(URI uri) {
        HttpRequestBase request = requestAndResponse.getRequest();
        request.setURI(uri);
    }

    public void addHeader(String name, String value){
        this.requestAndResponse.addRequestHeader(name, value);
    }

    public void addParameter(String name, String value){
        this.requestAndResponse.addParameter(name, value);
    }

    public void buildProxy(String proxyDomain, Integer proxyPort) {
        requestAndResponse.setProxy(new HttpHost(proxyDomain, proxyPort));
    }

    public void buildProxy(Integer proxyPort) {
        buildProxy("localhost", proxyPort);
    }
}
