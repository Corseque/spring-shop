package ru.spring.shop.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.shop.entity.security.Authority;

public interface AuthorityDao extends JpaRepository<Authority, Long> {
}
