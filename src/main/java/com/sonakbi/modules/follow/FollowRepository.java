package com.sonakbi.modules.follow;

import com.sonakbi.modules.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByFollowerIdAndFollowingId(Long followerId, Long followingId);

    boolean existsByFollowerAndFollowing(Account currentUser, Account targetUser);

    boolean existsByFollowerIdAndFollowingId(Long followerId, Long followingId);

    @Query("select count(*) from Follow f join f.follower a where a.id = :id")
    int countFollowerByAccountId(@Param("id") Long id);

    @Query("select count(*) from Follow f join f.following a where a.id = :id")
    int countFollowingByAccountId(@Param("id") Long id);
}
