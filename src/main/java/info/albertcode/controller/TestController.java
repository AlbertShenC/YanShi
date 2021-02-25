package info.albertcode.controller;

import info.albertcode.dao.ITaskDao;
import info.albertcode.domain.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    ITaskDao taskDao;

    @RequestMapping("")
    public String test(String name) {
        List<Task> task = taskDao.findAllTaskByFuzzyName("%" + name + "%");
        System.out.println(task);
        return "result";
    }
}
