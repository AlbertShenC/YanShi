package info.albertcode.controller;

import info.albertcode.dao.ITaskDao;
import info.albertcode.domain.task.Task;
import info.albertcode.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Controller
@RequestMapping("/task")
public class TaskController {
    private ITaskService taskService;

    private ITaskDao taskDao;

    @Autowired
    public TaskController(ITaskService taskService, ITaskDao taskDao) {
        this.taskService = taskService;
        this.taskDao = taskDao;
    }

    @RequestMapping("/execute")
    public String execute(Integer taskId) throws Exception {
        taskService.executeTask(taskId);
        return "result";
    }

    @RequestMapping("/testPre")
    public String testPre(Integer taskId) throws Exception {
        List<Task> tasks = taskDao.findSucceedingTasksOfSpecificTaskById(taskId);
        System.out.println(tasks);
        return "result";
    }

    @RequestMapping("/findTask")
    public String findTask(Integer taskId) throws Exception {
        Task task = taskDao.findTaskById(taskId);
        System.out.println(task);
        return "result";
    }
}
