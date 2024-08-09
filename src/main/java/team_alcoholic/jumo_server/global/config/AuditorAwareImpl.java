package team_alcoholic.jumo_server.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

@Configuration
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // 임시로 사용자 이름을 "temporaryUser"로 설정
        return Optional.of("temporaryUser");
    }
}