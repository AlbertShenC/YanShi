package info.albertcode.domain.event;

import info.albertcode.utils.enums.EEventType;

import java.util.Date;

/**
 * @Description: 事件：用于作为任务的输入输出使用
 * @Author: Albert Shen
 */

public abstract class Event {
    private Integer id;
    private Date generatedTime; // 事件生成事件，由抽象类负责，子类不具有修改权限
    private boolean successful; // 事件是否为成功执行，由抽象类负责，子类不具有修改权限
    protected EEventType type; // 事件的类型，如http请求，http相应，由子类在构造时自动生成，对外仅提供get方法
    protected String overview; // 事件的总览
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

    public EEventType getType() {
        return type;
    }

    public String getOverview() {
        return overview;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", type=" + type +
                ", overview='" + overview + '\'' +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
