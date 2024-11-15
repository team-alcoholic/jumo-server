package team_alcoholic.jumo_server.v2.note.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team_alcoholic.jumo_server.v2.note.domain.Note;
import team_alcoholic.jumo_server.v2.note.domain.PurchaseNote;
import team_alcoholic.jumo_server.v2.note.domain.TastingNote;

import java.util.List;

public interface TastingNoteRepository extends JpaRepository<TastingNote, Long> {

    /**
     * 최신순 노트 페이지네이션 조회
     * @param cursor 마지막으로 조회한 노트의 id
     * @param pageable paging
     */
    @EntityGraph(attributePaths = {"user", "liquor", "noteAromas.aroma"})
    @Query("select tn from tasting_note_new tn where tn.id < :cursor order by tn.id desc")
    List<Note> findListByCursor(Long cursor, Pageable pageable);

    /**
     * 최신순 노트 페이지네이션 조회
     * @param pageable paging
     */
    @EntityGraph(attributePaths = {"user", "liquor", "noteAromas.aroma"})
    @Query("select tn from tasting_note_new tn order by tn.id desc")
    List<Note> findList(Pageable pageable);

    /**
     * noteId 리스트로 테이스팅 노트 리스트 조회
     * @param idList noteId 리스트
     */
    @EntityGraph(attributePaths = {"user", "liquor", "noteAromas.aroma"})
    @Query("select tn from tasting_note_new tn where tn.id in :idList order by tn.id desc")
    List<TastingNote> findListByIdList(List<Long> idList);
}
