package com.fixitnow.fix_it_now.controller;

import com.fixitnow.fix_it_now.Entity.UserEntity;
import com.fixitnow.fix_it_now.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@RequestBody UserEntity userEntity){
        return ResponseEntity.ok(userService.addUser(userEntity));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserEntity userEntity){
        return ResponseEntity.ok(userService.updateUser(id, userEntity));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long id){
        return ResponseEntity.ok(userService.getProject(id));
    }
}
