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
    private static final LocalDateTime DEFAULT_MAX_DATE = LocalDateTime.of(9999, 12, 31, 23, 59, 59);


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
        Long id = (cursorId == null || cursorId == 0) ? DEFAULT_CURSOR_ID : cursorId;
        LocalDateTime date = (cursorDate == null) ? DEFAULT_MAX_DATE : cursorDate;

        List<Meeting> meetings;
        boolean eof;

        meetings = switch (sort) {
            case "created-at" -> meetingRepository.findMeetingsByCreatedAtAndIdCursor(date, id, pageable);
            case "meeting-at" -> meetingRepository.findMeetingsByMeetingAtAndIdCursor(date, id, pageable);
            case "meeting-at-asc" -> {
                // ascending order의 경우 어제 날짜를 동적으로 계산
                date = (cursorDate == null) ? getYesterday() : cursorDate;
                yield meetingRepository.findMeetingsByMeetingAtAndIdCursorAsc(date, id, pageable);

            }
            default -> throw new IllegalArgumentException("Invalid sort parameter: " + sort);
        };

        eof = (meetings.size() < limit + 1);

        if (!eof) {
            meetings.remove(meetings.size() - 1);
        }

        List<MeetingListDto> meetingList = meetings.stream()
                .map(MeetingListDto::new)
                .toList();
        LocalDateTime newCursorDate = getCursorDate(meetings, sort);

        return new MeetingListResDto(meetingList, meetings.get(meetings.size() - 1).getId(), eof, newCursorDate);
    }

    private LocalDateTime getCursorDate(List<Meeting> meetings, String sort) {
        if (meetings.isEmpty()) {
            return null;
        }
        Meeting lastMeeting = meetings.get(meetings.size() - 1);
        return switch (sort) {
            case "created-at" -> lastMeeting.getCreatedAt();
            case "meeting-at", "meeting-at-asc" -> lastMeeting.getMeetingAt();
            default -> null;
        };
    }

    private LocalDateTime getYesterday() {
        return LocalDateTime.now().minusDays(1);

    }

}


