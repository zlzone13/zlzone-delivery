package com.sparta.zlzonedelivery.user;

import jakarta.persistence.PrePersist;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserSignupListener {

    @PrePersist
    public void onPrePersist(User user) {
        if (user.getCreatedBy() == null) {
            log.info("초기 생성자와 수정자 주입");
            user.injectCreaterAndUpdater(user.getUsername(), user.getUsername());
        }
    }

}
