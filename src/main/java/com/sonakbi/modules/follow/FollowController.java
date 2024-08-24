package com.sonakbi.modules.follow;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountService;
import com.sonakbi.modules.account.CurrentAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;
    private final AccountService accountService;

    @PostMapping("/{followerId}/follow/{followingId}")
    public ResponseEntity follow(@PathVariable Long followerId, @PathVariable Long followingId) {
        followService.follow(followerId, followingId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{followerId}/unfollow/{followingId}")
    public ResponseEntity unfollow(@PathVariable Long followerId, @PathVariable Long followingId) {
        followService.unfollow(followerId, followingId);
        return ResponseEntity.ok().build();
    }
}
