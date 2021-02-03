package info.albertcode.service.impl;

import info.albertcode.dao.IEventDao;
import info.albertcode.domain.event.Event;
import info.albertcode.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "eventService")
public class EventServiceImpl implements IEventService {
    @Autowired
    private IEventDao eventDao;

    @Override
    public List<Event> findByPage(Integer page, Integer numPerPage) {
        System.out.println("业务层按页查询事件...");
        System.out.println(eventDao);
        return eventDao.findByColumn(page * numPerPage, numPerPage);
    }
}
