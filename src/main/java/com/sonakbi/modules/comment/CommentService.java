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

    public void createComment(String content, Editor editor, Account account) {
        Comment comment = Comment.builder()
                .content(content)
                .editor(editor)
                .account(account)
                .createdAt(LocalDateTime.now())
                .build();

        commentRepository.save(comment);
    }

    public List<Comment> getComments(Editor editor) {
        return commentRepository.findCommentWithEditorByUrl(editor.getUrl());
    }
}
