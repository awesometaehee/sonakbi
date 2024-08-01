package com.sonakbi.modules.comment;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final EditorService editorService;
    private final CommentService commentService;

    @PostMapping("/comment/{url}")
    public String createComment(@CurrentAccount Account account, @RequestBody CommentForm commentForm, @PathVariable String url, Model model) {
        String content = commentForm.getContent();
        Editor editor = editorService.getEditor(url, account);
        commentService.createComment(content, editor, account);

        return "redirect:/blog/" + account.getAccountPath(account.getUserId()) + "/view/" + editor.getUrl();
    }
}
