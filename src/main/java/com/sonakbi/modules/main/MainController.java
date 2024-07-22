package com.sonakbi.modules.main;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.CurrentAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);
        return "index";
    }
}
