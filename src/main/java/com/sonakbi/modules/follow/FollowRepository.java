package com.sonakbi.modules.follow;

import com.sonakbi.modules.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    boolean existsByFollowerAndFollowing(Account currentUser, Account targetUser);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);
}
