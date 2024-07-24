package team_alcoholic.jumo_server.domain.meeting.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import team_alcoholic.jumo_server.domain.meeting.domain.Meeting;
import team_alcoholic.jumo_server.domain.meeting.domain.MeetingImage;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingDto;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingListDto;

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
    public List<MeetingListDto> findLatestMeetingList(int limit) {
        return em.createQuery("select new team_alcoholic.jumo_server.domain.meeting.dto.MeetingListDto(m.id, m.uuid, m.name, m.status, m.meetingAt, m.fixAt, r.admnm, m.liquors, m.participatesMin, m.participatesMax, m.payment, m.byob, m.thumbnailImage, m.externalService) from Meeting m, Region r where m.region=r.admcd order by m.id desc limit :limit", MeetingListDto.class)
            .setParameter("limit", limit)
            .getResultList();
    }

    @Override
    public List<MeetingListDto> findLatestMeetingListById(int limit, Long cursor) {
        return em.createQuery("select new team_alcoholic.jumo_server.domain.meeting.dto.MeetingListDto(m.id, m.uuid, m.name, m.status, m.meetingAt, m.fixAt, r.admnm, m.liquors, m.participatesMin, m.participatesMax, m.payment, m.byob, m.thumbnailImage, m.externalService) from Meeting m, Region r where m.region=r.admcd and m.id<:cursor order by m.id desc limit :limit", MeetingListDto.class)
            .setParameter("limit", limit)
            .setParameter("cursor", cursor)
            .getResultList();
    }
}
