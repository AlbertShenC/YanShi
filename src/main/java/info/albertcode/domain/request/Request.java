package info.albertcode.domain.request;

/**
 * @Description: 请求：每个Task（任务）需要一个请求作为输入
 * @Author: Albert Shen
 */

public class Request {
    protected Integer id;
    protected String type; // 事件的类型，如http请求，http响应，创建时由子类负责
    protected String overview; // 事件的总览，大部分子类需要重写此类
    protected String header; // 事件的头部
    protected String body; // 事件体

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", overview='" + overview + '\'' +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}