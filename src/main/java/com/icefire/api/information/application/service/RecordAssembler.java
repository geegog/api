package com.icefire.api.information.application.service;

import com.icefire.api.information.application.dto.RecordDTO;
import com.icefire.api.information.domain.model.Record;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class RecordAssembler extends ResourceAssemblerSupport<Record, RecordDTO> {

    public RecordAssembler() {
        super(RecordService.class, RecordDTO.class);
    }


    @Override
    public RecordDTO toResource(Record record) {
        if (record == null)
            return null;
        RecordDTO dto = instantiateResource(record);
        dto.set_id(record.getId());
        dto.setCreated(record.getCreated());
        dto.setUpdated(record.getUpdated());
        dto.setValue(record.getValue());

        return dto;
    }
}
