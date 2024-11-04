package team_alcoholic.jumo_server.v2.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;
import team_alcoholic.jumo_server.v2.user.domain.UserImage;

import java.util.List;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    @Query("select ui from UserImage ui where ui.user = :user")
    List<UserImage> findByUser(NewUser user);
}
