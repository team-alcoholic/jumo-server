package team_alcoholic.jumo_server.domain.meeting.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingResDto;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingListResDto;
import team_alcoholic.jumo_server.domain.meeting.service.MeetingService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("meetings")
@RequiredArgsConstructor
public class MeetingController implements MeetingApi {

    private final MeetingService meetingService;

    @GetMapping("{id}")
    public MeetingResDto getMeetingById(@PathVariable("id") Long id) {
        return meetingService.findMeetingById(id);
    }

    @GetMapping()
    public MeetingListResDto getLatestMeetingList(
            @RequestParam(required = false, defaultValue = "created-at") String sort,
            @RequestParam(required = false, defaultValue = "30") int limit,
            @RequestParam(name = "cursor-id", required = false, defaultValue = "0") Long cursorId,
            @RequestParam(name = "cursor-date", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime cursorDate,
            @RequestParam(name = "liquors", required = false, defaultValue = "whisky,wine") String[] liquors) {

        return meetingService.findLatestMeetingList(limit, cursorId, sort, cursorDate, List.of(liquors));
    }
}
