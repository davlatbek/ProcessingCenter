package com.processingcenter.processingcenter.repositories;

import com.processingcenter.processingcenter.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by davlet on 1/31/18.
 * Интерфейс для доступа к объектам Account в бд.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAll();
    List<Account> findAllByLastNameStartsWithIgnoreCase(String lastName);
    Account findByLastName(String lastName);
    Account findBalanceByAccId(Long id);
}
