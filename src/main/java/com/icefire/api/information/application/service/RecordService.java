package com.icefire.api.information.application.service;

import com.icefire.api.common.infrastructure.security.AESCipher;
import com.icefire.api.information.application.dto.DataDTO;
import com.icefire.api.information.application.dto.RecordDTO;
import com.icefire.api.information.domain.model.Record;
import com.icefire.api.information.domain.repository.RecordRepository;
import com.icefire.api.information.rest.RecordRestController;
import com.icefire.api.user.application.service.UserService;
import com.icefire.api.user.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    RecordAssembler recordAssembler;

    @Autowired
    UserService userService;

    public Resources<RecordDTO> allRecords() {
        final Resources<RecordDTO> resources = new Resources<>(recordAssembler.toResources(recordRepository.findAll()));
        resources.add(linkTo(methodOn(RecordRestController.class)
                .allRecords()).withRel("records").withSelfRel().withType(HttpMethod.GET.toString()));
        return resources;
    }

    public Resources<RecordDTO> allUserRecords(Long id) {
        User user = userService.getUser(id);
        final Resources<RecordDTO> resources = new Resources<>(recordAssembler.toResources(recordRepository.findAllByUser(user)));
        resources.add(linkTo(methodOn(RecordRestController.class)
                .allUserRecords(user.getId())).withRel("records").withSelfRel().withType(HttpMethod.GET.toString()));
        return resources;
    }

    public RecordDTO getRecord(Long id) {
        User user = userService.getUser(id);
        return recordAssembler.toResource(recordRepository.findById(id).orElse(null));
    }

    public RecordDTO encrypt(String value, PublicKey publicKey, String username) {
        AESCipher aesCipher = new AESCipher(publicKey);

        String encryptedMessage = aesCipher.getEncryptedMessage(value);

        Record record = new Record();
        record.setValue(encryptedMessage);
        record.setUser(userService.getUser(username));

        Record recordEntity = null;
        try {
            recordEntity = recordRepository.save(record);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recordAssembler.toResource(recordEntity);
    }

    public RecordDTO encrypt(String value, PublicKey publicKey, String username, Long id) {
        AESCipher aesCipher = new AESCipher(publicKey);

        String encryptedMessage = aesCipher.getEncryptedMessage(value);

        Record record = recordRepository.findById(id).orElse(null);
        if (record == null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        record.setValue(encryptedMessage);

        Record recordEntity = null;
        try {
            recordEntity = recordRepository.save(record);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recordAssembler.toResource(recordEntity);
    }

    public RecordDTO decrypt(String value, PrivateKey privateKey, Long id) {
        AESCipher aesCipher = new AESCipher(privateKey);

        RecordDTO record = recordAssembler.toResource(recordRepository.findById(id).orElse(null));

        if (record == null) {
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        record.setValue(aesCipher.getDecryptedMessage(value));
        return record;
    }

}
