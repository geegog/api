package com.icefire.api.user.application.service;

import com.icefire.api.user.application.dto.UserDTO;
import com.icefire.api.user.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserAssembler extends ResourceAssemblerSupport<User, UserDTO> {

    @Autowired
    private UserAssembler userAssembler;

    public UserAssembler() {
        super(UserService.class, UserDTO.class);
    }


    @Override
    public UserDTO toResource(User user) {
        if (user == null)
            return null;
        UserDTO dto = instantiateResource(user);
        dto.set_id(user.getId());
        dto.setCreated(user.getCreated());
        dto.setPassword(user.getPassword());
        dto.setUsername(user.getUsername());
        dto.setUpdated(user.getUpdated());
        dto.setPublicKey(Base64.getEncoder().encodeToString(user.getPublicKey()));

        return dto;
    }
}
