package info.albertcode.domain.procedure;

import java.time.LocalDateTime;

/**
 * @Description: 储存流程启动时刻，三种策略
 *               1、定时在某个时刻运行，如每天12点23分，每周五22点，每月1号10点
 *               2、每间隔一段时间运行，如每1个小时运行一次
 *               3、手动启动
 * Procedure判断定时启动时刻的顺序：
 * (minute有效)？(
 *      (hour有效)?(
 *          (day有效)?(
 *              (month有效)?(每年的x月x号x点x分执行):(每月的x号x点x分执行)
 *          ):(
 *              (weekDay有效)?(每周的周x的x点x分执行):(每天的x点x分执行)
 *          )
 *      ):(每小时的x分执行)
 * ):(
 *      (minuteInterval有效)?(
 *          (hourInterval有效)?(
 *              (dayInterval有效)?(每隔x天x小时x分钟执行):(每隔x小时x分钟执行)
 *          ):(每隔x分钟执行)
 *      ):(手动启动)
 * )
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

    public InitTime() {
        this.month = -1;
        this.day = -1;
        this.weekDay = -1;
        this.hour = -1;
        this.minute = -1;
        this.dayInterval = -1;
        this.hourInterval = -1;
        this.minuteInterval = -1;
    }

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

    public Integer getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Integer weekDay) {
        this.weekDay = weekDay;
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

    /**
     * 返回下一个执行时刻
     * @param lastExecuteDate 上一次的执行时刻
     * @return 根据上一次执行时刻计算出来的，离当前时刻最近，且晚于当前时刻的，
     *         符合成员变量的要求的（即某时刻定时执行，或者每隔一段时间执行）下一次执行时间
     *         若成员变量全部无效，则返回 null，说明此流程没有定时执行的设置，只能手动启动
     */
    public LocalDateTime getNextExecuteDate(LocalDateTime lastExecuteDate){
        LocalDateTime nextExecuteDate;
        //todo：用户可以自定义当前时区
        if (minute != -1){
            if (hour != -1){
                if (day != -1){
                    if (month != -1){ // 月 日 时 分 有效
                        nextExecuteDate = LocalDateTime.of(lastExecuteDate.getYear(),
                                month, day, hour, minute);
                        while (nextExecuteDate.isBefore(LocalDateTime.now())){
                            nextExecuteDate = nextExecuteDate.plusYears(1);
                        }
                    } else { // 日 时 分 有效
                        nextExecuteDate = LocalDateTime.of(lastExecuteDate.getYear(),
                                lastExecuteDate.getMonth(), day, hour, minute);
                        while (nextExecuteDate.isBefore(LocalDateTime.now())){
                            nextExecuteDate = nextExecuteDate.plusMonths(1);
                        }
                    }
                } else if (weekDay != -1){ // 周 时 分 有效
                    nextExecuteDate = LocalDateTime.of(lastExecuteDate.getYear(),
                            lastExecuteDate.getMonth(),
                            lastExecuteDate.getDayOfMonth(), hour, minute)
                            .plusDays(weekDay - lastExecuteDate.getDayOfWeek().getValue());
                    while (nextExecuteDate.isBefore(LocalDateTime.now())){
                        nextExecuteDate = nextExecuteDate.plusWeeks(1);
                    }
                } else { // 时 分 有效
                    nextExecuteDate = LocalDateTime.of(lastExecuteDate.getYear(),
                            lastExecuteDate.getMonth(),
                            lastExecuteDate.getDayOfMonth(), hour, minute);
                    while (nextExecuteDate.isBefore(LocalDateTime.now())){
                        nextExecuteDate = nextExecuteDate.plusDays(1);
                    }
                }
            } else { // 分有效
                nextExecuteDate = LocalDateTime.of(lastExecuteDate.getYear(),
                        lastExecuteDate.getMonth(),
                        lastExecuteDate.getDayOfMonth(),
                        lastExecuteDate.getHour(), minute);
                while (nextExecuteDate.isBefore(LocalDateTime.now())){
                    nextExecuteDate = nextExecuteDate.plusHours(1);
                }
            }
        } else { // 可能间隔时间有效
            if (minuteInterval != -1){
                nextExecuteDate = lastExecuteDate;
                if (hourInterval != -1){
                    if (dayInterval != -1){ // 间隔日 时 分 有效
                        while (nextExecuteDate.isBefore(LocalDateTime.now())){
                            nextExecuteDate = nextExecuteDate.plusDays(dayInterval)
                                    .plusHours(hourInterval)
                                    .plusMinutes(minuteInterval);
                        }
                    } else { // 间隔时 分 有效
                        while (nextExecuteDate.isBefore(LocalDateTime.now())){
                            nextExecuteDate = nextExecuteDate.plusHours(hourInterval)
                                    .plusMinutes(minuteInterval);
                        }
                    }
                } else { // 间隔分 有效
                    while (nextExecuteDate.isBefore(LocalDateTime.now())){
                        nextExecuteDate = nextExecuteDate.plusMinutes(minuteInterval);
                    }
                }
            } else { // 所有数据均无效
                nextExecuteDate = null;
            }
        }
        return nextExecuteDate;
    }
}
