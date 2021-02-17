package info.albertcode.domain.event;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 事件：每个Task（任务）会产生一个输出事件
 * @Author: Albert Shen
 */

public class Event implements Serializable {
    protected Integer id;
    protected Date generatedTime; // 事件产生时间
    protected String belongedTaskName; // 所属事件的名称，仅用于展示，不建议用于查找其对应的Task
    // 方便用户在前端判断此事件是由哪一个Task生成的，因为每个Task只会记录其生成的最后一个Event的id
    // 具体场景，如：用户编写了相关模块，运行不符合期望，于是想查看一下某个Task前几次执行时产生的结果
    // 那么就需要在展示Event时，同时说明其由哪一个Task所产生
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

    public String getBelongedTaskName() {
        return belongedTaskName;
    }

    public void setBelongedTaskName(String belongedTaskName) {
        this.belongedTaskName = belongedTaskName;
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
                ", belongedTaskName='" + belongedTaskName + '\'' +
                ", successful=" + successful +
                ", type='" + type + '\'' +
                ", overview='" + overview + '\'' +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
