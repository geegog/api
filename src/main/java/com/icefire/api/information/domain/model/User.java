package com.icefire.api.information.domain.model;

import com.icefire.api.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;

    private String password;

    @Lob
    private byte[] publicKey;

}
