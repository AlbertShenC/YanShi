package info.albertcode.utils.http.builder;

import info.albertcode.utils.http.domain.HttpGetRequestAndResponse;

/**
 * @Description: 建造 Get 请求的建造者类
 * @Author: Albert Shen
 */

public class HttpGetRequestBuilder extends HttpRequestBuilder{
    public HttpGetRequestBuilder() {
        this.requestAndResponse = new HttpGetRequestAndResponse();
    }
}
