package com.icefire.api.common.application.exception;

import com.icefire.api.user.application.dto.UserDTO;

public class UserAlreadyExistsException extends Exception  {
    private static final long serialVersionUID = 1L;

    public UserAlreadyExistsException(){
        super();
    }

    public UserAlreadyExistsException(UserDTO userDTO){
        super("Username already exist!");
    }
}
