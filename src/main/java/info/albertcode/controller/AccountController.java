package info.albertcode.controller;

import info.albertcode.domain.User;
import info.albertcode.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Description: Account账户层
 * @Author: Albert Shen
 */
@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @RequestMapping(value = "/findAll")
    public String findAll(Model model){
        System.out.println("表现层：查询所有账户信息...");
        List<User> list = accountService.findAll();
        System.out.println("表现层：查询所有账户完成...");
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping("/save")
    public void save(User account, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("表现层：保存账户信息...");
        accountService.saveAccount(account);
        response.sendRedirect(request.getContextPath() + "/account/findAll");
        return;
    }
}
