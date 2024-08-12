package team_alcoholic.jumo_server.domain.tastingnote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_alcoholic.jumo_server.domain.tastingnote.domain.AiTastingNote;

public interface AiTastingNoteRepository extends JpaRepository<AiTastingNote, Long> {
}