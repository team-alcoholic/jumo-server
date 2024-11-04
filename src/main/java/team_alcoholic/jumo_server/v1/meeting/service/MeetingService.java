package team_alcoholic.jumo_server.v1.meeting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_alcoholic.jumo_server.v1.meeting.domain.Meeting;
import team_alcoholic.jumo_server.v1.meeting.dto.MeetingResDto;
import team_alcoholic.jumo_server.v1.meeting.dto.MeetingListDto;
import team_alcoholic.jumo_server.v1.meeting.exception.MeetingNotFoundException;
import team_alcoholic.jumo_server.v1.meeting.repository.MeetingRepository;
import team_alcoholic.jumo_server.v1.meeting.dto.MeetingListResDto;

import java.time.LocalDateTime;

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
    public MeetingListResDto findLatestMeetingList(
            int limit,
            Long cursorId,
            String sort,
            LocalDateTime cursorDate,
            List<String> liquors) {

        // Liquor names conversion
        List<String> convertedLiquors = convertLiquorNames(liquors);

        Pageable pageable = PageRequest.of(0, limit + 1);

        // cursorId와 cursorDate가 없는 경우, max값으로 초기화
        Long id = (cursorId == null || cursorId == 0) ? DEFAULT_CURSOR_ID : cursorId;
        LocalDateTime date = (cursorDate == null) ? DEFAULT_MAX_DATE : cursorDate;

        List<Meeting> meetings;
        boolean eof;

        meetings = switch (sort) {
            case "created-at" ->
                    meetingRepository.findMeetingsByCreatedAtAndIdCursor(convertedLiquors, date, id, pageable);
            case "meeting-at" ->
                    meetingRepository.findMeetingsByMeetingAtAndIdCursor(convertedLiquors, date, id, pageable);
            case "meeting-at-asc" -> {
                // ascending order의 경우 어제 날짜를 동적으로 계산
                date = (cursorDate == null) ? getYesterday() : cursorDate;
                yield meetingRepository.findMeetingsByMeetingAtAndIdCursorAsc(convertedLiquors, date, id, pageable);
            }
            default -> throw new IllegalArgumentException("Invalid sort parameter: " + sort);
        };

        eof = (meetings.size() < limit + 1);

        System.out.println("미팅 크기와 eof() : " + meetings.size() + eof);
        // liquors에 들어있는 항목
        System.out.println("liquors : " + liquors);
        for (String liquor : liquors) {
            System.out.println("liquor : " + liquor);
        }

        if (!eof) {
            meetings.remove(meetings.size() - 1);
        }

        // 비어있는 경우 처리
        if (meetings.isEmpty()) {
            return new MeetingListResDto(List.of(), id, true, date);
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

    private List<String> convertLiquorNames(List<String> liquors) {
        return liquors.stream()
                .map(liquor -> {
                    return switch (liquor.toLowerCase()) {
                        case "whisky" -> "위스키";
                        case "wine" -> "와인";
                        default -> liquor; // 변환이 필요 없는 경우 그대로 반환
                    };
                })
                .toList();
    }

}