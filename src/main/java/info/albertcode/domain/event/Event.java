package info.albertcode.domain.event;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 事件：每个Task（任务）会产生一个输出事件
 * @Author: Albert Shen
 */

public class Event implements Serializable {
    private Integer id;
    protected Date generatedTime; // 事件产生时间
    protected boolean successful; // 任务是否成功执行
    protected String type; // 事件的类型，如http响应，创建时由子类负责
    protected String overview; // 事件的总览，大部分子类需要重写此类
    protected String header; // 事件的头部
    protected String body; // 事件体

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getGeneratedTime() {
        return generatedTime;
    }

    public void setGeneratedTime(Date generatedTime) {
        this.generatedTime = generatedTime;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
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
        return "Event{" +
                "id=" + id +
                ", generatedTime=" + generatedTime +
                ", successful=" + successful +
                ", type='" + type + '\'' +
                ", overview='" + overview + '\'' +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
