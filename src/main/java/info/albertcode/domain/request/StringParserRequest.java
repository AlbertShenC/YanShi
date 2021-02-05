package info.albertcode.domain.request;

/**
 * @Description: 解析指定事件
 * overview：指定解析所用方法：CSS选择器（CSS)、Xpath、正则表达式(Regex)
 * header：当使用正则表达式时，需要提取哪些小括号内的内容，采用 & 连接，编号从 1 开始
 *         如 1&5
 *         0 表示整个表达式
 * body：解析语句
 *      如采用 Xpath 解析时：//*[@id="post_list"]/article[1]/section/div/a
 *      如采用 CSS选择器 解析时：#post_list > article:nth-child(2) > section > div > a
 *      如采用 正则表达式 解析时：<a>(.*)</a>
 * @Author: Albert Shen
 */

public class StringParserRequest extends Request{
    public StringParserRequest(){
        this.type = "StringParser";
    }

    public StringParserRequest(Request request){
        this.setId(request.getId());
        this.type = "StringParser";
        this.setOverview(request.getOverview());
        this.setHeader(request.getHeader());
        this.setBody(request.getBody());
        this.setAddition(request.getAddition());
    }
}
