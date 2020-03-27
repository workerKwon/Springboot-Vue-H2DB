package com.example.springboot_vue_h2db.controller;

import com.example.springboot_vue_h2db.model.User;
import com.example.springboot_vue_h2db.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "httpL//localhost:4200")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository repository;

    @RequestMapping(method = RequestMethod.GET, value = "/userList")
    public List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        repository.findAll().forEach(users::add);

        return users;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public User postUser(@RequestBody User user){
        User _user = repository.save(new User(user.getName(), user.getAge(), user.getPh()));
        return _user;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id){
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/age/{age}")
    public List<User> findByAge(@PathVariable int age) {
        List<User> users = repository.findByAge(age);
        return users;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user){
        Optional<User> userData = repository.findById(id);

        if(userData.isPresent()){
            User _user = userData.get();
            _user.setName(user.getName());
            _user.setAge(user.getAge());
            _user.setPh(user.getPh());
            _user.setActive(user.isActive());
            return new ResponseEntity<>(repository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
