package com.pick2cart.PickToCart.service;

import com.pick2cart.PickToCart.dto.ProductDto;
import com.pick2cart.PickToCart.entity.Product;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    public ProductDto createProduct(ProductDto productDto);
    public String deleteProduct(Long productId) throws ResourceNotFoundException;
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws ResourceNotFoundException;
    public ProductDto findProductById(Long productId) throws ResourceNotFoundException;
    public List<ProductDto> findProductByCategory(String category);
    public Page<Product> getAllProduct(String category, List<String> colors, List<String> sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize);
}
