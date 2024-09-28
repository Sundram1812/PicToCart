package com.pick2cart.PickToCart.service;

import com.pick2cart.PickToCart.entity.Rating;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ProductException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.request.RatingReq;

import java.util.List;

public interface RatingService {
    public Rating createRating(RatingReq ratingReq, User user) throws ProductException, ResourceNotFoundException;
    public List<Rating> getProductRatings(Long productId);
}
