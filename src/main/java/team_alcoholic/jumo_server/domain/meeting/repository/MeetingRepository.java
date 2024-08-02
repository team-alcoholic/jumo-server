package team_alcoholic.jumo_server.domain.meeting.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team_alcoholic.jumo_server.domain.meeting.domain.Meeting;


import java.time.LocalDateTime;
import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    // 특정 ID의 Meeting 조회
    @EntityGraph(attributePaths = "images")
    Meeting findMeetingById(Long id);

    // 특정 ID보다 작은 Meeting 목록 조회
    // n+1 문제 해결을 위해 EntityGraph 사용
    @EntityGraph(attributePaths = "region")
    List<Meeting> findMeetingsByIdLessThanOrderByIdDesc(Long cursor, Pageable pageable);

    // meetingAt 기준으로 최신 Meeting 목록 조회, 같은 meetingAt 경우 id로 정렬
    @EntityGraph(attributePaths = "region")
    @Query("SELECT m FROM Meeting m WHERE (m.meetingAt < :meetingAt OR (m.meetingAt = :meetingAt AND m.id < :id)) ORDER BY m.meetingAt DESC, m.id DESC")
    List<Meeting> findMeetingsByMeetingAtAndIdCursor(
            @Param("meetingAt") LocalDateTime meetingAt,
            @Param("id") Long id,
            Pageable pageable
    );

    // createdAt 기준으로 최신 Meeting 목록 조회, 같은 createdAt 경우 id로 정렬
    @EntityGraph(attributePaths = "region")
    @Query("SELECT m FROM Meeting m WHERE (m.createdAt < :createdAt OR (m.createdAt = :createdAt AND m.id < :id)) ORDER BY m.createdAt DESC, m.id DESC")
    List<Meeting> findMeetingsByCreatedAtAndIdCursor(
            @Param("createdAt") LocalDateTime createdAt,
            @Param("id") Long id,
            Pageable pageable
    );
}
