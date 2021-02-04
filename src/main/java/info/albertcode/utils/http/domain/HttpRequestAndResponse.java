package info.albertcode.utils.http.domain;

import org.apache.http.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

/**
 * @Description: 封装Http请求与响应，并发起Http请求
 * @Author: Albert Shen
 */

public abstract class HttpRequestAndResponse {
    protected List<NameValuePair> params; // 参数列表
    protected CloseableHttpClient httpClient; // 请求发起客户端
    protected HttpHost proxy; // 代理
    protected DefaultProxyRoutePlanner routePlanner; // 代理管理
    protected CloseableHttpResponse response; // 响应
    protected HttpEntity responseEntity; // 响应实体

    // 请求相关操作，请求允许读取、修改

    /**
     * 获取由Apache封装完成的请求对象，后续代码尽量不用
     */
    public abstract HttpRequestBase getRequest();

    /**
     * 获取请求路径，形式为： http(s)://域名:端口/路径
     * 将会自动删除 url 后的参数，如 https://www.baidu.com/s?ie=UTF-8&wd=Albert 会被强制修改为
     * https://www.baidu.com/s
     * 参数需要使用参数相关方法进行查询与修改
     * @return
     */
    public abstract String getUri();

    /**
     * 获取【键值对形式】的【所有】【请求头部】，所有键值被封装至列表中
     */
    public abstract List<NameValuePair> getRequestHeaders();

    /**
     * 添加【请求头部】，请求头部以键值对形式组织；如果添加的头部 键 已经存在，则将其替换为现在传入的键值对
     * @param name 请求头部键
     * @param value 请求头部值
     */
    public abstract void addRequestHeader(String name, String value);

    /**
     * 清空【请求头部】所有键值对
     */
    public abstract void clearRequestHeader();

    /**
     * 获取【键值对形式】的【所有】【参数】，所有键值被封装至列表中
     */
    public List<NameValuePair> getParameters() {
        return params;
    }

    /**
     * 添加【参数】，参数由键值对形式组织，允许添加相同参数
     * @param name 参数键
     * @param value 参数值
     */
    public void addParameter(String name, String value) {
        params.add(new BasicNameValuePair(name, value));
    }

    /**
     * 清空所有【参数】
     * 在 get 请求中，【参数】代表url后的查询键值对，
     * 如 get https://www.baidu.com/s?ie=UTF-8&wd=Albert 中，
     * 【参数】为 ie:UTF-8 与 wd:Albert
     * 在 post 请求中，【参数】代表请求体中的参数列表
     */
    public void clearParameter(){
        params.clear();
    }

    // 执行所需配置

    /**
     * 获取由Apache封装完成的代理主机地址与端口
     */
    public HttpHost getProxy() {
        return proxy;
    }

    /**
     * 返回由Apache封装完成的代理主机地址与端口
     */
    public void setProxy(HttpHost proxy) {
        this.proxy = proxy;
    }

    /**
     * 创建请求客户端，设置代理
     */
    protected void prepareExecute(){
        if (proxy != null){
            routePlanner = new DefaultProxyRoutePlanner(proxy);
            httpClient = HttpClients.custom()
                    .setRoutePlanner(routePlanner)
                    .build();
        } else {
            httpClient = HttpClients.createDefault();
        }
    }

    /**
     * 发送Http请求，并将结果封装至response属性中
     * @throws IOException
     */
    public abstract void execute() throws IOException;

    // 相应相关操作，只允许读取，不可修改

    /**
     * 获取由Apache封装完成的响应对象，后续代码尽量不用
     */
    public CloseableHttpResponse getResponse() {
        return response;
    }

    /**
     * 获取服务器响应 http 版本，如 HTTP/1.0 与 HTTP/1.1
     */
    public String getResponseProtocolVersion(){
        return this.response.getProtocolVersion().toString();
    }

    /**
     * 获取服务器返回的状态码，如 404
     */
    public String getResponseStatus(){
        return String.valueOf(this.response.getStatusLine().getStatusCode());
    }

    /**
     * 获取服务器返回的状态描述，如 Page Not Found
     */
    public String getResponseReasonPhrase(){
        return this.response.getStatusLine().getReasonPhrase();
    }

    /**
     * 获取【键值对形式】的【所有】【响应头部】，所有键值被封装至列表中
     */
    public List<NameValuePair> getResponseHeaders(){
        Header[] headers = this.response.getAllHeaders();
        return List.of(headers);
    }

    /**
     * 获取响应体，如请求页面时，返回为 html 页面
     */
    public String getResponseEntity() throws Exception {
        try {
            return EntityUtils.toString(responseEntity);
        } catch (IOException e) {
            // todo: 自定义异常处理
            throw new  Exception("body 为空");
        }
    }
}
