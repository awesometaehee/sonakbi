package com.sonakbi.modules.account;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    Account findByEmail(String emailOrUserId);

    Account findByUserId(String emailOrUserId);
}
