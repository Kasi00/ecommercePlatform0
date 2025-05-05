package com.example.ecommercePlatform.user;

import com.example.ecommercePlatform.user.Persistance.Role;
import com.example.ecommercePlatform.user.Persistance.User;
import com.example.ecommercePlatform.user.auth.LoginRequest;
import com.example.ecommercePlatform.user.auth.LoginResponse;
import lombok.RequiredArgsConstructor;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginService {
    @Value("${jwt.secret-key}")
    private String secretkey;

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request){
        User user = userService.getUser(request.getUsername());
        if(passwordEncoder.matches(request.getPassword(), user.getPassword())){
            return generateLoginResponse(user);

        }
        throw new RuntimeException("Invalid login attempt");
    }


    private LoginResponse generateLoginResponse(User user){

        try{
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .claim("roles",user.getRoles().stream().map(Role::getName).collect(Collectors.toSet()))
                    .issuer("Ecommerce.ge")
                    .expirationTime(new Date(new Date().getTime() + 1000 * 60 * 60 * 24)).build();

            JWSHeader header= new JWSHeader(JWSAlgorithm.HS256);
            SignedJWT signedJWT = new SignedJWT(header, claims);
            signedJWT.sign(new MACSigner(secretkey.getBytes()));

            return new LoginResponse(signedJWT.serialize());


        }catch (Exception e){
            throw new RuntimeException("failed to generateToken", e);

        }

    }




}


