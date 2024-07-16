package team_alcoholic.jumo_server.meetingtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.meetingtest.domain.MeetingTest;
import team_alcoholic.jumo_server.meetingtest.service.MeetingTestService;

import java.util.List;

@RestController
@RequestMapping("/meetingTest")
public class MeetingTestController {

    private final MeetingTestService meetingTestService;

    @Autowired
    public MeetingTestController(MeetingTestService meetingTestService) { this.meetingTestService = meetingTestService; }

    @GetMapping("list/latest")
    public List<MeetingTest> getLatestMeetingTests() {
        return meetingTestService.findLatestMeetingList();
    }

    @GetMapping("list/latest/{id}")
    public List<MeetingTest> getNextMeetingList(@PathVariable int id) {
        return meetingTestService.findLatestMeetingListById(id);
    }
}
