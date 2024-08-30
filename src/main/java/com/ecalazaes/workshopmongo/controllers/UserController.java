package com.ecalazaes.workshopmongo.controllers;

import com.ecalazaes.workshopmongo.dto.UserDTO;
import com.ecalazaes.workshopmongo.entities.Post;
import com.ecalazaes.workshopmongo.entities.User;
import com.ecalazaes.workshopmongo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>>  findAll(){
        List<User> list = userService.findAll();
        List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
        return ResponseEntity.ok(listDto);
    }

    @GetMapping( "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id){
        User user = userService.findById(id);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @PostMapping("/save")
    public ResponseEntity<UserDTO> findById(@RequestBody UserDTO userDTO){
        User user = userService.fromDTO(userDTO);
        user = userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserDTO(user));
    }

    @DeleteMapping( "delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable String id){
        user = userService.updateUser(id, user);
        return ResponseEntity.ok(user);
    }

    @GetMapping( "/{id}/posts")
    public ResponseEntity<List<Post>> findPosts(@PathVariable String id){
        User user = userService.findById(id);
        return ResponseEntity.ok(user.getPosts());
    }

}
