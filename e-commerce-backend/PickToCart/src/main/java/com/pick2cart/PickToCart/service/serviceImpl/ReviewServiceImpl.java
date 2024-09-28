package com.pick2cart.PickToCart.service.serviceImpl;

import com.pick2cart.PickToCart.dto.ProductDto;
import com.pick2cart.PickToCart.entity.Product;
import com.pick2cart.PickToCart.entity.Reviews;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.exception.ReviewsException;
import com.pick2cart.PickToCart.mapper.ProductMapper;
import com.pick2cart.PickToCart.repository.ReviewRepo;
import com.pick2cart.PickToCart.request.ReviewsReq;
import com.pick2cart.PickToCart.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ProductServiceImpl productService;
    private ReviewRepo reviewRepo;

    @Autowired
    public ReviewServiceImpl(ProductServiceImpl productService, ReviewRepo reviewRepo) {
        this.productService = productService;
        this.reviewRepo = reviewRepo;
    }

    @Override
    public Reviews createReview(User user, ReviewsReq reviewsReq) throws ReviewsException, ResourceNotFoundException {
        ProductDto productDto = productService.findProductById(reviewsReq.getProductId());
        Product product = ProductMapper.productDtoToProduct(productDto, new Product());
        product.setProductId(reviewsReq.getProductId());
        Reviews reviews=new Reviews();
        reviews.setUser(user);
        reviews.setProduct(product);
        reviews.setReview(reviewsReq.getReview());
        reviews.setCreatedAt(LocalDateTime.now());
//        System.out.println(reviews);
        Reviews savedReview = reviewRepo.save(reviews);

        return savedReview;
    }

    @Override
    public List<Reviews> getAllReview(Long productId) {
        List<Reviews> allReviewsOfProduct=reviewRepo.getAllReviewsOfProduct(productId);
        return allReviewsOfProduct;
    }
}
