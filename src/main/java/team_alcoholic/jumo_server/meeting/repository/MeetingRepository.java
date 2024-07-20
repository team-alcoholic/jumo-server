package team_alcoholic.jumo_server.meeting.repository;

import team_alcoholic.jumo_server.meeting.domain.Meeting;
import team_alcoholic.jumo_server.meeting.dto.MeetingDto;
import team_alcoholic.jumo_server.meeting.dto.MeetingListDto;

import java.util.List;

public interface MeetingRepository {
    MeetingDto findMeetingById(Long id);
    List<MeetingListDto> findLatestMeetingList(int limit);
    List<MeetingListDto> findLatestMeetingListById(int limit, Long cursor);
}
