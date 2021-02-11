package info.albertcode.domain.request;

/**
 * @Description: 解析指定事件
 * overview：指定解析所用方法：CSS选择器（CSS)、Xpath、正则表达式(Regex)
 * header：解析完成后 值 所对应的 键 名称，键之间使用 & 连接，键名称中不得包含 &
 * body：解析语句，json格式储存
 *      如采用 Xpath 解析时：//*[@id="post_list"]/article[1]/section/div/a
 *      如采用 CSS选择器 解析时：#post_list > article:nth-child(2) > section > div > a
 *      如采用 正则表达式 解析时：<a>(.*)</a>
 *      当使用正则表达式时，最后需添加一个数字，表示需要提取哪些小括号内的内容，括号编号从 1 开始
 *      0 表示整个表达式
 *      对于 Xpath，储存格式：{"xpath":"xxx"}
 *      对于 CSS，储存格式：{"selector":"xxx"}
 *      对于 正则表达式，储存格式：{"selector":"xxx", "groupNumber":xxx}
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
    }
}
