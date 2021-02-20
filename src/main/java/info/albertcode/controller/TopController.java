package info.albertcode.controller;

import info.albertcode.dispatch.YanShiCoreController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: Albert Shen
 */
@Controller
@RequestMapping("/top")
public class TopController {
    YanShiCoreController procedureController;

    @Autowired
    public TopController(YanShiCoreController procedureController) {
        this.procedureController = procedureController;
    }

    @RequestMapping("/init")
    public String execute() throws Exception {
        procedureController.init();
        return "result";
    }
}
