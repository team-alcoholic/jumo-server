package team_alcoholic.jumo_server.meeting.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import team_alcoholic.jumo_server.meeting.domain.Meeting;
import team_alcoholic.jumo_server.meeting.domain.MeetingImage;
import team_alcoholic.jumo_server.meeting.dto.MeetingDto;
import team_alcoholic.jumo_server.meeting.dto.MeetingListDto;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class JpaMeetingRepository implements MeetingRepository{

    private final EntityManager em;
    public JpaMeetingRepository(EntityManager em) { this.em = em;}

    @Override
    public MeetingDto findMeetingById(Long id) {
        List<MeetingImage> imageList = em.createQuery("select image from MeetingImage image where image.meeting=:id", MeetingImage.class)
            .setParameter("id", id)
            .getResultList();
        List<String> images = imageList.stream().map(MeetingImage::getUrl).collect(Collectors.toList());
        Meeting meeting = em.createQuery("select meeting from Meeting meeting where meeting.id=:id", Meeting.class)
            .setParameter("id", id)
            .getSingleResult();
        return new MeetingDto(meeting, images);
    }

    @Override
    public List<MeetingListDto> findLatestMeetingList() {
        return em.createQuery("select m from Meeting m order by m.id desc limit 30", MeetingListDto.class)
            .getResultList();
    }

    @Override
    public List<MeetingListDto> findLatestMeetingListById(Long id) {
        return em.createQuery("select m from Meeting m where m.id<:id order by m.id desc limit 30", MeetingListDto.class)
            .setParameter("id", id)
            .getResultList();
    }
}
