package com.pick2cart.PickToCart.service;

import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;

import java.util.List;

public interface UserService {
    public User findUserById(Long id) throws ResourceNotFoundException;
    public User findUserProfileByJwt(String jwt); //or we can say findUserProfileByUserName

}
