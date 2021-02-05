package info.albertcode.service.impl;

import info.albertcode.dao.IEventDao;
import info.albertcode.dao.IRequestDao;
import info.albertcode.dao.ITaskDao;
import info.albertcode.domain.event.Event;
import info.albertcode.domain.request.Request;
import info.albertcode.domain.task.HttpRequestTask;
import info.albertcode.domain.task.Task;
import info.albertcode.service.ITaskService;
import info.albertcode.service.taskServiceImpl.HttpRequestTaskServiceImpl;
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

        if (task.getType().equals("HttpRequest")){
            System.out.println("调用HttpRequest执行方法...");
            return executeHttpRequest(task);
        } else if (task.getType().equals("StringParser")){
            System.out.println("调用StringParser执行方法...");
            return executeStringParse(task);
        } else {
            System.out.println("调用具体执行方法错误...");
            return null;
        }
    }

    private Event executeHttpRequest(Task task) throws Exception {
        Event event = HttpRequestTaskServiceImpl.executeHttpRequest(task);
        eventDao.saveEvent(event);
        return event;
    }

    private Event executeStringParse(Task task) throws Exception{
        return null;
    }
}
