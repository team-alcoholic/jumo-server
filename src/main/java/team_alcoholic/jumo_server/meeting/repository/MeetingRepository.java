package team_alcoholic.jumo_server.meeting.repository;

import team_alcoholic.jumo_server.meeting.domain.Meeting;

import java.util.List;

public interface MeetingRepository {

    List<Meeting> findLatestMeetingList();
    List<Meeting> findLatestMeetingListById(Long id);
}
