package info.albertcode.dao;

import info.albertcode.domain.procedure.Procedure;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 操作 db_procedure 的相关方法
 * @Author: Albert Shen
 */
@Repository(value = "procedureDao")
public interface IProcedureDao {
    /**
     * 获取流程，其 id 为 给定的参数procedureId
     * @return 存在指定流程，则返回该流程，反之返回null
     */
    @Select("select id, name, entryTaskId, lastExecuteDateTime, month, day, weekDay, " +
            "hour, minute, dayInterval, hourInterval, minuteInterval " +
            "from db_procedure where id = #{procedureId}")
    @Results(id = "relatedDate", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "entryTask", column = "entryTaskId",
                    one = @One(select = "info.albertcode.dao.ITaskDao.findTaskById",
                            fetchType = FetchType.EAGER)),
            @Result(property = "lastExecuteDateTime", column = "lastExecuteDateTime"),
            @Result(property = "initTime.month", column = "month"),
            @Result(property = "initTime.day", column = "day"),
            @Result(property = "initTime.weekDay", column = "weekDay"),
            @Result(property = "initTime.hour", column = "hour"),
            @Result(property = "initTime.minute", column = "minute"),
            @Result(property = "initTime.dayInterval", column = "dayInterval"),
            @Result(property = "initTime.hourInterval", column = "hourInterval"),
            @Result(property = "initTime.minuteInterval", column = "minuteInterval")
    })
    public Procedure findProcedureById(Integer procedureId);

    /**
     * 获取流程，其 entryTask 的 id 为 给定参数entryTaskId
     * @return 存在指定流程，则返回该流程，反之返回null
     */
    @Select("select id, name, entryTaskId, lastExecuteDateTime, month, day, weekDay, " +
            "hour, minute, dayInterval, hourInterval, minuteInterval " +
            "from db_procedure where entryTaskId = #{entryTaskId}")
    @ResultMap(value = "relatedDate")
    public Procedure findProcedureWithEntryTask(Integer entryTaskId);

    /**
     * 获取流程列表，返回 [startNum, startNum + totalNum) 行数据
     * @return 一个列表，其 size 范围为 [0, totalNum]
     */
    @Select("select * from db_procedure limit #{startNum}, #{totalNum}")
    @ResultMap(value = "relatedDate")
    public List<Procedure> findProcedureByColumn(@Param("startNum") Integer startNum, @Param("totalNum") Integer totalNum);

    /**
     * 获取当前数据库中 流程 的总数
     * @return 一个整数，表示流程总数
     */
    @Select("select count(*) from db_procedure")
    public Integer findProcedureNum();

    /**
     * 新建一个流程
     * 需要注意，传入的流程理论上来说是不应该有 id 的，需要写入数据库后，
     * 才能从数据库中获取到由数据库自动生成的id号，所以即使传入的流程具有id，也并不会被采用，
     * 同时在写入数据库以后，原有id将会被覆盖。
     *
     * InitTime与Procedure是强绑定状态，必然同时出现，在数据库中两者表现为储存于同一张表中，
     * 而entryTask可能并不存在，所以在写入数据库时，将会自动判断是否存在entryTask，
     * 只有在存在entryTask的情况下，才会写入entryTaskId。
     */
    @Insert("<script> " +
                "insert into db_procedure (name, " +
                "<if test = 'entryTask != null'>" +
                    "entryTaskId, " +
                "</if>" +
                "lastExecuteDateTime, month, day, " +
                "weekDay, hour, minute, " +
                "dayInterval, hourInterval, " +
                "minuteInterval) " +
                "values (#{name}, " +
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

    /**
     * 更新一个流程
     * 大体上与saveProcedure相似，但要求是更新数据库中已经存在的流程，
     * 所以Procedure对象中要求存储有一个有效的id号。
     */
    @Update("<script> " +
                "update db_procedure set " +
                "name = #{name}, " +
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
