package com.pick2cart.PickToCart.service;

import com.pick2cart.PickToCart.entity.Reviews;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.exception.ReviewsException;
import com.pick2cart.PickToCart.request.ReviewsReq;

import java.util.List;

public interface ReviewService {
    public Reviews createReview(User user, ReviewsReq reviewsReq) throws ReviewsException, ResourceNotFoundException;
    public List<Reviews> getAllReview(Long productId);
}
