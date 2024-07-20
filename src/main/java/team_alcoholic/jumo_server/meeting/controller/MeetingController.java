package team_alcoholic.jumo_server.meeting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.meeting.domain.Meeting;
import team_alcoholic.jumo_server.meeting.dto.MeetingDto;
import team_alcoholic.jumo_server.meeting.dto.MeetingListDto;
import team_alcoholic.jumo_server.meeting.service.MeetingService;

import java.util.List;

@RestController
@RequestMapping("meetings")
public class MeetingController {

    private final MeetingService meetingService;

    @Autowired
    public MeetingController(MeetingService meetingService) { this.meetingService = meetingService; }

    @GetMapping("{id}")
    public MeetingDto getMeetingById(@PathVariable("id") Long id) {
        return meetingService.findMeetingById(id);
    }

    @GetMapping()
    public List<MeetingListDto> getLatestMeetingList(
        @RequestParam(required = false, defaultValue = "latest") String sort,
        @RequestParam(required = false, defaultValue = "30") int limit,
        @RequestParam(required = false, defaultValue = "0") Long cursor
    ) {
        switch (sort) {
            default:
                return meetingService.findLatestMeetingList(limit, cursor);
        }
    }
}
