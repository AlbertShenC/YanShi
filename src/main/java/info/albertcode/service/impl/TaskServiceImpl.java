package info.albertcode.service.impl;

import info.albertcode.dao.IEventDao;
import info.albertcode.dao.IRequestDao;
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
    private IRequestDao requestDao;
    @Autowired
    private IEventDao eventDao;

    // 临时模拟dao层使用
    private Task getTask(Integer taskId){
        Request request = requestDao.findRequestById(1); // todo:通过Task获取到request的id
        System.out.println(request);
        System.out.println("taskId = " + taskId);

        Task task = new HttpRequestTask();
        task.setId(taskId);
        task.setRequest(request);

        return task;
    }

    @Override
    public Event execute(Integer taskId) throws Exception {
        Task task = getTask(taskId);

        if (task.getType().equals("HttpRequest")){
            System.out.println("调用HttpRequest执行方法...");
            return executeHttpRequest(task);
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
}
