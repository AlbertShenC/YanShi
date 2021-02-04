package info.albertcode.dao;

import info.albertcode.domain.request.Request;
import org.apache.ibatis.annotations.Select;
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
}
