package info.albertcode.dao.encapsulation;

import info.albertcode.dao.IEventDao;
import info.albertcode.dao.IProcedureDao;
import info.albertcode.dao.IRequestDao;
import info.albertcode.dao.ITaskDao;
import info.albertcode.domain.event.Event;
import info.albertcode.domain.procedure.Procedure;
import info.albertcode.domain.request.Request;
import info.albertcode.domain.task.Task;
import info.albertcode.utils.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 封装 Procedure Task Request Event 相关的数据库操作
 * @Author: Albert Shen
 */
@Repository(value = "coreDaoEncapsulation")
public class CoreDaoEncapsulation {
    private IProcedureDao procedureDao;
    private ITaskDao taskDao;
    private IRequestDao requestDao;
    private IEventDao eventDao;

    @Autowired
    public CoreDaoEncapsulation(IProcedureDao procedureDao, ITaskDao taskDao, IRequestDao requestDao, IEventDao eventDao) {
        this.procedureDao = procedureDao;
        this.taskDao = taskDao;
        this.requestDao = requestDao;
        this.eventDao = eventDao;
    }

    // Procedure 相关查询操作

    public Procedure findProcedureById(Integer procedureId){
        return procedureDao.findProcedureById(procedureId);
    }

    public Procedure findProcedureByName(String procedureName){
        return procedureDao.findProcedureByName(procedureName);
    }

    public Map<String, Integer> findAllProcedureNameIdPairByFuzzyName(String procedureName){
        List<Procedure> procedureList = procedureDao.findAllProcedureNameIdPairByFuzzyName("%" + procedureName + "%");
        Map<String, Integer> map = new HashMap<>();
        for (Procedure procedure : procedureList){
            map.put(procedure.getName(), procedure.getId());
        }
        return map;
    }

    public Procedure findProcedureWithEntryTask(Integer entryTaskId){
        return procedureDao.findProcedureWithEntryTask(entryTaskId);
    }

    public List<Procedure> findProcedureByColumn(Integer startNum, Integer totalNum){
        return procedureDao.findProcedureByColumn(startNum, totalNum);
    }

    public Integer findProcedureNum(){
        return procedureDao.findProcedureNum();
    }

    // Task 相关查询操作

    public Task findTaskById(Integer taskId){
        return taskDao.findTaskById(taskId);
    }

    public Task findTaskByName(String taskName){
        return taskDao.findTaskByName(taskName);
    }

    public List<Task> findSucceedingTasksOfSpecificTaskById(Integer taskId){
        return taskDao.findSucceedingTasksOfSpecificTaskById(taskId);
    }

    public Map<String, Integer> findAllTaskNameIdPairByFuzzyName(String taskName){
        List<Task> taskList = taskDao.findAllTaskNameIdPairByFuzzyName("%" + taskName + "%");
        Map<String, Integer> map = new HashMap<>();
        for (Task task : taskList){
            map.put(task.getName(), task.getId());
        }
        return map;
    }

    // Request 相关查询操作

    public Request findRequestById(Integer requestId) {
        return requestDao.findRequestById(requestId);
    }

    // Event 相关查询操作

    public Event findEventById(Integer eventId) {
        return eventDao.findEventById(eventId);
    }

    public List<Event> findEventByColumn(Integer startNum, Integer totalNum){
        return eventDao.findEventByColumn(startNum, totalNum);
    }

    // Procedure 相关的简单储存操作

    public void saveOrUpdateProcedure(Procedure procedure) throws CustomException {
        if (procedure == null){
            throw new CustomException("流程为空");
        } else if (procedure.getId() == null ||
                procedureDao.findProcedureById(procedure.getId()) == null){
            procedureDao.saveProcedure(procedure);
        } else {
            procedureDao.updateProcedure(procedure);
        }
    }

    // Task 相关的简单储存操作

    public void saveOrUpdateTask(Task task) throws CustomException {
        if (task == null){
            throw new CustomException("任务为空");
        } else if (task.getId() == null ||
                taskDao.findTaskById(task.getId()) == null){
            taskDao.saveTask(task);
        } else {
            taskDao.updateTask(task);
        }
    }

    // Request 相关的简单储存操作

    public void saveOrUpdateRequest(Request request) throws CustomException {
        if (request == null){
            throw new CustomException("请求为空");
        } else if (request.getId() == null ||
                requestDao.findRequestById(request.getId()) == null){
            requestDao.saveRequest(request);
        } else {
            requestDao.updateRequest(request);
        }
    }

    // Event 相关的简单储存操作

    public void saveOrUpdateEvent(Event event) throws CustomException {
        if (event == null){
            throw new CustomException("事件为空");
        } else if (event.getId() == null ||
                eventDao.findEventById(event.getId()) == null){
            eventDao.saveEvent(event);
        } else {
            eventDao.updateEvent(event);
        }
    }


    // 较为复杂的多种数据结构之间的操作

    /**
     * 保存一个流程，并将一个任务设置为其入口任务
     * 流程与任务不要求必须存在于数据库中（即，可以是新建的）
     */
    public void saveOrUpdateProcedureAndItsEntryTask(Procedure procedure, Task entryTask) throws CustomException {
        saveOrUpdateTask(entryTask);
        procedure.setEntryTask(entryTask);
        saveOrUpdateProcedure(procedure);
    }

    /**
     * 保存一个任务，并将其设置为一个流程的入口任务，此流程的 id号 等于 传入参数procedureId
     * 要求此流程必须存在于数据库中
     * @throws CustomException 当此流程不存在与数据库中，抛出异常
     */
    public void saveOrUpdateTaskAndItsProcedure(Task task, Integer procedureId) throws CustomException {
        Procedure procedure = procedureDao.findProcedureById(procedureId);
        if (procedure == null){
            throw new CustomException("不存在id为 " + procedureId + " 的流程");
        }
        saveOrUpdateProcedureAndItsEntryTask(procedure, task);
    }

    /**
     * 保存一个任务，并将其设置为一个流程的入口任务，此流程的 名称 等于 传入参数procedureName
     * 要求此流程必须存在于数据库中
     * @throws CustomException 当此流程不存在与数据库中，抛出异常
     */
    public void saveOrUpdateTaskAndItsProcedure(Task task, String procedureName) throws CustomException {
        Procedure procedure = procedureDao.findProcedureByName(procedureName);
        if (procedure == null){
            throw new CustomException("不存在名称为 " + procedureName + " 的流程");
        }
        saveOrUpdateProcedureAndItsEntryTask(procedure, task);
    }

    /**
     * 保存一个任务，及其所对应的请求
     * 任务与请求不要求存在于数据库中（即，可以是新建的）
     */
    public void saveOrUpdateTaskAndItsRequest(Task task, Request request) throws CustomException {
        saveOrUpdateRequest(request);
        task.setRequest(request);
        saveOrUpdateTask(task);
    }

    /**
     * 保存一个事件，并修改产生此事件的任务相关内容
     */
    public void saveOrUpdateEventAndItsTask(Event event, Task task) throws CustomException {
        event.setBelongedTaskName(task.getName());
        saveOrUpdateEvent(event);
        task.setOutputEvent(event);
        saveOrUpdateTask(task);
    }

    /**
     * 保存一个事件，并将其设置为一个任务的输出事件，此事件的 id号 等于 传入参数taskId
     * 要求此事件必须存在于数据库中
     * @throws CustomException 当此事件不存在与数据库中，抛出异常
     */
    public void saveOrUpdateEventAndItsTask(Event event, Integer taskId) throws CustomException {
        Task task = taskDao.findTaskById(taskId);
        if (task == null){
            throw new CustomException("不存在id为 " + taskId + " 的任务");
        }
        saveOrUpdateEventAndItsTask(event, task);
    }

    /**
     * 保存一个事件，并将其设置为一个任务的输出事件，此事件的 名称 等于 传入参数taskName
     * 要求此事件必须存在于数据库中
     * @throws CustomException 当此事件不存在与数据库中，抛出异常
     */
    public void saveOrUpdateEventAndItsTask(Event event, String taskName) throws CustomException{
        Task task = taskDao.findTaskByName(taskName);
        if (task == null){
            throw new CustomException("不存在名称为 " + taskName + " 的任务");
        }
        saveOrUpdateEventAndItsTask(event, task);
    }
}
