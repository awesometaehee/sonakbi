package com.sonakbi.modules.main;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountRepository;
import com.sonakbi.modules.account.CurrentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final AccountRepository accountRepository;

    @GetMapping("/")
    public String home(@CurrentAccount Account account, Model model) {
        if(account != null) {
            model.addAttribute(account);
        }
        return "index";
    }
}
