package info.albertcode.dao;

import info.albertcode.domain.task.Task;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Repository(value = "taskDao")
public interface ITaskDao {

    @Select("select * from db_task where id = #{taskId}")
    @Results(id = "relatedData", value = {
            @Result(property = "request", column = "requestId",
                one = @One(select = "info.albertcode.dao.IRequestDao.findRequestById",
                           fetchType = FetchType.EAGER)),
            @Result(property = "preTask", column = "preTaskId",
                one = @One(select = "info.albertcode.dao.ITaskDao.findTaskById",
                           fetchType = FetchType.LAZY)),
            @Result(property = "outputEvent", column = "outputEventId",
                one = @One(select = "info.albertcode.dao.IEventDao.findEventById",
                           fetchType = FetchType.LAZY))
    })
    public Task findTaskById(Integer taskId);

    @Select("select * from db_task where name = #{taskName}")
    @ResultMap(value = "relatedData")
    public Task findTaskByName(String taskName);

    @Insert("<script> " +
                "insert into db_task " +
                "(type, name, requestId, " +
                "<if test = 'preTask != null'>" +
                    "preTaskId, inputEventProperty, " +
                "</if>" +
                "outputEventId) " +
                "values " +
                "(#{type}, #{name}, #{requestId}, #" +
                "<if test = 'preTask != null'>" +
                    "{preTaskId}, #{inputEventProperty}, " +
                "</if>" +
                "#{outputEventId}) " +
            "</script>")
    @SelectKey(keyColumn = "id", keyProperty = "id", before = false,
            resultType = Integer.class, statement = {" select last_insert_id()"})
    public void saveTask(Task task);

    @Update("<script> " +
                "update db_task set " +
                "type = #{type}, name = #{name}, requestId = #{requestId}, " +
                "<if test = 'preTask != null'>" +
                    "preTaskId = #{preTaskId}, inputEventProperty = #{inputEventProperty}, " +
                "</if>" +
                "outputEventId = #{outputEventId} " +
                "where id = #{id} " +
            "</script>")
    public void updateTask(Task task);
}
