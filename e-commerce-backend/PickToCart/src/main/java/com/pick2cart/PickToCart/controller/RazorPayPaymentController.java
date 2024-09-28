package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.entity.Order;
import com.pick2cart.PickToCart.entity.PaymentDetails;
import com.pick2cart.PickToCart.exception.OrderException;
import com.pick2cart.PickToCart.repository.OrderRepo;
import com.pick2cart.PickToCart.response.ApiResponse;
import com.pick2cart.PickToCart.response.PaymentLinkResponse;
import com.pick2cart.PickToCart.service.OrderService;
import com.pick2cart.PickToCart.service.UserService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RazorPayPaymentController {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;


    private OrderService orderService;
    private UserService userService;
    private OrderRepo orderRepo;

    @Autowired
    public RazorPayPaymentController(OrderService orderService, UserService userService, OrderRepo orderRepo) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderRepo=orderRepo;
    }


    @PostMapping("/payments/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, RazorpayException {
        Order order = orderService.findOrderById(orderId);

        try{

            RazorpayClient razorpayClient = new RazorpayClient(apiKey, apiSecret);
            JSONObject paymentLinkRequest= new JSONObject();
            paymentLinkRequest.put("amount", order.getTotalDiscountedPrice()*100);
            paymentLinkRequest.put("currency","INR");

            JSONObject customerDetails=new JSONObject();
            customerDetails.put("name", order.getUser().getFirstName() +" "+ order.getUser().getLastName());
            customerDetails.put("email", order.getUser().getEmail());

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);


            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("customer", customerDetails);

//            call back function which is use for redirect application on this given URL after successful payment
            paymentLinkRequest.put("callback_url","http://localhost:3000/payments/"+order.getId());
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment= razorpayClient.paymentLink.create(paymentLinkRequest);

            String paymentLinkId= payment.get("id");
            String paymentLinkUrl= payment.get("short_url");

            PaymentLinkResponse paymentLinkResponse = PaymentLinkResponse.builder()
                    .getPayment_link_id(paymentLinkId)
                    .payment_link_url(paymentLinkUrl)
                    .build();
            return ResponseEntity.status(HttpStatus.CREATED).body(paymentLinkResponse);


        }catch (Exception e){

            throw new RazorpayException(e.getMessage());

        }
    }


    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(name = "payment_id") String paymentId,
                                                @RequestParam(name = "order_id")Long orderId) throws OrderException, RazorpayException {

        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorpayClient= new RazorpayClient(apiKey, apiSecret);
        System.out.println("order :"+ order.getPaymentDetails() );
        try{

            Payment payment = razorpayClient.payments.fetch(paymentId);
            System.out.println(payment);
            if(payment.get("status").equals("captured")){
                PaymentDetails paymentDetails=new PaymentDetails();
                paymentDetails.setPaymentId(paymentId);
                paymentDetails.setStatus("COMPLETED");
                order.setPaymentDetails(paymentDetails);
                order.setOrderStatus("PLACED");
                Order updatedOrderDetails = orderRepo.save(order);
            }

            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Your order get placed")
                    .status(true)
                    .build();
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

        }catch (Exception e){
            throw new RazorpayException(e.getMessage());
        }
    }
}
