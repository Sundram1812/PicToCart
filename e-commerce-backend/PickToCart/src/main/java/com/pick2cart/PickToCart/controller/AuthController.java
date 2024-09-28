package com.pick2cart.PickToCart.controller;

import com.pick2cart.PickToCart.config.JwtProvider;
import com.pick2cart.PickToCart.entity.User;
import com.pick2cart.PickToCart.exception.UserAlreadyExistException;
import com.pick2cart.PickToCart.exception.UserNotFoundException;
import com.pick2cart.PickToCart.repository.UserRepo;
import com.pick2cart.PickToCart.request.LoginRequest;
import com.pick2cart.PickToCart.response.AuthResponse;
import com.pick2cart.PickToCart.service.CartService;
import com.pick2cart.PickToCart.service.serviceImpl.CustomUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private UserRepo userRepo;
    private JwtProvider jwtProvider;


    private PasswordEncoder passwordEncoder;
    private CustomUserServiceImpl customUserService;
    private CartService cartService;

    @Autowired
    public AuthController(UserRepo userRepo, JwtProvider jwtProvider,
                          PasswordEncoder passwordEncoder, CustomUserServiceImpl customUserService,
                          CartService cartService) {
        this.userRepo = userRepo;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.customUserService = customUserService;
        this.cartService = cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserAlreadyExistException {
        User isExistUser= userRepo.findByEmail(user.getEmail());
        if(isExistUser != null){
            throw  new UserAlreadyExistException("user already registered with email :" + user.getEmail() +". Try with another email");
        }

        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String mobile= user.getMobile();
        String role = user.getRole();



        User createUser=User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .mobile(mobile)
                .role(role)
                .build();

        User savedUser= userRepo.save(createUser);
        cartService.createCart(savedUser);

        Authentication authentication=new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtProvider.generateToken(authentication);

        AuthResponse authResponse = AuthResponse.builder()
                .jwt(token)
                .message("User Created Successful !!")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Authentication authentication=authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token=jwtProvider.generateToken(authentication);
        AuthResponse authResponse = AuthResponse.builder()
                .jwt(token)
                .message("Signing success")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }



    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserService.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid Username...");
        }

        if(! passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid Password...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
