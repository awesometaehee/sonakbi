package com.sonakbi.modules.like.event;

import com.sonakbi.modules.editor.Editor;
import com.sonakbi.modules.like.Likes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LikesCreateEvent {

    private final Likes likes;

    private final Editor editor;
}
