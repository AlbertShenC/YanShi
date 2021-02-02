package info.albertcode.dao;

import info.albertcode.domain.event.Event;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 事件Dao接口
 * @Author: Albert Shen
 */

@Repository(value = "taskDao")
public interface ITaskDao {

    /**
     * 查询 [startNum, startNum + totalNum) 行数据
     * @return 查询到的事件list
     */
    @Select("select * from db_event limit #{startNum - 1} #{totalNum}")
    public List<Event> findByColumn(Integer startNum, Integer totalNum);
}
