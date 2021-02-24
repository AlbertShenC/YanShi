package info.albertcode.controller;

import info.albertcode.dao.IProcedureDao;
import info.albertcode.dao.IRequestDao;
import info.albertcode.dao.ITaskDao;
import info.albertcode.domain.procedure.Procedure;
import info.albertcode.domain.request.Request;
import info.albertcode.domain.task.Task;
import info.albertcode.service.ITaskService;
import info.albertcode.utils.Number;
import info.albertcode.utils.enums.ETaskType;
import info.albertcode.utils.exception.CustomException;
import info.albertcode.utils.json.OneKeyOneValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Controller
@RequestMapping("/task")
public class TaskController {
    private IProcedureDao procedureDao;
    private ITaskService taskService;
    private ITaskDao taskDao;
    private IRequestDao requestDao;

    @Autowired
    public TaskController(IProcedureDao procedureDao, ITaskService taskService, ITaskDao taskDao, IRequestDao requestDao) {
        this.procedureDao = procedureDao;
        this.taskService = taskService;
        this.taskDao = taskDao;
        this.requestDao = requestDao;
    }

    @GetMapping(value = "/edit")
    public ModelAndView getCreatePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "新增任务");
        modelAndView.addObject("isCreate", true);
        modelAndView.addObject("enumList", ETaskType.values());
        modelAndView.setViewName("/task/edit");
        return modelAndView;
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView getUpdatePage(@PathVariable Integer id) throws CustomException {
        Task task = taskDao.findTaskById(id);
        if (task == null){
            throw new CustomException("不存在id为" + id + "的任务");
        }
        Procedure procedure = procedureDao.findProcedureWithEntryTask(id);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "修改任务:" + task.getName());
        modelAndView.addObject("isCreate", false);
        modelAndView.addObject("taskId", id);
        modelAndView.addObject("type", task.getTypeEnum());
        modelAndView.addObject("enumList", ETaskType.values());
        modelAndView.addObject("name", task.getName());
        if (procedure != null) {
            modelAndView.addObject("procedureId", procedure.getId());
        } else if (task.getInputTask() != null){
            modelAndView.addObject("inputTaskId", task.getInputTaskId());
            modelAndView.addObject("inputEventProperty", task.getInputEventProperty());
        }
        modelAndView.addObject("requestOverview", task.getRequest().getOverview());
        modelAndView.addObject("requestHeader", task.getRequest().getHeader());
        modelAndView.addObject("requestBody", task.getRequest().getBody());
        modelAndView.setViewName("/task/edit");
        return modelAndView;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public String createTask(Task task, String procedureId, String inputTaskId) {
        requestDao.saveRequest(task.getRequest());
        if (Number.isNumeric(procedureId)){
            Procedure procedure = procedureDao.findProcedureById(Integer.parseInt(procedureId));
            taskDao.saveTask(task);
            procedure.setEntryTask(task);
            procedureDao.updateProcedure(procedure);
        } else if (Number.isNumeric(inputTaskId)){
            Task inputTask = taskDao.findTaskById(Integer.parseInt(inputTaskId));
            task.setInputTask(inputTask);
            taskDao.saveTask(task);
        } else {
            System.out.println("无数字，错误");
        }
        OneKeyOneValue keyValue = new OneKeyOneValue();
        keyValue.addKeyValue("message", "success");
        return keyValue.toJsonObject().toJSONString();
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseBody
    public String updateTask(@PathVariable Integer id, Task task, String procedureId, String inputTaskId) {
        Task taskInDatabase = taskDao.findTaskById(id);
        Request requestInDatabase = taskInDatabase.getRequest();
        task.getRequest().setId(requestInDatabase.getId());
        task.setId(id);
        requestDao.updateRequest(task.getRequest());
        if (Number.isNumeric(procedureId)){
            Procedure procedure = procedureDao.findProcedureById(Integer.parseInt(procedureId));
            taskDao.updateTask(task);
            procedure.setEntryTask(task);
            procedureDao.updateProcedure(procedure);
        } else if (Number.isNumeric(inputTaskId)){
            Task inputTask = taskDao.findTaskById(Integer.parseInt(inputTaskId));
            task.setInputTask(inputTask);
            taskDao.updateTask(task);
        } else {
            System.out.println("无数字，错误");
        }
        OneKeyOneValue keyValue = new OneKeyOneValue();
        keyValue.addKeyValue("message", "success");
        return keyValue.toJsonObject().toJSONString();
    }

    @RequestMapping("/execute")
    public String execute(Integer taskId) throws Exception {
        taskService.executeTask(taskId);
        return "result";
    }
}
