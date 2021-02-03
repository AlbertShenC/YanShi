package info.albertcode.utils.http.director;

import info.albertcode.utils.http.builder.HttpGetRequestBuilder;
import info.albertcode.utils.http.builder.HttpPostRequestBuilder;
import info.albertcode.utils.http.builder.HttpRequestBuilder;
import info.albertcode.utils.http.domain.HttpRequestAndResponse;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class Director {
    private HttpRequestBuilder builder;

    public Director(String requestMethod){
        if (requestMethod.equalsIgnoreCase("get")){
            builder = new HttpGetRequestBuilder();
        } else if (requestMethod.equalsIgnoreCase("post")){
            builder = new HttpPostRequestBuilder();
        } else {
            //todo: 自定义异常——请求method并非定义中的方法
        }
    }

    // todo: 异常处理
    public Director uri(String uri) throws URISyntaxException {
        builder.buildUri(uri);
        return this;
    }

    public Director uri(URI uri){
        builder.buildUri(uri);
        return this;
    }

    public Director header(String name, String value){
        builder.addHeader(name, value);
        return this;
    }

    public Director parameter(String name, String value){
        builder.addParameter(name, value);
        return this;
    }

    public Director proxy(String proxyDomain, int proxyPort){
        builder.buildProxy(proxyDomain, proxyPort);
        return this;
    }

    public Director proxy(Integer proxyPort){
        builder.buildProxy(proxyPort);
        return this;
    }

    public HttpRequestAndResponse build(){
        return builder.getRequestAndResponse();
    }
}
