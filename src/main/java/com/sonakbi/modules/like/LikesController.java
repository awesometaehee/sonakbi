package com.sonakbi.modules.like;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountService;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;
    private final EditorService editorService;
    private final AccountService accountService;

    @PostMapping("/{url}/{id}/like")
    @ResponseBody
    public ResponseEntity likeSubmit(@CurrentAccount Account account, @PathVariable String url, @PathVariable Long id) {
        Editor editor = editorService.getEditor(url, account, accountService.getAccountInfo(id));
        likesService.likeSubmit(editor, account, url);
        int likeCount = editor.getLikeCount();
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/{url}/{id}/isLiked")
    public ResponseEntity<Boolean> isLiked(@CurrentAccount Account account, @PathVariable String url, @PathVariable Long id) {
        Editor editor = editorService.getEditor(url, account, accountService.getAccountInfo(id));
        Boolean isLiked = likesService.isLiked(editor, account, url);
        return ResponseEntity.ok(isLiked);
    }
}
