package info.albertcode.utils.taskServiceImpl;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedOutput;
import info.albertcode.domain.event.Event;
import info.albertcode.domain.event.RssGenerateEvent;
import info.albertcode.domain.request.RssGenerateRequest;
import info.albertcode.domain.task.Task;
import info.albertcode.utils.exception.CustomException;
import info.albertcode.utils.pair.OneKeyManyValues;

import java.util.*;

/**
 * @Description:
 * @Author: Albert Shen
 */
public class RssGenerateTaskServiceImpl extends TaskWithInputEvent{

    /**
     * 根据json格式的输入转换成对应的xml格式的rss
     */
    private static String generateRss(RssGenerateRequest request, OneKeyManyValues keyValueStringToParser) throws CustomException {
        Channel channel = new Channel("rss_2.0");
        channel.setEncoding("UTF-8");
        channel.setTitle(request.getChannelTitle());
        channel.setLink(request.getChannelUrl());
        channel.setDescription(request.getChannelDescription() + " - Made by YanShi");
        channel.setLanguage("zh-cn");
        channel.setLastBuildDate(new Date());
        channel.setWebMaster("AlbertShen");
        channel.setGenerator("YanShi");

        List<Item> items = new ArrayList<>();
        Iterator<LinkedHashMap<String, Object>> iteratorByGroup = keyValueStringToParser.getIteratorByGroup();
        while (iteratorByGroup.hasNext()){
            LinkedHashMap<String, Object> next = iteratorByGroup.next();
            Item item = new Item();

            item.setTitle((String) next.get(request.getTitleAlias()));
            item.setAuthor((String) next.get(request.getAuthorAlias()));

            Description description = new Description();
            description.setValue((String) next.get(request.getTextAlias()));
            item.setDescription(description);

            Guid guid = new Guid();
            guid.setPermaLink(false);
            guid.setValue((String) next.get(request.getUrlAlias()));
            item.setGuid(guid);
            item.setLink((String) next.get(request.getUrlAlias()));

            items.add(item);
        }

        channel.setItems(items);

        WireFeedOutput out = new WireFeedOutput();
        try {
            return out.outputString(channel);
        } catch (FeedException ex) {
            throw new CustomException("生成rss时出现异常");
        }
    }

    private static RssGenerateRequest prepareRequest(Task task){
        return new RssGenerateRequest(task.getRequest());
    }

    public static Event executeRssGenerate(Task task) throws CustomException {
        RssGenerateRequest request = prepareRequest(task);
        String stringToGenerateRss = getInputEventString(task);

        String rss = generateRss(request, new OneKeyManyValues(stringToGenerateRss));
        return new RssGenerateEvent(rss);
    }
}
