package com.pick2cart.PickToCart.repository;

import com.pick2cart.PickToCart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {

    public User findByEmail(String email);

}
