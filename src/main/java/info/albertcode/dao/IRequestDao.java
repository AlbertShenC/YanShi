package info.albertcode.dao;

import info.albertcode.domain.request.Request;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Repository(value = "requestDao")
public interface IRequestDao {
    /**
     * 查询指定id的请求
     */
    @Select("select * from db_request where id = #{requestId}")
    public Request findRequestById(Integer requestId);

    /**
     * 保存一个新增request
     */
    @Insert("insert into db_request (type, overview, header, body) " +
            "VALUES (#{type}, #{overview}, #{header}, #{body})")
    @SelectKey(keyColumn = "id", keyProperty = "id", before = false,
            resultType = Integer.class, statement = {" select last_insert_id()"})
    public void saveRequest(Request request);

    /**
     * 更新一个已有的request
     */
    @Update("update db_request set " +
            "type = #{type}, overview = #{overview}, " +
            "header = #{header}, body = #{body} " +
            "where id = #{id}")
    public void updateRequest(Request request);
}
