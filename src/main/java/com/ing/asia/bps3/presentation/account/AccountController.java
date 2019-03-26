package com.ing.asia.bps3.presentation.account;

import com.ing.asia.bps3.core.domain.account.Account;
import com.ing.asia.bps3.infrastrcuture.domain.account.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable Long accountId) {
        return ResponseEntity.ok(accountService.getAccount(accountId));
    }
}
