package com.icefire.api.information.application.service;

import com.icefire.api.information.application.dto.RecordDTO;
import com.icefire.api.common.infrastructure.security.*;
import com.icefire.api.information.domain.model.Record;
import com.icefire.api.information.domain.repository.RecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecordService {

    @Autowired
    RecordRepository recordRepository;

    @Autowired
    RecordAssembler recordAssembler;


    public RecordDTO encrypt(String value, byte[] publicKey) {
        AESCipher aesCipher = new AESCipher(publicKey);

        String encryptedMessage = aesCipher.getEncryptedMessage(value);

        Record record = new Record();
        record.setValue(encryptedMessage);
        record.setUser(null);

        return recordAssembler.toResource(recordRepository.save(record));
    }

    public String decrypt(String value) {
        byte[] privateKey = KeyGenerator.getPrivateKey("");
        AESCipher aesCipher = new AESCipher(privateKey);

        String decryptedMessage = aesCipher.getDecryptedMessage(value);

        return decryptedMessage;
    }

}
