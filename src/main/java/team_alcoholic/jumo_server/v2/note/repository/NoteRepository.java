package team_alcoholic.jumo_server.v2.note.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team_alcoholic.jumo_server.v2.note.domain.Note;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    /**
     * 노트 상세 조회
     * @param id 노트 id
     */
    @EntityGraph(attributePaths = {"user", "liquor", "noteImages"})
    @Query("select n from Note n where n.id=:id")
    Optional<Note> findDetailById(Long id);
}
