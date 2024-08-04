package com.sonakbi.modules.blog;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountService;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.comment.Comment;
import com.sonakbi.modules.comment.CommentForm;
import com.sonakbi.modules.comment.CommentService;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorService;
import com.sonakbi.modules.tag.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sonakbi.modules.blog.BlogController.BLOG_URL;

@Controller
@RequiredArgsConstructor
@RequestMapping(BLOG_URL + "/{userId}")
public class BlogController {

    private final EditorService editorService;
    private final AccountService accountService;
    private final TagRepository tagRepository;
    private final CommentService commentService;

    public static final String BLOG_URL = "/blog";
    public static final String BLOG = "blog";

    @GetMapping("/post")
    public String myPostForm(@CurrentAccount Account account, @PathVariable String userId, Model model) {
        model.addAttribute(account);
        Account accountInfo = accountService.getAccountInfo(userId);
        boolean checkEqualAccount = account.checkEqualAccount(account, accountInfo); // true = 본 계정 false = 방문자

        model.addAttribute("accountInfo", accountInfo);
        model.addAttribute("postList", editorService.getEditorList(accountInfo, checkEqualAccount));
        model.addAttribute("tagList", tagRepository.findTagCountByUserId(userId, checkEqualAccount));

        return BLOG + "/post";
    }

    @GetMapping("/series")
    public String mySeriesForm(@CurrentAccount Account account, @PathVariable String userId, Model model) {
        model.addAttribute(account);
        model.addAttribute("accountInfo", accountService.getAccountInfo(userId));

        return BLOG + "/series";
    }

    @GetMapping("/about")
    public String myAboutForm(@CurrentAccount Account account, @PathVariable String userId, Model model) {
        model.addAttribute(account);
        model.addAttribute("accountInfo", accountService.getAccountInfo(userId));

        return BLOG + "/about";
    }

    @GetMapping("/view/{url}")
    public String viewForm(@CurrentAccount Account account, Model model,
                           @PathVariable String userId, @PathVariable String url) {

        Editor editor = editorService.getEditor(url, accountService.getAccountInfo(userId));
        List<Comment> commentList = commentService.getComments(editor);
        model.addAttribute(account);
        model.addAttribute(editor);
        model.addAttribute(commentList);

        return BLOG + "/view";
    }

}
