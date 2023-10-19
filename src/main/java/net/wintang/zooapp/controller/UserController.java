package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.dto.request.UserUpdateDTO;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.DuplicatedKeyException;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.exception.PermissionDeniedException;
import net.wintang.zooapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> getUsersById(@PathVariable int id) throws NotFoundException {
        return userService.getUserById(id);
    }

    @GetMapping("/customers")
    public ResponseEntity<ResponseObject> getCustomers() {
        return userService.getCustomers();
    }

    @GetMapping("/zoo-trainers")
    public ResponseEntity<ResponseObject> getZooTrainers() {
        return userService.getZooTrainers();
    }

    @GetMapping("/staff")
    public ResponseEntity<ResponseObject> getStaff() {
        return userService.getStaff();
    }

    @PostMapping("/customers")
    public ResponseEntity<ResponseObject> createCustomer(@Valid @RequestBody UserRequestDTO user) throws DuplicatedKeyException {
        return userService.createCustomer(user);
    }

    @PostMapping("/staff")
    public ResponseEntity<ResponseObject> createStaff(@Valid @RequestBody UserRequestDTO user) throws DuplicatedKeyException {
        return userService.createStaff(user);
    }

    @PostMapping("/zoo-trainers")
    public ResponseEntity<ResponseObject> createZooTrainer(@Valid @RequestBody UserRequestDTO user) throws DuplicatedKeyException {
        return userService.createZooTrainer(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateUserById(@Valid @RequestBody UserUpdateDTO user, @PathVariable int id) throws NotFoundException, PermissionDeniedException {
        return userService.updateUserById(user, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteUserById(@PathVariable int id) throws NotFoundException {
        return userService.deleteUserById(id);
    }

    @PostMapping("/password-reset/verification")
    public ResponseEntity<ResponseObject> verifyEmail(@RequestBody String email) throws NotFoundException {
        return userService.verifyEmail(email);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<ResponseObject> resetPassword(@RequestBody String newPassword,
                                                        @RequestParam String email) throws NotFoundException {
        return userService.resetPassword(newPassword, email);
    }
}
