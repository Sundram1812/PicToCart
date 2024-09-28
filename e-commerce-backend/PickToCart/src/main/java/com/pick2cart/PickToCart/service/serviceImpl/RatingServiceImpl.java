package com.pick2cart.PickToCart.service.serviceImpl;

import com.fasterxml.jackson.core.PrettyPrinter;
import com.pick2cart.PickToCart.dto.ProductDto;
import com.pick2cart.PickToCart.entity.Product;
import com.pick2cart.PickToCart.entity.Rating;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ProductException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.mapper.ProductMapper;
import com.pick2cart.PickToCart.repository.RatingRepo;
import com.pick2cart.PickToCart.request.RatingReq;
import com.pick2cart.PickToCart.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    private RatingRepo ratingRepo;
    private ProductServiceImpl productService;

    @Autowired
    public RatingServiceImpl(RatingRepo ratingRepo, ProductServiceImpl productService) {
        this.ratingRepo = ratingRepo;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingReq ratingReq, User user) throws ProductException, ResourceNotFoundException {
        ProductDto productDto=productService.findProductById(ratingReq.getProductId());
        Product product= ProductMapper.productDtoToProduct(productDto, new Product());
        product.setProductId(ratingReq.getProductId());
        Rating rating=new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(ratingReq.getRating());
        rating.setCreatedAt(LocalDateTime.now());
        Rating savedRating = ratingRepo.save(rating);


        return savedRating;
    }

    @Override
    public List<Rating> getProductRatings(Long productId) {
        System.out.println(productId);
        List<Rating> ratings=ratingRepo.getAllRatingsOfProduct(productId);
        return ratings;
    }
}
