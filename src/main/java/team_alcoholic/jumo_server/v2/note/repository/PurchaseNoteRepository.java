package team_alcoholic.jumo_server.v2.note.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team_alcoholic.jumo_server.v2.note.domain.Note;
import team_alcoholic.jumo_server.v2.note.domain.PurchaseNote;

import java.util.List;

public interface PurchaseNoteRepository extends JpaRepository<PurchaseNote, Long> {

    /**
     * 최신순 노트 페이지네이션 조회
     * @param cursor 마지막으로 조회한 노트의 id
     * @param pageable paging
     */
    @EntityGraph(attributePaths = {"user", "liquor", "noteImages"})
    @Query("select pn from PurchaseNote pn where pn.id < :cursor order by pn.id desc")
    List<Note> findListByCursor(Long cursor, Pageable pageable);

    /**
     * 최신순 노트 페이지네이션 조회
     * @param pageable paging
     */
    @EntityGraph(attributePaths = {"user", "liquor", "noteImages"})
    @Query("select pn from PurchaseNote pn order by pn.id desc")
    List<Note> findList(Pageable pageable);
}
