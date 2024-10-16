package team_alcoholic.jumo_server.v2.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_alcoholic.jumo_server.v1.user.domain.User;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;

import java.util.UUID;

/**
 * 사용자 Repository
 */
public interface UserRepository extends JpaRepository<NewUser, Long> {

    /**
     * provider, providerId로 사용자 조회
     * @param provider
     * @param providerId
     */
    NewUser findByProviderAndProviderId(String provider, String providerId);

    /**
     * UUID로 사용자 조회
     * @param userUuid
     */
    NewUser findByUserUuid(UUID userUuid);
}