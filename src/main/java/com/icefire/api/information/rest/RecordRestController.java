package com.icefire.api.information.rest;

import com.icefire.api.common.application.exception.BadValueException;
import com.icefire.api.common.application.exception.RecordNotFoundException;
import com.icefire.api.common.application.exception.UserNotFoundException;
import com.icefire.api.common.infrastructure.security.KeyGenerator;
import com.icefire.api.information.application.dto.DataDTO;
import com.icefire.api.information.application.service.RecordService;
import com.icefire.api.user.application.dto.UserDTO;
import com.icefire.api.user.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/api/data")
public class RecordRestController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private UserService userService;

    @PostMapping("/encrypt")
    public ResponseEntity<?> encrypt(@RequestBody DataDTO dataDTO) {
        if (StringUtils.isEmpty(dataDTO.getValue())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Value cannot be empty");
        }
        String username = Objects.requireNonNull(authUser()).getUsername();
        UserDTO userDTO = userService.getUserDTO(username);
        try {
            return new ResponseEntity<>(recordService.encrypt(dataDTO.getValue(), KeyGenerator.getPublicKey(userDTO.getPublicKey()), username), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/{id}/encrypt_update")
    public ResponseEntity<?> encrypt(@RequestBody DataDTO dataDTO, @PathVariable Long id) {
        if (StringUtils.isEmpty(dataDTO.getValue())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Value cannot be empty");
        }
        String username = Objects.requireNonNull(authUser()).getUsername();
        UserDTO userDTO = userService.getUserDTO(username);
        try {
            return new ResponseEntity<>(recordService.encrypt(dataDTO.getValue(), KeyGenerator.getPublicKey(userDTO.getPublicKey()), username, id), HttpStatus.OK);
        } catch (RecordNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/{id}/decrypt")
    public ResponseEntity<?> decrypt(@RequestBody DataDTO dataDTO, @PathVariable Long id) {
        if (StringUtils.isEmpty(dataDTO.getValue())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Value cannot be empty");
        }
        String username = Objects.requireNonNull(authUser()).getUsername();
        UserDTO userDTO = userService.getUserDTO(username);
        try {
            return new ResponseEntity<>(recordService.decrypt(dataDTO.getValue(), KeyGenerator.getPrivateKey(username), id), HttpStatus.OK);
        } catch (RecordNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (BadValueException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @GetMapping("/{userId}/records")
    public ResponseEntity<?> allUserRecords(@PathVariable Long userId) {
        String username = Objects.requireNonNull(authUser()).getUsername();
        UserDTO userDTO = userService.getUserDTO(username);
        return new ResponseEntity<>(recordService.allUserRecords(userId), HttpStatus.OK);
    }

    @GetMapping("/records")
    public ResponseEntity<?> allRecords() {
        String username = Objects.requireNonNull(authUser()).getUsername();
        UserDTO userDTO = userService.getUserDTO(username);
        return new ResponseEntity<>(recordService.allRecords(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getARecord(@PathVariable Long id) {
        String username = Objects.requireNonNull(authUser()).getUsername();
        UserDTO userDTO = userService.getUserDTO(username);
        return new ResponseEntity<>(recordService.getRecord(id), HttpStatus.OK);
    }



    private User authUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return (User) principal;
        } else {
            return null;
        }
    }

}
