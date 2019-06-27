package com.icefire.api.user.application.dto;

import com.icefire.api.common.rest.ResourceSupport;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO extends ResourceSupport {

    private Long _id;

    private String password;

    private String username;

    private LocalDateTime created;

    private LocalDateTime updated;
}
