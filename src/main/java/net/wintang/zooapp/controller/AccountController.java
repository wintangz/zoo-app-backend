package net.wintang.zooapp.controller;

import net.wintang.zooapp.ResponseObject;
import net.wintang.zooapp.model.Account;
import net.wintang.zooapp.service.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountController {
    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("")
    public ResponseEntity<ResponseObject> findAllAccounts(){
        ResponseObject responseObject = new ResponseObject();
        List<Account> accounts = accountService.findAllAccounts();
        if(!accounts.isEmpty()){
            responseObject.setStatus("Ok");
            responseObject.setMessage("Successfully");
            responseObject.setData(accounts);
        } else {
            responseObject.setStatus("Ok");
            responseObject.setMessage("No account found!!!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                responseObject);
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseObject> createNewAccount(@RequestBody Account account) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("Ok", "Create successfully", accountService.createNewAccount(account))
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateAccountById(@PathVariable Long id, @RequestBody Account account) {
        account.setId(id);
        Account updatedAccount = accountService.updateAccountById(account);
        if(updatedAccount != null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Updated successfully", updatedAccount)
            );
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("Failed", "Account not found!", null)
            );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseObject> deleteAccountById(@PathVariable Long id) {
        if(accountService.deleteAccountById(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Deleted successfully", null)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Account not found!", null)
            );
        }
    }
}
