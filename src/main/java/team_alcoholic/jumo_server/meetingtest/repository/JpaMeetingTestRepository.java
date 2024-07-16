package team_alcoholic.jumo_server.meetingtest.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import team_alcoholic.jumo_server.meetingtest.domain.MeetingTest;

import java.util.List;

@Repository
public class JpaMeetingTestRepository implements MeetingTestRepository {

    private final EntityManager em;
    public JpaMeetingTestRepository(EntityManager em) { this.em = em; }

    @Override
    public List<MeetingTest> findLatestMeetingList() {
        return em.createQuery("select m from MeetingTest m order by m.id desc limit 30", MeetingTest.class)
            .getResultList();
    }

    @Override
    public List<MeetingTest> findLatestMeetingListById(int id) {
        return em.createQuery("select m from MeetingTest m where m.id < :id order by m.id desc limit 30", MeetingTest.class)
            .setParameter("id", id)
            .getResultList();
    }
}
