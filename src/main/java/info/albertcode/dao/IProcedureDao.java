package info.albertcode.dao;

import info.albertcode.domain.procedure.InitTime;
import info.albertcode.domain.procedure.Procedure;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

/**
 * @Description: initTime 与 Procedure 是强绑定状态，一一对应，故在数据库的结构上将其体现为一张表
 * @Author: Albert Shen
 */
@Repository(value = "procedureDao")
public interface IProcedureDao {
    @Select("select * from db_procedure where id = #{procedureId}")
    @Results(id = "relatedDate", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "lastExecuteDateTime", column = "lastExecuteDateTime"),
            @Result(property = "initTime.month", column = "month"),
            @Result(property = "initTime.day", column = "day"),
            @Result(property = "initTime.weekDay", column = "weekDay"),
            @Result(property = "initTime.hour", column = "hour"),
            @Result(property = "initTime.minute", column = "minute"),
            @Result(property = "initTime.dayInterval", column = "dayInterval"),
            @Result(property = "initTime.hourInterval", column = "hourInterval"),
            @Result(property = "initTime.minuteInterval", column = "minuteInterval"),
            @Result(property = "entryTask", column = "entryTaskId",
                    one = @One(select = "info.albertcode.dao.ITaskDao.findTaskById",
                            fetchType = FetchType.EAGER)),
    })
    public Procedure findProcedureById(Integer procedureId);

    @Insert("<script> " +
                "insert into db_procedure (" +
                "<if test = 'entryTask != null'>" +
                    "entryTaskId, " +
                "</if>" +
                "lastExecuteDateTime, month, day, " +
                "weekDay, hour, minute, " +
                "dayInterval, hourInterval, " +
                "minuteInterval) " +
                "values (" +
                "<if test = 'entryTask != null'>" +
                    "#{entryTaskId}, " +
                "</if>" +
                "#{lastExecuteDateTime}, #{initTime.month}, #{initTime.day}, " +
                "#{initTime.weekDay}, #{initTime.hour}, #{initTime.minute}, " +
                "#{initTime.dayInterval}, #{initTime.hourInterval}, " +
                "#{initTime.minuteInterval})" +
            "</script>")
    @SelectKey(keyColumn = "id", keyProperty = "id", before = false,
            resultType = Integer.class, statement = {" select last_insert_id()"})
    public void saveProcedure(Procedure procedure);

    @Update("<script> " +
                "update db_procedure set " +
                "<if test = 'entryTask != null'>" +
                    "entryTaskId = #{entryTaskId}, " +
                "</if>" +
                "lastExecuteDateTime = #{lastExecuteDateTime}, " +
                "month = #{initTime.month}, " +
                "day = #{initTime.day}, " +
                "weekDay = #{initTime.weekDay}, " +
                "hour = #{initTime.hour}, " +
                "minute = #{initTime.minute}, " +
                "dayInterval = #{initTime.dayInterval}, " +
                "hourInterval = #{initTime.hourInterval}, " +
                "minuteInterval = #{initTime.minuteInterval} " +
                "where id = #{id}" +
            "</script>")
    public void updateProcedure(Procedure procedure);
}
