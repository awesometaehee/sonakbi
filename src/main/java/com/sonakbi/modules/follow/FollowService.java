package com.sonakbi.modules.follow;

import com.sonakbi.modules.account.Account;
import com.sonakbi.modules.account.AccountFollowDto;
import com.sonakbi.modules.account.AccountRepository;
import com.sonakbi.modules.follow.event.FollowCreateEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final AccountRepository accountRepository;
    private final ApplicationEventPublisher eventPublisher;

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
            eventPublisher.publishEvent(new FollowCreateEvent(follow));
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

    public List<AccountFollowDto> getFollowStatus(Long userId, Long currentUser) {
        List<Follow> followers = followRepository.findAllFollowerIdByAccountId(userId);
        List<Follow> following = followRepository.findAllFollowingIdByAccountId(currentUser);

        List<AccountFollowDto> collect = followers.stream()
                .map(follower -> {
                    boolean isFollowing = following.stream()
                            .anyMatch(f -> f.getFollowing().getId().equals(follower.getFollower().getId()));
                    return new AccountFollowDto(follower.getFollowing().getId()
                            , follower.getFollowing().getUserId()
                            , follower.getFollowing().getProfileImage()
                            , follower.getFollowing().getShortDescription()
                            , isFollowing);
                }).toList();

        /*
        followers.stream().map(follower -> {
            boolean isFollowing = following.stream()
                    .anyMatch(f -> f.getFollowing().getId().equals(follower.getFollower().getId()));
            return new AccountFollowDto(follower.getFollower(), isFollowing);
        }).collect(Collectors.toList());
        */

        return null;
    }

    public List<AccountFollowDto> getFollowerList(Long userId, Long currentUserId) {
        List<Follow> followers = followRepository.findAllFollowerIdByAccountId(currentUserId);
        List<Follow> followings = followRepository.findAllFollowingIdByAccountId(userId);

        return followers.stream().map(follower -> {
            boolean isFollowing = followings.stream()
                    .anyMatch(f -> f.getFollowing().getId().equals(follower.getFollower().getId()));

            return new AccountFollowDto(follower.getFollower().getId()
                    , follower.getFollower().getUserId()
                    , follower.getFollower().getProfileImage()
                    , follower.getFollower().getShortDescription()
                    , isFollowing);
        }).collect(Collectors.toList());
    }

    public List<AccountFollowDto> getFollowingList(Long userId, Long currentUserId) {
        List<Follow> followings = followRepository.findAllFollowingIdByAccountId(currentUserId);
        List<Follow> followers = followRepository.findAllFollowerIdByAccountId(userId);

        return followings.stream().map(following -> {
            boolean isFollowing = followers.stream()
                    .anyMatch(f -> f.getFollower().getId().equals(following.getFollowing().getId()));
            return new AccountFollowDto(following.getFollowing().getId()
                    , following.getFollowing().getUserId()
                    , following.getFollowing().getProfileImage()
                    , following.getFollowing().getShortDescription()
                    , isFollowing);
        }).collect(Collectors.toList());
    }
}
