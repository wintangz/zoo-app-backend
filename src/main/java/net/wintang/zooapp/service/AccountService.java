package net.wintang.zooapp.service;

import net.wintang.zooapp.ResponseObject;
import net.wintang.zooapp.entity.Account;
import net.wintang.zooapp.model.AccountDTO;
import net.wintang.zooapp.repository.IAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService implements IAccountService {
    private final IAccountRepository accountRepository;

    @Autowired
    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    private List<AccountDTO> mapToModel(List<Account> accounts) {
        return accounts.stream().map(AccountDTO::new).toList();
    }

    @Override
    public ResponseEntity<ResponseObject> findAllAccounts() {
        ResponseObject response = new ResponseObject();
        List<AccountDTO> accounts = mapToModel(accountRepository.findAll());
        if(!accounts.isEmpty()){
            response.setStatus("Ok");
            response.setMessage("Successfully");
            response.setData(accounts);
        } else {
            response.setStatus("Ok");
            response.setMessage("No account found!!!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ResponseObject> createNewAccount(Account account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("Ok", "Create successfully", accountRepository.save(account)));
    }

    @Override
    public Account updateAccountById(Account account) {
        if(accountRepository.findById(account.getId()).isPresent()){
            return accountRepository.save(account);
        }
        return null;
    }

    @Override
    public boolean deleteAccountById(Long id) {
        if(accountRepository.findById(id).isPresent()) {
            accountRepository.deleteById(id);
            return true;
        } else return false;
    }
}
