package com.sonakbi.modules.blog;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.CurrentAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    private static final String BLOG_URL = "/blog";
    private static final String BLOG = "blog";

    @GetMapping(BLOG_URL + "/post")
    public String myPostForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);

        return BLOG + "/post";
    }

    @GetMapping(BLOG_URL + "/series")
    public String mySeriesForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);

        return BLOG + "/series";
    }

    @GetMapping(BLOG_URL + "/about")
    public String myAboutForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);

        return BLOG + "/about";
    }
}
