package info.albertcode.dao;

import info.albertcode.domain.procedure.InitTime;
import info.albertcode.domain.procedure.Procedure;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

/**
 * @Description: initTime 与 Procedure 是强绑定状态，一一对应，且 id 相同
 *               故两张表的操作放在同一个接口中
 * @Author: Albert Shen
 */
@Repository(value = "procedureDao")
public interface IProcedureDao {

    @Select("select * from db_init_time where id = #{initTimeId}")
    public InitTime findInitTimeById(Integer initTimeId);

    @Insert("insert into db_init_time " +
            "(id, month, day, weekDay, " +
            "hour, minute, dayInterval, " +
            "hourInterval, minuteInterval) " +
            "VALUES " +
            "(#{initTimeId}, #{initTime.month}, #{initTime.day}, #{initTime.weekDay}, " +
            "#{initTime.hour}, #{initTime.minute}, #{initTime.dayInterval}, " +
            "#{initTime.hourInterval}, #{initTime.minuteInterval});")
    public void saveInitTime(@Param("initTimeId") Integer initTimeId, @Param("initTime") InitTime initTime);

    @Update("update db_init_time set " +
            "month = #{initTime.month}, day = #{initTime.day}, " +
            "weekDay = #{initTime.weekDay}, hour = #{initTime.hour}, " +
            "minute = #{initTime.minute}, dayInterval = #{initTime.dayInterval}, " +
            "hourInterval = #{initTime.hourInterval}, minuteInterval = #{initTime.minuteInterval} " +
            "where id = #{initTimeId}")
    public void updateInitTime(@Param("initTimeId") Integer initTimeId, @Param("initTime") InitTime initTime);

    @Select("select * from db_procedure where id = #{procedureId}")
    @Results(id = "relatedDate", value = {
            @Result(property = "entryTask", column = "entryTaskId",
                    one = @One(select = "info.albertcode.dao.ITaskDao.findTaskById",
                            fetchType = FetchType.EAGER)),
            @Result(property = "initTime", column = "id",
                    one = @One(select = "info.albertcode.dao.IProcedureDao.findInitTimeById",
                            fetchType = FetchType.EAGER))
    })
    public Procedure findProcedureById(Integer procedureId);

    @Insert("<script> " +
                "insert into db_procedure (" +
                "<if test = 'entryTask != null'>" +
                    "entryTaskId, " +
                "</if>" +
                "lastExecuteDateTime) " +
                "values (" +
                "<if test = 'entryTask != null'>" +
                    "#{entryTaskId}, " +
                "</if>" +
                "#{lastExecuteDateTime})" +
            "</script>")
    @SelectKey(keyColumn = "id", keyProperty = "id", before = false,
            resultType = Integer.class, statement = {" select last_insert_id()"})
    public void saveProcedure(Procedure procedure);

    @Update("<script> " +
                "update db_procedure set " +
                "<if test = 'entryTask != null'>" +
                    "entryKeyId = #{entryKeyId}, " +
                "</if>" +
                "lastExecuteDateTime = #{lastExecuteDateTime} " +
                "where id = #{id}" +
            "</script>")
    public void updateProcedure(Procedure procedure);
}
