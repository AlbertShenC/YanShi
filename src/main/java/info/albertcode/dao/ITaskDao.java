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
                fetchType = FetchType.EAGER))
    })
    public Task findTaskById(Integer taskId);
}
