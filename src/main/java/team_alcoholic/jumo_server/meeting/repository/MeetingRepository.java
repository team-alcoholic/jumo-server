package team_alcoholic.jumo_server.meeting.repository;

import team_alcoholic.jumo_server.meeting.domain.Meeting;
import team_alcoholic.jumo_server.meeting.dto.MeetingDto;

import java.util.List;

public interface MeetingRepository {

    MeetingDto findMeetingById(Long id);
    List<Meeting> findLatestMeetingList();
    List<Meeting> findLatestMeetingListById(Long id);
}
