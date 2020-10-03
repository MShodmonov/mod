package uz.mod.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.mod.entity.User;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
    @Bean
    public AuditorAware<User> auditorProvider()
    {
        return new AuditAwareImpl();
    }
    private static class AuditAwareImpl implements AuditorAware<User>
    {
        @Override
        public Optional<User> getCurrentAuditor() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication instanceof AnonymousAuthenticationToken || authentication == null)
            {
                return Optional.empty();
            }
            else return Optional.of((User)authentication.getPrincipal());
        }
    }
}
