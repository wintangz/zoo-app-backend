package net.wintang.zooapp.service;

import net.wintang.zooapp.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAccountService {
    List<Account> findAllAccounts();
    Account createNewAccount(Account account);
}
