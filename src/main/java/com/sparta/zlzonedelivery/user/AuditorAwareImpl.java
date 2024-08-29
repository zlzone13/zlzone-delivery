package com.sparta.zlzonedelivery.user;

import com.sparta.zlzonedelivery.global.auth.security.UserDetailsImpl;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Optional<Object> auditor = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal);

        if (auditor.get() instanceof String user) {
            if (user.equals("anonymousUser")) return Optional.empty();
        } else if (auditor.get() instanceof UserDetailsImpl user) {
            return Optional.of(user.getUsername());
        }

        return Optional.empty();
    }

}
