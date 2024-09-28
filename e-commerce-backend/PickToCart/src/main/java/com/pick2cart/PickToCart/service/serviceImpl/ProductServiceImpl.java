package com.pick2cart.PickToCart.service.serviceImpl;

import com.pick2cart.PickToCart.dto.ProductDto;
import com.pick2cart.PickToCart.entity.Category;
import com.pick2cart.PickToCart.entity.Product;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.mapper.ProductMapper;
import com.pick2cart.PickToCart.repository.CategoryRepo;
import com.pick2cart.PickToCart.repository.ProductRepo;
import com.pick2cart.PickToCart.service.ProductService;
import com.pick2cart.PickToCart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {



    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ProductRepo productRepo;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category topLevel = categoryRepo.findByName(productDto.getTopLevelCategory());

        if(topLevel == null){
            Category topLevelCategory=new Category();
            topLevelCategory.setName(productDto.getTopLevelCategory());
            topLevelCategory.setLevel(1);
            topLevel = categoryRepo.save(topLevelCategory);
        }
        Category secondLevel = categoryRepo.findByNameAndParent(productDto.getSecondLevelCategory(), topLevel.getName());
        if(secondLevel == null){
            Category secondLevelCategory=new Category();
            secondLevelCategory.setName(productDto.getSecondLevelCategory());
            secondLevelCategory.setParentCategory(topLevel);
            secondLevelCategory.setLevel(2);
            secondLevel = categoryRepo.save(secondLevelCategory);
        }

        Category thirdLevel = categoryRepo.findByNameAndParent(productDto.getThirdLevelCategory(), secondLevel.getName());
        if(thirdLevel == null){
            Category thirdLevelCategory=new Category();
            thirdLevelCategory.setName(productDto.getThirdLevelCategory());
            thirdLevelCategory.setParentCategory(secondLevel);
            thirdLevelCategory.setLevel(3);
            thirdLevel = categoryRepo.save(thirdLevelCategory);
        }

        System.out.println(productDto.getSizes());
        Product product = ProductMapper.productDtoToProduct(productDto, new Product());
        System.out.println(product.getSizes());
        product.setCategory(thirdLevel);

        Product savedProduct = productRepo.save(product);
        ProductDto productDto1 = ProductMapper.productToProductDto(savedProduct, new ProductDto());

        return productDto1;

    }


//    DELETE PRODUCT
    @Override
    public String deleteProduct(Long productId) throws ResourceNotFoundException {

        Product product=productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product",productId));
        productRepo.delete(product);

        return "Product deleted";
    }


//    UPDATE PRODUCT
    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws ResourceNotFoundException {
        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product",productId));
        if(productDto.getQuantity() !=0){
            product.setQuantity(productDto.getQuantity());
        }
        Product updatedProduct=productRepo.save(product);

        return ProductMapper.productToProductDto(product, new ProductDto());
    }

    @Override
    public ProductDto findProductById(Long productId) throws ResourceNotFoundException {
        Product product=productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product",productId));
        ProductDto productDto = ProductMapper.productToProductDto(product, new ProductDto());
        return productDto;
    }

    @Override
    public List<ProductDto> findProductByCategory(String category) {
        return null;
    }

    @Override
    public Page<Product> getAllProduct(String category, List<String> colors,
                                       List<String> sizes, Integer minPrice,
                                       Integer maxPrice, Integer minDiscount,
                                       String sort, String stock,
                                       Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Product> products = productRepo.filterProducts(category, minPrice, maxPrice, minDiscount, sort);

//       Logic for Sort By Color
        System.out.println("color :"+colors);
        if(!colors.isEmpty()){
            products=products.stream().filter((p)-> colors.stream().anyMatch((c)-> c.equalsIgnoreCase(p.getColor()))).collect(Collectors.toList());

        }

//        Logic For In_Stock or Not
        if(stock !=null ){
            if(stock.equalsIgnoreCase("in_stock")){
                products=products.stream().filter((p)->p.getQuantity() > 0).collect(Collectors.toList());
            }else {
                products=products.stream().filter((p)->p.getQuantity() == 0).collect(Collectors.toList());
            }
        }

        int startIndex =(int) pageable.getOffset();
        int endIndex=Math.min(startIndex + pageable.getPageSize() , products.size());
        List<Product> pageContent = products.subList(startIndex, endIndex);
        Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());

        return filteredProducts;
    }
}
