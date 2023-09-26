package net.wintang.zooapp.controller;

import net.wintang.zooapp.util.ResponseObject;
import net.wintang.zooapp.model.UserDTO;
import net.wintang.zooapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getAllUsersInfo() {
        return userService.findAllUsers();
    }

    @PostMapping("/staff")
    public ResponseEntity<ResponseObject> createNewStaff(@RequestBody UserDTO user) { return userService.createNewStaff(user); }

    @GetMapping("/staff")
    public ResponseEntity<ResponseObject> getAllStaffInfo() { return userService.findAllStaff(); }
}
