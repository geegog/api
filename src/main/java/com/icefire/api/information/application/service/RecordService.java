package com.icefire.api.information.application.service;

import com.icefire.api.common.infrastructure.security.AESCipher;
import com.icefire.api.common.infrastructure.security.KeyGenerator;
import com.icefire.api.information.application.dto.DataDTO;
import com.icefire.api.information.application.dto.RecordDTO;
import com.icefire.api.information.domain.model.Record;
import com.icefire.api.information.domain.repository.RecordRepository;
import com.icefire.api.user.application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    RecordAssembler recordAssembler;

    @Autowired
    UserService userService;

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

    public DataDTO decrypt(String value, PrivateKey privateKey) {
        AESCipher aesCipher = new AESCipher(privateKey);
        DataDTO dataDTO = new DataDTO();
        dataDTO.setValue(aesCipher.getDecryptedMessage(value));
        return dataDTO;
    }

}
