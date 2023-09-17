package net.wintang.zooapp.service;

import net.wintang.zooapp.model.Account;
import net.wintang.zooapp.repository.IAccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService implements IAccountService {
    private final IAccountRepository accountRepository;
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account createNewAccount(Account account) {
        return accountRepository.save(account);
    }
}
