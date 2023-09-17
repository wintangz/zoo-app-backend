package net.wintang.zooapp.service;

import net.wintang.zooapp.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAccountService {
    public List<Account> findAllAccounts();
    public Account createNewAccount(Account account);
}
