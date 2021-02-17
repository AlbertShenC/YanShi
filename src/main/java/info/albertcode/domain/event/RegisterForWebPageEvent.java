package info.albertcode.domain.event;

import java.util.Date;

/**
 * @Description: 与普通Task生成的Event不同
 *               此Event只有在对应Task没有 输出Event时（即正常情况下的首次执行时）
 *               才会被新建，之后的执行都是覆盖此Event的body内容
 *               Event的id等于前端访问页面所需的id号
 * overview：
 * header：
 * body：页面内容
 * @Author: Albert Shen
 */

public class RegisterForWebPageEvent extends Event{
    public RegisterForWebPageEvent(String body) {
        this.type = "RegisterForWebPage";
        this.generatedTime = new Date();
        this.body = body;
    }

    public RegisterForWebPageEvent(Event event) {
        this.setId(event.getId());
        this.setBelongedTaskName(event.getBelongedTaskName());
        this.setGeneratedTime(new Date());
        this.setType("RegisterForWebPage");
        this.setOverview(event.getOverview());
        this.setHeader(event.getHeader());
        this.setBody(event.getBody());
    }
}
