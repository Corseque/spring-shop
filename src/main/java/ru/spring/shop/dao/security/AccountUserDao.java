package ru.spring.shop.dao.security;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.shop.entity.security.AccountUser;

import java.util.Optional;

public interface AccountUserDao extends JpaRepository<AccountUser, Long> {
    Optional<AccountUser> findByUsername(String username);
    Optional<AccountUser> findByUsernameAndPassword(String username, String password);
}
