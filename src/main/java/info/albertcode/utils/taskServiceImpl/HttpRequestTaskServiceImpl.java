package info.albertcode.utils.taskServiceImpl;

import info.albertcode.domain.event.Event;
import info.albertcode.domain.event.HttpResponseEvent;
import info.albertcode.domain.request.HttpRequestRequest;
import info.albertcode.domain.task.Task;
import info.albertcode.utils.http.director.Director;
import info.albertcode.utils.http.domain.HttpRequestAndResponse;
import info.albertcode.utils.pair.OneKeyOneValue;
import org.apache.http.NameValuePair;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class HttpRequestTaskServiceImpl {

    private static HttpRequestRequest prepareRequest(Task task){
        return new HttpRequestRequest(task.getRequest());
    }

    private static HttpRequestAndResponse httpRequestAndResponseEncapsulation(HttpRequestRequest request) throws URISyntaxException {
        Director director = new Director(request.getMethod())
                .uri(request.getUrl());

        if (request.getProxyPort() != null){
            if (request.getProxyDomain() != null) {
                director.proxy(request.getProxyDomain(), request.getProxyPort());
            } else {
                director.proxy(request.getProxyPort());
            }
        }

        OneKeyOneValue headers = new OneKeyOneValue(request.getHeader());
        Iterator<String> iteratorHeader = headers.getKeyIterator();
        while (iteratorHeader.hasNext()) {
            String key = iteratorHeader.next();
            String value = (String) headers.getValue(key);
            director.header(key, value);
        }

        OneKeyOneValue parameters = new OneKeyOneValue(request.getBody());
        Iterator<String> iteratorParameter = parameters.getKeyIterator();
        while (iteratorParameter.hasNext()){
            String key = iteratorParameter.next();
            String value = (String) headers.getValue(key);
            director.parameter(key, value);
        }

        return director.build();
    }

    /**
     * 将数据库的对象封装为执行所需的对象
     */
    private static Event eventEncapsulation(HttpRequestAndResponse requestAndResponse) throws Exception {
        HttpResponseEvent event = new HttpResponseEvent();
        event.setSuccessful(true); // todo:成功条件判断
        event.setHttpVersion(requestAndResponse.getResponseProtocolVersion());
        event.setStatusCode(requestAndResponse.getResponseStatus());
        event.setReasonPhrase(requestAndResponse.getResponseReasonPhrase());

        OneKeyOneValue resultHeaders = new OneKeyOneValue();
        List<NameValuePair> responseHeaders = requestAndResponse.getResponseHeaders();
        for (NameValuePair responseHeader : responseHeaders){
            resultHeaders.addValue(responseHeader.getName(), responseHeader.getValue());
        }
        event.setHeader(resultHeaders);

        event.setBody(requestAndResponse.getResponseEntity());
        return event;
    }

    public static Event executeHttpRequest(Task task) throws Exception{
        HttpRequestRequest request = prepareRequest(task);
        HttpRequestAndResponse requestAndResponse = httpRequestAndResponseEncapsulation(request);
        requestAndResponse.execute();
        return eventEncapsulation(requestAndResponse);
    }
}
