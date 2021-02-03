package info.albertcode.utils.http.domain;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: get 请求封装类
 * @Author: Albert Shen
 */

public class HttpGetRequestAndResponse extends HttpRequestAndResponse{
    private HttpGet request; // 请求对象

    public HttpGetRequestAndResponse() {
        this.request = new HttpGet();
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
            URIBuilder uriBuilder = new URIBuilder(oldUri);
            for (NameValuePair parameter : this.params){
                uriBuilder.setParameter(parameter.getName(), parameter.getValue());
            }
            this.request.setURI(uriBuilder.build());
        } catch (Exception e){
            // todo: 自定义异常
        }
        response = httpClient.execute(request);
        responseEntity = response.getEntity();
    }
}
