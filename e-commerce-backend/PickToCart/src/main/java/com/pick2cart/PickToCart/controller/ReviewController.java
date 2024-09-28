package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.entity.Reviews;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.exception.ReviewsException;
import com.pick2cart.PickToCart.request.ReviewsReq;
import com.pick2cart.PickToCart.service.serviceImpl.ReviewServiceImpl;
import com.pick2cart.PickToCart.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private ReviewServiceImpl reviewService;
    private UserServiceImpl userService;

    @Autowired
    public ReviewController(ReviewServiceImpl reviewService, UserServiceImpl userService) {
        this.reviewService = reviewService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Reviews> createReviews(@RequestBody ReviewsReq reviewsReq, @RequestHeader("Authorization") String jwt) throws ReviewsException, ResourceNotFoundException {
        User user=userService.findUserProfileByJwt(jwt);
        Reviews review = reviewService.createReview(user, reviewsReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(review);
    }

    @GetMapping
    public ResponseEntity<List<Reviews>> getAllReviewOfProduct(@RequestParam Long productId){
        List<Reviews> allReview = reviewService.getAllReview(productId);
        return ResponseEntity.status(HttpStatus.OK).body(allReview);
    }
}
