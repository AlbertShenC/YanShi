package info.albertcode.domain.request;

import info.albertcode.utils.pair.OneKeyManyValues;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * @Description: 解析指定事件
 * overview：指定解析所用方法：CSS选择器（CSS)、Xpath、正则表达式(Regex)
 * header：解析完成后 值 所对应的 键 名称，键之间使用 & 连接，键名称中不得包含 &
 * Body：解析语句，json格式储存，储存格式
 *      [
 *          {
 *              "method": "xpath",
 *              "name": "title",
 *              "selector": "//*[@id=\"post_list\"]/article[1]/section/div/a"
 *          },
 *          {
 *              "method": "regex",
 *              "name": "url",
 *              "selector": "<a>(.*)</a>",
 *              "groupNumber": 1
 *          }
 *      ]
 *      method字段表示采用何种方式解析，目前包含 xpath，css，regex 三种
 *      name字段表示将解析获得的结果，最后封装时的键值对中的键的名称
 *      selector字段表示具体的解析语句
 *      当使用正则表达式时，需额外添加一个字段groupNumber，表示需要提取哪些小括号内的内容，
 *      括号编号从 1 开始，0 表示整个表达式
 * @Author: Albert Shen
 */

public class StringParserRequest extends Request{
    // 储存解析语句
    private OneKeyManyValues keyValuesBody;

    public StringParserRequest(Request request){
        this.setId(request.getId());
        this.setOverview(request.getOverview());
        this.setHeader(request.getHeader());
        this.setBody(request.getBody());
    }

    @Override
    public void setBody(String jsonArrayString) {
        keyValuesBody = new OneKeyManyValues(jsonArrayString);
    }

    public Iterator<LinkedHashMap<String, Object>> getBodyIterator() {
        return keyValuesBody.getIteratorByGroup();
    }
}
