package info.albertcode.domain.procedure;

/**
 * @Description: 储存流程启动时刻，两种策略
 *                  1、定时在某个时刻运行，如每天12点23分，每周五22点，每月1号10点
 *                  2、每间隔一段时间运行，如每1个小时运行一次
 * @Author: Albert Shen
 */

public class InitTime {
    private Integer month; // 在几月启动，-1表示此配置无效
    private Integer day; // 在几号启动，-1表示此配置无效
    private Integer weekDay; // 在周几启动，-1表示此配置无效
    private Integer hour; // 在几点启动，-1表示此配置无效
    private Integer minute; // 在几分启动，-1表示此配置无效

    private Integer dayInterval; // 每隔几天启动，-1表示此配置无效
    private Integer hourInterval; // 每隔几小时启动，-1表示此配置无效
    private Integer minuteInterval; // 每隔几分钟启动，-1表示此配置无效

    //todo:时间越界的判断，抛出自定义异常

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getDayInterval() {
        return dayInterval;
    }

    public void setDayInterval(Integer dayInterval) {
        this.dayInterval = dayInterval;
    }

    public Integer getHourInterval() {
        return hourInterval;
    }

    public void setHourInterval(Integer hourInterval) {
        this.hourInterval = hourInterval;
    }

    public Integer getMinuteInterval() {
        return minuteInterval;
    }

    public void setMinuteInterval(Integer minuteInterval) {
        this.minuteInterval = minuteInterval;
    }
}
