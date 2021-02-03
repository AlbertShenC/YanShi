package info.albertcode;

import info.albertcode.dao.IEventDao;
import info.albertcode.domain.event.Event;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class DaoTest {
    @Autowired
    private IEventDao eventDao;

    @Test
    public void testFindByColumn(){
        System.out.println("eventDao = " + eventDao);
        List<Event> events = eventDao.findByColumn(0, 10);
        System.out.println(events);
    }
}
