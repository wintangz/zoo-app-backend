package net.wintang.zooapp.controller;

import jakarta.validation.Valid;
import net.wintang.zooapp.dto.request.UserRequestDTO;
import net.wintang.zooapp.util.ApplicationConstants;
import net.wintang.zooapp.util.ResponseObject;
import net.wintang.zooapp.dto.UserDTO;
import net.wintang.zooapp.service.IUserService;
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

    @PostMapping("/staff")
    public ResponseEntity<ResponseObject> createUserStaff(@Valid @RequestBody UserRequestDTO user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject(ApplicationConstants.ResponseStatusMessage.FAILED,
                            bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toString(),
                            null));
        }
        return userService.createStaff(user);
    }

    @GetMapping("/staff")
    public ResponseEntity<ResponseObject> getUsersStaff() { return userService.findAllStaff(); }

    @PutMapping("/staff/{id}")
    public ResponseEntity<ResponseObject> updateUserStaff(@RequestBody UserDTO user,@PathVariable int id) {
        return userService.updateStaff(user, id);
    }

    @DeleteMapping("/staff/{id}")
    public ResponseEntity<ResponseObject> deleteStaff(@PathVariable int id) {
        return userService.deleteStaff(id);
    }

    @PostMapping("/trainers")
    public ResponseEntity<ResponseObject> createNewTrainer(@Valid @RequestBody UserRequestDTO user) { return userService.createZooTrainer(user); }

    @GetMapping("/trainers")
    public ResponseEntity<ResponseObject> getAllTrainerInfo() {return userService.findAllTrainer();}
}
