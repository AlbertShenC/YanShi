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
     * 获取 id值 为传入参数 id 的行
     * @return 查询到的事件
     */
    @Select("select id, generatedTime, belongedTaskName, successful, type, " +
            "overview, header, body from db_event where id = #{id}")
    @Results(id = "eventData", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "generatedTime", column = "generatedTime"),
            @Result(property = "belongedTaskName", column = "belongedTaskName"),
            @Result(property = "successful", column = "successful"),
            @Result(property = "type", column = "type"),
            @Result(property = "overview", column = "overview"),
            @Result(property = "header", column = "header"),
            @Result(property = "body", column = "body")
    })
    public Event findEventById(Integer id);

    /**
     * 获取事件列表，返回 [startNum, startNum + totalNum) 行数据
     * @return 一个列表，其 size 范围为 [0, totalNum]
     */
    @Select("select * from db_event limit #{startNum}, #{totalNum}")
    @ResultMap(value = "eventData")
    public List<Event> findEventByColumn(@Param("startNum") Integer startNum, @Param("totalNum") Integer totalNum);

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
