package info.albertcode.dao;

import info.albertcode.domain.task.Task;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Repository(value = "taskDao")
public interface ITaskDao {

    @Select("select * from db_task where id = #{taskId}")
    @Results(value = {
            @Result(property = "request", column = "requestId",
                one = @One(select = "info.albertcode.dao.IRequestDao.findRequestById",
                fetchType = FetchType.EAGER)),
            @Result(property = "inputEvent", column = "inputEvent",
                one = @One(select = "info.albertcode.dao.IEventDao.findLastEventGeneratedByGivenTask",
                fetchType = FetchType.LAZY))
    })
    public Task findTaskById(Integer taskId);

    @Select("select * from db_task where name = #{taskName}")
    @Results(value = {
            @Result(property = "request", column = "requestId",
                    one = @One(select = "info.albertcode.dao.IRequestDao.findRequestById",
                            fetchType = FetchType.EAGER))
    })
    public Task findTaskByName(String taskName);
}
