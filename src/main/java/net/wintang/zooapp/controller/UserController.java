package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.UserRequestDTO;
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

    @GetMapping("/{role}")
    public ResponseEntity<ResponseObject> getUsersByRole(@PathVariable String role) {
        return userService.findUsersByRole(role);
    }

    @PostMapping("/{role}")
    public ResponseEntity<ResponseObject> createUserByRole(@Valid @RequestBody UserRequestDTO user, @PathVariable String role, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ApplicationConstants.ResponseStatus.FAILED,
                            bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList(),
                            null));
        }
        return userService.createUserByRole(user, role);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUser(@RequestBody UserRequestDTO user, @PathVariable int id) {
        return userService.updateStaff(user, id);
    }

    @DeleteMapping("/{role}/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable String role, @PathVariable int id) {
        return userService.deleteUserByRoleAndId(role, id);
    }
}
