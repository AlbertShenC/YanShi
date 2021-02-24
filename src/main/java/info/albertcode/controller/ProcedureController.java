package info.albertcode.controller;

import info.albertcode.dao.IProcedureDao;
import info.albertcode.domain.procedure.Procedure;
import info.albertcode.utils.exception.CustomException;
import info.albertcode.utils.json.OneKeyOneValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    //todo:Procedure展示页
//    @GetMapping(value = "")
//    public ModelAndView showAllProcedure() {
//        ModelAndView modelAndView = new ModelAndView();
//    }

    @GetMapping(value = "/edit")
    public ModelAndView getCreatePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "新建流程");
        modelAndView.addObject("isCreate", true);
        modelAndView.setViewName("/procedure/edit");
        return modelAndView;
    }

    @GetMapping(value = "/edit/{id}")
    public ModelAndView getUpdatePage(@PathVariable Integer id) throws CustomException {
        Procedure procedure = procedureDao.findProcedureById(id);
        if (procedure == null){
            throw new CustomException("不存在id为" + id + "的流程");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("title", "修改流程");
        modelAndView.addObject("isCreate", false);
        modelAndView.addObject("procedureId", procedure.getId());
        modelAndView.addObject("name", procedure.getName());
        modelAndView.addObject("month", procedure.getInitTime().getMonth());
        modelAndView.addObject("day", procedure.getInitTime().getDay());
        modelAndView.addObject("weekDay", procedure.getInitTime().getWeekDay());
        modelAndView.addObject("hour", procedure.getInitTime().getHour());
        modelAndView.addObject("minute", procedure.getInitTime().getMinute());
        modelAndView.addObject("dayInterval", procedure.getInitTime().getDayInterval());
        modelAndView.addObject("hourInterval", procedure.getInitTime().getHourInterval());
        modelAndView.addObject("minuteInterval", procedure.getInitTime().getMinuteInterval());
        modelAndView.setViewName("/procedure/edit");
        return modelAndView;
    }

    @PostMapping(value = "/edit")
    @ResponseBody
    public String createProcedure(Procedure procedure){
        procedure.setLastExecuteDateTime(LocalDateTime.now());
        procedureDao.saveProcedure(procedure);

        OneKeyOneValue keyValue = new OneKeyOneValue();
        keyValue.addKeyValue("message", "success");
        return keyValue.toJsonObject().toJSONString();
    }

    @PutMapping(value = "/edit/{id}")
    @ResponseBody
    public String updateProcedure(@PathVariable Integer id, Procedure procedure) throws CustomException {
        Procedure procedureInDatabase = procedureDao.findProcedureById(id);
        if (procedureInDatabase == null){
            throw new CustomException("不存在id为" + id + "的流程");
        }

        procedure.setId(id);
        procedure.setLastExecuteDateTime(procedureInDatabase.getLastExecuteDateTime());
        procedureDao.updateProcedure(procedure);

        OneKeyOneValue keyValue = new OneKeyOneValue();
        keyValue.addKeyValue("message", "success");
        return keyValue.toJsonObject().toJSONString();
    }
}
