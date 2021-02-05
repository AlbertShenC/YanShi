package info.albertcode.controller;

import info.albertcode.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private ITaskService taskService;

    @RequestMapping("/execute")
    public String execute(Integer taskId) throws Exception {
        taskService.execute(taskId);
        return "result";
    }
}
