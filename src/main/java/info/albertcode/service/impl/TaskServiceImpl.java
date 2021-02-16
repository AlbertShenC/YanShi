package info.albertcode.service.impl;

import info.albertcode.dao.IEventDao;
import info.albertcode.dao.ITaskDao;
import info.albertcode.domain.event.Event;
import info.albertcode.domain.task.Task;
import info.albertcode.service.ITaskService;
import info.albertcode.utils.taskServiceImpl.HttpRequestTaskServiceImpl;
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

    @Autowired
    private ITaskDao taskDao;
    @Autowired
    private IEventDao eventDao;

    @Override
    public Event execute(Integer taskId) throws Exception {
        Task task = taskDao.findTaskById(taskId);

        switch (task.getType()){
            case "HttpRequest":
                System.out.println("调用HttpRequest执行方法...");
                return executeHttpRequest(task);
            case "StringParser":
                System.out.println("调用StringParser执行方法...");
                return executeStringParse(task);
            case "RssGenerate":
                System.out.println("调用RssGenerate执行方法");
                return executeRssGenerate(task);
            default:
                System.out.println("调用具体执行方法错误...");
                return null;
        }
    }

    private Event saveEvent(Task task, Event event){
        event.setBelongedTask(task.getName());
        eventDao.saveEvent(event);
        return event;
    }

    private Event executeHttpRequest(Task task) throws Exception {
        Event event = HttpRequestTaskServiceImpl.executeHttpRequest(task);
        return saveEvent(task, event);
    }

    private Event executeStringParse(Task task) {
        Event event = StringParserTaskServiceImpl.executeStringParser(task);
        return saveEvent(task, event);
    }

    private Event executeRssGenerate(Task task) {
        Event event = RssGenerateTaskServiceImpl.executeRssGenerate(task);
        return saveEvent(task, event);
    }
}
