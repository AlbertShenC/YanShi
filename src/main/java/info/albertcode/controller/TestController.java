package info.albertcode.controller;

import info.albertcode.dao.encapsulation.CoreDaoEncapsulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    CoreDaoEncapsulation coreDaoEncapsulation;

    @RequestMapping("")
    public String test(String name) {
        Map<String, Integer> map = coreDaoEncapsulation.findAllTaskNameIdPairByFuzzyName(name);
        System.out.println(map);
        System.out.println(map.keySet());
        return "result";
    }
}
