package info.albertcode;

import info.albertcode.domain.procedure.InitTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class TempTest {
    public static void main(String[] args) {
        InitTime time = new InitTime();
        LocalDateTime lastExecuteTime = LocalDateTime.now().minusYears(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("上次执行时刻：" + lastExecuteTime.format(formatter));

        System.out.println("手动执行：" + time.getNextExecuteDate(lastExecuteTime));

        time.setMinuteInterval(10);
        System.out.println("间隔十分钟执行：" + time.getNextExecuteDate(lastExecuteTime).format(formatter));

        time.setMinuteInterval(0);
        time.setHourInterval(0);
        time.setDayInterval(7);
        System.out.println("间隔一周执行：" + time.getNextExecuteDate(lastExecuteTime).format(formatter));

        time.setMinute(0);
        System.out.println("每小时的0分时刻执行：" + time.getNextExecuteDate(lastExecuteTime).format(formatter));

        time.setHour(0);
        time.setWeekDay(1);
        System.out.println("每周一的0点0分时刻执行：" + time.getNextExecuteDate(lastExecuteTime).format(formatter));

        time.setDay(10);
        System.out.println("每月10号的0点0分执行：" + time.getNextExecuteDate(lastExecuteTime).format(formatter));

        time.setMonth(1);
        System.out.println("每年的1月10号的0点0分执行：" + time.getNextExecuteDate(lastExecuteTime).format(formatter));
    }
}
