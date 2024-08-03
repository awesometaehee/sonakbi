package com.sonakbi.modules.comment;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.editor.Editor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(String content, Editor editor, Account account) {
        editor.addComment();

        Comment comment = new Comment();
        comment.setAccount(account);
        comment.setEditor(editor);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    public List<Comment> getComments(Editor editor) {
        return commentRepository.findCommentWithEditorByUrl(editor.getUrl());
    }

    public void updateComment(Comment comment, String content) {
        comment.setContent(content);
    }
}
