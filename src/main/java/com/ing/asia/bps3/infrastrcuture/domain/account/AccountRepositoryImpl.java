package com.ing.asia.bps3.infrastrcuture.domain.account;

import com.ing.asia.bps3.core.domain.account.Account;
import com.ing.asia.bps3.core.domain.account.AccountRepository;

public class AccountRepositoryImpl implements AccountRepository {

    private final AccountEntityJPA accountEntityJPA;

    public AccountRepositoryImpl(AccountEntityJPA accountEntityJPA) {
        this.accountEntityJPA = accountEntityJPA;
    }

    @Override
    public Account findById(Long id) {
        AccountEntity accountEntity = accountEntityJPA.findById(id).get();
        return new Account(accountEntity.getId(), accountEntity.getName(), accountEntity.getBalance());
    }

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = accountEntityJPA.findById(account.getId()).get();
        accountEntity.setBalance(account.getBalance());
        accountEntity.setName(account.getName());
        AccountEntity saved = accountEntityJPA.save(accountEntity);
        return new Account(saved.getId(), saved.getName(), saved.getBalance());
    }
}
