package com.sonakbi.modules.follow.event;

import com.sonakbi.modules.follow.Follow;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FollowCreateEvent {

    private final Follow follow;
}
