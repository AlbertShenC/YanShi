package info.albertcode.utils.taskServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.WireFeedOutput;
import info.albertcode.domain.event.Event;
import info.albertcode.domain.event.RssGenerateEvent;
import info.albertcode.domain.request.RssGenerateRequest;
import info.albertcode.domain.request.StringParserRequest;
import info.albertcode.domain.task.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @Author: Albert Shen
 */
public class RssGenerateTaskServiceImpl extends TaskWithInputEvent{

    /**
     * 根据json格式的输入转换成对应的xml格式的rss
     * @param channelInJson rss中的channel信息
     * @param allItemsInJson rss中的item信息，一个item用一个JsonObject表示，多个object一起组成一个array
     * @param itemMapping item中的原始数据可能和最终生成的rss并非一一对应
     *                    如最终rss中每个item都有一个title，但在原始数据中可能被用户设置为BiaoTi
     *                    仅用于映射item中的数据
     */
    private static String generateRss(JSONObject channelInJson, JSONArray allItemsInJson, JSONObject itemMapping){
        Channel channel = new Channel("rss_2.0");
        channel.setEncoding("UTF-8");
        channel.setTitle(channelInJson.getString("title"));
        channel.setLink(channelInJson.getString("url"));
        channel.setDescription(channelInJson.getString("description") + " - Made by YanShi");
        channel.setLanguage("zh-cn");
        channel.setLastBuildDate(new Date());
        channel.setWebMaster("AlbertShen");
        channel.setGenerator("YanShi");

        List<Item> items = new ArrayList<>();
        for (int i = 0; i < allItemsInJson.size(); i++){
            Item item = new Item();
            JSONObject itemInJson = allItemsInJson.getJSONObject(i);

            item.setTitle(itemInJson.getString(itemMapping.getString("titleAlias")));
            item.setAuthor(itemInJson.getString(itemMapping.getString("authorAlias")));

            Description description = new Description();
            description.setValue(itemInJson.getString(itemMapping.getString("textAlias")));
            item.setDescription(description);

            Guid guid = new Guid();
            guid.setPermaLink(false);
            guid.setValue(itemInJson.getString(itemMapping.getString("urlAlias")));
            item.setGuid(guid);
            item.setLink(itemInJson.getString(itemMapping.getString("urlAlias")));
            items.add(item);
        }

        channel.setItems(items);

        WireFeedOutput out = new WireFeedOutput();
        try {
            return out.outputString(channel);
        } catch (FeedException ex) {
            //todo:自定义异常
            return "error";
        }
    }

    private static RssGenerateRequest prepareRequest(Task task){
        return new RssGenerateRequest(task.getRequest());
    }

    private static JSONObject generateChannelInJson(RssGenerateRequest request){
        JSONObject object = new JSONObject();
        object.put("title", request.getChannelTitle());
        object.put("url", request.getChannelUrl());
        object.put("description", request.getChannelDescription());
        return object;
    }

    private static JSONObject generateItemMapping(RssGenerateRequest request){
        JSONObject object = new JSONObject();
        object.put("titleAlias", request.getTitleAlias());
        object.put("authorAlias", request.getAuthorAlias());
        object.put("textAlias", request.getTextAlias());
        object.put("urlAlias", request.getUrlAlias());
        return object;
    }

    public static Event executeRssGenerate(Task task) {
        RssGenerateRequest request = prepareRequest(task);
        String stringToGenerateRss = getInputEventString(task);
        JSONObject channelInJson = generateChannelInJson(request);
        JSONArray allItemsInJson = JSON.parseArray(stringToGenerateRss);
        JSONObject itemMapping = generateItemMapping(request);

        String rss = generateRss(channelInJson, allItemsInJson, itemMapping);
        return new RssGenerateEvent(rss);
    }
}
