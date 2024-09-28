package com.pick2cart.PickToCart.service.serviceImpl;

import com.pick2cart.PickToCart.config.JwtProvider;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.exception.UserNotFoundException;
import com.pick2cart.PickToCart.repository.UserRepo;
import com.pick2cart.PickToCart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;
    private JwtProvider jwtProvider;

    @Autowired
    public UserServiceImpl(UserRepo userRepo, JwtProvider jwtProvider) {
        this.userRepo = userRepo;
        this.jwtProvider=jwtProvider;
    }

    @Override
    public User findUserById(Long id) throws ResourceNotFoundException {
        Optional<User> user=userRepo.findById(id);
        if(user.isPresent()){
            return user.get();
        }

        throw new ResourceNotFoundException("User", id);

    }

    @Override
    public User findUserProfileByJwt(String jwt) {
        String emailFromToken = jwtProvider.getEmailFromToken(jwt);
        User user = userRepo.findByEmail(emailFromToken);
        if(user!=null){
            return user;
        }

        throw new UserNotFoundException("user doesn't exist with given email id");
    }
}
