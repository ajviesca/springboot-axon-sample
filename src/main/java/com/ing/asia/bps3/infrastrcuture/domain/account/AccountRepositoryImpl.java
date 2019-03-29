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

    @Override
    public Account save(Account account) {
        AccountEntity accountEntity = accountJPA.findById(account.getId()).get();
        accountEntity.setBalance(account.getBalance());
        accountEntity.setName(account.getName());
        AccountEntity saved = accountJPA.save(accountEntity);
        return new Account(saved.getId(), saved.getName(), saved.getBalance());
    }
}
