package team_alcoholic.jumo_server.v2.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team_alcoholic.jumo_server.v2.note.domain.Note;
import team_alcoholic.jumo_server.v2.note.domain.NoteLike;
import team_alcoholic.jumo_server.v2.user.domain.NewUser;

public interface NoteLikeRepository extends JpaRepository<NoteLike, Long> {

    @Query("select nl from NoteLike nl where nl.note = :note and nl.user = :user")
    NoteLike findNoteLikeByNoteAndUser(Note note, NewUser user);
}
