package info.albertcode.utils.http.builder;

import info.albertcode.utils.http.domain.HttpPostRequestAndResponse;

/**
 * @Description:  建造 Post 请求的建造者类
 * @Author: Albert Shen
 */

public class HttpPostRequestBuilder extends HttpRequestBuilder{
    public HttpPostRequestBuilder() {
        this.requestAndResponse = new HttpPostRequestAndResponse();
    }
}
