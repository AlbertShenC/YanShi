package info.albertcode;

import info.albertcode.utils.http.director.Director;
import info.albertcode.utils.http.domain.HttpRequestAndResponse;
import org.apache.http.NameValuePair;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class HttpTest {
    public static void main(String[] args) throws Exception {
        System.out.println("\n============================\n");
        HttpRequestAndResponse requestAndResponse = new Director("get")
                .uri("http://4s.quyixian.com/SQB/?a=1")
//                .proxy(8888)
                .parameter("ie", "UTF-8")
                .parameter("wd", "test")
                .header("User-Agent", "Chrome")
                .build();
        requestAndResponse.execute();
        System.out.println("请求路径：" + requestAndResponse.getUri());
        System.out.println("-------------------------------------");
        List<NameValuePair> parameters = requestAndResponse.getParameters();
        System.out.println("请求参数数量： " + parameters.size());
        System.out.println("请求参数：");
        for (NameValuePair parameter : parameters){
            System.out.println(parameter.getName() + ":" + parameter.getValue());
        }
        System.out.println("-------------------------------------");
        System.out.println("请求提交的完整URI：" + requestAndResponse.getRequest().getURI());
        System.out.println("-------------------------------------");
        List<NameValuePair> headers = requestAndResponse.getRequestHeaders();
        System.out.println("请求头部数量： " + headers.size());
        System.out.println("请求头部：");
        for (NameValuePair header : headers){
            System.out.println(header.getName() + ":" + header.getValue());
        }
        System.out.println("-------------------------------------");
        System.out.println("response协议版本：" + requestAndResponse.getResponseProtocolVersion());
        System.out.println("-------------------------------------");
        System.out.println("response状态码：" + requestAndResponse.getResponseStatus());
        System.out.println("-------------------------------------");
        System.out.println("response协议版本：" + requestAndResponse.getResponseReasonPhrase());
        System.out.println("-------------------------------------");
        List<NameValuePair> responseHeaders = requestAndResponse.getResponseHeaders();
        System.out.println("响应头部数量： " + responseHeaders.size());
        System.out.println("响应头部：");
        for (NameValuePair responseHeader : responseHeaders){
            System.out.println(responseHeader.getName() + ":" + responseHeader.getValue());
        }
        System.out.println("------------------响应Body--------------------");
        System.out.println(requestAndResponse.getResponseEntity());
        System.out.println("------------------移除请求Header--------------------");
        requestAndResponse.clearRequestHeader();
        List<NameValuePair> requestHeadersAfter = requestAndResponse.getRequestHeaders();
        System.out.println("移除后请求头部数量： " + requestHeadersAfter.size());
        System.out.println("移除后请求头部：");
        for (NameValuePair requestHeader : requestHeadersAfter) {
            System.out.println(requestHeader.getName() + ":" + requestHeader.getValue());
        }
        System.out.println("------------------移除请求参数--------------------");
        requestAndResponse.clearParameter();
        List<NameValuePair> requestParamsAfter = requestAndResponse.getParameters();
        System.out.println("移除后请求参数数量： " + requestParamsAfter.size());
        System.out.println("移除后请求参数：");
        for (NameValuePair requestParam : requestParamsAfter) {
            System.out.println(requestParam.getName() + ":" + requestParam.getValue());
        }
    }
}
