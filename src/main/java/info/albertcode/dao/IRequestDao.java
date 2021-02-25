package info.albertcode.dao;

import info.albertcode.domain.request.Request;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Repository(value = "requestDao")
public interface IRequestDao {
    /**
     * 获取请求，其 id 为 给定参数requestId
     * @return 存在指定任务，则返回该任务，反之返回null
     */
    @Select("select id, overview, header, body from db_request " +
            "where id = #{requestId}")
    @Results(id = "requestData", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "overview", column = "overview"),
            @Result(property = "header", column = "header"),
            @Result(property = "body", column = "body")
    })
    public Request findRequestById(Integer requestId);

    /**
     * 新建一个请求
     * 与Procedure同理，新建时不存在id，且对象内部的id属性将会被数据库自动生成的id覆盖。
     */
    @Insert("insert into db_request (overview, header, body) " +
            "VALUES (#{overview}, #{header}, #{body})")
    @SelectKey(keyColumn = "id", keyProperty = "id", before = false,
            resultType = Integer.class, statement = {" select last_insert_id()"})
    public void saveRequest(Request request);

    /**
     * 更新一个已有的请求
     * 类似于Procedure，此时要求Request必须存在一个id。
     */
    @Update("update db_request set " +
            "overview = #{overview}, " +
            "header = #{header}, body = #{body} " +
            "where id = #{id}")
    public void updateRequest(Request request);
}
