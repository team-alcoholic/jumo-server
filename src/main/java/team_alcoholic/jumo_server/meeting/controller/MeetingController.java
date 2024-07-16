package team_alcoholic.jumo_server.meeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team_alcoholic.jumo_server.meeting.domain.Meeting;
import team_alcoholic.jumo_server.meeting.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("meeting")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) { this.meetingService = meetingService; }

    @GetMapping("list/latest")
    public List<Meeting> getLatestMeetingList() {
        return meetingService.findLatestMeetingList();
    }

    @GetMapping("list/latest/{id}")
    public List<Meeting> getLatestMeetingListById(@PathVariable Long id) {
        return meetingService.findLatestMeetingListById(id);
    }
}
