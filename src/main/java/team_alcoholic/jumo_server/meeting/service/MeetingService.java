package team_alcoholic.jumo_server.meeting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.meeting.domain.Meeting;
import team_alcoholic.jumo_server.meeting.repository.MeetingRepository;

import java.util.List;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository) { this.meetingRepository = meetingRepository;}

    public List<Meeting> findLatestMeetingList() {
        return meetingRepository.findLatestMeetingList();
    }

    public List<Meeting> findLatestMeetingListById(Long id) {
        return meetingRepository.findLatestMeetingListById(id);
    }
}
