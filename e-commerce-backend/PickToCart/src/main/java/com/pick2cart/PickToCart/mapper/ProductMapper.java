package com.pick2cart.PickToCart.mapper;

import com.pick2cart.PickToCart.dto.ProductDto;
import com.pick2cart.PickToCart.entity.Product;

import java.time.LocalDateTime;


public class ProductMapper {


    public static Product productDtoToProduct(ProductDto productDto, Product product){
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setDiscountPercent(productDto.getDiscountPercent());
        product.setQuantity(productDto.getQuantity());
        product.setBrand(productDto.getBrand());
        product.setColor(productDto.getColor());
        product.setNumRatings(productDto.getNumRatings());
        product.setSizes(productDto.getSizes());
        product.setImageUrl(productDto.getImageUrl());
        productDto.setCreatedAt(LocalDateTime.now());

        product.setCategory(productDto.getCategory());
        product.setRatings(productDto.getRatings());
        product.setReviews(productDto.getReviews());
        return product;
    }

    public static ProductDto productToProductDto(Product product, ProductDto productDto){
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setDiscountedPrice(product.getDiscountedPrice());
        productDto.setDiscountPercent(product.getDiscountPercent());
        productDto.setQuantity(product.getQuantity());
        productDto.setBrand(product.getBrand());
        productDto.setColor(product.getColor());
        productDto.setNumRatings(product.getNumRatings());
        productDto.setSizes(product.getSizes());
        productDto.setImageUrl(product.getImageUrl());
        productDto.setCreatedAt(LocalDateTime.now());
        productDto.setRatings(product.getRatings());
        productDto.setReviews(product.getReviews());
        productDto.setCategory(product.getCategory());
        return productDto;
    }
}
