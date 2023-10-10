package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.dto.request.UserUpdateDTO;
import net.wintang.zooapp.service.IUserService;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUsersById(@PathVariable int id) {
        return userService.findUserById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody UserRequestDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList(),
                            null));
        }
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@Valid @RequestBody UserUpdateDTO user, BindingResult bindingResult, @PathVariable int id) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList(),
                            null));
        }
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable int id) {
        return userService.deleteUserById(id);
    }
}
