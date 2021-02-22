package info.albertcode.controller;

import info.albertcode.dao.IProcedureDao;
import info.albertcode.domain.procedure.InitTime;
import info.albertcode.domain.procedure.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Controller
@RequestMapping(value = "/procedure")
public class ProcedureController {
    private IProcedureDao procedureDao;

    @Autowired
    public ProcedureController(IProcedureDao procedureDao) {
        this.procedureDao = procedureDao;
    }

    @RequestMapping("/testSave")
    public String testSave() {
        Procedure procedure = new Procedure();
        procedure.setInitTime(new InitTime());
        procedure.setLastExecuteDateTime(LocalDateTime.now());
        procedureDao.saveProcedure(procedure);
        return "result";
    }
}
