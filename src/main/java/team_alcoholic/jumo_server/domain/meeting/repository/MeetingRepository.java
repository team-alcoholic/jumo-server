package team_alcoholic.jumo_server.domain.meeting.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import team_alcoholic.jumo_server.domain.meeting.domain.Meeting;


import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    // 특정 ID의 Meeting 조회
    @EntityGraph(attributePaths = "images")
    Meeting findMeetingById(Long id);

    // 특정 ID보다 작은 Meeting 목록 조회
    // n+1 문제 해결을 위해 EntityGraph 사용
    @EntityGraph(attributePaths = "region")
    List<Meeting> findByIdLessThanOrderByIdDesc(Long cursor, Pageable pageable);
}
