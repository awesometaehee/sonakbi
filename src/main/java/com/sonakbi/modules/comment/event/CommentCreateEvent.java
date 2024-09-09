package com.sonakbi.modules.comment.event;


import com.sonakbi.modules.comment.Comment;
import com.sonakbi.modules.editor.Editor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentCreateEvent {

    private final Comment comment;

    private final Editor editor;
}
