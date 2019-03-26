package com.ing.asia.bps3.infrastrcuture.domain.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountJPA extends JpaRepository<AccountEntity, Long> {
}
