package com.sonakbi.modules.comment;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountRepository;
import com.sonakbi.modules.account.AccountService;
import com.sonakbi.modules.account.CurrentAccount;
import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.editor.EditorService;
import com.sonakbi.modules.util.Chrono;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final EditorService editorService;
    private final CommentService commentService;
    private final AccountService accountService;
    private final CommentRepository commentRepository;

    @PostMapping("/comment/{url}/{id}/create")
    @ResponseBody
    public ResponseEntity<CommentDto> createComment(@CurrentAccount Account account, @RequestBody CommentForm commentForm,
                                                    @PathVariable String url, @PathVariable Long id) {
        String content = commentForm.getContent();
        Editor editor = editorService.getEditor(url, account, accountService.getAccountInfo(id));
        Comment comment = commentService.createComment(content, editor, account);

        CommentDto commentDto = new CommentDto(content, comment.getId(), account.getUserId(), account.getProfileImage(), comment.getCreatedAt());

        return ResponseEntity.ok(commentDto);
    }

    @PostMapping("/comment/{url}/{id}/{seriesId}/update")
    @ResponseBody
    public ResponseEntity<CommentDto> updateComment(@CurrentAccount Account account, @RequestBody CommentForm commentForm,
                                                    @PathVariable String url, @PathVariable Long id, @PathVariable Long seriesId) {
        String content = commentForm.getContent();
        Editor editor = editorService.getEditor(url, account, accountService.getAccountInfo(id));
        Comment comment = commentRepository.findById(seriesId).orElseThrow();

        if(editor == null) {
            return ResponseEntity.badRequest().build();
        }

        commentService.updateComment(comment, content);
        CommentDto commentDto = new CommentDto(content, comment.getId(), account.getUserId(), account.getProfileImage(), comment.getCreatedAt());

        return ResponseEntity.ok(commentDto);
    }

    @PostMapping("/comment/{url}/{id}/{seriesId}/delete")
    @ResponseBody
    public ResponseEntity deleteComment(@CurrentAccount Account account, @PathVariable String url, @PathVariable Long id, @PathVariable Long seriesId) {
        Editor editor = editorService.getEditor(url, account, accountService.getAccountInfo(id));
        Comment comment = commentRepository.findById(seriesId).orElseThrow();
        commentService.deleteComment(editor, comment);
        return ResponseEntity.ok().build();
    }
}
