package team_alcoholic.jumo_server.v1.tastingnote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_alcoholic.jumo_server.v1.tastingnote.domain.AiTastingNote;

public interface AiTastingNoteRepository extends JpaRepository<AiTastingNote, Long> {
}