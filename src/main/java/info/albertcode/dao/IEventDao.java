package info.albertcode.dao;

import info.albertcode.domain.event.Event;
import org.apache.ibatis.annotations.*;
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
    public List<Event> findEventByColumn(@Param("startNum") Integer startNum, @Param("totalNum") Integer totalNum);

    /**
     * 获取 id值 为传入参数 id 的行
     * @return 查询到的事件
     */
    @Select("select * from db_event where id = #{id}")
    public Event findEventById(Integer id);

    @Insert("insert into db_event " +
            "(generatedTime, belongedTaskName, successful, type, " +
            "overview, header, body) " +
            "values " +
            "(#{generatedTime}, #{belongedTaskName}, #{successful}, #{type}, " +
            "#{overview}, #{header}, #{body})")
    @SelectKey(keyColumn = "id", keyProperty = "id", before = false,
               resultType = Integer.class, statement = {" select last_insert_id()"})
    public void saveEvent(Event event);

    @Update("update db_event set " +
            "generatedTime = #{generatedTime}, belongedTaskName = #{belongedTaskName}, " +
            "successful = #{successful}, type = #{type}, " +
            "overview = #{overview}, header = #{header}, body = #{body} " +
            "where id = #{id}")
    public void updateEvent(Event event);
}
