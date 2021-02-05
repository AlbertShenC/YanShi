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

    @Insert("insert into db_event " +
            "(generatedTime, successful, type, overview, header, body) " +
            "values " +
            "(#{generatedTime}, #{successful}, #{type}, #{overview}, " +
            "#{header}, #{body});")
    public void saveEvent(Event event);
}
