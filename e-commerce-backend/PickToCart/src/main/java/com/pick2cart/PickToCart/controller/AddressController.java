package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.entity.Address;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.AddressException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.service.AddressService;
import com.pick2cart.PickToCart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    private AddressService addressService;
    private UserService userService;

    @Autowired
    public AddressController(AddressService addressService, UserService userService) {
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Address> createNewAddress(@RequestParam Long userId, @RequestBody Address address, @RequestHeader("Authorization") String jwt) throws AddressException {
        User user=userService.findUserProfileByJwt(jwt);
        Address createdAddress = addressService.addAddress(user, userId, address);
        return ResponseEntity.status(HttpStatus.OK).body(createdAddress);

    }

    @GetMapping
    public ResponseEntity<Address> getAddressById(@RequestParam Long addressId) throws AddressException {
        Address addressById = addressService.getAddressById(addressId);
        return ResponseEntity.status(HttpStatus.OK).body(addressById);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAddressById(@RequestParam Long userId, @RequestParam Long addressId, @RequestHeader("Authorization") String jwt ) throws AddressException, ResourceNotFoundException {
        User user=userService.findUserProfileByJwt(jwt);
        String message = addressService.deleteAddressById(user, userId, addressId);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
