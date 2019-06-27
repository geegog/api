package com.icefire.api.user.application.service;

import com.icefire.api.common.infrastructure.security.*;
import com.icefire.api.user.application.dto.UserDTO;
import com.icefire.api.user.domain.model.User;
import com.icefire.api.user.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAssembler userAssembler;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO addUser(UserDTO userDTO) {
        User user = new User();
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUsername());
        user.setPublicKey(KeyGenerator.keyPairGenerator(userDTO.getUsername()));

        User userEntity = null;
        try {
            userEntity = userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return userAssembler.toResource(userEntity);
    }

}
