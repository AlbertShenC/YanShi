package info.albertcode;

import info.albertcode.domain.procedure.InitTime;
import info.albertcode.utils.enums.ETaskType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class TempTest {
    public static void main(String[] args) {
        ETaskType type = ETaskType.RssGenerate;

        for (ETaskType e: ETaskType.values()){
            System.out.println(e);
            System.out.println(e.getValue());
        }
    }
}
