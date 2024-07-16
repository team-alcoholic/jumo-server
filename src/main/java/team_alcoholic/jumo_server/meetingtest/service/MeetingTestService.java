package team_alcoholic.jumo_server.meetingtest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team_alcoholic.jumo_server.meetingtest.domain.MeetingTest;
import team_alcoholic.jumo_server.meetingtest.repository.MeetingTestRepository;

import java.util.List;

@Service
public class MeetingTestService {

    private final MeetingTestRepository meetingTestRepository;

    @Autowired
    public MeetingTestService(MeetingTestRepository meetingTestRepository) { this.meetingTestRepository = meetingTestRepository; }

    public List<MeetingTest> findLatestMeetingList() {
        return meetingTestRepository.findLatestMeetingList();
    }

    public List<MeetingTest> findLatestMeetingListById(int id) {
        return meetingTestRepository.findLatestMeetingListById(id);
    }
}
