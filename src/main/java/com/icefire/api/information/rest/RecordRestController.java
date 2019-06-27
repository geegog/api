package com.icefire.api.information.rest;

import com.icefire.api.common.infrastructure.security.KeyGenerator;
import com.icefire.api.information.application.dto.DataDTO;
import com.icefire.api.information.application.service.RecordService;
import com.icefire.api.user.application.dto.UserDTO;
import com.icefire.api.user.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/data")
public class RecordRestController {

    @Autowired
    RecordService recordService;

    @Autowired
    UserService userService;

    @PostMapping("/encrypt")
    public ResponseEntity<?> encrypt(Principal principal, @RequestBody DataDTO dataDTO) {
        String username = principal.getName();
        UserDTO userDTO = userService.getUserDTO(username);
        return new ResponseEntity<>(recordService.encrypt(dataDTO.getValue(), KeyGenerator.getPublicKey(userDTO.getPublicKey()), username), HttpStatus.OK);
    }

    @PostMapping("/decrypt")
    public ResponseEntity<?> decrypt(Principal principal, @RequestBody DataDTO dataDTO) {
        String username = principal.getName();
        UserDTO userDTO = userService.getUserDTO(username);
        return new ResponseEntity<>(recordService.decrypt(dataDTO.getValue(), KeyGenerator.getPrivateKey(username)), HttpStatus.OK);
    }

}
