package info.albertcode.utils.taskServiceImpl;

import info.albertcode.domain.event.Event;
import info.albertcode.domain.event.RegisterForWebPageEvent;
import info.albertcode.domain.request.RegisterForWebPageRequest;
import info.albertcode.domain.request.Request;
import info.albertcode.domain.request.StringParserRequest;
import info.albertcode.domain.task.Task;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class RegisterForWebPageTaskServiceImpl extends TaskWithInputEvent{
    private static RegisterForWebPageRequest prepareRequest(Task task){
        return new RegisterForWebPageRequest(task.getRequest());
    }

    private static RegisterForWebPageEvent prepareOutputEvent(Task task){
        Event event = task.getOutputEvent();
        if (event == null){
            return new RegisterForWebPageEvent("");
        } else {
            return new RegisterForWebPageEvent(task.getOutputEvent());
        }
    }

    public static Event executeRegisterForWebPage(Task task){
        String stringToParser = getInputEventString(task);
        RegisterForWebPageEvent outputEvent = prepareOutputEvent(task);

        outputEvent.setBody(stringToParser);

        return outputEvent;
    }
}
