package ru.spring.shop.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.shop.entity.security.AccountUser;
import ru.spring.shop.entity.security.ConfirmationToken;

import java.util.Optional;
import java.util.Set;

public interface ConfirmationTokenDao extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
    Set<Optional<ConfirmationToken>> findByAccountUser(AccountUser accountUser);
}
