package info.albertcode.service;

import info.albertcode.domain.event.Event;

import java.net.URISyntaxException;

/**
 * @Description:
 * @Author: Albert Shen
 */

public interface ITaskService {

    public Event execute(Integer taskId) throws Exception;
}
