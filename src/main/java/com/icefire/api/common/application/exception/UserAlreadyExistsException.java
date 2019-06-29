package com.icefire.api.common.application.exception;

import com.icefire.api.user.application.dto.UserDTO;

public class UserAlreadyExistsException extends Exception  {
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(){
        super();
    }

    public UserAlreadyExistsException(UserDTO userDTO){
        super(String.format("Username already exist! (Record id: %s)", userDTO.getUsername()));
    }
}
