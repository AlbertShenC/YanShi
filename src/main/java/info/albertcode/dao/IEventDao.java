package info.albertcode.dao;

import info.albertcode.domain.event.Event;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 事件Dao接口
 * @Author: Albert Shen
 */

@Repository(value = "eventDao")
public interface IEventDao {

    /**
     * 查询 [startNum, startNum + totalNum) 行数据
     * @return 查询到的事件list
     */
    @Select("select * from db_event limit #{startNum}, #{totalNum}")
    public List<Event> findByColumn(@Param("startNum") Integer startNum, @Param("totalNum") Integer totalNum);

    /**
     * 获取 id值 为传入参数 id 的行
     * @return 查询到的事件
     */
    //todo: 如果查询一个不存在的行会怎么样？
    public Event findById(Integer id);

    /**
     * 获取 name值 为传入参数 eventName 的行
     * @return 查询到的事件
     */
    @Select("select * from db_event where name = #{taskName}")
    public Event findEventByName(String eventName);

    /**
     * 获取指定名称的任务所生成的最后一个事件
     */
    @Select("select * from db_event where belongedTask = #{taskName} order by id desc limit 1")
    public Event findLastEventGeneratedByGivenTask(String taskName);

    @Insert("insert into db_event " +
            "(belongedTask, generatedTime, successful, type, overview, header, body) " +
            "values " +
            "(#{belongedTask}, #{generatedTime}, #{successful}, #{type}, #{overview}, " +
            "#{header}, #{body});")
    public void saveEvent(Event event);
}
