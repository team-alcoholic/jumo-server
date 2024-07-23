package team_alcoholic.jumo_server.domain.meeting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingDto;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingListResponseDto;
import team_alcoholic.jumo_server.domain.meeting.service.MeetingService;

@RestController
@RequestMapping("meetings")
@RequiredArgsConstructor
public class MeetingController implements MeetingApi {

    private final MeetingService meetingService;

    @GetMapping("{id}")
    public MeetingDto getMeetingById(@PathVariable("id") Long id) {
        return meetingService.findMeetingById(id);
    }

    @GetMapping()
    public MeetingListResponseDto getLatestMeetingList(
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
