package ru.spring.shop.dao.security;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.shop.entity.security.AccountRole;

public interface AccountRoleDao extends JpaRepository<AccountRole, Long> {
    AccountRole findByName(String name);
}
