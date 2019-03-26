package com.ing.asia.bps3.core.domain.account;

public interface AccountRepository {
    Account findById(Long id);

    Account save(Account account);
}
