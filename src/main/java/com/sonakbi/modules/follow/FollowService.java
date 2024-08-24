package com.sonakbi.modules.follow;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final AccountRepository accountRepository;

    public void follow(Long followerId, Long followingId) {
        Account follower = accountRepository.findById(followerId).orElseThrow();
        Account following = accountRepository.findById(followingId).orElseThrow();

        boolean isExist = followRepository.existsByFollowerIdAndFollowingId(followerId, followingId);

        if(!isExist) {
            Follow follow = Follow.builder()
                    .follower(follower)
                    .following(following)
                    .build();

            followRepository.save(follow);
        } else {
            throw new IllegalArgumentException("이미 팔로우한 계정입니다.");
        }
    }

    public void unfollow(Long followerId, Long followingId) {
        Follow follow = followRepository.findByFollowerIdAndFollowingId(followerId, followingId);
        if(follow != null) {
            followRepository.deleteById(follow.getId());
        }
    }

    public boolean isFollowing(Account currentUser, Account targetUser) {
        return followRepository.existsByFollowerAndFollowing(currentUser, targetUser);
    }
}
