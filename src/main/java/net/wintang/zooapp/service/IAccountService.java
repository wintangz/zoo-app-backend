package net.wintang.zooapp.service;

import net.wintang.zooapp.entity.Account;
import net.wintang.zooapp.model.AccountModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAccountService {
    List<AccountModel> findAllAccounts();
    Account createNewAccount(Account account);
    Account updateAccountById(Account account);
    boolean deleteAccountById(Long id);
}
