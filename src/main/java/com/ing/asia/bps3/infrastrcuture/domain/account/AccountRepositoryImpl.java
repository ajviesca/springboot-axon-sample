package com.ing.asia.bps3.infrastrcuture.domain.account;

import com.ing.asia.bps3.core.entity.account.AccountRepository;

public class AccountRepositoryImpl implements AccountRepository {

    private final AccountJPA accountJPA;

    public AccountRepositoryImpl(AccountJPA accountJPA) {
        this.accountJPA = accountJPA;
    }
}
