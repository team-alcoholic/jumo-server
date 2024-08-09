package team_alcoholic.jumo_server.domain.tastingnote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_alcoholic.jumo_server.domain.tastingnote.domain.TastingNote;

public interface TastingNoteRepository extends JpaRepository<TastingNote, Long> {
}
