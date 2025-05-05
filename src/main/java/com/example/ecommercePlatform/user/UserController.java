package com.example.ecommercePlatform.user;

import com.example.ecommercePlatform.user.Persistance.User;
import com.example.ecommercePlatform.user.model.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = userService.getUser(username);
        return ResponseEntity.ok(user);

    }
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUsersId(@PathVariable Long id) {
        userService.getUserById(id);
        return ResponseEntity.ok(userService.getUserById(id));
    }


    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        userService.getAllUsers();
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @PostMapping("/create")
    public void createUser(@RequestBody UserRequest request){
        userService.createUser(request);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable long id, @RequestBody UserRequest request) {
        userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }



}
