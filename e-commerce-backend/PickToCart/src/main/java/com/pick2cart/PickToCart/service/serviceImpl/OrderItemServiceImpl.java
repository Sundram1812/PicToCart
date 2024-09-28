package com.pick2cart.PickToCart.service.serviceImpl;

import com.pick2cart.PickToCart.dto.ProductDto;
import com.pick2cart.PickToCart.entity.OrderItem;
import com.pick2cart.PickToCart.entity.Product;
import com.pick2cart.PickToCart.exception.OrderItemException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.mapper.ProductMapper;
import com.pick2cart.PickToCart.repository.OrderItemRepo;
import com.pick2cart.PickToCart.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemRepo orderItemRepo;
    private ProductServiceImpl productService;
    private UserServiceImpl userService;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepo orderItemRepo, ProductServiceImpl productService, UserServiceImpl userService) {
        this.orderItemRepo = orderItemRepo;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public OrderItem createOrderItem(Long userId,Long productId,OrderItem orderItem) throws OrderItemException, ResourceNotFoundException {
//        String orderItemId=UUID.randomUUID().toString();
        ProductDto productDto= productService.findProductById(productId);
        Product product = ProductMapper.productDtoToProduct(productDto, new Product());
        product.setProductId(productId);

        LocalDate futureDate = LocalDate.now().plusDays(5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        String dateStr = futureDate.format(formatter);

        orderItem.setProduct(product);
        orderItem.setUserId(userId);
        orderItem.setDeliveryDate(dateStr);

        OrderItem savedOrderItem = orderItemRepo.save(orderItem);
        return savedOrderItem;
    }

    @Override
    public OrderItem updateOrderItem(Long userId, Long orderItemId, OrderItem orderItem) throws OrderItemException {

        System.out.println("userId : "+userId);
        System.out.println("orderItemId : "+orderItemId);

        Optional<OrderItem> oldOrderItem = orderItemRepo.findById(orderItemId);


        if(oldOrderItem.isEmpty()){
            throw new OrderItemException("Order Item Does Not Present with Given Id: "+orderItemId);
        }

        if(oldOrderItem.get().getUserId() != userId){
            throw new OrderItemException("You can not update other users orderItem");
        }

        oldOrderItem.get().setSize(orderItem.getSize());
        OrderItem updatedOrderItem = orderItemRepo.save(oldOrderItem.get());


        return updatedOrderItem;
    }

    @Override
    public String removeOrderItem(Long userId, Long orderItemId) throws OrderItemException {
        Optional<OrderItem> orderItem = orderItemRepo.findById(orderItemId);

        if (orderItem.isEmpty()){
            throw new OrderItemException("Order Item Not found with given Id: "+orderItemId);
        }

        if(orderItem.get().getUserId() != userId ){
            throw new OrderItemException("You can not Delete other users orderItem");
        }


        orderItemRepo.deleteById(orderItemId);
        return "Order Item deleted.";
    }

    @Override
    public OrderItem findOrderItemById(Long orderItemId) throws OrderItemException {
        Optional<OrderItem> orderItem = orderItemRepo.findById(orderItemId);
        if(orderItem.isEmpty()){
            throw new OrderItemException("Order Item doesn't exist with given Id: "+orderItemId);
        }
        return orderItem.get();
    }

    @Override
    public List<OrderItem> findAllOrderItem() throws OrderItemException {
        List<OrderItem> allOrderItem = orderItemRepo.findAll();
        return allOrderItem;
    }


}
