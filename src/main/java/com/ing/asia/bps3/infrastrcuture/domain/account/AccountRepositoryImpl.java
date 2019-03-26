package com.ing.asia.bps3.infrastrcuture.domain.account;

import com.ing.asia.bps3.core.domain.account.Account;
import com.ing.asia.bps3.core.domain.account.AccountRepository;

public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJPA accountJPA;

    public AccountRepositoryImpl(AccountJPA accountJPA) {
        this.accountJPA = accountJPA;
    }

    @Override
    public Account findById(Long id) {
        AccountEntity accountEntity = accountJPA.findById(id).get();
        return new Account(accountEntity.getId(), accountEntity.getName(), accountEntity.getBalance());
    }
}
