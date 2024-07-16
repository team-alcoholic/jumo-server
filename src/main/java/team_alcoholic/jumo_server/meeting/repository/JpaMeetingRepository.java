package team_alcoholic.jumo_server.meeting.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import team_alcoholic.jumo_server.meeting.domain.Meeting;

import java.util.List;

@Repository
public class JpaMeetingRepository implements MeetingRepository{

    private final EntityManager em;
    public JpaMeetingRepository(EntityManager em) { this.em = em;}

    @Override
    public List<Meeting> findLatestMeetingList() {
        return em.createQuery("select m from Meeting m order by m.id desc limit 10", Meeting.class)
            .getResultList();
    }

    @Override
    public List<Meeting> findLatestMeetingListById(Long id) {
        return em.createQuery("select m from Meeting m where m.id<:id order by m.id desc limit 10", Meeting.class)
            .setParameter("id", id)
            .getResultList();
    }
}
