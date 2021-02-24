package info.albertcode.dao;

import info.albertcode.domain.task.Task;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Repository(value = "taskDao")
public interface ITaskDao {

    @Select("select * from db_task where id = #{taskId}")
    @Results(id = "relatedData", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "request", column = "requestId",
                    one = @One(select = "info.albertcode.dao.IRequestDao.findRequestById",
                           fetchType = FetchType.EAGER)),
            @Result(property = "inputTask", column = "inputTaskId",
                    one = @One(select = "info.albertcode.dao.ITaskDao.findTaskById",
                           fetchType = FetchType.LAZY)),
            @Result(property = "outputEvent", column = "outputEventId",
                    one = @One(select = "info.albertcode.dao.IEventDao.findEventById",
                           fetchType = FetchType.LAZY)),
            @Result(property = "nextTasks", column = "id",
                    many = @Many(select = "info.albertcode.dao.ITaskDao.findSucceedingTasksOfSpecificTaskById",
                        fetchType = FetchType.LAZY))
    })
    public Task findTaskById(Integer taskId);

    @Select("select * from db_task where name = #{taskName}")
    @ResultMap(value = "relatedData")
    public Task findTaskByName(String taskName);

    @Select("select * from db_task where inputTaskId = #{taskId}")
    @ResultMap(value = "relatedData")
    public List<Task> findSucceedingTasksOfSpecificTaskById(Integer taskId);

    @Insert("<script> " +
                "insert into db_task " +
                "(type, name, requestId" +
                "<if test = 'inputTask != null'>" +
                    ", inputTaskId, inputEventProperty" +
                "</if>" +
                "<if test = 'outputEvent != null'>" +
                    ", outputEventId" +
                "</if>" +
                ") " +
                "values " +
                "(#{type}, #{name}, #{requestId}" +
                "<if test = 'inputTask != null'>" +
                    ", #{inputTaskId}, #{inputEventProperty}" +
                "</if>" +
                "<if test = 'outputEvent != null'>" +
                    ", #{outputEventId}" +
                "</if>" +
                ") " +
            "</script>")
    @SelectKey(keyColumn = "id", keyProperty = "id", before = false,
            resultType = Integer.class, statement = {" select last_insert_id()"})
    public void saveTask(Task task);

    @Update("<script> " +
                "update db_task set " +
                "type = #{type}, name = #{name}, requestId = #{requestId}" +
                "<if test = 'inputTask != null'>" +
                    ", inputTaskId = #{inputTaskId}, inputEventProperty = #{inputEventProperty}" +
                "</if>" +
                "<if test = 'outputEvent != null'>" +
                    ", outputEventId = #{outputEventId} " +
                "</if>" +
                "where id = #{id} " +
            "</script>")
    public void updateTask(Task task);
}
