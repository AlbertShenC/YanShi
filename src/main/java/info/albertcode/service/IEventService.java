package info.albertcode.service;

import info.albertcode.domain.event.Event;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */

public interface IEventService {

    public List<Event> findByPage(Integer page, Integer numPerPage);
}
