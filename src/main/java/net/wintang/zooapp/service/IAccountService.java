package net.wintang.zooapp.service;

import net.wintang.zooapp.util.ResponseObject;
import net.wintang.zooapp.entity.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IAccountService {
    ResponseEntity<ResponseObject> findAllAccounts();
    ResponseEntity<ResponseObject> createNewAccount(Account account);
    Account updateAccountById(Account account);
    boolean deleteAccountById(Long id);
}
