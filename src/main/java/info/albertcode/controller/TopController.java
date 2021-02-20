package info.albertcode.controller;

import info.albertcode.dispatch.YanShiCoreController;
import info.albertcode.dispatch.YanShiCoreTimer;
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
    YanShiCoreController yanShiCoreController;
    YanShiCoreTimer yanShiCoreTimer;

    @Autowired
    public TopController(YanShiCoreController yanShiCoreController, YanShiCoreTimer yanShiCoreTimer) {
        this.yanShiCoreController = yanShiCoreController;
        this.yanShiCoreTimer = yanShiCoreTimer;
    }

    @RequestMapping("/init")
    public String init() throws Exception {
        yanShiCoreController.init();
        return "result";
    }

    @RequestMapping("/execute")
    public String execute(Integer procedureId) {
        yanShiCoreTimer.registerProcedure(procedureId);
        return "result";
    }
}
