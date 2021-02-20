package info.albertcode.controller;

import info.albertcode.domain.event.Event;
import info.albertcode.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Controller
@RequestMapping(value = "/event")
public class EventController {
    private IEventService eventService;

    @Autowired
    public EventController(IEventService eventService) {
        this.eventService = eventService;
    }

    @RequestMapping(value = "/findByPage")
    public String findByPage(Model model){
        System.out.println("表现层，按页查询事件...");
        List<Event> events = eventService.findByPage(0, 10);
        System.out.println("表现层，按页查询事件完成...");
        model.addAttribute("events", events);
        return "event_list";
    }
}
