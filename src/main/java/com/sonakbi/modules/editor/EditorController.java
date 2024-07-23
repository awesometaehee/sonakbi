package com.sonakbi.modules.editor;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.CurrentAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EditorController {

    private static final String EDITOR_URL = "/editor";
    private static final String EDITOR = "editor";

    @GetMapping(EDITOR_URL + "/write")
    public String writeForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);

        return EDITOR + "/write";
    }
}
