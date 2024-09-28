package com.pick2cart.PickToCart.service.serviceImpl;

import com.pick2cart.PickToCart.entity.Address;
import com.pick2cart.PickToCart.entity.Cart;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.AddressException;
import com.pick2cart.PickToCart.exception.ResourceNotFoundException;
import com.pick2cart.PickToCart.repository.AddressRepo;
import com.pick2cart.PickToCart.repository.CartRepo;
import com.pick2cart.PickToCart.repository.UserRepo;
import com.pick2cart.PickToCart.service.AddressService;
import com.pick2cart.PickToCart.service.CartService;
import com.pick2cart.PickToCart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    private AddressRepo addressRepo;
    private UserRepo userRepo;
    private CartService cartService;
    private CartRepo cartRepo;

    @Autowired
    public AddressServiceImpl(AddressRepo addressRepo, UserRepo userRepo,CartService cartService,CartRepo cartRepo) {
        this.addressRepo = addressRepo;
        this.userRepo=userRepo;
        this.cartService=cartService;
        this.cartRepo=cartRepo;
    }

    @Override
    public Address addAddress(User user, Long userId, Address address) throws AddressException {
        if(userId != user.getUserId()){
            throw new AddressException("You can't add address into other users");
        }
        address.setUser(user);
        Address savedAddress = addressRepo.save(address);
        return savedAddress;
    }

    @Override
    public List<Address> getAllAddressOfUser(Long userId) throws AddressException {
        return null;
    }

    @Override
    public Address getAddressById( Long addressId) throws AddressException {
        Optional<Address> address = addressRepo.findById(addressId);
        if(address.isEmpty()){
            throw new AddressException("Address Not found with Id: "+addressId);
        }


        return address.get();
    }

    @Override
    public String deleteAddressById(User user,Long userId, Long addressId) throws AddressException, ResourceNotFoundException {
        Address addressById = getAddressById(addressId);

        if(user.getUserId() != userId){
            throw new AddressException("You can not delete others Address");
        }

        addressRepo.deleteById(addressId);
        return "Address deleted";
    }
}
