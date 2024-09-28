package com.pick2cart.PickToCart.service;

import com.pick2cart.PickToCart.entity.Address;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.AddressException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.repository.AddressRepo;

import java.util.List;

public interface AddressService {
    public Address addAddress(User user, Long userId, Address address) throws AddressException;
    public List<Address> getAllAddressOfUser(Long userId) throws AddressException;
    public Address getAddressById(Long addressId) throws AddressException;
    public String deleteAddressById(User user,Long userId, Long addressId) throws AddressException, ResourceNotFoundException;
}
