package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.entity.Rating;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ProductException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.request.RatingReq;
import com.pick2cart.PickToCart.service.serviceImpl.RatingServiceImpl;
import com.pick2cart.PickToCart.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class RatingController {

    private RatingServiceImpl ratingService;
    private UserServiceImpl userService;

    @Autowired
    public RatingController(RatingServiceImpl ratingService, UserServiceImpl userService) {
        this.ratingService = ratingService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody RatingReq ratingReq, @RequestHeader("Authorization") String jwt) throws ResourceNotFoundException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Rating rating = ratingService.createRating(ratingReq, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(rating);
    }


    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatingOfProduct(@RequestParam Long productId){
        List<Rating> productRatings = ratingService.getProductRatings(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productRatings);
    }
}
