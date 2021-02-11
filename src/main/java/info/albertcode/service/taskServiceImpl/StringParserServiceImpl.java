package info.albertcode.service.taskServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import info.albertcode.dao.IEventDao;
import info.albertcode.dao.ITaskDao;
import info.albertcode.domain.event.Event;
import info.albertcode.domain.event.StringParserEvent;
import info.albertcode.domain.request.StringParserRequest;
import info.albertcode.domain.task.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Service(value = "stringParserService")
public class StringParserServiceImpl {

    private static StringParserRequest prepareRequest(Task task){
        return new StringParserRequest(task.getRequest());
    }

    /**
     *
     * @param selector CSS选择器的解析语句
     * @param stringToParser 被解析的字符串
     * @return
     */
    private static List<String> executeCSS(String selector, String stringToParser){
        Document document = Jsoup.parse(stringToParser);
        Elements cssElements = document.select(selector);

        List<String> returnValue = new ArrayList<String>();
        for (Element element : cssElements){
            returnValue.add(element.toString());
        }

        return returnValue;
    }

    /**
     *
     * @param xpath Xpath的解析语句
     * @param stringToParser 被解析的字符串
     * @return
     */
    private static List<String> executeXpath(String xpath, String stringToParser){
        JXDocument jxDocument = JXDocument.create(stringToParser);
        List<Object> xpathElements = jxDocument.sel(xpath);

        List<String> returnValue = new ArrayList<String>();
        for (Object o : xpathElements){
            returnValue.add(o.toString());
        }

        return returnValue;
    }

    /**
     *
     * @param pattern 匹配模式字符串
     * @param stringToParser 被解析字符串
     * @param groupNumber 需要获取的 group 下标
     * @return
     */
    private static List<String> executeRegex(String pattern, String stringToParser, Integer groupNumber){
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(stringToParser);

        List<String> returnValue = new ArrayList<>();
        while (matcher.find()){
            returnValue.add(matcher.group(groupNumber));
        }

        return returnValue;
    }


    public static Event executeStringParser(Task task){
        // 获取到任务所对应的request与输入event
        StringParserRequest request = prepareRequest(task);
        Event inputEvent = task.getInputEvent();
        String stringToParser = null;
        switch (task.getInputEventProperty()){
            case "overview":
                stringToParser = inputEvent.getOverview();
                break;
            case "header":
                stringToParser = inputEvent.getHeader();
                break;
            case "body":
                stringToParser = inputEvent.getBody();
                break;
            default:
                // todo:自定义异常，获取的Event部分名称错误
        }

        // 获取要封装的键值对，并根据request内容对输入event进行解析
        String[] keys = request.getHeader().split("&");
        JSONArray valueArray = JSON.parseArray(request.getBody());
        Integer size = keys.length;
        if (valueArray.size() != size){
            // todo:自定义异常，键值对数量不一致
        }
        List<List<String>> results = new ArrayList<>();

        switch (request.getOverview()){
            case "CSS":
                for (int i = 0; i < size; i++){
                    JSONObject object = valueArray.getJSONObject(i);
                    results.add(
                            executeCSS(
                                    object.getString("selector"),
                                    stringToParser
                            )
                    );
                }
                break;
            case "Xpath":
                for (int i = 0; i < size; i++){
                    JSONObject object = valueArray.getJSONObject(i);
                    results.add(
                            executeXpath(
                                    object.getString("xpath"),
                                    stringToParser
                            )
                    );
                }
                break;
            case "Regex":
                for (int i = 0; i < size; i++){
                    JSONObject object = valueArray.getJSONObject(i);
                    results.add(
                            executeRegex(
                                    object.getString("regex"),
                                    stringToParser,
                                    object.getInteger("groupNumber")
                            )
                    );
                }
                break;
            default:
                //todo:自定义异常
        }
        return new StringParserEvent(Arrays.asList(keys), results);
    }
}
