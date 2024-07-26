package com.sonakbi.modules.blog;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BlogController {

    private final EditorService editorService;

    private static final String BLOG_URL = "/blog";
    private static final String BLOG = "blog";

    @GetMapping(BLOG_URL + "/{userId}/post")
    public String myPostForm(@CurrentAccount Account account, @PathVariable String userId, Model model) {
        model.addAttribute(account);

        return BLOG + "/post";
    }

    @GetMapping(BLOG_URL + "/{userId}/series")
    public String mySeriesForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);

        return BLOG + "/series";
    }

    @GetMapping(BLOG_URL + "/{userId}/about")
    public String myAboutForm(@CurrentAccount Account account, Model model) {
        model.addAttribute(account);

        return BLOG + "/about";
    }

    @GetMapping(BLOG_URL + "/{userId}/view/{url}")
    public String viewForm(@CurrentAccount Account account, Model model,
                           @PathVariable String userId, @PathVariable String url) {
        Editor editor = editorService.getEditor(url);
        model.addAttribute(account);
        model.addAttribute(editor);

        return BLOG + "/view";
    }
}
