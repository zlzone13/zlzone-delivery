package com.sparta.zlzonedelivery.global.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
public class SoftDeletableBaseTimeEntity extends BaseTimeEntity{

    @Column(insertable = false)
    protected LocalDateTime deletedAt;

    @Column(insertable = false)
    protected UUID deletedId;

    protected void delete(LocalDateTime currentTime, UUID userId) {
        if (deletedAt == null) {
            deletedAt = currentTime;
            deletedId = userId;
        }
    }

    public boolean isDeleted() {
        return deletedAt != null;
    }

    protected void restore() {
        deletedAt = null;
        deletedId = null;
    }

}
