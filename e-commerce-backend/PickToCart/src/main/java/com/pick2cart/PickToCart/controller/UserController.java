package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<User> findUserById(@RequestHeader("Authorization") String jwt) throws ResourceNotFoundException {
        User user = userService.findUserProfileByJwt(jwt);
        User userInfo = userService.findUserById(user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
