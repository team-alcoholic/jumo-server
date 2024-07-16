package team_alcoholic.jumo_server.meetingtest.repository;

import team_alcoholic.jumo_server.meetingtest.domain.MeetingTest;

import java.util.List;

public interface MeetingTestRepository {
    List<MeetingTest> findLatestMeetingList();
    List<MeetingTest> findLatestMeetingListById(int id);
}
