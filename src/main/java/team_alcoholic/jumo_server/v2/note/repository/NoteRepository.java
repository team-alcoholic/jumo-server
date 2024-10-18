package team_alcoholic.jumo_server.v2.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team_alcoholic.jumo_server.v2.note.domain.Note;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("select n from Note n join fetch n.user join fetch n.liquor join fetch n.noteImages where n.id=:id")
    Optional<Note> findDetailById(Long id);
}
