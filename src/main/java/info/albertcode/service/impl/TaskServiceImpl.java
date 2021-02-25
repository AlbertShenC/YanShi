package info.albertcode.service.impl;

import info.albertcode.dao.encapsulation.CoreDaoEncapsulation;
import info.albertcode.domain.event.Event;
import info.albertcode.domain.task.Task;
import info.albertcode.service.ITaskService;
import info.albertcode.utils.exception.CustomException;
import info.albertcode.utils.taskServiceImpl.HttpRequestTaskServiceImpl;
import info.albertcode.utils.taskServiceImpl.RegisterForWebPageTaskServiceImpl;
import info.albertcode.utils.taskServiceImpl.RssGenerateTaskServiceImpl;
import info.albertcode.utils.taskServiceImpl.StringParserTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "taskService")
public class TaskServiceImpl implements ITaskService {
    private CoreDaoEncapsulation coreDaoEncapsulation;

    @Autowired
    public TaskServiceImpl(CoreDaoEncapsulation coreDaoEncapsulation) {
        this.coreDaoEncapsulation = coreDaoEncapsulation;
    }

    @Override
    public Event executeTask(Integer taskId) throws Exception {
        Task task = coreDaoEncapsulation.findTaskById(taskId);
        if (task == null){
            throw new CustomException("不存在id为 " + taskId + " 的任务，无法执行");
        }

        switch (task.getTypeEnum()){
            case HttpRequest:
                System.out.println("调用HttpRequest执行方法...");
                return executeHttpRequest(task);
            case StringParser:
                System.out.println("调用StringParser执行方法...");
                return executeStringParse(task);
            case RssGenerate:
                System.out.println("调用RssGenerate执行方法");
                return executeRssGenerate(task);
            case RegisterForWebPage:
                System.out.println("调用RegisterForWebPage执行方法");
                return executeRegisterForWebPage(task);
            default:
                System.out.println("调用具体执行方法错误...");
                return null;
        }
    }

    private Event saveEvent(Task task, Event event) throws CustomException {
        coreDaoEncapsulation.saveOrUpdateEventAndItsTask(event, task);
        return event;
    }

    private Event executeHttpRequest(Task task) throws Exception {
        Event event = HttpRequestTaskServiceImpl.executeHttpRequest(task);
        return saveEvent(task, event);
    }

    private Event executeStringParse(Task task) throws CustomException {
        Event event = StringParserTaskServiceImpl.executeStringParser(task);
        return saveEvent(task, event);
    }

    private Event executeRssGenerate(Task task) throws CustomException {
        Event event = RssGenerateTaskServiceImpl.executeRssGenerate(task);
        return saveEvent(task, event);
    }

    private Event executeRegisterForWebPage(Task task) throws CustomException {
        Event event = RegisterForWebPageTaskServiceImpl.executeRegisterForWebPage(task);
        return saveEvent(task, event);
    }
}
