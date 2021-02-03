package info.albertcode.utils.http.domain;

import org.apache.http.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: post 请求封装类
 * @Author: Albert Shen
 */

public class HttpPostRequestAndResponse extends HttpRequestAndResponse{
    private HttpPost request;

    public HttpPostRequestAndResponse() {
        this.request = new HttpPost();
        this.params = new ArrayList<NameValuePair>();
        this.addRequestHeader("User-Agent", "YanShi");
    }

    @Override
    public HttpRequestBase getRequest() {
        return request;
    }

    @Override
    public String getUri() {
        URI uri = request.getURI();
        return uri.getScheme() + "://" +
                uri.getHost() + uri.getPath();
    }

    @Override
    public List<NameValuePair> getRequestHeaders() {
        Header[] headers = this.request.getAllHeaders();
        return List.of(headers);
    }

    @Override
    public void addRequestHeader(String name, String value) {
        this.request.setHeader(name, value);
    }

    @Override
    public void clearRequestHeader() {
        Header[] headers = this.request.getAllHeaders();
        for (Header header : headers){
            this.request.removeHeader(header);
        }
        this.addRequestHeader("User-Agent", "YanShi");
    }

    @Override
    public void execute() throws IOException {
        super.prepareExecute();
        String oldUri = this.getUri();
        try {
            this.request.setURI(new URIBuilder(oldUri).build());
        } catch (Exception e){
            // todo: 自定义异常
        }
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, Consts.UTF_8);
        request.setEntity(formEntity);
        response = httpClient.execute(request);
        responseEntity = response.getEntity();
    }
}
