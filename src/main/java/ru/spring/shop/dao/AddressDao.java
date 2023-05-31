package ru.spring.shop.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.spring.shop.entity.Address;

public interface AddressDao extends JpaRepository<Address, Long> {
}
