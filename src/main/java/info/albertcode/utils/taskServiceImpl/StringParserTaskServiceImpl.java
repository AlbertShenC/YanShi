package info.albertcode.utils.taskServiceImpl;

import info.albertcode.domain.event.Event;
import info.albertcode.domain.event.StringParserEvent;
import info.albertcode.domain.request.StringParserRequest;
import info.albertcode.domain.task.Task;
import info.albertcode.utils.exception.CustomException;
import info.albertcode.utils.pair.OneKeyManyValues;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;

import java.util.Iterator;
import java.util.LinkedHashMap;
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
    private static void executeCSS(String selector, String stringToParser, String key, OneKeyManyValues keyValues){
        Document document = Jsoup.parse(stringToParser);
        Elements cssElements = document.select(selector);

        for (Element element : cssElements){
            keyValues.addValue(key, element.toString());
        }
    }

    /**
     *
     * @param xpath Xpath的解析语句
     * @param stringToParser 被解析的字符串
     * @return
     */
    private static void executeXpath(String xpath, String stringToParser, String key, OneKeyManyValues keyValues){
        JXDocument jxDocument = JXDocument.create(stringToParser);
        List<Object> xpathElements = jxDocument.sel(xpath);

        for (Object o : xpathElements){
            keyValues.addValue(key, o.toString());
        }
    }

    /**
     *
     * @param pattern 匹配模式字符串
     * @param stringToParser 被解析字符串
     * @param groupNumber 需要获取的 group 下标
     * @return
     */
    private static void executeRegex(String pattern, String stringToParser, Integer groupNumber, String key, OneKeyManyValues keyValues){
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(stringToParser);

        while (matcher.find()){
            keyValues.addValue(key, matcher.group(groupNumber));
        }
    }


    public static Event executeStringParser(Task task) throws CustomException {
        // 获取到任务所对应的request与 要处理的输入event字符串
        StringParserRequest request = prepareRequest(task);
        String stringToParser = getInputEventString(task);

        OneKeyManyValues keyValues = new OneKeyManyValues();

        // 获取解析语句迭代器
        Iterator<LinkedHashMap<String, Object>> bodyIterator = request.getBodyIterator();
        while (bodyIterator.hasNext()){
            LinkedHashMap<String, Object> next = bodyIterator.next();
            switch ((String) next.get("method")){
                case "xpath":
                    executeXpath((String) next.get("selector"),
                            stringToParser,
                            (String) next.get("name"),
                            keyValues);
                    break;
                case "css":
                    executeCSS((String) next.get("selector"),
                            stringToParser,
                            (String) next.get("name"),
                            keyValues);
                    break;
                case "regex":
                    executeRegex((String) next.get("selector"),
                            stringToParser,
                            (Integer) next.get("groupNumber"),
                            (String) next.get("name"),
                            keyValues);
                    break;
                default:
                    throw new CustomException("暂无" + next.get("method") + "方式的解析方式");
            }
        }

        return new StringParserEvent(keyValues.toJsonStringByGroup());
    }
}
