package info.albertcode.dao;

import info.albertcode.domain.procedure.Procedure;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Repository(value = "procedureDao")
public interface ProcedureDao {

    public Procedure findProcedureById(Integer procedureId);
}
