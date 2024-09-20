package com.sonakbi.modules.like;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountService;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorRepository;
import com.sonakbi.modules.editor.EditorService;
import com.sonakbi.modules.editor.dto.EditorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;
    private final EditorService editorService;
    private final AccountService accountService;
    private final EditorRepository editorRepository;
    private final LikesRepository likesRepository;


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

    @GetMapping("/liked")
    public String getLikesList(@CurrentAccount Account account, Model model) {
        Set<Likes> likes = likesRepository.findByAccount(account);
        List<EditorDto> postList = EditorDto.from(editorRepository.findByLikes(likes));

        model.addAttribute(account);
        model.addAttribute("postList", postList);
        return "like/list";
    }
}
