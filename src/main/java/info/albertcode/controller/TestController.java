package info.albertcode.controller;

import info.albertcode.utils.exception.CustomException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "")
    public String testPut(@CookieValue(value = "JSESSIONID") String cookieValue) throws CustomException {
        System.out.println(cookieValue);
        throw new CustomException("一个自定义错误");
//        return "result";
    }
}
