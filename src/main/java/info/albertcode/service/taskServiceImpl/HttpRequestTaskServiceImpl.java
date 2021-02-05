package info.albertcode.service.taskServiceImpl;

import info.albertcode.domain.event.Event;
import info.albertcode.domain.event.HttpResponseEvent;
import info.albertcode.domain.request.HttpRequestRequest;
import info.albertcode.domain.task.Task;
import info.albertcode.utils.http.director.Director;
import info.albertcode.utils.http.domain.HttpRequestAndResponse;
import org.apache.http.NameValuePair;

import java.net.URISyntaxException;
import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class HttpRequestTaskServiceImpl {

    public static HttpRequestRequest prepareRequest(Task task){
        return new HttpRequestRequest(task.getRequest());
    }

    public static HttpRequestAndResponse httpRequestAndResponseEncapsulation(HttpRequestRequest request) throws URISyntaxException {
        Director director = new Director(request.getMethod())
                .uri(request.getUrl());

        String[] headers = request.getHeader().split("&");
        for (String header : headers){
            String[] nameAndValue = header.split("=");
            director.header(nameAndValue[0], nameAndValue[1]);
        }

        String[] parameters = request.getBody().split("&");
        for (String parameter : parameters){
            String[] nameAndValue = parameter.split("=");
            director.parameter(nameAndValue[0], nameAndValue[1]);
        }

        return director.build();
    }

    public static Event eventEncapsulation(HttpRequestAndResponse requestAndResponse) throws Exception {
        HttpResponseEvent event = new HttpResponseEvent();
        event.setSuccessful(true); // todo:成功条件判断
        event.setHttpVersion(requestAndResponse.getResponseProtocolVersion());
        event.setStatusCode(requestAndResponse.getResponseStatus());
        event.setReasonPhrase(requestAndResponse.getResponseReasonPhrase());

        String resultHeaders = "";
        List<NameValuePair> responseHeaders = requestAndResponse.getResponseHeaders();
        for (NameValuePair responseHeader : responseHeaders){
            resultHeaders = resultHeaders + responseHeader.getName() + "=" +
                    responseHeader.getValue() + "&";
        }
        event.setHeader(resultHeaders.substring(0, resultHeaders.length() - 1));

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
