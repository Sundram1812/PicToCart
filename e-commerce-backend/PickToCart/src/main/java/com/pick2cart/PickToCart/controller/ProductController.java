package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.dto.ProductDto;
import com.pick2cart.PickToCart.entity.Product;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.repository.ProductRepo;
import com.pick2cart.PickToCart.service.serviceImpl.ProductServiceImpl;
import com.pick2cart.PickToCart.service.serviceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ProductRepo productRepo;


    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto product = productService.createProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping
    public ResponseEntity<ProductDto> getProductById(@RequestParam Long productId) throws ResourceNotFoundException {
//        System.out.println(userService.findUserProfileByJwt(jwt));
        ProductDto productById = productService.findProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(productById);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteProductById(@RequestParam Long productId) throws ResourceNotFoundException {
        String message = productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestParam Long productId, @RequestBody ProductDto productDto) throws ResourceNotFoundException {
        ProductDto updatedProductDto = productService.updateProduct(productId, productDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProductDto);

    }


    @GetMapping("/all")
    public ResponseEntity<Page<Product>> findProductByCategoryHandler(@RequestParam String category, @RequestParam List<String> colors,
                                                                         @RequestParam List<String> sizes, @RequestParam Integer minPrice,
                                                                         @RequestParam Integer maxPrice, @RequestParam Integer minDiscount,
                                                                         @RequestParam String sort, @RequestParam String stock,
                                                                         @RequestParam Integer pageNumber, @RequestParam Integer pageSize){

        Page<Product> allProduct = productService.getAllProduct(category, colors, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, pageSize);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(allProduct);

//                                            ?category="shirt"&colors=&sizes=&minPrice=0&maxPrice=10000&minDiscount=23&sort=&stock=&pageNumber=1&pageSize=1

    }



//    Create multiple product

    @PostMapping("/createMultipleProduct")
    public ResponseEntity<List<ProductDto>> createMultipleProduct(@RequestBody ProductDto []productDtos){
        List<ProductDto> allSavedProduct=new ArrayList<>();

        for (ProductDto productDto : productDtos){
            ProductDto savedProductDto = productService.createProduct(productDto);
            allSavedProduct.add(savedProductDto);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(allSavedProduct);
    }
}
