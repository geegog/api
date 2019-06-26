package com.icefire.api.information.application.service;

import com.icefire.api.information.application.dto.UserDTO;
import com.icefire.api.information.application.security.KeyGenerator;
import com.icefire.api.information.domain.model.User;
import com.icefire.api.information.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAssembler userAssembler;

    public UserDTO addUser(UserDTO userDTO) {
        User user = new User();
        user.setPassword(userDTO.getPassword());
        user.setUsername(userDTO.getUsername());
        user.setPublicKey(KeyGenerator.keyPairGenerator(userDTO.getUsername()));

        return userAssembler.toResource(userRepository.save(user));
    }

}
