package team_alcoholic.jumo_server.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_alcoholic.jumo_server.domain.user.domain.User;

/**
 * 사용자 Repository
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * provider, providerId로 사용자 조회
     *
     * @param provider
     * @param providerId
     * @return 사용자 정보
     */
    User findByProviderAndProviderId(String provider, String providerId);
}