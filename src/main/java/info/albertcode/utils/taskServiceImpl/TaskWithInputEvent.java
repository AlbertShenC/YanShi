package info.albertcode.utils.taskServiceImpl;

import info.albertcode.domain.event.Event;
import info.albertcode.domain.task.Task;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class TaskWithInputEvent {
    protected static String getInputEventString(Task task){
        Event inputEvent = task.getInputEvent();
        String stringToParser = null;
        switch (task.getInputEventProperty()){
            case "overview":
                stringToParser = inputEvent.getOverview();
                break;
            case "header":
                stringToParser = inputEvent.getHeader();
                break;
            case "body":
                stringToParser = inputEvent.getBody();
                break;
            default:
                // todo:自定义异常，获取的Event部分名称错误
        }
        return stringToParser;
    }
}
