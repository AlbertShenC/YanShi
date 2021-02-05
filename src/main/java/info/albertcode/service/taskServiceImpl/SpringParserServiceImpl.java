package info.albertcode.service.taskServiceImpl;

import info.albertcode.domain.event.Event;
import info.albertcode.domain.request.StringParserRequest;
import info.albertcode.domain.task.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.seimicrawler.xpath.JXDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: Albert Shen
 */

public class SpringParserServiceImpl {
    private static StringParserRequest prepareRequest(Task task){
        return new StringParserRequest(task.getRequest());
    }

    /**
     *
     * @param selector CSS选择器的解析语句
     * @param stringToParser 被解析的字符串
     * @return
     */
    public static List<String> executeCSS(String selector, String stringToParser){
        Document document = Jsoup.parse(stringToParser);
        Elements cssElements = document.select(selector);

        List<String> returnValue = new ArrayList<String>();
        for (Element element : cssElements){
            returnValue.add(element.toString());
        }

        return returnValue;
    }

    public static List<String> executeXpath(String xpath, String stringToParser){
        JXDocument jxDocument = JXDocument.create(stringToParser);
        List<Object> xpathElements = jxDocument.sel(xpath);

        List<String> returnValue = new ArrayList<String>();
        for (Object o : xpathElements){
            returnValue.add(o.toString());
        }

        return returnValue;
    }

    public static List<String> executeRegex(String pattern, String stringToParser, Integer groupNumber){
        Pattern r = Pattern.compile(pattern);
        Matcher matcher = r.matcher(stringToParser);

        List<String> returnValue = new ArrayList<String>();
        while (matcher.find()){
            returnValue.add(matcher.group(groupNumber));
        }

        return returnValue;
    }


    public static Event executeSpringParser(Task task){
        StringParserRequest request = prepareRequest(task);
        // todo: 获取输入事件，执行
        return null;
    }
}
