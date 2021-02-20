package info.albertcode.domain.task;

import info.albertcode.utils.enums.ETaskType;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class RssGenerateTask extends Task{
    public RssGenerateTask() {
        this.typeEnum = ETaskType.RssGenerate;
    }
}
