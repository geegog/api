package com.icefire.api.information.application.service;

import com.icefire.api.information.application.dto.UserDTO;
import com.icefire.api.information.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

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

        return dto;
    }
}
