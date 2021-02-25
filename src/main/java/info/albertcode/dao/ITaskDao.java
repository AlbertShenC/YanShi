package info.albertcode.dao;

import info.albertcode.domain.task.Task;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 操作 db_task 的相关方法
 * @Author: Albert Shen
 */
@Repository(value = "taskDao")
public interface ITaskDao {

    /**
     * 获取任务，其 id 为 给定参数taskId
     *
     * 一个任务有且只有一个Request，且一般而言应该是类似于Procedure与InitTime的强绑定关系，
     * 但Task类的相关操作过多，如果将Request与Task置于同一张表中，此表的规模过大，不利于维护。
     *
     * 除此之外，Task的前置任务，后续任务，输出事件均采用懒惰加载。
     *
     * 需要特别注意，前置任务与后续任务无论如何也不能同时采用立即加载，因为可能触发死循环，
     * 即A后续任务为B，若要获取A，则要立即加载B，而为了加载B，又需要立即加载A，从而造成死循环。
     * 而在数据结构中，如此设计的原因，是为了对外部暴露的接口更加利于开发者进行开发，
     * 而无需关心数据库内部的储存逻辑。
     *
     * type在数据结构中采用枚举变量进行储存，在数据库中储存其对应的整数序号，
     * 具体到实际命名中，type代表的是对应整数序号，而typeEnum才是枚举类型。
     *
     * @return 存在指定任务，则返回该任务，反之返回null
     */
    @Select("select id, type, name, requestId, inputTaskId, inputEventProperty, " +
            "outputEventId from db_task where id = #{taskId}")
    @Results(id = "relatedData", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "type", column = "type"),
            @Result(property = "name", column = "name"),
            @Result(property = "request", column = "requestId",
                    one = @One(select = "info.albertcode.dao.IRequestDao.findRequestById",
                           fetchType = FetchType.EAGER)),
            @Result(property = "inputTask", column = "inputTaskId",
                    one = @One(select = "info.albertcode.dao.ITaskDao.findTaskById",
                           fetchType = FetchType.LAZY)),
            @Result(property = "inputEventProperty", column = "inputEventProperty"),
            @Result(property = "outputEvent", column = "outputEventId",
                    one = @One(select = "info.albertcode.dao.IEventDao.findEventById",
                           fetchType = FetchType.LAZY)),
            @Result(property = "nextTasks", column = "id",
                    many = @Many(select = "info.albertcode.dao.ITaskDao.findSucceedingTasksOfSpecificTaskById",
                        fetchType = FetchType.LAZY))
    })
    public Task findTaskById(Integer taskId);

    /**
     * 获取一个任务，其 名称 为 给定参数taskName
     * @return 存在指定任务，则返回该任务，反之返回null
     */
    @Select("select id, type, name, requestId, inputTaskId, inputEventProperty, " +
            "outputEventId from db_task where name = #{taskName}")
    @ResultMap(value = "relatedData")
    public Task findTaskByName(String taskName);

    /**
     * 查找多个任务，其 前置任务的id 为 给定参数 taskId
     * @return 一个列表，其 size 范围为 [0, 正无穷)
     */
    @Select("select id, type, name, requestId, inputTaskId, inputEventProperty, " +
            "outputEventId from db_task where inputTaskId = #{taskId}")
    @ResultMap(value = "relatedData")
    public List<Task> findSucceedingTasksOfSpecificTaskById(Integer taskId);

    /**
     * 查找多个任务，其名称包含至少一个子字符串与 传入参数taskName 相同
     * @return 一个列表，其 size 范围为 [0, 正无穷)
     */
    @Select("select name, id from db_task where name like #{taskName}")
    @Results(value = {
            @Result(property = "name", column = "name"),
            @Result(property = "id", column = "id")
    })
    public List<Task> findAllTaskNameIdPairByFuzzyName(String taskName);

    /**
     * 新建一个任务
     * 与Procedure同理，新建时不存在id，且对象内部的id属性将会被数据库自动生成的id覆盖。
     * 前置任务与输出事件可能不存在，故需要单独判断
     */
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

    /**
     * 更新一个已有的任务
     * 类似于Procedure，此时要求Task必须存在一个id。
     */
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
