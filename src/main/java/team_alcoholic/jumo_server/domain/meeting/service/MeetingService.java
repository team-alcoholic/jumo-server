package team_alcoholic.jumo_server.domain.meeting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_alcoholic.jumo_server.domain.meeting.domain.Meeting;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingResDto;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingListDto;
import team_alcoholic.jumo_server.domain.meeting.exception.MeetingNotFoundException;
import team_alcoholic.jumo_server.domain.meeting.repository.MeetingRepository;
import team_alcoholic.jumo_server.domain.meeting.dto.MeetingListResDto;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MeetingService {

    private static final Long DEFAULT_CURSOR_ID = Long.MAX_VALUE;
    private static final LocalDateTime DEFAULT_CURSOR_DATE = LocalDateTime.of(9999, 12, 31, 23, 59, 59);


    private final MeetingRepository meetingRepository;

    public MeetingResDto findMeetingById(Long id) {
        Meeting meeting = meetingRepository.findMeetingById(id);
        if (meeting == null) {
            throw new MeetingNotFoundException(id);
        }
        return new MeetingResDto(meeting);
    }

    @Transactional(readOnly = true)
    public MeetingListResDto findLatestMeetingList(int limit, Long cursorId, String sort, LocalDateTime cursorDate) {

        Pageable pageable = PageRequest.of(0, limit + 1);

        // cursorId와 cursorDate가 없는 경우, max값으로 초기화
        Long id = (cursorId == 0) ? DEFAULT_CURSOR_ID : cursorId;
        LocalDateTime date = (cursorDate == null) ? DEFAULT_CURSOR_DATE : cursorDate;

        List<Meeting> meetings = sort.equals("meeting-at") ?
                meetingRepository.findMeetingsByMeetingAtAndIdCursor(date, id, pageable) :
                meetingRepository.findMeetingsByCreatedAtAndIdCursor(date, id, pageable);
        boolean eof = (meetings.size() < limit + 1);

        if (!eof) {
            meetings.remove(meetings.size() - 1);
        }

        List<MeetingListDto> meetingList = meetings.stream().map(MeetingListDto::new).toList();
        LocalDateTime newCursorDate = sort.equals("meeting-at") ? meetings.get(meetings.size() - 1).getMeetingAt() : meetings.get(meetings.size() - 1).getCreatedAt();
        return new MeetingListResDto(meetingList, meetings.get(meetings.size() - 1).getId(), eof, newCursorDate);
    }

}


