package info.albertcode.controller;

import info.albertcode.dao.IEventDao;
import info.albertcode.domain.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Controller
@RequestMapping("/customPage")
public class CustomPageController {
    private IEventDao eventDao;

    @Autowired
    public CustomPageController(IEventDao eventDao) {
        this.eventDao = eventDao;
    }

    @RequestMapping(value = "", produces = "application/xml;charset=utf-8")
    @ResponseBody
    public String getCustomPageData(Integer id){
        Event event = eventDao.findEventById(id);
        if (event == null){
            return "error";
        }
        switch (event.getType()){
            case "RegisterForWebPage":
                return event.getBody();
            default:
                return "error";
        }
    }
}
