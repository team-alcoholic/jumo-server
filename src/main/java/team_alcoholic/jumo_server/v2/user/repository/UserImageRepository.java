package team_alcoholic.jumo_server.v2.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_alcoholic.jumo_server.v2.user.domain.UserImage;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {}
