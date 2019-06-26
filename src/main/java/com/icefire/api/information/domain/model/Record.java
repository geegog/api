package com.icefire.api.information.domain.model;

import com.icefire.api.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class Record extends BaseEntity {

    private String value;

    @ManyToOne
    private User user;

}
