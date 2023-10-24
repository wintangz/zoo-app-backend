package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.*;
import net.wintang.zooapp.dto.response.ResponseObject;
import net.wintang.zooapp.exception.DuplicatedKeyException;
import net.wintang.zooapp.exception.NotFoundException;
import net.wintang.zooapp.exception.PermissionDeniedException;
import net.wintang.zooapp.service.IUserService;
import net.wintang.zooapp.util.TokenExtractor;
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

    @GetMapping("/{id}/orders")
    public ResponseEntity<ResponseObject> getOrdersByUserId(@PathVariable int id) throws NotFoundException, PermissionDeniedException {
        return userService.getOrdersByUserId(id);
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

    @PostMapping("/password-reset/email")
    public ResponseEntity<ResponseObject> verifyEmail(@RequestBody EmailVerificationRequestDTO emailVerificationRequestDTO) throws NotFoundException {
        return userService.verifyEmail(emailVerificationRequestDTO.getEmail());
    }

    @PostMapping("/password-reset/verification-code")
    public ResponseEntity<ResponseObject> verifyCode(@RequestBody VerificationRequestDTO code) throws NotFoundException {
        return userService.verifyCode(code);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<ResponseObject> resetPassword(@Valid @RequestBody PasswordResetDTO request) throws NotFoundException {
        return userService.resetPassword(request.getNewPassword(), request.getToken());
    }

    @PostMapping("/password-change")
    public ResponseEntity<ResponseObject> changePassword(@Valid @RequestBody PasswordChangeDTO request, @RequestHeader("Authorization") String authorization) throws NotFoundException {
        return userService.changePassword(request.getOldPassword(), request.getNewPassword(), TokenExtractor.extractToken(authorization));
    }
}
