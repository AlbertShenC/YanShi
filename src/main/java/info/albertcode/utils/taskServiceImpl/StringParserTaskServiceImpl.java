package info.albertcode.utils.taskServiceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import info.albertcode.domain.event.Event;
import info.albertcode.domain.event.StringParserEvent;
import info.albertcode.domain.request.StringParserRequest;
import info.albertcode.domain.task.Task;
import info.albertcode.utils.pair.impl.OneKeyManyValues;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: Albert Shen
 */
public class StringParserTaskServiceImpl extends TaskWithInputEvent{

    private static StringParserRequest prepareRequest(Task task){
        return new StringParserRequest(task.getRequest());
    }

    /**
     *
     * @param selector CSS选择器的解析语句
     * @param stringToParser 被解析的字符串
     * @return
     */
    private static void executeCSS(String selector, String stringToParser, int keySubscript, OneKeyManyValues keyValues){
        Document document = Jsoup.parse(stringToParser);
        Elements cssElements = document.select(selector);

        for (Element element : cssElements){
            keyValues.addValue(keySubscript, element.toString());
        }
    }

    /**
     *
     * @param xpath Xpath的解析语句
     * @param stringToParser 被解析的字符串
     * @return
     */
    private static void executeXpath(String xpath, String stringToParser, int keySubscript, OneKeyManyValues keyValues){
        JXDocument jxDocument = JXDocument.create(stringToParser);
        List<Object> xpathElements = jxDocument.sel(xpath);

        for (Object o : xpathElements){
            keyValues.addValue(keySubscript, o.toString());
        }
    }

    /**
     *
     * @param pattern 匹配模式字符串
     * @param stringToParser 被解析字符串
     * @param groupNumber 需要获取的 group 下标
     * @return
     */
    private static void executeRegex(String pattern, String stringToParser, Integer groupNumber, int keySubscript, OneKeyManyValues keyValues){
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(stringToParser);

        while (matcher.find()){
            keyValues.addValue(keySubscript, matcher.group(groupNumber));
        }
    }


    public static Event executeStringParser(Task task){
        // 获取到任务所对应的request与 要处理的输入event字符串
        StringParserRequest request = prepareRequest(task);
        String stringToParser = getInputEventString(task);

        // 获取要封装的键值对，并根据request内容对输入event进行解析
        String[] keys = request.getHeader().split("&");
        OneKeyManyValues keyValues = new OneKeyManyValues();
        for (String key : keys){
            keyValues.addKey(key);
        }

        JSONArray valueArray = JSON.parseArray(request.getBody());
        Integer size = keys.length;
        if (valueArray.size() != size){
            // todo:自定义异常，键值对数量不一致
        }

        switch (request.getOverview()){
            case "CSS":
                for (int i = 0; i < size; i++){
                    JSONObject object = valueArray.getJSONObject(i);
                    executeCSS(
                            object.getString("selector"),
                            stringToParser,
                            i,
                            keyValues
                    );
                }
                break;
            case "Xpath":
                for (int i = 0; i < size; i++){
                    JSONObject object = valueArray.getJSONObject(i);
                    executeXpath(
                            object.getString("xpath"),
                            stringToParser,
                            i,
                            keyValues
                    );
                }
                break;
            case "Regex":
                for (int i = 0; i < size; i++){
                    JSONObject object = valueArray.getJSONObject(i);
                    executeRegex(
                            object.getString("regex"),
                            stringToParser,
                            object.getInteger("groupNumber"),
                            i,
                            keyValues
                    );
                }
                break;
            default:
                //todo:自定义异常
        }
        return new StringParserEvent(keyValues.toJsonString());
    }
}
